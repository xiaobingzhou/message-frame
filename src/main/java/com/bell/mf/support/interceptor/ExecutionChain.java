package com.bell.mf.support.interceptor;

import java.util.List;

import com.bell.mf.handler.MessageFrameRequest;

public interface ExecutionChain {
	
	List<MessageFrameHandlerInterceptor> getInterceptors();
	
	boolean addInterceptor(MessageFrameHandlerInterceptor interceptor);
	
	void applyPreHandle(MessageFrameRequest request);
	
	void applyPostHandle(MessageFrameRequest request);
}
