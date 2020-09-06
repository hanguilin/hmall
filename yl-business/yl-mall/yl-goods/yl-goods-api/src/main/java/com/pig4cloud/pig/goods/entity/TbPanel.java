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

import java.time.LocalDateTime;
import java.util.List;

/**
 * 内容分类
 *
 * @author yl
 * @date 2020-08-22 11:41:30
 */
@Data
@TableName("tb_panel")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "内容分类")
public class TbPanel extends Model<TbPanel> {
	private static final long serialVersionUID = 1L;

	/**
	 * 类目ID
	 */
	@TableId
	@ApiModelProperty(value = "类目ID")
	private Integer id;
	/**
	 * 板块名称
	 */
	@ApiModelProperty(value = "板块名称")
	private String name;
	/**
	 * 类型 0轮播图 1板块种类一 2板块种类二 3板块种类三
	 */
	@ApiModelProperty(value = "类型 0轮播图 1板块种类一 2板块种类二 3板块种类三 ")
	private Integer type;
	/**
	 * 排列序号
	 */
	@ApiModelProperty(value = "排列序号")
	private Integer sortOrder;
	/**
	 * 所属位置 0首页 1商品推荐 2我要捐赠
	 */
	@ApiModelProperty(value = "所属位置 0首页 1商品推荐 2我要捐赠")
	private Integer position;
	/**
	 * 板块限制商品数量
	 */
	@ApiModelProperty(value = "板块限制商品数量")
	private Integer limitNum;
	/**
	 * 状态
	 */
	@ApiModelProperty(value = "状态")
	private Integer status;
	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	private String remark;
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

	@TableField(exist = false)
	private List<TbPanelContent> panelContents;
}
