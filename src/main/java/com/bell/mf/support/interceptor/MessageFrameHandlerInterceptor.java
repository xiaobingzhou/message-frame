package com.bell.mf.support.interceptor;

import com.bell.mf.handler.MessageFrameRequest;

/**
 * 定义MessageFrameHandler方法拦截器接口MessageFrameHandlerInterceptor
 * @author bell.zhouxiaobing
 * @since 1.3
 */
public interface MessageFrameHandlerInterceptor {

	/**
	 * 判断该拦截器是否要拦截该请求
	 * @param request
	 * @return true:表示要拦截该请求，false:表示不拦截该请求
	 * @since 1.5.1
	 */
	default boolean support(MessageFrameRequest request) {
		return true;
	}

	void preHandle(MessageFrameRequest request);

	void postHandle(MessageFrameRequest request);

}
