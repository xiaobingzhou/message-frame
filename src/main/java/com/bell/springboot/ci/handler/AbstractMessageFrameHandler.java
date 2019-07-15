package com.bell.springboot.ci.handler;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.bell.springboot.ci.MessageFrame;
import com.bell.springboot.ci.repository.MessageFrameHandlerRepository;
import com.bell.springboot.ci.repository.ParameterName;

public abstract class AbstractMessageFrameHandler implements MessageFrameHandler {

	/**
	 * 需要子类实现获取到处理器仓库方法
	 * 
	 * @return
	 */
	protected abstract MessageFrameHandlerRepository getRepository();

	/**
	 * 使用反射来调用指令码对应的处理方法
	 */
	@Override
	public void handle(String deviceId, MessageFrame messageFrame, String message) {
		if (messageFrame == null) {
			throw new RuntimeException("messageFrame 为空");
		}
		Method messageFrameHandlerMethod = getRepository().getHandlerMethod(messageFrame.getCommandCode());
		if (messageFrameHandlerMethod == null) {
			throw new RuntimeException("指令码：" + messageFrame.getCommandCode() + " 找不到解析方法！");
		}
		try {
			messageFrameHandlerMethod.invoke(this,
					getMethodArgs(deviceId, messageFrame, message));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Object[] getMethodArgs(String deviceId, MessageFrame messageFrame, String message) {
		List<Object> list = new ArrayList<Object>();
		String[] handlerMethodParameterNames = getRepository().getHandlerMethodParameterNames(messageFrame.getCommandCode());
		System.out.println(Arrays.asList(handlerMethodParameterNames));
		
		for (String parameterName : handlerMethodParameterNames) {
			if (ParameterName.DEVICE_ID.getName().equals(parameterName)) {
				list.add(deviceId);
			} else if (ParameterName.MESSAGE_FRAME.getName().equals(parameterName)) {
				list.add(messageFrame);
			} else if (ParameterName.MESSAGE.getName().equals(parameterName)) {
				list.add(message);
			}
		}
		return list.toArray();
	}

}
