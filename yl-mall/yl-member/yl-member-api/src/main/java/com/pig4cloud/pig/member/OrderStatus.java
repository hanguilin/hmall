package com.pig4cloud.pig.member;

/**
 * 订单状态
 * @author hanguilin
 */
public enum OrderStatus {

	UN_PAY(0, "未付款"),

	PAYED(1, "已付款"),

	UN_SHIP(2, "未发货"),

	SHIPPED(3, "已发货"),

	DEAL_SUCCESS(4, "交易成功"),

	DEAL_CLOSED(5, "交易关闭"),

	DEAL_FAILED(6, "交易失败");

	private int code;

	private String value;

	OrderStatus(int code, String value){
		this.code = code;
		this.value = value;
	}

	public int code(){
		return this.code;
	}
}
