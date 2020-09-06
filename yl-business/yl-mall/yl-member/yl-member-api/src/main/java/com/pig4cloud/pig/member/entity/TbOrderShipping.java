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
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 订单物流
 *
 * @author hanguilin
 * @date 2020-08-30 01:30:49
 */
@Data
@TableName("tb_order_shipping")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "订单物流")
public class TbOrderShipping extends Model<TbOrderShipping> {
	private static final long serialVersionUID = 1L;

	/**
	 * 订单ID
	 */
	@TableId(value = "order_id", type = IdType.ASSIGN_ID)
	@ApiModelProperty(value = "订单ID")
	private String orderId;
	/**
	 * 收货人全名
	 */
	@ApiModelProperty(value = "收货人全名")
	private String receiverName;
	/**
	 * 固定电话
	 */
	@ApiModelProperty(value = "固定电话")
	private String receiverTelephone;
	/**
	 * 移动电话
	 */
	@ApiModelProperty(value = "移动电话")
	private String receiverMobile;
	/**
	 * 省份
	 */
	@ApiModelProperty(value = "省份")
	private String receiverState;
	/**
	 * 城市
	 */
	@ApiModelProperty(value = "城市")
	private String receiverCity;
	/**
	 * 区/县
	 */
	@ApiModelProperty(value = "区/县")
	private String receiverDistrict;
	/**
	 * 收货地址，如：xx路xx号
	 */
	@ApiModelProperty(value = "收货地址，如：xx路xx号")
	private String receiverAddress;
	/**
	 * 邮政编码,如：310001
	 */
	@ApiModelProperty(value = "邮政编码,如：310001")
	private String receiverZip;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private LocalDateTime created;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private LocalDateTime updated;

	/**
	 * 逻辑删除
	 */
	@TableLogic
	private String delFlag;
}
