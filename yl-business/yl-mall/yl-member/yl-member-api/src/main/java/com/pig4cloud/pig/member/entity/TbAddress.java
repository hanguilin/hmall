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

/**
 * @author yl
 * @date 2020-08-23 13:10:03
 */
@Data
@TableName("tb_address")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "地址")
public class TbAddress extends Model<TbAddress> {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId(value = "id", type = IdType.AUTO)
	@ApiModelProperty(value = "主键id")
	private Long id;
	/**
	 * 客户id
	 */
	@ApiModelProperty(value = "客户id")
	private Long memberId;
	/**
	 * 接收人姓名
	 */
	@ApiModelProperty(value = "接收人姓名")
	private String receiverName;
	/**
	 * 接收人联系电话
	 */
	@ApiModelProperty(value = "接收人联系电话")
	private String receiverTelephone;
	/**
	 * 接收人住址
	 */
	@ApiModelProperty(value = "接收人住址")
	private String receiverAddress;
	/**
	 * 是否默认地址
	 */
	@ApiModelProperty(value = "是否默认地址")
	private String isDefault;

	/**
	 * 逻辑删除
	 */
	@TableLogic
	private String delFlag;
}
