package com.pig4cloud.pig.search.controller;

import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.security.annotation.Inner;
import com.pig4cloud.pig.search.dto.SearchDto;
import com.pig4cloud.pig.search.dto.SearchResult;
import com.pig4cloud.pig.search.service.ElasticsearchService;
import com.pig4cloud.pig.search.util.ElasticsearchUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Elasticsearch搜索服务
 * @author hanguilin
 */
@Inner(value = false)
@RestController
@RequiredArgsConstructor
@Api(value = "全文检索")
public class ElasticsearchController {

	private final ElasticsearchService elasticsearchService;

	/**
	 * 创建索引库
	 *
	 * @param index 索引名
	 * @return R<String>
	 * @throws IOException
	 */
	@ApiOperation(value = "创建索引", notes = "创建索引")
	@GetMapping("/create/{index}")
	public R<String> create(@PathVariable("index") String index) throws IOException{
		boolean flag = ElasticsearchUtil.createIndex(index);
		return flag ? R.ok() : R.failed("创建失败");
	}

	/**
	 * 根据id查询数据
	 *
	 * @param id elasticsearch生成的id
	 * @return R<Map<String, Object>>
	 * @throws IOException
	 */
	@ApiOperation(value = "根据id查询数据", notes = "根据id查询数据")
	@GetMapping("/get")
	public R<Map<String, Object>> getData(@RequestParam("id") String id) throws IOException{
		Map<String, Object> result = ElasticsearchUtil.searchDataByElasticsearchId("order", id, "name,age,desc");
		return R.ok(result);
	}

	/**
	 * 查询列表数据
	 *
	 * @param searchDto 查询实体
	 * @return R<SearchResult<Map<String, Object>>>
	 * @throws IOException
	 */
	@ApiOperation(value = "查询列表数据", notes = "查询列表数据")
	@PostMapping("/list")
	public R<SearchResult<Map<String, Object>>> getList(@RequestBody SearchDto searchDto) throws IOException{
		return R.ok(elasticsearchService.search(searchDto));
	}

	/**
	 * 插入数据
	 *
	 * @param index 索引
	 * @param objects 数据
	 * @return R<String>
	 * @throws IOException
	 */
	@ApiOperation(value = "插入数据", notes = "插入数据")
	@PostMapping("/add/{index}")
	public R<Boolean> addData(@PathVariable("index") String index, @RequestBody List<Object> objects) {
		return R.ok(ElasticsearchUtil.bulkPost(index, objects));
	}
}
