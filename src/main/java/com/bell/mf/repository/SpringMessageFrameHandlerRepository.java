package com.bell.mf.repository;

import com.bell.mf.handler.MessageFrameHandler;
import com.bell.mf.support.repository.AbstractHandlerRepository;

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
