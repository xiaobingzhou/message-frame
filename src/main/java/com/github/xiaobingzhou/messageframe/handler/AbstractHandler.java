package com.github.xiaobingzhou.messageframe.handler;

import java.lang.reflect.Method;

import com.github.xiaobingzhou.messageframe.repository.HandlerRepository;
import com.github.xiaobingzhou.messageframe.request.HandlerRequest;

/**
 * AbstractHandler，指令码处理抽象类
 * @author bell.zhouxiaobing
 * @since 1.3
 */
public abstract class AbstractHandler implements Handler {
	
	protected abstract HandlerRepository getHandlerRepository();

	protected abstract Object[] getMethodArgs(HandlerRequest request);
	
	/**
	 * 使用反射来调用指令码对应的处理方法
	 * @param request
	 * @throws HandlerException
	 */
	protected void doHandle(HandlerRequest request) throws HandlerException {
		HandlerRepository handlerRepository = getHandlerRepository();

		Method method = handlerRepository.getHandlerMethod(request);
		if (method == null) {
			throw new HandlerException(String.format("指令码:[%s] 版本号:[%s] 解析方法未找到",
					request.getCommandCode(), request.getProtocolVer()));
		}

		try {
			method.invoke(handlerRepository.getHandler(request),
					getMethodArgs(request));
		} catch (Exception e) {
			throw new HandlerException(String.format("执行[%s]方法出错", method.getName()), e);
		}
	}

}
