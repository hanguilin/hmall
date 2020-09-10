package com.pig4cloud.pig.search.service.impl;

import com.pig4cloud.pig.search.dto.SearchDto;
import com.pig4cloud.pig.search.dto.SearchResult;
import com.pig4cloud.pig.search.service.ElasticsearchService;
import com.pig4cloud.pig.search.util.ElasticsearchUtil;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchPhraseQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

/**
 * 搜索业务类
 *
 * @author hanguilin
 */
@Service
public class ElasticsearchServiceImpl implements ElasticsearchService {

	/**
	 * 搜索
	 *
	 * @param searchDto 过滤
	 * @return SearchResult<Map < String, Object>>
	 */
	@Override
	public SearchResult<Map<String, Object>> search(SearchDto searchDto) throws IOException {
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
		Map<String, Object> filter = searchDto.getFilter();
		if (filter != null && !filter.isEmpty()) {
			filter.entrySet().stream().forEach(entry -> {
				MatchPhraseQueryBuilder matchPhraseQueryBuilder = QueryBuilders.matchPhraseQuery(entry.getKey(), entry.getValue());
				boolQueryBuilder.must(matchPhraseQueryBuilder);
			});
			searchSourceBuilder.query(boolQueryBuilder);
		}
		SearchResult data = ElasticsearchUtil.searchListData(searchDto.getIndex(), searchSourceBuilder, searchDto.getSize(), searchDto.getPage(), searchDto.getFields(), searchDto.getSortField(), searchDto.getHighlightField());
		return data;
	}
}
