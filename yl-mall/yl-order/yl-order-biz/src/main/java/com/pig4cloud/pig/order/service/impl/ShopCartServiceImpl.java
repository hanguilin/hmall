package com.pig4cloud.pig.order.service.impl;

import com.alibaba.fastjson.JSON;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.goods.entity.TbItem;
import com.pig4cloud.pig.goods.feign.RemoteItemService;
import com.pig4cloud.pig.order.dto.CartProduct;
import com.pig4cloud.pig.order.service.ShopCartService;
import com.pig4cloud.pig.order.util.DtoUtil;
import com.pig4cloud.pig.member.util.HashKeyUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 购物车业务实现类
 *
 * @author hanguilin
 */
@Service
public class ShopCartServiceImpl implements ShopCartService {

	private RedisTemplate<String, String> redisTemplate;

	private RemoteItemService remoteItemService;

	@Autowired
	public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@Autowired
	public void setRemoteItemService(RemoteItemService remoteItemService) {
		this.remoteItemService = remoteItemService;
	}

	/**
	 * 添加到购物车
	 *
	 * @param userId 用户id
	 * @param itemId 商品id
	 * @param num 数量
	 * @return 是否添加成功
	 */
	@Override
	public boolean addCart(long userId, long itemId, int num) {
		String hashKey = HashKeyUtil.getCartHashKey(userId);
		HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
		String itemIdStr = String.valueOf(itemId);
		Boolean hasKey = hashOperations.hasKey(hashKey, itemIdStr);
		//如果存在数量相加
		if (hasKey) {
			String json = hashOperations.get(hashKey, itemIdStr);
			if (StringUtils.isNotBlank(json)) {
				CartProduct cartProduct = JSON.parseObject(json, CartProduct.class);
				cartProduct.setProductNum(cartProduct.getProductNum() + num);
				hashOperations.put(hashKey, itemIdStr, JSON.toJSONString(cartProduct));
			} else {
				return false;
			}
			return true;
		}
		//如果不存在，根据商品id取商品信息
		R<TbItem> itemR = remoteItemService.getById(itemId);
		if (itemR == null || itemR.getData() == null) {
			return false;
		}
		CartProduct cartProduct = DtoUtil.tbItemToCartProduct(itemR.getData());
		cartProduct.setProductNum((long) num);
		cartProduct.setChecked("1");
		hashOperations.put(hashKey, itemIdStr, JSON.toJSONString(cartProduct));
		return false;
	}

	/**
	 * 获取用户购物车内商品
	 *
	 * @param userId 用户id
	 * @return List<CartProduct>
	 */
	@Override
	public List<CartProduct> getUserCart(long userId) {
		HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
		List<String> values = hashOperations.values(HashKeyUtil.getCartHashKey(userId));
		List<CartProduct> products = values.stream().map(o -> JSON.parseObject(o, CartProduct.class)).collect(Collectors.toList());
		return products;
	}

}
