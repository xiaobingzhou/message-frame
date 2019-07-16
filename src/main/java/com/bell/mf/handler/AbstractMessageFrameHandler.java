package com.bell.mf.handler;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.bell.mf.MessageFrame;
import com.bell.mf.repository.MessageFrameHandlerRepository;
import com.bell.mf.repository.ParameterName;

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
	public void handle(MessageFrameRequest request) {
		if (request == null) {
			throw new RuntimeException("request 为空");
		}
		MessageFrame messageFrame = request.getMessageFrame();
		Method messageFrameHandlerMethod = getRepository().getHandlerMethod(messageFrame.getCommandCode());
		if (messageFrameHandlerMethod == null) {
			throw new RuntimeException("指令码：" + messageFrame.getCommandCode() + " 找不到解析方法！");
		}
		try {
			messageFrameHandlerMethod.invoke(this,
					getMethodArgs(request));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Object[] getMethodArgs(MessageFrameRequest request) {
		List<Object> list = new ArrayList<Object>();
		String[] handlerMethodParameterNames = getRepository().getHandlerMethodParameterNames(request.getMessageFrame().getCommandCode());
		System.out.println(Arrays.asList(handlerMethodParameterNames));
		
		for (String parameterName : handlerMethodParameterNames) {
			if (ParameterName.DEVICE_ID.getName().equals(parameterName)) {
				list.add(request.getDeviceId());
			} else if (ParameterName.MESSAGE_FRAME.getName().equals(parameterName)) {
				list.add(request.getMessageFrame());
			} else if (ParameterName.MESSAGE.getName().equals(parameterName)) {
				list.add(request.getMessage());
			} else if (ParameterName.SYS_DATE.getName().equals(parameterName)) {
				list.add(request.getSystemDate());
			}
		}
		return list.toArray();
	}

}
