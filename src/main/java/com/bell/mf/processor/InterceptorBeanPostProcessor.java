package com.bell.mf.processor;

import com.bell.mf.interceptor.ExecutionChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;

import com.bell.mf.interceptor.HandlerInterceptor;

/**
 * 方法拦截器后置处理器
 * @author bell.zhouxiaobing
 * @since 1.3
 */
public class InterceptorBeanPostProcessor implements BeanPostProcessor, Ordered{

	private static Logger logger = LoggerFactory.getLogger(InterceptorBeanPostProcessor.class);

	private ExecutionChain executionChain;

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if (bean instanceof HandlerInterceptor) {
			logger.info("InterceptorBeanPostProcessor ==> "+beanName);
			getExecutionChain().addInterceptor((HandlerInterceptor)bean);
		}
		return bean;
	}

	@Override
	public int getOrder() {
		return Ordered.HIGHEST_PRECEDENCE;
	}

	public ExecutionChain getExecutionChain() {
		return executionChain;
	}

	public void setExecutionChain(ExecutionChain executionChain) {
		this.executionChain = executionChain;
	}
}
