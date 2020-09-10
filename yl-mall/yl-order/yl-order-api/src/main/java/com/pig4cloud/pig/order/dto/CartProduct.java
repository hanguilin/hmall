package com.pig4cloud.pig.order.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author hanguilin
 */
@Data
public class CartProduct implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * 产品id
	 */
    private Long productId;

	/**
	 * 售价
	 */
	private BigDecimal salePrice;

	/**
	 * 数量
	 */
    private Long productNum;

	/**
	 * 限购数量
	 */
	private Long limitNum;

    private String checked;

	/**
	 * 产品名称
	 */
	private String productName;

	/**
	 * 产品图片
	 */
    private String productImg;

}
