package com.pig4cloud.pig.member.dto;


import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 客户
 *
 * @author hanguilin
 */
@Data
public class Member implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * 用户id
	 */
    private Long id;

	/**
	 * 用户账户名
	 */
    private String userName;

	/**
	 * 密码
	 */
    private String password;

	/**
	 * 手机号
	 */
    private String telephone;

	/**
	 * 邮箱
	 */
    private String email;

	/**
	 * 性别
	 */
    private String sex;

	/**
	 * 地址
	 */
    private String address;

	/**
	 * 头像
	 */
    private String file;

	/**
	 * 描述
	 */
    private String description;

	/**
	 * 积分
	 */
    private Integer points;

	/**
	 * 余额
	 */
    private BigDecimal balance;

	/**
	 * 账户状态
	 */
    private int state;

	/**
	 * 认证信息
	 */
    private String token;

    private String message;

}
