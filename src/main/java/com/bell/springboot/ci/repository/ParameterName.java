package com.bell.springboot.ci.repository;

/**
 * 支持的参数名
 * @author bell.zhouxiaobing
 *
 */
public enum ParameterName {
	DEVICE_ID("deviceId"), MESSAGE_FRAME("messageFrame"), MESSAGE("message");
	String name;
	private ParameterName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
}
