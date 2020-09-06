package com.pig4cloud.pig.search.feign;

import com.pig4cloud.pig.common.core.constant.ServiceNameConstants;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.search.dto.SearchDto;
import com.pig4cloud.pig.search.dto.SearchResult;
import com.pig4cloud.pig.search.feign.factory.RemoteSearchFallbackFactory;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 搜索feign接口
 * @author hanguilin
 */
@FeignClient(value = ServiceNameConstants.SEARCH_SERVICE, contextId = "remoteSearchService", fallbackFactory = RemoteSearchFallbackFactory.class)
public interface RemoteSearchService {

	/**
	 * 插入数据
	 *
	 * @param index 索引库
	 * @param objects 数据
	 * @return R<String>
	 * @throws IOException
	 */
	@PostMapping("/add/{index}")
	R<Boolean> addData(@PathVariable("index") String index, @RequestBody List<Object> objects) throws IOException;



	/**
	 * 查询列表数据
	 *
	 * @param searchDto 查询实体
	 * @return R<List<Map<String, Object>>>
	 * @throws IOException
	 */
	@ApiOperation(value = "查询列表数据", notes = "查询列表数据")
	@PostMapping("/list")
	R<SearchResult<Map<String, Object>>> getList(@RequestBody SearchDto searchDto) throws IOException;
}
