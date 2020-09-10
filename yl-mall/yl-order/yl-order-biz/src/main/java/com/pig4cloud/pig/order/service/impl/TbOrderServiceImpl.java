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
package com.pig4cloud.pig.order.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.pig4cloud.pig.common.core.exception.HmallException;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.member.OrderStatus;
import com.pig4cloud.pig.member.entity.TbAddress;
import com.pig4cloud.pig.member.entity.TbMember;
import com.pig4cloud.pig.member.feign.RemoteMemberService;
import com.pig4cloud.pig.member.util.DateUtil;
import com.pig4cloud.pig.member.util.HashKeyUtil;
import com.pig4cloud.pig.order.dto.CartProduct;
import com.pig4cloud.pig.order.dto.Order;
import com.pig4cloud.pig.order.dto.OrderInfo;
import com.pig4cloud.pig.order.entity.TbOrder;
import com.pig4cloud.pig.order.entity.TbOrderItem;
import com.pig4cloud.pig.order.entity.TbOrderShipping;
import com.pig4cloud.pig.order.mapper.TbOrderMapper;
import com.pig4cloud.pig.order.service.TbOrderItemService;
import com.pig4cloud.pig.order.service.TbOrderService;
import com.pig4cloud.pig.order.service.TbOrderShippingService;
import com.pig4cloud.pig.order.util.DtoUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author hanguilin
 * @date 2020-08-30 01:30:49
 */
@Service
@Slf4j
public class TbOrderServiceImpl extends ServiceImpl<TbOrderMapper, TbOrder> implements TbOrderService {

	private RemoteMemberService remoteMemberService;

	private TbOrderItemService tbOrderItemService;

	private TbOrderShippingService tbOrderShippingService;

	private RedisTemplate<String, String> redisTemplate;

	@Autowired
	public void setTbMemberService(RemoteMemberService remoteMemberService) {
		this.remoteMemberService = remoteMemberService;
	}

	@Autowired
	public void setTbOrderItemService(TbOrderItemService tbOrderItemService) {
		this.tbOrderItemService = tbOrderItemService;
	}

	@Autowired
	public void setTbOrderShippingService(TbOrderShippingService tbOrderShippingService) {
		this.tbOrderShippingService = tbOrderShippingService;
	}

	@Autowired
	public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	/**
	 * 新增订单
	 *
	 * @param orderInfo 订单信息
	 * @return R<String>
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public R<String> createOrder(OrderInfo orderInfo) {
		long userId = orderInfo.getUserId();
		R<TbMember> memberR = remoteMemberService.getById(userId);
		TbMember member = memberR.getData();
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

		List<Object> productIdList = list.stream().map(o -> o.getProductId()).collect(Collectors.toList());
		Object[] productIdArray = new Object[productIdList.size()];
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

	/**
	 * 获得用户所有订单
	 *
	 * @param userId 用户id
	 * @param page   分页对象
	 * @return R<Page < Order>>
	 */
	@Override
	public Page<Order> getOrderList(Long userId, Page<TbOrder> page) {
		Page<Order> resultPage = new Page<>();
		List<Order> resultOrders = Lists.newArrayList();
		// 获取用户订单
		TbOrder tbOrder = new TbOrder();
		tbOrder.setUserId(userId);
		Page<TbOrder> orderPage = super.page(page, Wrappers.query(tbOrder));
		List<TbOrder> orderRecords = orderPage.getRecords();
		if (!orderRecords.isEmpty()) {
			List<String> orderIds = orderRecords.stream().map(TbOrder::getOrderId).collect(Collectors.toList());
			List<TbOrderShipping> tbOrderShippings = tbOrderShippingService.list(Wrappers.lambdaQuery(TbOrderShipping.class).in(TbOrderShipping::getOrderId, orderIds));
			Map<String, TbOrderShipping> tbOrderShippingsMap = tbOrderShippings.stream().collect(Collectors.toMap(TbOrderShipping::getOrderId, Function.identity(), (v1, v2) -> v1));
			List<TbOrderItem> tbOrderItems = tbOrderItemService.list(Wrappers.lambdaQuery(TbOrderItem.class).in(TbOrderItem::getOrderId, orderIds));
			Map<String, List<TbOrderItem>> tbOrderItemsGroup = tbOrderItems.stream().collect(Collectors.groupingBy(TbOrderItem::getOrderId));
			orderRecords.forEach(record -> {
				String orderId = record.getOrderId();
				TbOrderShipping tbOrderShipping = tbOrderShippingsMap.get(orderId);
				List<TbOrderItem> itemsForOrder = tbOrderItemsGroup.get(orderId);
				Order order = setOrderInfo(record, tbOrderShipping, itemsForOrder);
				resultOrders.add(order);
			});
		}
		resultPage.setTotal(orderPage.getTotal());
		resultPage.setCurrent(orderPage.getCurrent());
		resultPage.setRecords(resultOrders);
		return resultPage;
	}

	/**
	 * 删除订单
	 *
	 * @param orderId 订单id
	 * @return Boolean
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public R<Boolean> delById(String orderId) {
		if (StringUtils.isBlank(orderId)) {
			return R.failed("订单id为空");
		}
		tbOrderItemService.remove(Wrappers.lambdaQuery(TbOrderItem.class).eq(TbOrderItem::getOrderId, orderId));
		tbOrderShippingService.remove(Wrappers.lambdaQuery(TbOrderShipping.class).eq(TbOrderShipping::getOrderId, orderId));
		super.removeById(orderId);
		return R.ok();
	}

	@Override
	public R<Order> getDetailById(String orderId) {
		if (StringUtils.isBlank(orderId)) {
			return R.failed("订单id为空");
		}
		TbOrder tbOrder = super.getById(orderId);
		if (tbOrder == null) {
			return R.failed("订单不存在");
		}
		TbOrderShipping tbOrderShipping = tbOrderShippingService.getOne(Wrappers.lambdaQuery(TbOrderShipping.class).eq(TbOrderShipping::getOrderId, orderId));
		List<TbOrderItem> itemsForOrder = tbOrderItemService.list(Wrappers.lambdaQuery(TbOrderItem.class).eq(TbOrderItem::getOrderId, orderId));
		Order order = setOrderInfo(tbOrder, tbOrderShipping, itemsForOrder);
		return R.ok(order);
	}

	/**
	 * 生成并设置order信息
	 * @param tbOrder 订单表数据
	 * @param tbOrderShipping 订单物流
	 * @param itemsForOrder 订单商品
	 * @return Order
	 */
	private Order setOrderInfo(TbOrder tbOrder, TbOrderShipping tbOrderShipping, List<TbOrderItem> itemsForOrder){
		Order order = new Order();
		String validTime = judgeOrder(tbOrder);
		if (validTime != null) {
			order.setFinishDate(validTime);
		}
		order.setOrderId(tbOrder.getOrderId());
		order.setOrderStatus(tbOrder.getStatus());
		order.setCreateDate(DateUtil.format(tbOrder.getCreateTime()));
		if (tbOrder.getPaymentTime() != null) {
			order.setPayDate(DateUtil.format(tbOrder.getPaymentTime()));
		}
		if (tbOrder.getCloseTime() != null) {
			order.setCloseDate(DateUtil.format(tbOrder.getCloseTime()));
		}
		if (tbOrder.getEndTime() != null && tbOrder.getStatus() == OrderStatus.DEAL_SUCCESS.code()) {
			order.setFinishDate(DateUtil.format(tbOrder.getEndTime()));
		}
		// 收货地址信息
		TbAddress address = new TbAddress();
		address.setReceiverName(tbOrderShipping.getReceiverName());
		address.setReceiverAddress(tbOrderShipping.getReceiverAddress());
		address.setReceiverTelephone(tbOrderShipping.getReceiverTelephone());
		order.setAddressInfo(address);
		// 订单金额
		order.setOrderTotal(tbOrder.getPayment() == null ? BigDecimal.ZERO : tbOrder.getPayment());
		// 商品
		List<CartProduct> listProduct = itemsForOrder.stream().map(item -> DtoUtil.tbOrderItemToCartProduct(item)).collect(Collectors.toList());
		order.setGoodsList(listProduct);
		return order;
	}

	/**
	 * 判断订单是否超时未支付
	 *
	 * @param tbOrder 订单
	 * @return 超时时间
	 */
	private String judgeOrder(TbOrder tbOrder) {

		String result = null;
		if (tbOrder.getStatus() == OrderStatus.UN_PAY.code()) {
			//判断是否已超1天
			LocalDateTime createTime = tbOrder.getCreateTime();
			LocalDateTime plusDays = createTime.plusDays(1);
			LocalDateTime now = LocalDateTime.now();
			if (plusDays.isBefore(now)) {
				//设置失效
				tbOrder.setStatus(OrderStatus.DEAL_CLOSED.code());
				tbOrder.setCloseTime(now);
				if (!super.updateById(tbOrder)) {
					throw new HmallException("更新订单失效失败");
				}
			} else {
				//返回到期时间
				result = DateUtil.format(plusDays);
			}
		}
		return result;
	}

}
