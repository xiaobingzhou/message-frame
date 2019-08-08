package com.bell.mf.support.interceptor;

import java.util.List;

import com.bell.mf.handler.MessageFrameRequest;

/**
 * ExecutionChain接口，定义方法拦截器执行
 * @author bell.zhouxiaobing
 * @since 1.3
 */
public interface ExecutionChain {
	
	List<MessageFrameHandlerInterceptor> getInterceptors();
	
	boolean addInterceptor(MessageFrameHandlerInterceptor interceptor);
	
	void applyPreHandle(MessageFrameRequest request);
	
	void applyPostHandle(MessageFrameRequest request);
}
