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

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.common.core.exception.HmallException;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.member.dto.CartProduct;
import com.pig4cloud.pig.member.dto.OrderInfo;
import com.pig4cloud.pig.member.entity.TbMember;
import com.pig4cloud.pig.member.entity.TbOrder;
import com.pig4cloud.pig.member.entity.TbOrderItem;
import com.pig4cloud.pig.member.entity.TbOrderShipping;
import com.pig4cloud.pig.member.mapper.TbOrderMapper;
import com.pig4cloud.pig.member.service.TbMemberService;
import com.pig4cloud.pig.member.service.TbOrderItemService;
import com.pig4cloud.pig.member.service.TbOrderService;
import com.pig4cloud.pig.member.service.TbOrderShippingService;
import com.pig4cloud.pig.member.util.HashKeyUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hanguilin
 * @date 2020-08-30 01:30:49
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TbOrderServiceImpl extends ServiceImpl<TbOrderMapper, TbOrder> implements TbOrderService {

	private final TbMemberService tbMemberService;

	private final TbOrderItemService tbOrderItemService;

	private final TbOrderShippingService tbOrderShippingService;

	private final RedisTemplate<String, String> redisTemplate;

	/**
	 * 新增订单
	 * @param orderInfo 订单信息
	 * @return R<String>
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public R<String> createOrder(OrderInfo orderInfo) {
		long userId = orderInfo.getUserId();
		TbMember member = tbMemberService.getById(userId);
		if (member == null) {
			throw new HmallException("下单用户不存在");
		}
		TbOrder order = new TbOrder();
		//生成订单ID
		order.setUserId(userId);
		order.setBuyerNick(member.getUserName());
		order.setPayment(orderInfo.getOrderTotal());
		LocalDateTime now = LocalDateTime.now();
		order.setCreateTime(now);
		order.setUpdateTime(now);
		//0、未付款，1、已付款，2、未发货，3、已发货，4、交易成功，5、交易关闭，6、交易失败
		order.setStatus(0);

		if (!save(order)) {
			throw new HmallException("生成订单失败");
		}
		String orderId = order.getOrderId();

		List<CartProduct> list = orderInfo.getGoodsList();
		for (CartProduct cartProduct : list) {
			TbOrderItem orderItem = new TbOrderItem();
			//生成订单商品ID
			orderItem.setItemId(String.valueOf(cartProduct.getProductId()));
			orderItem.setOrderId(orderId);
			orderItem.setNum(Math.toIntExact(cartProduct.getProductNum()));
			orderItem.setPrice(cartProduct.getSalePrice());
			orderItem.setTitle(cartProduct.getProductName());
			orderItem.setPicPath(cartProduct.getProductImg());
			orderItem.setTotalFee(cartProduct.getSalePrice().multiply(BigDecimal.valueOf(cartProduct.getProductNum())));

			if (!tbOrderItemService.save(orderItem)) {
				throw new HmallException("生成订单商品失败");
			}
		}

		//物流表
		TbOrderShipping orderShipping = new TbOrderShipping();
		orderShipping.setOrderId(orderId);
		orderShipping.setReceiverName(orderInfo.getReceiverName());
		orderShipping.setReceiverAddress(orderInfo.getReceiverAddress());
		orderShipping.setReceiverTelephone(orderInfo.getReceiverTelephone());
		LocalDateTime createShipNow = LocalDateTime.now();
		orderShipping.setCreated(createShipNow);
		orderShipping.setUpdated(createShipNow);

		if (!tbOrderShippingService.save(orderShipping)) {
			throw new HmallException("生成物流信息失败");
		}

		List<String> productIdList = list.stream().map(o->String.valueOf(o.getProductId())).collect(Collectors.toList());
		String[] productIdArray = new String[productIdList.size()];
		productIdList.toArray(productIdArray);
		//删除购物车中含该订单的商品
		try {
			HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
			String cartHashKey = HashKeyUtil.getCartHashKey(userId);
			hashOperations.delete(cartHashKey, productIdArray);
		} catch (Exception e) {
			throw new HmallException(e.getMessage(), e);
		}

		return R.ok(orderId);
	}
}
