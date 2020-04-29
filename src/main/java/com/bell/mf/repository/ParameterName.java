package com.bell.mf.repository;

import com.bell.mf.IMessageFrame;
import com.bell.mf.handler.MessageFrameRequest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 支持的参数名
 * @author bell.zhouxiaobing
 * @since 1.2
 */
public enum ParameterName {

	DEVICE_ID("deviceId", String.class),
	MESSAGE_FRAME("messageFrame", IMessageFrame.class),
	MESSAGE("message", String.class),
	SYS_DATE("sysDate", Date.class),
	REQUEST("request", MessageFrameRequest.class);

	String name;
	Class clazz;
	private ParameterName(String name, Class clazz) {
		this.name = name;
		this.clazz = clazz;
	}

	public String getName() {
		return name;
	}

	public Class getClazz() {
		return clazz;
	}

	public static List<String> getAllName() {
		List<String> result = new ArrayList<String>();
		ParameterName[] values = ParameterName.values();
		for (ParameterName value : values) {
			result.add(value.getName() + "("+ value.getClazz().getName() +")");
		}
		return result;
	}

}
