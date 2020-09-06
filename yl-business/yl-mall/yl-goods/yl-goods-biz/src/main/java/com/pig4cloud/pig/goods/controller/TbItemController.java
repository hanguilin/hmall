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

package com.pig4cloud.pig.goods.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import com.pig4cloud.pig.common.security.annotation.Inner;
import com.pig4cloud.pig.goods.entity.TbItem;
import com.pig4cloud.pig.goods.service.TbItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


/**
 * 商品表
 *
 * @author yl
 * @date 2020-08-22 11:41:30
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/item")
@Api(value = "item", tags = "商品表管理")
public class TbItemController {

	private final TbItemService tbItemService;

	/**
	 * 分页查询
	 *
	 * @param page   分页对象
	 * @param tbItem 商品表
	 * @return
	 */
	@Inner(value = false)
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page")
	public R getTbItemPage(Page page, TbItem tbItem) {
		return R.ok(tbItemService.page(page, Wrappers.query(tbItem)));
	}


	/**
	 * 通过id查询商品表
	 *
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/{id}")
	@Inner(value = false)
	public R<TbItem> getById(@PathVariable("id") Long id) {
		return R.ok(tbItemService.getById(id));
	}

	/**
	 * 新增商品表
	 *
	 * @param tbItem 商品表
	 * @return R
	 */
	@ApiOperation(value = "新增商品表", notes = "新增商品表")
	@SysLog("新增商品表")
	@PostMapping
	@PreAuthorize("@pms.hasPermission('mall_tbitem_add')")
	public R save(@RequestBody TbItem tbItem) {
		return R.ok(tbItemService.save(tbItem));
	}

	/**
	 * 修改商品表
	 *
	 * @param tbItem 商品表
	 * @return R
	 */
	@ApiOperation(value = "修改商品表", notes = "修改商品表")
	@SysLog("修改商品表")
	@PutMapping
	@PreAuthorize("@pms.hasPermission('mall_tbitem_edit')")
	public R updateById(@RequestBody TbItem tbItem) {
		return R.ok(tbItemService.updateById(tbItem));
	}

	/**
	 * 通过id删除商品表
	 *
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id删除商品表", notes = "通过id删除商品表")
	@SysLog("通过id删除商品表")
	@DeleteMapping("/{id}")
	@PreAuthorize("@pms.hasPermission('mall_tbitem_del')")
	public R removeById(@PathVariable Long id) {
		return R.ok(tbItemService.removeById(id));
	}

}
