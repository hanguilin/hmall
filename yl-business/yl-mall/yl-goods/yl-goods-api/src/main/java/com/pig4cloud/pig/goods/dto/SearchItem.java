package com.pig4cloud.pig.goods.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author hanguilin
 */
@Data
public class SearchItem implements Serializable {

	private static final long serialVersionUID = 1L;

    private Long productId;

    private BigDecimal salePrice;

    private String productName;

    private String subTitle;

    private String productImageBig;

    private String categoryName;

    private Integer cid;

}
