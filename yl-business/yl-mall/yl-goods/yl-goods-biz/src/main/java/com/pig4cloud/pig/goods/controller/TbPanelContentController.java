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
import com.pig4cloud.pig.goods.entity.TbPanelContent;
import com.pig4cloud.pig.goods.service.TbPanelContentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


/**
 * @author yl
 * @date 2020-08-22 11:41:30
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/panelcontent")
@Api(value = "panelcontent", tags = "管理")
public class TbPanelContentController {

	private final TbPanelContentService tbPanelContentService;

	/**
	 * 分页查询
	 *
	 * @param page           分页对象
	 * @param tbPanelContent
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page")
	@PreAuthorize("@pms.hasPermission('mall_tbpanelcontent_get')")
	public R getTbPanelContentPage(Page page, TbPanelContent tbPanelContent) {
		return R.ok(tbPanelContentService.page(page, Wrappers.query(tbPanelContent)));
	}


	/**
	 * 通过id查询
	 *
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/{id}")
	@PreAuthorize("@pms.hasPermission('mall_tbpanelcontent_get')")
	public R getById(@PathVariable("id") Integer id) {
		return R.ok(tbPanelContentService.getById(id));
	}

	/**
	 * 新增
	 *
	 * @param tbPanelContent
	 * @return R
	 */
	@ApiOperation(value = "新增", notes = "新增")
	@SysLog("新增")
	@PostMapping
	@PreAuthorize("@pms.hasPermission('mall_tbpanelcontent_add')")
	public R save(@RequestBody TbPanelContent tbPanelContent) {
		return R.ok(tbPanelContentService.save(tbPanelContent));
	}

	/**
	 * 修改
	 *
	 * @param tbPanelContent
	 * @return R
	 */
	@ApiOperation(value = "修改", notes = "修改")
	@SysLog("修改")
	@PutMapping
	@PreAuthorize("@pms.hasPermission('mall_tbpanelcontent_edit')")
	public R updateById(@RequestBody TbPanelContent tbPanelContent) {
		return R.ok(tbPanelContentService.updateById(tbPanelContent));
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
	@PreAuthorize("@pms.hasPermission('mall_tbpanelcontent_del')")
	public R removeById(@PathVariable Integer id) {
		return R.ok(tbPanelContentService.removeById(id));
	}

}
