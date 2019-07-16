package com.bell.mf.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import com.bell.mf.handler.MessageFrameHandler;

/**
 * MessageFrameHandler后置处理器
 * @author bell.zhouxiaobing
 *
 */
@Component
public class MessageFrameHandlerBeanPostProcessor implements BeanPostProcessor, Ordered{

	private static Logger logger = LoggerFactory.getLogger(MessageFrameHandlerBeanPostProcessor.class);
	@Autowired
	MessageFrameHandlerRepository messageFrameHandlerRepository;
	
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if (bean instanceof MessageFrameHandler) {
			logger.info("MessageFrameHandlerBeanPostProcessor ==> "+beanName);
			messageFrameHandlerRepository.setHandler((MessageFrameHandler)bean, beanName);
		}
		return bean;
	}

	@Override
	public int getOrder() {
		return Ordered.HIGHEST_PRECEDENCE;
	}

	
	
}
