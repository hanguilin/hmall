package com.pig4cloud.pig.search.util;


import com.alibaba.fastjson.JSON;
import com.pig4cloud.pig.common.core.util.IdUtil;
import com.pig4cloud.pig.common.core.util.SpringContextHolder;
import com.pig4cloud.pig.search.dto.SearchResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.GeoDistanceQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Elasticsearch工具类
 * @author hanguilin
 */
@Slf4j
public class ElasticsearchUtil {

    /**
     * 最好不要自定义id 会影响插入速度。
	 */
	private static RestHighLevelClient restHighLevelClient = SpringContextHolder.getApplicationContext().getBean(RestHighLevelClient.class);

	/**
	 * 创建索引
	 *
	 * @param index
	 * @return
	 */
	public static boolean createIndex(String index) throws IOException {
		if (isIndexExist(index)) {
			log.error("Index is  exits!");
			return false;
		}
		//1.创建索引请求
		CreateIndexRequest request = new CreateIndexRequest(index);
		//2.执行客户端请求
		org.elasticsearch.client.indices.CreateIndexResponse response = restHighLevelClient.indices()
			.create(request, RequestOptions.DEFAULT);
		return response.isAcknowledged();
	}

	/**
	 * 判断索引是否存在
	 *
	 * @param index
	 * @return
	 */
	public static boolean isIndexExist(String index) throws IOException {
		GetIndexRequest request = new GetIndexRequest(index);
		boolean exists = restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT);
		return exists;
	}

	/**
	 * 删除索引
	 *
	 * @param index
	 * @return
	 */
	public static boolean deleteIndex(String index) throws IOException {
		if (!isIndexExist(index)) {
			log.error("Index is not exits!");
			return false;
		}
		DeleteIndexRequest request = new DeleteIndexRequest(index);
		AcknowledgedResponse delete = restHighLevelClient.indices()
			.delete(request, RequestOptions.DEFAULT);
		return delete.isAcknowledged();
	}

	/**
	 * 数据添加，自定义id
	 *
	 * @param object 要增加的数据
	 * @param index  索引，类似数据库
	 * @param id     数据ID,为null时es随机生成
	 * @return
	 */
	public static String addData(Object object, String index, String id) throws IOException {
		//创建请求
		IndexRequest request = new IndexRequest(index);
		//规则 put /test_index/_doc/1
		request.id(id);
		request.timeout(TimeValue.timeValueSeconds(1));
		//将数据放入请求 json
		IndexRequest source = request.source(JSON.toJSONString(object), XContentType.JSON);
		//客户端发送请求
		IndexResponse response = restHighLevelClient.index(request, RequestOptions.DEFAULT);
		return response.getId();
	}

	/**
	 * 数据添加 随机id
	 *
	 * @param object 要增加的数据
	 * @param index  索引，类似数据库
	 * @return
	 */
	public static String addData(Object object, String index) throws IOException {
		return addData(object, index, IdUtil.uuid().toUpperCase());
	}

	/**
	 * 通过ID删除数据
	 *
	 * @param index 索引，类似数据库
	 * @param id    数据ID
	 * @return
	 */
	public static void deleteDataById(String index, String id) throws IOException {
		DeleteRequest request = new DeleteRequest(index, id);
		restHighLevelClient.delete(request, RequestOptions.DEFAULT);
	}

	/**
	 * 通过ID 更新数据
	 *
	 * @param object 要更新数据
	 * @param index  索引，类似数据库
	 * @param id     数据ID
	 * @return
	 */
	public static void updateDataById(Object object, String index, String id) throws IOException {
		UpdateRequest update = new UpdateRequest(index, id);
		update.timeout("1s");
		update.doc(JSON.toJSONString(object), XContentType.JSON);
		restHighLevelClient.update(update, RequestOptions.DEFAULT);
	}

	/**
	 * 通过ID获取数据
	 *
	 * @param index  索引，类似数据库
	 * @param id     数据ID
	 * @param fields 需要显示的字段，逗号分隔（缺省为全部字段）
	 * @return
	 */
	public static Map<String, Object> searchDataByElasticsearchId(String index, String id, String fields) throws IOException {
		GetRequest request = new GetRequest(index, id);
		if (StringUtils.isNotEmpty(fields)) {
			//只查询特定字段。如果需要查询所有字段则不设置该项。
			request.fetchSourceContext(new FetchSourceContext(true, fields.split(","), Strings.EMPTY_ARRAY));
		}
		GetResponse response = restHighLevelClient.get(request, RequestOptions.DEFAULT);
		return response.getSource();
	}

	/**
	 * 通过ID判断文档是否存在
	 *
	 * @param index 索引，类似数据库
	 * @param id    数据ID
	 * @return
	 */
	public static boolean existsById(String index, String id) throws IOException {
		GetRequest request = new GetRequest(index, id);
		//不获取返回的_source的上下文
		request.fetchSourceContext(new FetchSourceContext(false));
		request.storedFields("_none_");
		return restHighLevelClient.exists(request, RequestOptions.DEFAULT);
	}

	/**
	 * 批量插入false成功
	 *
	 * @param index   索引，类似数据库
	 * @param objects 数据
	 * @return
	 */
	public static boolean bulkPost(String index, List<?> objects) {
		BulkRequest bulkRequest = new BulkRequest();
		BulkResponse response = null;
		//最大数量不得超过20万
		for (Object object : objects) {
			IndexRequest request = new IndexRequest(index);
			request.source(JSON.toJSONString(object), XContentType.JSON);
			bulkRequest.add(request);
		}
		try {
			response = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response.hasFailures();
	}

	/**
	 * 根据经纬度查询范围查找location 经纬度字段，distance 距离中心范围KM，lat  lon 圆心经纬度
	 *
	 * @param index
	 * @param longitude
	 * @param latitude
	 * @param distance
	 * @return
	 */
	public static SearchResponse geoDistanceQuery(String index, Float longitude, Float latitude, String distance) throws IOException {

		if (longitude == null || latitude == null) {
			return null;
		}
		//拼接条件
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		// 以某点为中心，搜索指定范围
		GeoDistanceQueryBuilder distanceQueryBuilder = new GeoDistanceQueryBuilder("location");
		distanceQueryBuilder.point(latitude, longitude);
		//查询单位：km
		distanceQueryBuilder.distance(distance, DistanceUnit.KILOMETERS);
		boolQueryBuilder.filter(distanceQueryBuilder);

		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(boolQueryBuilder);

		SearchRequest searchRequest = new SearchRequest(index);
		searchRequest.source(searchSourceBuilder);

		SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		return searchResponse;
	}

	/**
	 * 获取低水平客户端
	 *
	 * @return
	 */
	public static RestClient getLowLevelClient() {
		return restHighLevelClient.getLowLevelClient();
	}

	/**
	 * 高亮结果集 特殊处理
	 * map转对象 JSONObject.parseObject(JSONObject.toJSONString(map), Content.class)
	 *
	 * @param searchResponse
	 * @param highlightField
	 */
	private static List<Map<String, Object>> setSearchResponse(SearchResponse searchResponse, String highlightField) {
		//解析结果
		ArrayList<Map<String, Object>> list = new ArrayList<>();
		for (SearchHit hit : searchResponse.getHits().getHits()) {
			//原来的结果
			Map<String, Object> sourceAsMap = hit.getSourceAsMap();
			// 高亮字段不为空
			if(StringUtils.isNotBlank(highlightField)){
				Map<String, HighlightField> high = hit.getHighlightFields();
				HighlightField title = high.get(highlightField);
				//解析高亮字段,将原来的字段换为高亮字段
				if (title != null) {
					Text[] texts = title.fragments();
					String nTitle = "";
					for (Text text : texts) {
						nTitle += text;
					}
					//替换
					sourceAsMap.put(highlightField, nTitle);
				}
			}
			list.add(sourceAsMap);
		}
		return list;
	}

	/**
	 * 查询并分页
	 *
	 * @param index          索引名称
	 * @param builder          查询条件
	 * @param size           文档大小限制
	 * @param page			 当前页,1开始
	 * @param fields         需要显示的字段，逗号分隔（缺省为全部字段）
	 * @param sortField      排序字段
	 * @param highlightField 高亮字段
	 * @return SearchResult<Map<String, Object>>
	 */
	public static SearchResult<Map<String, Object>> searchListData(String index,
											  SearchSourceBuilder builder,
											  Integer size,
											  Integer page,
											  String fields,
											  String sortField,
											  String highlightField) throws IOException {
		SearchResult result = new SearchResult();
		SearchRequest request = new SearchRequest(index);
		if (StringUtils.isNotEmpty(fields)) {
			//只查询特定字段。如果需要查询所有字段则不设置该项。
			builder.fetchSource(new FetchSourceContext(true, fields.split(","), Strings.EMPTY_ARRAY));
		}
		Integer pageFrom = page <= 1 ? 1 : page;
		Integer from = (pageFrom - 1) * size;
		//设置确定结果要从哪个索引开始搜索的from选项，默认为0
		builder.from(from);
		builder.size(size);
		if (StringUtils.isNotEmpty(sortField)) {
			//排序字段，注意如果proposal_no是text类型会默认带有keyword性质，需要拼接.keyword
			builder.sort(sortField + ".keyword", SortOrder.ASC);
		}
		if(StringUtils.isNotBlank(highlightField)){
			//高亮
			HighlightBuilder highlight = new HighlightBuilder();
			highlight.field(highlightField);
			//关闭多个高亮
			highlight.requireFieldMatch(false);
			highlight.preTags("<span style='color:red'>");
			highlight.postTags("</span>");
			builder.highlighter(highlight);
		}
		request.source(builder);
		SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
		System.out.println(JSON.toJSONString(response));
		if (response.status().getStatus() == HttpStatus.OK.value()) {
			// 解析对象
			List<Map<String, Object>> maps = setSearchResponse(response, highlightField);
			result.setRecords(maps);
			result.setTotal(response.getHits().getTotalHits().value);
			return result;
		}
		return result;
	}
}
