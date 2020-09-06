/*
 *    Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: lengleng (wangiegie@gmail.com)
 */

package com.pig4cloud.pig.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.member.dto.Member;
import com.pig4cloud.pig.member.entity.TbMember;

/**
 * 用户表
 *
 * @author yl
 * @date 2020-08-23 13:10:03
 */
public interface TbMemberService extends IService<TbMember> {

	/**
	 * 根据token获取成员
	 *
	 * @param token 认证
	 * @return Member
	 */
	Member getUserByToken(String token);

	/**
	 * 登录
	 *
	 * @param member 用户
	 * @return R<Member>
	 */
	R<Member> login(Member member);
}
