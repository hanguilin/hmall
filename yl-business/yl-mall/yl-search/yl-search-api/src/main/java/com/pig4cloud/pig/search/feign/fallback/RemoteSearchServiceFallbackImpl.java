package com.pig4cloud.pig.search.feign.fallback;

import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.search.dto.SearchDto;
import com.pig4cloud.pig.search.dto.SearchResult;
import com.pig4cloud.pig.search.feign.RemoteSearchService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;


/**
 * 商品feign接口回调类
 * @author hanguilin
 */
@Slf4j
@Component
public class RemoteSearchServiceFallbackImpl implements RemoteSearchService {

	@Setter
	private Throwable cause;

	public void setCause(Throwable cause) {
		this.cause = cause;
	}


	@Override
	public R<Boolean> addData(String index, List<Object> objects) throws IOException {
		log.error("elasticsearch feign插入数据出错", cause);
		return null;
	}

	@Override
	public R<SearchResult<Map<String, Object>>> getList(SearchDto searchDto) throws IOException {
		log.error("elasticsearch feign查询数据出错", cause);
		return null;
	}
}
