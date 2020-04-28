package com.bell.mf.support;

import com.bell.mf.handler.MessageFrameHandlerException;
import com.bell.mf.handler.MessageFrameRequest;
import com.bell.mf.support.handler.AbstractHandler;
import com.bell.mf.support.interceptor.ExecutionChain;
import com.bell.mf.support.repository.HandlerRepository;

/**
 * MessageFrameHandler调度器
 * @author bell.zhouxiaobing
 * @since 1.3
 */
public class DispatcherMessageFrameHandler extends AbstractHandler implements Dispatcher{

	private HandlerRepository repository;
	
	private ExecutionChain executionChain;

	@Override
	public void dispatch(MessageFrameRequest request) throws MessageFrameHandlerException {
		// 执行前
		getExecutionChain().applyPreHandle(request);
		// 执行处理
		doHandle(request);
		// 执行后
		getExecutionChain().applyPostHandle(request);
	}

	public ExecutionChain getExecutionChain() {
		return executionChain;
	}

	public void setExecutionChain(ExecutionChain executionChain) {
		this.executionChain = executionChain;
	}

	public HandlerRepository getRepository() {
		return repository;
	}

	public void setRepository(HandlerRepository repository) {
		this.repository = repository;
	}

}
