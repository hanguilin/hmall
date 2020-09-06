package com.pig4cloud.pig.common.core.util;

import java.util.UUID;

/**
 * Id生成器
 * @author hanguilin
 */
public class IdUtil {

	/**
	 * 生成18位long型id
	 * @return long
	 */
	public static String uuid() {
		String uuid = UUID.randomUUID().toString();
		return uuid.replaceAll("-", "");
	}
}
