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

package com.pig4cloud.pig.goods.entity;

import com.baomidou.mybatisplus.annotation.TableField;
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
 * @author yl
 * @date 2020-08-22 11:41:30
 */
@Data
@TableName("tb_panel_content")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "")
public class TbPanelContent extends Model<TbPanelContent> {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	@ApiModelProperty(value = "")
	private Integer id;
	/**
	 * 所属板块id
	 */
	@ApiModelProperty(value = "所属板块id")
	private Integer panelId;
	/**
	 * 类型 0关联商品 1其他链接
	 */
	@ApiModelProperty(value = "类型 0关联商品 1其他链接")
	private Integer type;
	/**
	 * 关联商品id
	 */
	@ApiModelProperty(value = "关联商品id")
	private Long productId;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private Integer sortOrder;
	/**
	 * 其他链接
	 */
	@ApiModelProperty(value = "其他链接")
	private String fullUrl;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private String picUrl;
	/**
	 * 3d轮播图备用
	 */
	@ApiModelProperty(value = "3d轮播图备用")
	private String picUrl2;
	/**
	 * 3d轮播图备用
	 */
	@ApiModelProperty(value = "3d轮播图备用")
	private String picUrl3;
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
	 * 关联商品信息
	 */
	@TableField(exist = false)
	private BigDecimal salePrice;

	@TableField(exist = false)
	private String productName;

	@TableField(exist = false)
	private String subTitle;

	@TableField(exist = false)
	private String productImageBig;
}
