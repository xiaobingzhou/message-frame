package com.bell.mf.support.interceptor;

import java.util.List;

import com.bell.mf.handler.MessageFrameRequest;

/**
 * ExecutionChain接口，定义方法拦截器执行
 * @author bell.zhouxiaobing
 * @since 1.3
 */
public interface ExecutionChain {

	// 全局拦截器
	List<MessageFrameHandlerInterceptor> getInterceptors();

	// 指令码拦截器
	List<MessageFrameHandlerInterceptor> getCommandCodeInterceptors(String commandCode);

	boolean addInterceptor(MessageFrameHandlerInterceptor interceptor);
	
	void applyPreHandle(MessageFrameRequest request);
	
	void applyPostHandle(MessageFrameRequest request);
}
