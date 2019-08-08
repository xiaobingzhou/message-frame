package com.bell.mf.repository;

import com.bell.mf.handler.MessageFrameHandler;
import com.bell.mf.support.repository.AbstractHandlerRepository;

/**
 * 继承 {@link AbstractHandlerRepository} 抽象类</br>
 * 实现 {@link MessageFrameHandlerRepository} 接口</br>
 * @author bell.zhouxiaobing
 * @since 1.2
 */
public class SpringMessageFrameHandlerRepository extends AbstractHandlerRepository implements MessageFrameHandlerRepository{

	@Override
	public void setHandler(MessageFrameHandler messageFrameHandler, String beanName) {
		super.setHandler(messageFrameHandler, beanName);
	}

	@Override
	public MessageFrameHandler getHandler(String commandCode) {
		return (MessageFrameHandler)super.getHandler(commandCode);
	}
	
}
