package com.pig4cloud.pig.member.util;

/**
 * 生成redis中hashkey
 * @author hanguilin
 */
public class HashKeyUtil {

	/**
	 * session前缀
	 */
	private static final String SESSION_PRE = "session_";

	/**
	 * 购物车前缀
	 */
	private static final String CART_PRE = "cart_";

	/**
	 * 货物购物车hashkey
	 *
	 * @param userId 用户id
	 * @return redis的hashkey
	 */
	public static String getCartHashKey(long userId){
		return CART_PRE + userId;
	}

	/**
	 * Session hashkey
	 *
	 * @param token 用户认证信息
	 * @return redis的hashkey
	 */
	public static String getSessionKey(String token){
		return SESSION_PRE + token;
	}
}
