package com.pig4cloud.pig.order.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 订单信息
 * @author hanguilin
 */
@Data
public class OrderInfo implements Serializable{

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 用户id
	 */
    private Long userId;

	/**
	 * 收货地址id
	 */
    private Long addressId;

	/**
	 * 联系电话
	 */
    private String receiverTelephone;

	/**
	 * 用户名
	 */
    private String receiverName;

	/**
	 * 收货地址
	 */
    private String receiverAddress;

	/**
	 * 订单总价
	 */
    private BigDecimal orderTotal;

	/**
	 * 商品列表
	 */
	private List<CartProduct> goodsList;
}
