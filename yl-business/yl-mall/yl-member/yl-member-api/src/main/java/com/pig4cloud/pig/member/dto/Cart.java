package com.pig4cloud.pig.member.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 购物车
 *
 * @author hanguilin
 */
@Data
public class Cart implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 用户id
	 */
    private Long userId;

	/**
	 * 商品id
	 */
	private Long productId;

    private String checked;

	/**
	 * 商品数量
	 */
	private int productNum;

}
