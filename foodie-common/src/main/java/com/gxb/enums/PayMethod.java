package com.gxb.enums;

/**
 * @Description: 支付方式 枚举
 */
public enum PayMethod {

	WEIXIN(1, "微信"),
	ALIPAY(2, "支付宝");

	private final Integer type;
	private final String value;

	PayMethod(Integer type, String value){
		this.type = type;
		this.value = value;
	}

	public Integer getType() {
		return type;
	}

	public String getValue() {
		return value;
	}}
