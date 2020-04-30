package com.github.xiaobingzhou.messageframe.enums;

import com.alibaba.fastjson.JSONObject;
import com.github.xiaobingzhou.messageframe.IMessageFrame;
import com.github.xiaobingzhou.messageframe.request.HandlerRequest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 支持的参数名
 * @author bell.zhouxiaobing
 * @since 1.2
 */
public enum ParameterNameEnum {

	DEVICE_ID("deviceId", String.class),
	MESSAGE_FRAME("messageFrame", IMessageFrame.class),
	MESSAGE("message", String.class),
	SYS_DATE("sysDate", Date.class),
	BODY_JSON("bodyJson", JSONObject.class),
	REQUEST("request", HandlerRequest.class);

	String name;
	Class clazz;
	private ParameterNameEnum(String name, Class clazz) {
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
		ParameterNameEnum[] values = ParameterNameEnum.values();
		for (ParameterNameEnum value : values) {
			result.add(value.getName() + "("+ value.getClazz().getName() +")");
		}
		return result;
	}

}
