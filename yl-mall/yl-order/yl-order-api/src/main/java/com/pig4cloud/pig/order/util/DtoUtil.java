package com.pig4cloud.pig.order.util;

import com.pig4cloud.pig.goods.entity.TbItem;
import com.pig4cloud.pig.order.dto.CartProduct;
import com.pig4cloud.pig.order.entity.TbOrderItem;

/**
 * dto对象转换类
 *
 * @author hanguilin
 */
public class DtoUtil {

	/**
	 * 购物车单件商品默认限制数量
	 */
	private static final long DEFAULT_LIMIT = 10;

	/**
	 * item实体转CartProduct
	 *
	 * @param tbItem 商品
	 * @return CartProduct
	 */
	public static CartProduct tbItemToCartProduct(TbItem tbItem) {
		CartProduct cartProduct = new CartProduct();

		cartProduct.setProductId(tbItem.getId());
		cartProduct.setProductName(tbItem.getTitle());
		cartProduct.setSalePrice(tbItem.getPrice());
		cartProduct.setProductImg(tbItem.getImages().get(0));
		if (tbItem.getLimitNum() == null) {
			cartProduct.setLimitNum(Long.valueOf(tbItem.getNum()));
		} else if (tbItem.getLimitNum() < 0 && tbItem.getNum() < 0) {
			cartProduct.setLimitNum(DEFAULT_LIMIT);
		} else {
			cartProduct.setLimitNum(Long.valueOf(tbItem.getLimitNum()));
		}
		return cartProduct;
	}

	/**
	 * TbOrderItem实体转CartProduct
	 *
	 * @param tbOrderItem 订单表实体
	 * @return CartProduct
	 */
	public static CartProduct tbOrderItemToCartProduct(TbOrderItem tbOrderItem) {
		CartProduct cartProduct = new CartProduct();
		cartProduct.setProductId(Long.valueOf(tbOrderItem.getItemId()));
		cartProduct.setProductName(tbOrderItem.getTitle());
		cartProduct.setSalePrice(tbOrderItem.getPrice());
		cartProduct.setProductNum(Long.valueOf(tbOrderItem.getNum()));
		cartProduct.setProductImg(tbOrderItem.getPicPath());
		return cartProduct;
	}

}
