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
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 商品表
 *
 * @author yl
 * @date 2020-08-22 11:41:30
 */
@Data
@TableName("tb_item")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "商品表")
public class TbItem extends Model<TbItem> {

	private static final long serialVersionUID = 1L;

	private static final List<String> EMPTY_LIST = Lists.newArrayList();

	/**
	 * 商品id，同时也是商品编号
	 */
	@TableId
	@ApiModelProperty(value = "商品id，同时也是商品编号")
	private Long id;
	/**
	 * 商品标题
	 */
	@ApiModelProperty(value = "商品标题")
	private String title;
	/**
	 * 商品卖点
	 */
	@ApiModelProperty(value = "商品卖点")
	private String sellPoint;
	/**
	 * 商品价格
	 */
	@ApiModelProperty(value = "商品价格")
	private BigDecimal price;
	/**
	 * 库存数量
	 */
	@ApiModelProperty(value = "库存数量")
	private Integer num;
	/**
	 * 售卖数量限制
	 */
	@ApiModelProperty(value = "售卖数量限制")
	private Integer limitNum;
	/**
	 * 商品图片
	 */
	@ApiModelProperty(value = "商品图片")
	private String image;

	@TableField(exist = false)
	private List<String> images;
	/**
	 * 所属分类
	 */
	@ApiModelProperty(value = "所属分类")
	private Long cid;
	/**
	 * 商品状态 1正常 0下架
	 */
	@ApiModelProperty(value = "商品状态 1正常 0下架")
	private String status;
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
	 * 描述
	 */
	@ApiModelProperty(value = "描述")
	private String description;

	@TableField(exist = false)
	@ApiModelProperty(value = "价格区间过滤-小值")
	private BigDecimal priceStart;

	@TableField(exist = false)
	@ApiModelProperty(value = "价格区间过滤-大值")
	private BigDecimal priceEnd;

	@TableField(exist = false)
	@ApiModelProperty(value = "价格排序字段")
	private Integer sortFilter;

	public List<String> getImages(){
		if(StringUtils.isBlank(this.image)){
			return EMPTY_LIST;
		}
		return Splitter.on(",").splitToList(this.image);
	}
}
