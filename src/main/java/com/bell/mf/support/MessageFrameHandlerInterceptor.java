package com.bell.mf.support;

import com.bell.mf.handler.MessageFrameRequest;

/**
 * 
 * @author bell.zhouxiaobing
 * @since 1.3
 */
public interface MessageFrameHandlerInterceptor {
	
	void preHandle(MessageFrameRequest request);
	
	void postHandle(MessageFrameRequest request);
	
}
