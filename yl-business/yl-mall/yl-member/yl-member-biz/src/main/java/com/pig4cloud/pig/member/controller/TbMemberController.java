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

package com.pig4cloud.pig.member.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import com.pig4cloud.pig.common.security.annotation.Inner;
import com.pig4cloud.pig.member.dto.Member;
import com.pig4cloud.pig.member.entity.TbMember;
import com.pig4cloud.pig.member.service.TbMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


/**
 * 用户表
 *
 * @author yl
 * @date 2020-08-23 13:10:03
 */
@Inner(value = false)
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@Api(value = "member", tags = "用户表管理")
public class TbMemberController {

	private final TbMemberService tbMemberService;

	/**
	 * 分页查询
	 *
	 * @param page     分页对象
	 * @param tbMember 用户表
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page")
	@PreAuthorize("@pms.hasPermission('member_tbmember_get')")
	public R getTbMemberPage(Page page, TbMember tbMember) {
		return R.ok(tbMemberService.page(page, Wrappers.query(tbMember)));
	}


	/**
	 * 通过id查询用户表
	 *
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/{id}")
	public R getById(@PathVariable("id") Long id) {
		return R.ok(tbMemberService.getById(id));
	}

	/**
	 * 新增用户表
	 *
	 * @param tbMember 用户表
	 * @return R
	 */
	@ApiOperation(value = "新增用户表", notes = "新增用户表")
	@SysLog("新增用户表")
	@PostMapping
	public R save(@RequestBody TbMember tbMember) {
		return R.ok(tbMemberService.save(tbMember));
	}

	/**
	 * 修改用户表
	 *
	 * @param tbMember 用户表
	 * @return R
	 */
	@ApiOperation(value = "修改用户表", notes = "修改用户表")
	@SysLog("修改用户表")
	@PutMapping
	public R updateById(@RequestBody TbMember tbMember) {
		return R.ok(tbMemberService.updateById(tbMember));
	}

	/**
	 * 通过id删除用户表
	 *
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id删除用户表", notes = "通过id删除用户表")
	@SysLog("通过id删除用户表")
	@DeleteMapping("/{id}")
	public R removeById(@PathVariable Long id) {
		return R.ok(tbMemberService.removeById(id));
	}

	/**
	 * 判断用户是否登录
	 *
	 * @param token 用户token
	 * @return R<Member>
	 */
	@GetMapping("/checkLogin")
	@ApiOperation(value = "判断用户是否登录", notes = "判断用户是否登录")
	public R<Member> checkLogin(@RequestParam(defaultValue = "") String token) {
		Member member = tbMemberService.getUserByToken(token);
		return member == null ? R.failed() : R.ok(member);
	}

	/**
	 * 登录
	 *
	 * @param member 用户
	 * @return R<Member>
	 */
	@PostMapping("/login")
	@ApiOperation(value = "用户登录", notes = "用户登录")
	public R<Member> login(@RequestBody Member member) {
		return tbMemberService.login(member);
	}

	/**
	 * 注销
	 *
	 * @param token 认证信息
	 * @return R
	 */
	@GetMapping("/logOut")
	@ApiOperation(value = "用户注销", notes = "用户注销")
	public R login(@RequestParam("token") String token) {
		return tbMemberService.logOut(token);
	}
}
