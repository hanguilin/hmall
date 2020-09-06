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
 * 订单
 *
 * @author hanguilin
 * @date 2020-08-30 01:30:49
 */
@Data
@TableName("tb_order")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "订单")
public class TbOrder extends Model<TbOrder> {
private static final long serialVersionUID = 1L;

    /**
     * 订单id
     */
	@TableId(value = "order_id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value="订单id")
    private String orderId;
    /**
     * 实付金额
     */
    @ApiModelProperty(value="实付金额")
    private BigDecimal payment;
    /**
     * 支付类型 1在线支付 2货到付款
     */
    @ApiModelProperty(value="支付类型 1在线支付 2货到付款")
    private Integer paymentType;
    /**
     * 邮费
     */
    @ApiModelProperty(value="邮费")
    private BigDecimal postFee;
    /**
     * 状态 0未付款 1已付款 2未发货 3已发货 4交易成功 5交易关闭 6交易失败
     */
    @ApiModelProperty(value="状态 0未付款 1已付款 2未发货 3已发货 4交易成功 5交易关闭 6交易失败")
    private Integer status;
    /**
     * 订单创建时间
     */
    @ApiModelProperty(value="订单创建时间")
    private LocalDateTime createTime;
    /**
     * 订单更新时间
     */
    @ApiModelProperty(value="订单更新时间")
    private LocalDateTime updateTime;
    /**
     * 付款时间
     */
    @ApiModelProperty(value="付款时间")
    private LocalDateTime paymentTime;
    /**
     * 发货时间
     */
    @ApiModelProperty(value="发货时间")
    private LocalDateTime consignTime;
    /**
     * 交易完成时间
     */
    @ApiModelProperty(value="交易完成时间")
    private LocalDateTime endTime;
    /**
     * 交易关闭时间
     */
    @ApiModelProperty(value="交易关闭时间")
    private LocalDateTime closeTime;
    /**
     * 物流名称
     */
    @ApiModelProperty(value="物流名称")
    private String shippingName;
    /**
     * 物流单号
     */
    @ApiModelProperty(value="物流单号")
    private String shippingCode;
    /**
     * 用户id
     */
    @ApiModelProperty(value="用户id")
    private Long userId;
    /**
     * 买家留言
     */
    @ApiModelProperty(value="买家留言")
    private String buyerMessage;
    /**
     * 买家昵称
     */
    @ApiModelProperty(value="买家昵称")
    private String buyerNick;
    /**
     * 买家是否已经评价
     */
    @ApiModelProperty(value="买家是否已经评价")
    private Integer buyerComment;
    }
