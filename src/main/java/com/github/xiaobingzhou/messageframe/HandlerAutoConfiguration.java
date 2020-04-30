package com.github.xiaobingzhou.messageframe;

import com.github.xiaobingzhou.messageframe.bind.BindParam;
import com.github.xiaobingzhou.messageframe.bind.impl.*;
import com.github.xiaobingzhou.messageframe.interceptor.ExecutionChain;
import com.github.xiaobingzhou.messageframe.mapper.Mapper;
import com.github.xiaobingzhou.messageframe.repository.BindParamRepository;
import com.github.xiaobingzhou.messageframe.repository.BodyCodecRepository;
import com.github.xiaobingzhou.messageframe.repository.HandlerRepository;
import com.github.xiaobingzhou.messageframe.repository.impl.HandlerRepositoryImpl;
import com.github.xiaobingzhou.messageframe.repository.impl.BindParamRepositoryImpl;
import com.github.xiaobingzhou.messageframe.repository.impl.BodyCodecRepositoryImpl;
import com.github.xiaobingzhou.messageframe.processor.RepositoryBeanPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.github.xiaobingzhou.messageframe.interceptor.ExecutionChainImpl;

/**
 * MessageFrameHandlerAutoConfiguration自动配置类
 * @author bell.zhouxiaobing
 * @since 1.3
 */
@Configuration
public class HandlerAutoConfiguration {

	@Bean
	public Dispatcher dispatcher() {
		DispatcherImpl dispatcher = new DispatcherImpl();
		dispatcher.setExecutionChain(executionChain());
		dispatcher.setHandlerRepository(handlerRepository());
        dispatcher.setBindParamRepository(bindParamRepository());
		return dispatcher;
	}

	@Bean
	public BeanPostProcessor beanPostProcessor() {
		RepositoryBeanPostProcessor beanPostProcessor = new RepositoryBeanPostProcessor();
		beanPostProcessor.setHandlerRepository(handlerRepository());
		beanPostProcessor.setBindParamRepository(bindParamRepository());
		beanPostProcessor.setBodyCodecRepository(bodyCodecRepository());
		beanPostProcessor.setExecutionChain(executionChain());
		return beanPostProcessor;
	}

	@Bean
	public HandlerRepository handlerRepository() {
		return new HandlerRepositoryImpl();
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
	public ExecutionChain executionChain() {
		return new ExecutionChainImpl();
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

	@Bean
	public Mapper mapper() {
		return new Mapper(bodyCodecRepository());
	}

}
