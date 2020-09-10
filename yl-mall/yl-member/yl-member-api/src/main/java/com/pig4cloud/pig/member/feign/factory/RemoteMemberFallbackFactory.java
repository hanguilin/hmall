package com.pig4cloud.pig.member.feign.factory;

import com.pig4cloud.pig.member.feign.RemoteMemberService;
import com.pig4cloud.pig.member.feign.fallback.RemoteMemberServiceFallbackImpl;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;


/**
 * 客户feign接口回调工厂
 * @author hanguilin
 */
@Component
public class RemoteMemberFallbackFactory implements FallbackFactory<RemoteMemberService> {

	@Override
	public RemoteMemberService create(Throwable throwable) {
		RemoteMemberServiceFallbackImpl remoteItemServiceFallback = new RemoteMemberServiceFallbackImpl();
		remoteItemServiceFallback.setCause(throwable);
		return remoteItemServiceFallback;
	}
}
