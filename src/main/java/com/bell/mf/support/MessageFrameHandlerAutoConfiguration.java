package com.bell.mf.support;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @author bell.zhouxiaobing
 * @since 1.3
 */
@Configuration
public class MessageFrameHandlerAutoConfiguration {

	@Bean
	public DispatcherMessageFrameHandler dispatcherMessageFrameHandler() {
		DispatcherMessageFrameHandler dispatcherMessageFrameHandler = new DispatcherMessageFrameHandler();
		dispatcherMessageFrameHandler.setHandlerExecutionChain(messageFrameHandlerExecutionChain());
		dispatcherMessageFrameHandler.setAnnotationRepository(annotationmessageFrameHandlerRepository());
		return dispatcherMessageFrameHandler;
	}
	
	@Bean
	public AnnotationHandlerBeanPostProcessor annotationHandlerBeanPostProcessor() {
		AnnotationHandlerBeanPostProcessor beanPostProcessor = new AnnotationHandlerBeanPostProcessor();
		beanPostProcessor.setRepository(annotationmessageFrameHandlerRepository());
		return beanPostProcessor;
	}

	@Bean
	public AnnotationMessageFrameHandlerRepository annotationmessageFrameHandlerRepository() {
		return new AnnotationSpringMessageFrameHandlerRepository();
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
