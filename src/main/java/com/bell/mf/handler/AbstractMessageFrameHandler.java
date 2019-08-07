package com.bell.mf.handler;

import com.bell.mf.repository.MessageFrameHandlerRepository;
import com.bell.mf.support.handler.AbstractHandler;

public abstract class AbstractMessageFrameHandler extends AbstractHandler implements MessageFrameHandler{

	protected abstract MessageFrameHandlerRepository getRepository();
	
	@Override
	public void handle(MessageFrameRequest request) throws MessageFrameHandlerException {
		doHandle(request);
	}
}
