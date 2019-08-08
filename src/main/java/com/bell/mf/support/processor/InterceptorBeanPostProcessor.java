package com.bell.mf.support.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;

import com.bell.mf.support.interceptor.MessageFrameHandlerExecutionChain;
import com.bell.mf.support.interceptor.MessageFrameHandlerInterceptor;

/**
 * 方法拦截器后置处理器
 * @author bell.zhouxiaobing
 * @since 1.3
 */
public class InterceptorBeanPostProcessor implements BeanPostProcessor, Ordered{

	private static Logger logger = LoggerFactory.getLogger(InterceptorBeanPostProcessor.class);
	
	private MessageFrameHandlerExecutionChain messageFrameHandlerExecutionChain;
	
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if (bean instanceof MessageFrameHandlerInterceptor) {
			logger.info("InterceptorBeanPostProcessor ==> "+beanName);
			messageFrameHandlerExecutionChain.addInterceptor((MessageFrameHandlerInterceptor)bean);
		}
		return bean;
	}

	@Override
	public int getOrder() {
		return Ordered.HIGHEST_PRECEDENCE;
	}

	public MessageFrameHandlerExecutionChain getHandlerExecutionChain() {
		return messageFrameHandlerExecutionChain;
	}

	public void setHandlerExecutionChain(MessageFrameHandlerExecutionChain messageFrameHandlerExecutionChain) {
		this.messageFrameHandlerExecutionChain = messageFrameHandlerExecutionChain;
	}
}
