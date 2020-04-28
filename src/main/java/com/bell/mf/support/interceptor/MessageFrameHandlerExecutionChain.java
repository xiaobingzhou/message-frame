package com.bell.mf.support.interceptor;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bell.mf.handler.MessageFrameRequest;
import org.springframework.core.OrderComparator;

/**
 * ExecutionChain接口实现
 * @author bell.zhouxiaobing
 * @since 1.3
 */
public class MessageFrameHandlerExecutionChain implements ExecutionChain{

	private static final Logger logger = LoggerFactory.getLogger(MessageFrameHandlerExecutionChain.class);

	private List<MessageFrameHandlerInterceptor> interceptors = new ArrayList<MessageFrameHandlerInterceptor>();

	public boolean addInterceptor(MessageFrameHandlerInterceptor interceptor) {
		if (interceptor == null) {
			logger.info("interceptor is null!");
			return false;
		}
		boolean add = this.interceptors.add(interceptor);
		OrderComparator.sort(interceptors);// 排序
		return add;
	}
	
	public void applyPreHandle(MessageFrameRequest request) {
		for (MessageFrameHandlerInterceptor messageFrameHandlerInterceptor : interceptors) {
			if (messageFrameHandlerInterceptor.support(request)) {
				messageFrameHandlerInterceptor.preHandle(request);
			}
		}
	}
	public void applyPostHandle(MessageFrameRequest request) {
		for (MessageFrameHandlerInterceptor messageFrameHandlerInterceptor : interceptors) {
			if (messageFrameHandlerInterceptor.support(request)) {
				messageFrameHandlerInterceptor.postHandle(request);
			}
		}
	}

	@Override
	public String toString() {
		return "MessageFrameHandlerExecutionChain [interceptors=" + interceptors + "]";
	}

	public List<MessageFrameHandlerInterceptor> getInterceptors() {
		return interceptors;
	}
	
	public void setInterceptors(List<MessageFrameHandlerInterceptor> interceptors) {
		this.interceptors = interceptors;
	}
	
}
