package com.bell.mf.handler;

import com.bell.mf.repository.MessageFrameHandlerRepository;
import com.bell.mf.support.handler.AbstractHandler;
/**
 * 抽象AbstractMessageFrameHandler，继承抽象AbstractHandler，实现MessageFrameHandler接口
 * @author bell.zhouxiaobing
 * @since 1.2
 * @Deprecated 在类上使用@MessageFrameHandler注解替换继承AbstractMessageFrameHandler抽象类
 */
public abstract class AbstractMessageFrameHandler extends AbstractHandler implements MessageFrameHandler{
	
	protected abstract MessageFrameHandlerRepository getRepository();
	
	@Override
	public void handle(MessageFrameRequest request) throws MessageFrameHandlerException {
		doHandle(request);
	}
}
