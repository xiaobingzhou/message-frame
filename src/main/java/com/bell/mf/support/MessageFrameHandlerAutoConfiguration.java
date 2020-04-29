package com.bell.mf.support;

import com.bell.mf.bind.BindParam;
import com.bell.mf.bind.impl.*;
import com.bell.mf.support.interceptor.ExecutionChain;
import com.bell.mf.support.processor.BindParamBeanPostProcessor;
import com.bell.mf.support.processor.BodyCodecBeanPostProcessor;
import com.bell.mf.support.repository.*;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.bell.mf.repository.MessageFrameHandlerRepository;
import com.bell.mf.repository.SpringMessageFrameHandlerRepository;
import com.bell.mf.support.interceptor.MessageFrameHandlerExecutionChain;
import com.bell.mf.support.processor.HandlerBeanPostProcessor;
import com.bell.mf.support.processor.InterceptorBeanPostProcessor;

/**
 * MessageFrameHandlerAutoConfiguration自动配置类
 * @author bell.zhouxiaobing
 * @since 1.3
 */
@Configuration
public class MessageFrameHandlerAutoConfiguration {

	@Bean
	public Dispatcher dispatcher() {
		DispatcherHandlerEnhanceMethodParam dispatcher = new DispatcherHandlerEnhanceMethodParam();
		dispatcher.setExecutionChain(executionChain());
		dispatcher.setRepository(handlerRepository());
		dispatcher.setBodyCodecRepository(bodyCodecRepository());
        dispatcher.setBindParamRepository(bindParamRepository());
		return dispatcher;
	}

    @Bean
	public ExecutionChain executionChain() {
		return new MessageFrameHandlerExecutionChain();
	}

	@Bean
	public BeanPostProcessor interceptorBeanPostProcessor() {
		InterceptorBeanPostProcessor interceptorBeanPostProcessor = new InterceptorBeanPostProcessor();
		interceptorBeanPostProcessor.setExecutionChain(executionChain());
		return interceptorBeanPostProcessor;
	}

	@Bean
	public BeanPostProcessor handlerBeanPostProcessor() {
		HandlerBeanPostProcessor beanPostProcessor = new HandlerBeanPostProcessor();
		beanPostProcessor.setRepository(handlerRepository());
		return beanPostProcessor;
	}

	@Bean
	public BeanPostProcessor bodyCodecBeanPostProcessor() {
		BodyCodecBeanPostProcessor beanPostProcessor = new BodyCodecBeanPostProcessor();
		beanPostProcessor.setRepository(bodyCodecRepository());
		return beanPostProcessor;
	}

	@Bean
	public BeanPostProcessor bindParamBeanPostProcessor() {
		BindParamBeanPostProcessor beanPostProcessor = new BindParamBeanPostProcessor();
		beanPostProcessor.setRepository(bindParamRepository());
		return beanPostProcessor;
	}

	@Bean
	public HandlerRepository handlerRepository() {
		return new AnnotationSpringMessageFrameHandlerRepository();
	}

	@Bean
	public BodyCodecRepository bodyCodecRepository() {
		return new BodyCodecRepositoryImpl();
	}

	@Bean
	public BindParamRepository bindParamRepository() {
		return new BindParamRepositoryImpl();
	}

	@Bean
	public MessageFrameHandlerRepository messageFrameHandlerRepository() {
		return new SpringMessageFrameHandlerRepository();
	}

	@Bean
	public InitChecker initChecker() {
		return new InitChecker();
	}

	@Bean
	public DeviceIdBindParam deviceIdBindParam() {
		return new DeviceIdBindParam();
	}

	@Bean
	public MessageBindParam messageBindParam() {
		return new MessageBindParam();
	}

	@Bean
	public MessageFrameBindParam messageFrameBindParam() {
		return new MessageFrameBindParam();
	}

	@Bean
	public BindParam requestBindParam() {
		return new RequestBindParam();
	}

	@Bean
	public BindParam sysDateBindParam() {
		return new SysDateBindParam();
	}

	@Bean
	public BindParam bodyJsonBindParam() {
		return new BodyJsonBindParam();
	}

}
