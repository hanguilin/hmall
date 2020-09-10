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
import com.pig4cloud.pig.goods.entity.TbPanel;
import com.pig4cloud.pig.goods.service.TbPanelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


/**
 * 内容分类
 *
 * @author yl
 * @date 2020-08-22 11:41:30
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/panel")
@Api(value = "panel", tags = "内容分类管理")
public class TbPanelController {

	private final TbPanelService tbPanelService;

	/**
	 * 分页查询
	 *
	 * @param page    分页对象
	 * @param tbPanel 内容分类
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page")
	@PreAuthorize("@pms.hasPermission('mall_tbpanel_get')")
	public R getTbPanelPage(Page page, TbPanel tbPanel) {
		return R.ok(tbPanelService.page(page, Wrappers.query(tbPanel)));
	}


	/**
	 * 通过id查询内容分类
	 *
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/{id}")
	@PreAuthorize("@pms.hasPermission('mall_tbpanel_get')")
	public R getById(@PathVariable("id") Integer id) {
		return R.ok(tbPanelService.getById(id));
	}

	/**
	 * 新增内容分类
	 *
	 * @param tbPanel 内容分类
	 * @return R
	 */
	@ApiOperation(value = "新增内容分类", notes = "新增内容分类")
	@SysLog("新增内容分类")
	@PostMapping
	@PreAuthorize("@pms.hasPermission('mall_tbpanel_add')")
	public R save(@RequestBody TbPanel tbPanel) {
		return R.ok(tbPanelService.save(tbPanel));
	}

	/**
	 * 修改内容分类
	 *
	 * @param tbPanel 内容分类
	 * @return R
	 */
	@ApiOperation(value = "修改内容分类", notes = "修改内容分类")
	@SysLog("修改内容分类")
	@PutMapping
	@PreAuthorize("@pms.hasPermission('mall_tbpanel_edit')")
	public R updateById(@RequestBody TbPanel tbPanel) {
		return R.ok(tbPanelService.updateById(tbPanel));
	}

	/**
	 * 通过id删除内容分类
	 *
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id删除内容分类", notes = "通过id删除内容分类")
	@SysLog("通过id删除内容分类")
	@DeleteMapping("/{id}")
	@PreAuthorize("@pms.hasPermission('mall_tbpanel_del')")
	public R removeById(@PathVariable Integer id) {
		return R.ok(tbPanelService.removeById(id));
	}

}
