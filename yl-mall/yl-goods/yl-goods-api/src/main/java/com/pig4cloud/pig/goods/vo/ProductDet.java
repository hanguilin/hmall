package com.pig4cloud.pig.goods.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author yl
 */
@Data
@EqualsAndHashCode
public class ProductDet implements Serializable {

	/**
	 * 产品id
	 */
	private Long productId;

	/**
	 * 价格
	 */
	private BigDecimal salePrice;

	/**
	 * 产品名称
	 */
	private String productName;

	/**
	 * 产品子标题
	 */
	private String subTitle;

	/**
	 * 限制数量
	 */
	private Long limitNum;

	/**
	 * 产品大图
	 */
	private String productImageBig;

	/**
	 * 产品描述
	 */
	private String detail;

	/**
	 * 产品小图
	 */
	private List<String> productImageSmall;

}
