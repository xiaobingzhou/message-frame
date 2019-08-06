package com.bell.mf.support;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.bell.mf.IMessageFrame;
import com.bell.mf.handler.MessageFrameHandlerException;
import com.bell.mf.handler.MessageFrameRequest;
import com.bell.mf.repository.ParameterName;

/**
 * MessageFrameHandler调度器
 * 
 * @author bell.zhouxiaobing
 * @since 1.3
 */
public class DispatcherMessageFrameHandler implements Dispatcher{

	private AnnotationMessageFrameHandlerRepository annotationRepository;
	
	private MessageFrameHandlerExecutionChain handlerExecutionChain;

	@Override
	public void dispatch(MessageFrameRequest request) throws MessageFrameHandlerException {
		// 执行前
		getHandlerExecutionChain().applyPreHandle(request);
		// 执行处理
		handle(request);
		// 执行后
		getHandlerExecutionChain().applyPostHandle(request);
	}

	/**
	 * 使用反射来调用指令码对应的处理方法
	 * @param request
	 * @throws MessageFrameHandlerException
	 */
	protected void handle(MessageFrameRequest request) throws MessageFrameHandlerException{
		IMessageFrame iMessageFrame = request.getMessageFrame();
		Method method = annotationRepository.getHandlerMethod(iMessageFrame.getCommandCode());
		if (method == null) {
			throw new MessageFrameHandlerException("指令码：" + iMessageFrame.getCommandCode() + " 找不到解析方法！");
		}
		try {
			method.invoke(annotationRepository.getHandler(iMessageFrame.getCommandCode()),
					getMethodArgs(request));
		} catch (Exception e) {
			throw new MessageFrameHandlerException(String.format("执行%s方法出错", method.getName()), e);
		}
	}

	protected Object[] getMethodArgs(MessageFrameRequest request) {
		List<Object> list = new ArrayList<Object>();
		String[] handlerMethodParameterNames = annotationRepository.getHandlerMethodParameterNames(request.getMessageFrame().getCommandCode());
		
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

	public AnnotationMessageFrameHandlerRepository getAnnotationRepository() {
		return annotationRepository;
	}

	public void setAnnotationRepository(AnnotationMessageFrameHandlerRepository annotationRepository) {
		this.annotationRepository = annotationRepository;
	}

	public MessageFrameHandlerExecutionChain getHandlerExecutionChain() {
		return handlerExecutionChain;
	}

	public void setHandlerExecutionChain(MessageFrameHandlerExecutionChain handlerExecutionChain) {
		this.handlerExecutionChain = handlerExecutionChain;
	}

}
