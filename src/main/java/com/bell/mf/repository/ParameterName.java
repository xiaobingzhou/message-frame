package com.bell.mf.repository;

import java.util.ArrayList;
import java.util.List;

/**
 * 支持的参数名
 * @author bell.zhouxiaobing
 *
 */
public enum ParameterName {
	DEVICE_ID("deviceId"), MESSAGE_FRAME("messageFrame"), MESSAGE("message"), SYS_DATE("sysDate");
	String name;
	private ParameterName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public static List<String> getAllName() {
		List<String> result = new ArrayList<String>();
		ParameterName[] values = ParameterName.values();
		for (int i = 0; i < values.length; i++) {
			result.add(values[i].getName());
		}
		return result;
	}
}
