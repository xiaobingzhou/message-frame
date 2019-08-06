package com.bell.mf.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;

import com.bell.mf.annotation.MessageFrameHandler;

/**
 * 
 * @author bell.zhouxiaobing
 * @since 1.3
 */
public class AnnotationHandlerBeanPostProcessor implements BeanPostProcessor, Ordered{

	private static Logger logger = LoggerFactory.getLogger(AnnotationHandlerBeanPostProcessor.class);
	
	private AnnotationMessageFrameHandlerRepository repository;
	
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if (bean instanceof com.bell.mf.handler.MessageFrameHandler) {
			String msg = "["+beanName + "]使用了@MessageFrameHandler注解就不能够是MessageFrameHandler的实现类！";
			logger.error(msg);
			throw new FatalBeanException(msg);
		}
		MessageFrameHandler declaredAnnotation = bean.getClass().getDeclaredAnnotation(MessageFrameHandler.class);
		if (declaredAnnotation != null) {
			logger.info("AnnotationHandlerBeanPostProcessor ==> "+beanName);
			repository.setHandler(bean, beanName);
		}
		return bean;
	}

	@Override
	public int getOrder() {
		return Ordered.HIGHEST_PRECEDENCE;
	}

	public AnnotationMessageFrameHandlerRepository getRepository() {
		return repository;
	}

	public void setRepository(AnnotationMessageFrameHandlerRepository repository) {
		this.repository = repository;
	}
	
}
