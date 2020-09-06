package com.pig4cloud.pig.member.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 日期格式化
 * @author hanguilin
 */
public class DateUtil {

	private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	/**
	 * 格式化成字符串
	 *
	 * @param localDateTime 日期
	 * @return 日期字符串
	 */
	public static String format(LocalDateTime localDateTime){
		return localDateTime.format(DATE_TIME_FORMAT);
	}
}
