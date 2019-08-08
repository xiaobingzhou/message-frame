package com.bell.mf.support;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bell.mf.repository.MessageFrameHandlerRepository;
import com.bell.mf.repository.SpringMessageFrameHandlerRepository;
import com.bell.mf.support.interceptor.MessageFrameHandlerExecutionChain;
import com.bell.mf.support.processor.HandlerBeanPostProcessor;
import com.bell.mf.support.processor.InterceptorBeanPostProcessor;
import com.bell.mf.support.repository.AnnotationSpringMessageFrameHandlerRepository;
import com.bell.mf.support.repository.HandlerRepository;

/**
 * MessageFrameHandlerAutoConfiguration自动配置类
 * @author bell.zhouxiaobing
 * @since 1.3
 */
@Configuration
public class MessageFrameHandlerAutoConfiguration {

	@Bean
	public Dispatcher dispatcher() {
		DispatcherMessageFrameHandler dispatcherMessageFrameHandler = new DispatcherMessageFrameHandler();
		dispatcherMessageFrameHandler.setHandlerExecutionChain(messageFrameHandlerExecutionChain());
		dispatcherMessageFrameHandler.setRepository(handlerRepository());
		return dispatcherMessageFrameHandler;
	}
	
	@Bean
	public HandlerBeanPostProcessor handlerBeanPostProcessor() {
		HandlerBeanPostProcessor beanPostProcessor = new HandlerBeanPostProcessor();
		beanPostProcessor.setRepository(handlerRepository());
		return beanPostProcessor;
	}

	@Bean
	public HandlerRepository handlerRepository() {
		return new AnnotationSpringMessageFrameHandlerRepository();
	}
	
	@Bean
	public MessageFrameHandlerRepository messageFrameHandlerRepository() {
		return new SpringMessageFrameHandlerRepository();
	}
	
	@Bean
	public InterceptorBeanPostProcessor interceptorBeanPostProcessor() {
		InterceptorBeanPostProcessor interceptorBeanPostProcessor = new InterceptorBeanPostProcessor();
		interceptorBeanPostProcessor.setHandlerExecutionChain(messageFrameHandlerExecutionChain());
		return interceptorBeanPostProcessor;
	}

	@Bean
	public MessageFrameHandlerExecutionChain messageFrameHandlerExecutionChain() {
		return new MessageFrameHandlerExecutionChain();
	}
	
}
