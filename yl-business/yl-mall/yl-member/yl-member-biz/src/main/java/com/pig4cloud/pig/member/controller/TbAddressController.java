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
import com.pig4cloud.pig.member.entity.TbAddress;
import com.pig4cloud.pig.member.service.TbAddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * @author yl
 * @date 2020-08-23 13:10:03
 */
@Inner(value = false)
@RestController
@RequiredArgsConstructor
@RequestMapping("/address")
@Api(value = "address", tags = "地址管理")
public class TbAddressController {

	private final TbAddressService tbAddressService;

	/**
	 * 分页查询地址
	 *
	 * @param page      分页对象
	 * @param tbAddress
	 * @return
	 */
	@ApiOperation(value = "分页查询地址", notes = "分页查询地址")
	@GetMapping("/page")
	public R getTbAddressPage(Page page, TbAddress tbAddress) {
		return R.ok(tbAddressService.page(page, Wrappers.query(tbAddress)));
	}


	/**
	 * 通过id查询地址
	 *
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询地址", notes = "通过id查询地址")
	@GetMapping("/{id}")
	public R getById(@PathVariable("id") Long id) {
		return R.ok(tbAddressService.getById(id));
	}

	/**
	 * 新增地址
	 *
	 * @param tbAddress
	 * @return R
	 */
	@ApiOperation(value = "新增地址", notes = "新增地址")
	@SysLog("新增地址")
	@PostMapping
	public R save(@RequestBody TbAddress tbAddress) {
		return R.ok(tbAddressService.save(tbAddress));
	}

	/**
	 * 修改地址
	 *
	 * @param tbAddress
	 * @return R
	 */
	@ApiOperation(value = "修改地址", notes = "修改地址")
	@SysLog("修改地址")
	@PutMapping
	public R updateById(@RequestBody TbAddress tbAddress) {
		return R.ok(tbAddressService.updateById(tbAddress));
	}

	/**
	 * 通过id删除地址
	 *
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id删除地址", notes = "通过id删除地址")
	@SysLog("通过id删除地址")
	@DeleteMapping("/{id}")
	public R removeById(@PathVariable Long id) {
		return R.ok(tbAddressService.removeById(id));
	}

}
