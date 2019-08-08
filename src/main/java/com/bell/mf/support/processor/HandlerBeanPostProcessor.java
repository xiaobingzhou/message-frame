package com.bell.mf.support.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;

import com.bell.mf.annotation.MessageFrameHandler;
import com.bell.mf.support.repository.HandlerRepository;

/**
 * MessageFrameHandler注解或接口的后置处理器
 * @author bell.zhouxiaobing
 * @since 1.3
 */
public class HandlerBeanPostProcessor implements BeanPostProcessor, Ordered{

	private static Logger logger = LoggerFactory.getLogger(HandlerBeanPostProcessor.class);
	
	private HandlerRepository repository;
	
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		boolean hasHandlerAnnotation = hasHandlerAnnotation(bean);
		boolean isHandlerImpl = isHandlerImpl(bean);
		if (hasHandlerAnnotation) {
			if (isHandlerImpl) {
				String msg = "["+beanName + "]使用了@MessageFrameHandler注解就不能够是MessageFrameHandler的实现类！";
				logger.error(msg);
				throw new FatalBeanException(msg);
			}
			logger.info("HandlerBeanPostProcessor hasHandlerAnnotation ==> "+beanName);
			repository.setHandler(bean, beanName);
		}else if (isHandlerImpl) {
			logger.info("HandlerBeanPostProcessor isHandlerImpl ==> "+beanName);
			repository.setHandler(bean, beanName);
		}
		return bean;
	}

	private boolean isHandlerImpl(Object bean) {
		return bean instanceof com.bell.mf.handler.MessageFrameHandler;
	}

	private boolean hasHandlerAnnotation(Object bean) {
		boolean hasHandlerAnnotation = bean.getClass().getDeclaredAnnotation(MessageFrameHandler.class) != null;
		return hasHandlerAnnotation;
	}

	@Override
	public int getOrder() {
		return Ordered.HIGHEST_PRECEDENCE;
	}

	public HandlerRepository getRepository() {
		return repository;
	}

	public void setRepository(HandlerRepository repository) {
		this.repository = repository;
	}
	
}
