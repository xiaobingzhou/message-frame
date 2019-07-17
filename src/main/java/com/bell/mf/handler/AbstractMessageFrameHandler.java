package com.bell.mf.handler;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
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
	 * @param request
	 * @throws MessageFrameHandlerException
	 */
	@Override
	public void handle(MessageFrameRequest request) throws MessageFrameHandlerException{
		MessageFrame messageFrame = request.getMessageFrame();
		Method method = getRepository().getHandlerMethod(messageFrame.getCommandCode());
		if (method == null) {
			throw new MessageFrameHandlerException("指令码：" + messageFrame.getCommandCode() + " 找不到解析方法！");
		}
		try {
			method.invoke(this,
					getMethodArgs(request));
		} catch (Exception e) {
			throw new MessageFrameHandlerException(String.format("执行%s方法出错", method.getName()), e);
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
