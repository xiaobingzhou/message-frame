package com.bell.mf.support.interceptor;

import com.bell.mf.handler.MessageFrameRequest;

/**
 * 定义MessageFrameHandler方法拦截器接口MessageFrameHandlerInterceptor
 * @author bell.zhouxiaobing
 * @since 1.3
 */
public interface MessageFrameHandlerInterceptor {
	
	void preHandle(MessageFrameRequest request);
	
	void postHandle(MessageFrameRequest request);
	
}
