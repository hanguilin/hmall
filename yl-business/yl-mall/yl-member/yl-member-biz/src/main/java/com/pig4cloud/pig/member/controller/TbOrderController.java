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
import com.pig4cloud.pig.member.dto.OrderInfo;
import com.pig4cloud.pig.member.entity.TbOrder;
import com.pig4cloud.pig.member.service.TbOrderService;
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
@Inner(value = false)
@RestController
@RequiredArgsConstructor
@RequestMapping("/order" )
@Api(value = "order", tags = "管理订单")
public class TbOrderController {

    private final  TbOrderService tbOrderService;

    /**
     * 分页查询订单
     * @param page 分页对象
     * @param tbOrder 订单信息
     * @return
     */
    @ApiOperation(value = "分页查询订单", notes = "分页查询订单")
    @GetMapping("/page" )
    public R getTbOrderPage(Page page, TbOrder tbOrder) {
        return R.ok(tbOrderService.page(page, Wrappers.query(tbOrder)));
    }


    /**
     * 通过id查询订单
     * @param orderId id
     * @return R
     */
    @ApiOperation(value = "通过id查询订单", notes = "通过id查询订单")
    @GetMapping("/{orderId}" )
    public R getById(@PathVariable("orderId" ) String orderId) {
        return R.ok(tbOrderService.getById(orderId));
    }

    /**
     * 新增订单
     * @param orderInfo 订单信息
     * @return R<Long>
     */
    @ApiOperation(value = "新增订单", notes = "新增订单")
    @SysLog("新增订单" )
    @PostMapping
    public R<String> save(@RequestBody OrderInfo orderInfo) {
        return tbOrderService.createOrder(orderInfo);
    }

    /**
     * 修改订单
     * @param tbOrder 订单信息
     * @return R
     */
    @ApiOperation(value = "修改订单", notes = "修改订单")
    @SysLog("修改订单" )
    @PutMapping
    public R updateById(@RequestBody TbOrder tbOrder) {
        return R.ok(tbOrderService.updateById(tbOrder));
    }

    /**
     * 通过id删除订单
     * @param orderId id
     * @return R
     */
    @ApiOperation(value = "通过id删除订单", notes = "通过id删除订单")
    @SysLog("通过id删除订单" )
    @DeleteMapping("/{orderId}" )
    public R removeById(@PathVariable String orderId) {
        return R.ok(tbOrderService.removeById(orderId));
    }

}
