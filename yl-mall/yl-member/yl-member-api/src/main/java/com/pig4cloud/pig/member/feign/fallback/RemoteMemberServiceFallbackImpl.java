package com.pig4cloud.pig.member.feign.fallback;

import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.member.entity.TbMember;
import com.pig4cloud.pig.member.feign.RemoteMemberService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


/**
 * 客户feign接口回调类
 * @author hanguilin
 */
@Slf4j
@Component
public class RemoteMemberServiceFallbackImpl implements RemoteMemberService {

	@Setter
	private Throwable cause;

	public void setCause(Throwable cause) {
		this.cause = cause;
	}

	@Override
	public R<TbMember> getById(Long id) {
		log.error("feign 查询客户信息失败", cause);
		return null;
	}
}
