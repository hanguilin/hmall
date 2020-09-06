package com.pig4cloud.pig.goods.feign;

import com.pig4cloud.pig.common.core.constant.ServiceNameConstants;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.goods.entity.TbItem;
import com.pig4cloud.pig.goods.feign.factory.RemoteItemFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 商品feign接口
 * @author hanguilin
 */
@FeignClient(value = ServiceNameConstants.GOODS_SERVICE, contextId = "remoteItemService", fallbackFactory = RemoteItemFallbackFactory.class)
public interface RemoteItemService {

	/**
	 * 通过id查询商品表
	 *
	 * @param id id
	 * @return R
	 */
	@GetMapping("/item/{id}")
	R<TbItem> getById(@PathVariable("id") Long id);

}
