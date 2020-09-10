package com.pig4cloud.pig.goods.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author yl
 */
@Data
@EqualsAndHashCode
public class Product implements Serializable {

	private Long productId;

	private BigDecimal salePrice;

	private String productName;

	private String subTitle;

	private String productImageBig;
}
