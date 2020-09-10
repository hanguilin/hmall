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

package com.pig4cloud.pig.order.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import com.pig4cloud.pig.order.entity.TbOrderShipping;
import com.pig4cloud.pig.order.service.TbOrderShippingService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 *
 *
 * @author hanguilin
 * @date 2020-08-30 01:30:49
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/ordershipping" )
@Api(value = "ordershipping", tags = "管理")
public class TbOrderShippingController {

    private final TbOrderShippingService tbOrderShippingService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param tbOrderShipping
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('member_tbordershipping_get')" )
    public R getTbOrderShippingPage(Page page, TbOrderShipping tbOrderShipping) {
        return R.ok(tbOrderShippingService.page(page, Wrappers.query(tbOrderShipping)));
    }


    /**
     * 通过id查询
     * @param orderId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{orderId}" )
    @PreAuthorize("@pms.hasPermission('member_tbordershipping_get')" )
    public R getById(@PathVariable("orderId" ) String orderId) {
        return R.ok(tbOrderShippingService.getById(orderId));
    }

    /**
     * 新增
     * @param tbOrderShipping
     * @return R
     */
    @ApiOperation(value = "新增", notes = "新增")
    @SysLog("新增" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('member_tbordershipping_add')" )
    public R save(@RequestBody TbOrderShipping tbOrderShipping) {
        return R.ok(tbOrderShippingService.save(tbOrderShipping));
    }

    /**
     * 修改
     * @param tbOrderShipping
     * @return R
     */
    @ApiOperation(value = "修改", notes = "修改")
    @SysLog("修改" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('member_tbordershipping_edit')" )
    public R updateById(@RequestBody TbOrderShipping tbOrderShipping) {
        return R.ok(tbOrderShippingService.updateById(tbOrderShipping));
    }

    /**
     * 通过id删除
     * @param orderId id
     * @return R
     */
    @ApiOperation(value = "通过id删除", notes = "通过id删除")
    @SysLog("通过id删除" )
    @DeleteMapping("/{orderId}" )
    @PreAuthorize("@pms.hasPermission('member_tbordershipping_del')" )
    public R removeById(@PathVariable String orderId) {
        return R.ok(tbOrderShippingService.removeById(orderId));
    }

}
