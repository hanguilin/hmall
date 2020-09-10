package com.pig4cloud.pig.member.feign;

import com.pig4cloud.pig.common.core.constant.ServiceNameConstants;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.member.entity.TbMember;
import com.pig4cloud.pig.member.feign.factory.RemoteMemberFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 客户feign接口
 * @author hanguilin
 */
@FeignClient(value = ServiceNameConstants.MEMBER_SERVICE, contextId = "remoteMemberService", fallbackFactory = RemoteMemberFallbackFactory.class)
public interface RemoteMemberService {

	/**
	 * 通过id查询商品表
	 *
	 * @param id id
	 * @return R
	 */
	@GetMapping("/member/{id}")
	R<TbMember> getById(@PathVariable("id") Long id);

}
