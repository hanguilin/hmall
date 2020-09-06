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
import com.pig4cloud.pig.member.entity.TbOrderItem;
import com.pig4cloud.pig.member.service.TbOrderItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


/**
 * @author hanguilin
 * @date 2020-08-30 01:55:06
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/tborderitem")
@Api(value = "tborderitem", tags = "管理")
public class TbOrderItemController {

	private final TbOrderItemService tbOrderItemService;

	/**
	 * 分页查询
	 *
	 * @param page        分页对象
	 * @param tbOrderItem
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page")
	@PreAuthorize("@pms.hasPermission('member_tborderitem_get')")
	public R getTbOrderItemPage(Page page, TbOrderItem tbOrderItem) {
		return R.ok(tbOrderItemService.page(page, Wrappers.query(tbOrderItem)));
	}


	/**
	 * 通过id查询
	 *
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/{id}")
	@PreAuthorize("@pms.hasPermission('member_tborderitem_get')")
	public R getById(@PathVariable("id") String id) {
		return R.ok(tbOrderItemService.getById(id));
	}

	/**
	 * 新增
	 *
	 * @param tbOrderItem
	 * @return R
	 */
	@ApiOperation(value = "新增", notes = "新增")
	@SysLog("新增")
	@PostMapping
	@PreAuthorize("@pms.hasPermission('member_tborderitem_add')")
	public R save(@RequestBody TbOrderItem tbOrderItem) {
		return R.ok(tbOrderItemService.save(tbOrderItem));
	}

	/**
	 * 修改
	 *
	 * @param tbOrderItem
	 * @return R
	 */
	@ApiOperation(value = "修改", notes = "修改")
	@SysLog("修改")
	@PutMapping
	@PreAuthorize("@pms.hasPermission('member_tborderitem_edit')")
	public R updateById(@RequestBody TbOrderItem tbOrderItem) {
		return R.ok(tbOrderItemService.updateById(tbOrderItem));
	}

	/**
	 * 通过id删除
	 *
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id删除", notes = "通过id删除")
	@SysLog("通过id删除")
	@DeleteMapping("/{id}")
	@PreAuthorize("@pms.hasPermission('member_tborderitem_del')")
	public R removeById(@PathVariable String id) {
		return R.ok(tbOrderItemService.removeById(id));
	}

}
