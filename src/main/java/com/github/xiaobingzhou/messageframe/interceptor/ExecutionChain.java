package com.github.xiaobingzhou.messageframe.interceptor;

import java.util.List;

import com.github.xiaobingzhou.messageframe.request.HandlerRequest;
import com.github.xiaobingzhou.messageframe.response.HandlerResponse;

/**
 * ExecutionChain接口，定义方法拦截器执行
 * @author bell.zhouxiaobing
 * @since 1.3
 */
public interface ExecutionChain {

	// 全局拦截器
	List<HandlerInterceptor> getInterceptors();

	// 指令码拦截器
	List<HandlerInterceptor> getCommandCodeInterceptors(String commandCode);

	boolean addInterceptor(HandlerInterceptor interceptor);
	
	void applyPreHandle(HandlerRequest request);
	
	void applyPostHandle(HandlerRequest request);

	void triggerAfterSend(HandlerResponse response);
}
