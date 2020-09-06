package com.pig4cloud.pig.member.controller;

import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.security.annotation.Inner;
import com.pig4cloud.pig.member.dto.Cart;
import com.pig4cloud.pig.member.dto.CartProduct;
import com.pig4cloud.pig.member.service.ShopCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 购物车
 *
 * @author hanguilin
 */
@Inner(value = false)
@RequiredArgsConstructor
@RestController
@RequestMapping("/shopcart")
@Api(value = "shopcart", tags = "购物车管理")
public class ShopCartController {

	/**
	 * 购物车业务类
	 */
	private final ShopCartService shopCarService;

	/**
	 * 添加进购物车
	 *
	 * @param cart 购物车实体
	 * @return R<Boolean>
	 */
	@ApiOperation(value = "添加进购物车", notes = "添加进购物车")
	@PostMapping("/add")
	public R<Boolean> doAddToCart(@RequestBody Cart cart) {
		boolean flag = shopCarService.addCart(cart.getUserId(), cart.getProductId(), cart.getProductNum());
		return flag ? R.ok() : R.failed();
	}


	/**
	 * 获取购物车商品列表
	 *
	 * @param cart 过滤实体
	 * @return R<List<CartProduct>>
	 */
	@ApiOperation(value = "获取购物车商品列表", notes = "获取购物车商品列表")
	@PostMapping("/list")
	public R<List<CartProduct>> getCartList(@RequestBody Cart cart) {
		List<CartProduct> list = shopCarService.getUserCart(cart.getUserId());
		return R.ok(list);
	}
}
