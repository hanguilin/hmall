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

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.member.dto.Order;
import com.pig4cloud.pig.member.dto.OrderInfo;
import com.pig4cloud.pig.member.entity.TbOrder;

/**
 *
 *
 * @author hanguilin
 * @date 2020-08-30 01:30:49
 */
public interface TbOrderService extends IService<TbOrder> {

	/**
	 * 新增订单
	 * @param orderInfo 订单信息
	 * @return R<String>
	 */
	R<String> createOrder(OrderInfo orderInfo);

	/**
	 * 获得用户所有订单
	 *
	 * @param userId 用户id
	 * @param page 分页对象
	 * @return R<Page<Order>>
	 */
	Page<Order> getOrderList(Long userId, Page<TbOrder> page);

	/**
	 * 删除订单
	 *
	 * @param orderId 订单id
	 * @return Boolean
	 */
	R<Boolean> delById(String orderId);

	/**
	 * 获取订单详情
	 *
	 * @param orderId 订单id
	 * @return R<Order>
	 */
	R<Order> getDetailById(String orderId);
}
