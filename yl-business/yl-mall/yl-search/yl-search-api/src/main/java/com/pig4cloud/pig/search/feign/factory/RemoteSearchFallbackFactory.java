package com.pig4cloud.pig.search.feign.factory;

import com.pig4cloud.pig.search.feign.RemoteSearchService;
import com.pig4cloud.pig.search.feign.fallback.RemoteSearchServiceFallbackImpl;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;


/**
 * 商品feign接口回调工厂
 * @author hanguilin
 */
@Component
public class RemoteSearchFallbackFactory implements FallbackFactory<RemoteSearchService> {

	@Override
	public RemoteSearchService create(Throwable throwable) {
		RemoteSearchServiceFallbackImpl remoteItemServiceFallback = new RemoteSearchServiceFallbackImpl();
		remoteItemServiceFallback.setCause(throwable);
		return remoteItemServiceFallback;
	}
}
