package com.pig4cloud.pig.goods.feign.factory;

import com.pig4cloud.pig.goods.feign.RemoteItemService;
import com.pig4cloud.pig.goods.feign.fallback.RemoteItemServiceFallbackImpl;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;


/**
 * 商品feign接口回调工厂
 * @author hanguilin
 */
@Component
public class RemoteItemFallbackFactory implements FallbackFactory<RemoteItemService> {

	@Override
	public RemoteItemService create(Throwable throwable) {
		RemoteItemServiceFallbackImpl remoteItemServiceFallback = new RemoteItemServiceFallbackImpl();
		remoteItemServiceFallback.setCause(throwable);
		return remoteItemServiceFallback;
	}
}
