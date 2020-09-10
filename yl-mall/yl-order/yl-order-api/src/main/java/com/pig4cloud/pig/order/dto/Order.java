package com.pig4cloud.pig.order.dto;


import com.pig4cloud.pig.member.entity.TbAddress;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 订单
 *
 * @author hanguilin
 */
@Data
public class Order implements Serializable {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 订单id
	 */
	private String orderId;

	/**
	 * 订单总金额
	 */
	private BigDecimal orderTotal;

	/**
	 * 地址信息
	 */
	private TbAddress addressInfo;

	/**
	 * 商品列表
	 */
	private List<CartProduct> goodsList;

	/**
	 * 订单状态
	 */
	private int orderStatus;

	/**
	 * 创建日期
	 */
	private String createDate;

	/**
	 * 订单关闭日期
	 */
	private String closeDate;

	/**
	 * 订单完成日期
	 */
	private String finishDate;

	/**
	 * 订单支付日期
	 */
	private String payDate;
}
