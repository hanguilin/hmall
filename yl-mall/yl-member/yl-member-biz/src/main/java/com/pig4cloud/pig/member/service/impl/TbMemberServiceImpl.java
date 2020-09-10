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
package com.pig4cloud.pig.member.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.member.dto.Member;
import com.pig4cloud.pig.member.entity.TbMember;
import com.pig4cloud.pig.member.mapper.TbMemberMapper;
import com.pig4cloud.pig.member.service.TbMemberService;
import com.pig4cloud.pig.member.util.DtoUtil;
import com.pig4cloud.pig.member.util.HashKeyUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 用户表
 *
 * @author yl
 * @date 2020-08-23 13:10:03
 */
@Service
public class TbMemberServiceImpl extends ServiceImpl<TbMemberMapper, TbMember> implements TbMemberService {

	/**
	 * 启用状态常量
	 */
	private static final String ENABLED = "0";

	private RedisTemplate<String, String> redisTemplate;

	@Autowired
	public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@Override
	public Member getUserByToken(String token) {
		String sessionKey = HashKeyUtil.getSessionKey(token);
		String json = redisTemplate.opsForValue().get(sessionKey);
		if (StringUtils.isBlank(json)) {
			return null;
		}
		//重置过期时间
		redisTemplate.expire(sessionKey, 1800, TimeUnit.SECONDS);
		return JSON.parseObject(json, Member.class);
	}

	/**
	 * 登录
	 *
	 * @param member 用户
	 * @return R<Member>
	 */
	@Override
	public R<Member> login(Member member) {
		String userName = member.getUserName();
		String password = member.getPassword();
		if (StringUtils.isBlank(userName) || StringUtils.isBlank(password)) {
			return R.failed("缺少参数");
		}
		LambdaQueryWrapper<TbMember> lambdaQueryWrapper = Wrappers.lambdaQuery();
		lambdaQueryWrapper.eq(TbMember::getState, ENABLED);
		lambdaQueryWrapper.eq(TbMember::getUserName, userName);
		TbMember tbMember = getOne(lambdaQueryWrapper);
		if (tbMember == null) {
			return R.failed("用户名不存在");
		}
		String passwordDb = tbMember.getPassword();
		if (!DigestUtils.md5DigestAsHex(password.getBytes()).equals(passwordDb)) {
			return R.failed("用户名或密码错误");
		}
		String token = UUID.randomUUID().toString();
		Member memberResult = DtoUtil.tbMemberToMember(tbMember);
		memberResult.setToken(token);
		// 用户信息写入redis,超时时间1800s=30min
		redisTemplate.opsForValue().set(HashKeyUtil.getSessionKey(token), JSON.toJSONString(memberResult), 1800, TimeUnit.SECONDS);
		return R.ok(memberResult);
	}

	/**
	 * 注销
	 *
	 * @param token 认证信息
	 * @return R
	 */
	@Override
	public R logOut(String token) {
		redisTemplate.delete(token);
		return R.ok();
	}

}
