package com.bell.mf.repository;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import com.bell.mf.handler.MessageFrameHandler;


@Component
public class MessageFrameableBeanPostProcessor implements BeanPostProcessor {

	private static Logger logger = LoggerFactory.getLogger(MessageFrameableBeanPostProcessor.class);
	
	@Autowired
	MessageFrameHandlerRepository messageFrameHandlerRepository;
	
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if (bean instanceof MessageFrameHandler) {
			messageFrameHandlerRepository.setMessageFrameHandler((MessageFrameHandler)bean, beanName);
		}
		return bean;
	}

	
	
}
