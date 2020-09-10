package com.pig4cloud.pig.search.service;

import com.pig4cloud.pig.search.dto.SearchDto;
import com.pig4cloud.pig.search.dto.SearchResult;

import java.io.IOException;
import java.util.Map;

/**
 * 搜索业务类
 *
 * @author hanguilin
 */
public interface ElasticsearchService {

	/**
	 * 搜索
	 *
	 * @param searchDto 过滤
	 * @return SearchResult<Map<String, Object>>
	 */
	SearchResult<Map<String, Object>> search(SearchDto searchDto) throws IOException;
}
