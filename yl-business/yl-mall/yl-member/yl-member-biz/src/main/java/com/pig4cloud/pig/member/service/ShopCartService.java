package com.pig4cloud.pig.member.service;

import com.pig4cloud.pig.member.dto.CartProduct;

import java.util.List;

/**
 * 购物车业务类
 * @author hanguilin
 */
public interface ShopCartService {

	/**
	 * 添加到购物车
	 *
	 * @param userId 用户id
	 * @param itemId 商品id
	 * @param num 数量
	 * @return 是否添加成功
	 */
	boolean addCart(long userId, long itemId, int num);

	/**
	 * 获取用户购物车内商品
	 *
	 * @param userId 用户id
	 * @return List<CartProduct>
	 */
	List<CartProduct> getUserCart(long userId);
}
