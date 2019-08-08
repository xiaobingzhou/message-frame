package com.bell.mf.support.handler;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.bell.mf.IMessageFrame;
import com.bell.mf.handler.MessageFrameHandlerException;
import com.bell.mf.handler.MessageFrameRequest;
import com.bell.mf.repository.ParameterName;
import com.bell.mf.support.repository.HandlerRepository;

/**
 * 
 * @author bell.zhouxiaobing
 * @since 1.3
 */
public abstract class AbstractHandler implements Handler{
	
	protected abstract HandlerRepository getRepository();
	
	/**
	 * 使用反射来调用指令码对应的处理方法
	 * @param request
	 * @throws MessageFrameHandlerException
	 */
	protected void doHandle(MessageFrameRequest request) throws MessageFrameHandlerException{
		IMessageFrame iMessageFrame = request.getMessageFrame();
		Method method = getRepository().getHandlerMethod(iMessageFrame.getCommandCode());
		if (method == null) {
			throw new MessageFrameHandlerException("指令码：" + iMessageFrame.getCommandCode() + " 找不到解析方法！");
		}
		try {
			method.invoke(getRepository().getHandler(iMessageFrame.getCommandCode()),
					getMethodArgs(request));
		} catch (Exception e) {
			throw new MessageFrameHandlerException(String.format("执行%s方法出错", method.getName()), e);
		}
	}

	protected Object[] getMethodArgs(MessageFrameRequest request) {
		List<Object> list = new ArrayList<Object>();
		String[] handlerMethodParameterNames = getRepository().getHandlerMethodParameterNames(request.getMessageFrame().getCommandCode());
		
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
