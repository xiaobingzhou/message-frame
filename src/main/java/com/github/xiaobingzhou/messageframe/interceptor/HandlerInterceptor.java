package com.github.xiaobingzhou.messageframe.interceptor;

import com.github.xiaobingzhou.messageframe.request.HandlerRequest;
import com.github.xiaobingzhou.messageframe.response.HandlerResponse;

/**
 * 定义Handler接口的拦截器接口HandlerInterceptor
 * @author bell.zhouxiaobing
 * @since 1.3
 */
public interface HandlerInterceptor {

	/**
	 * 判断该拦截器是否要拦截该请求
	 * @param request 请求
	 * @return true:表示要拦截该请求，false:表示不拦截该请求
	 * @since 1.5.1
	 */
	default boolean support(HandlerRequest request) {
		return true;
	}

	default void preHandle(HandlerRequest request) {};

	default void postHandle(HandlerRequest request) {};

	/**
	 * 发送响应后处理
	 * @param response 响应
	 */
	default void afterSend(HandlerResponse response) {};
}
