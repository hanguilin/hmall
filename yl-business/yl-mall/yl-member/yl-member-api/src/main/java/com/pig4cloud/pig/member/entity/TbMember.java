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
import java.time.LocalDateTime;

/**
 * 用户表
 *
 * @author yl
 * @date 2020-08-23 13:10:03
 */
@Data
@TableName("tb_member")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "用户表")
public class TbMember extends Model<TbMember> {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId(value = "id", type = IdType.AUTO)
	@ApiModelProperty(value = "主键id")
	private Long id;
	/**
	 * 用户名
	 */
	@ApiModelProperty(value = "用户名")
	private String userName;
	/**
	 * 密码，加密存储
	 */
	@ApiModelProperty(value = "密码，加密存储")
	private String password;
	/**
	 * 注册手机号
	 */
	@ApiModelProperty(value = "注册手机号")
	private String telephone;
	/**
	 * 注册邮箱
	 */
	@ApiModelProperty(value = "注册邮箱")
	private String email;
	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	private LocalDateTime created;
	/**
	 * 更新时间
	 */
	@ApiModelProperty(value = "更新时间")
	private LocalDateTime updated;
	/**
	 * 性别
	 */
	@ApiModelProperty(value = "性别")
	private String sex;
	/**
	 * 地址
	 */
	@ApiModelProperty(value = "地址")
	private String address;
	/**
	 * 账户状态
	 */
	@ApiModelProperty(value = "账户状态")
	private String state;
	/**
	 * 头像
	 */
	@ApiModelProperty(value = "头像")
	private String file;
	/**
	 * 描述
	 */
	@ApiModelProperty(value = "描述")
	private String description;
	/**
	 * 积分
	 */
	@ApiModelProperty(value = "积分")
	private Integer points;
	/**
	 * 余额
	 */
	@ApiModelProperty(value = "余额")
	private BigDecimal balance;
}
