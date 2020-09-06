package com.pig4cloud.pig.goods.feign.fallback;

import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.goods.entity.TbItem;
import com.pig4cloud.pig.goods.feign.RemoteItemService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


/**
 * 商品feign接口回调类
 * @author hanguilin
 */
@Slf4j
@Component
public class RemoteItemServiceFallbackImpl implements RemoteItemService {

	@Setter
	private Throwable cause;

	public void setCause(Throwable cause) {
		this.cause = cause;
	}

	@Override
	public R<TbItem> getById(Long id) {
		log.error("feign 查询商品失败", cause);
		return null;
	}
}
