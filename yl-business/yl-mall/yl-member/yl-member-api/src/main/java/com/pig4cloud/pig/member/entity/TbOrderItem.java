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

package com.pig4cloud.pig.member.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 订单商品表
 *
 * @author hanguilin
 * @date 2020-08-30 01:55:06
 */
@Data
@TableName("tb_order_item")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "订单商品")
public class TbOrderItem extends Model<TbOrderItem> {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	@ApiModelProperty(value = "id")
	private String id;
	/**
	 * 商品id
	 */
	@ApiModelProperty(value = "商品id")
	private String itemId;
	/**
	 * 订单id
	 */
	@ApiModelProperty(value = "订单id")
	private String orderId;
	/**
	 * 商品购买数量
	 */
	@ApiModelProperty(value = "商品购买数量")
	private Integer num;
	/**
	 * 商品标题
	 */
	@ApiModelProperty(value = "商品标题")
	private String title;
	/**
	 * 商品单价
	 */
	@ApiModelProperty(value = "商品单价")
	private BigDecimal price;
	/**
	 * 商品总金额
	 */
	@ApiModelProperty(value = "商品总金额")
	private BigDecimal totalFee;
	/**
	 * 商品图片地址
	 */
	@ApiModelProperty(value = "商品图片地址")
	private String picPath;
}
