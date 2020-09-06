package com.pig4cloud.pig.member.util;

import com.pig4cloud.pig.goods.entity.TbItem;
import com.pig4cloud.pig.member.dto.CartProduct;
import com.pig4cloud.pig.member.dto.Member;
import com.pig4cloud.pig.member.entity.TbMember;

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
	 * tbMemer实体转Member
	 *
	 * @param tbMemer 用户
	 * @return Member
	 */
	public static Member tbMemerToMember(TbMember tbMemer) {
		Member member = new Member();
		member.setId(tbMemer.getId());
		member.setUserName(tbMemer.getUserName());
		member.setEmail(tbMemer.getEmail());
		member.setTelephone(tbMemer.getTelephone());
		member.setAddress(tbMemer.getAddress());
		member.setBalance(tbMemer.getBalance());
		member.setFile(tbMemer.getFile());
		member.setPoints(tbMemer.getPoints());
		member.setSex(tbMemer.getSex());
		member.setDescription(tbMemer.getDescription());
		return member;
	}

}
