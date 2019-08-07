package com.bell.mf.support;

import com.bell.mf.handler.MessageFrameHandlerException;
import com.bell.mf.handler.MessageFrameRequest;
import com.bell.mf.support.handler.AbstractHandler;
import com.bell.mf.support.interceptor.MessageFrameHandlerExecutionChain;
import com.bell.mf.support.repository.HandlerRepository;

/**
 * MessageFrameHandler调度器
 * 
 * @author bell.zhouxiaobing
 * @since 1.3
 */
public class DispatcherMessageFrameHandler extends AbstractHandler implements Dispatcher{

	private HandlerRepository repository;
	
	private MessageFrameHandlerExecutionChain handlerExecutionChain;

	@Override
	public void dispatch(MessageFrameRequest request) throws MessageFrameHandlerException {
		// 执行前
		getHandlerExecutionChain().applyPreHandle(request);
		// 执行处理
		doHandle(request);
		// 执行后
		getHandlerExecutionChain().applyPostHandle(request);
	}

	public MessageFrameHandlerExecutionChain getHandlerExecutionChain() {
		return handlerExecutionChain;
	}

	public void setHandlerExecutionChain(MessageFrameHandlerExecutionChain handlerExecutionChain) {
		this.handlerExecutionChain = handlerExecutionChain;
	}

	public HandlerRepository getRepository() {
		return repository;
	}

	public void setRepository(HandlerRepository repository) {
		this.repository = repository;
	}

}
