package com.bell.mf.processor;

import com.bell.mf.annotation.Handler;
import com.bell.mf.bind.BindParam;
import com.bell.mf.codec.BodyCodec;
import com.bell.mf.interceptor.ExecutionChain;
import com.bell.mf.interceptor.HandlerInterceptor;
import com.bell.mf.mapper.MapperField;
import com.bell.mf.repository.BindParamRepository;
import com.bell.mf.repository.BodyCodecRepository;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;

import com.bell.mf.repository.HandlerRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * MessageFrameHandler注解或接口的后置处理器
 * @author bell.zhouxiaobing
 * @since 1.3
 */
@Slf4j
public class RepositoryBeanPostProcessor implements BeanPostProcessor, Ordered{

	@Setter private ExecutionChain executionChain;
	@Setter private HandlerRepository handlerRepository;
	@Setter private BodyCodecRepository bodyCodecRepository;
	@Setter private BindParamRepository bindParamRepository;

	@Override
	public int getOrder() {
		return Ordered.HIGHEST_PRECEDENCE;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		// 添加处理器
		addHandler(bean, beanName);

		// 添加参数绑定器
		addBindParam(bean, beanName);

		// 添加解码器
		addBodyCodec(bean, beanName);

		// 添加拦截器
		addInterceptor(bean, beanName);

		return bean;
	}

	private void addHandler(Object bean, String beanName) {
		boolean hasHandlerAnnotation = bean.getClass().getDeclaredAnnotation(Handler.class) != null;
		if (hasHandlerAnnotation) {
			log.debug("{}: @Handler({}) ==> {}", this.getClass(), bean.getClass(), beanName);
			handlerRepository.setHandler(bean, beanName);
		}
	}

	protected void addBindParam(Object bean, String beanName) {
		if (bean instanceof BindParam) {
			log.debug("{}: BindParam({}) {}", this.getClass(), bean.getClass(), beanName);
			bindParamRepository.addBindParam((BindParam) bean);
		}
	}

	protected void addBodyCodec(Object bean, String beanName) {
		if (bean instanceof BodyCodec) {
			log.debug("{}: BodyCodec({}) ==> {}", this.getClass(), bean.getClass(), beanName);
			BodyCodec bodyCodec = (BodyCodec)bean;
			bodyCodec.getCommandCodes().forEach(c -> {
				bodyCodecRepository.setBodyCodec(c, bodyCodec);
			});
		}
	}

	protected void addInterceptor(Object bean, String beanName) {
		if (bean instanceof HandlerInterceptor) {
			log.debug("{}: HandlerInterceptor({}) ==> {}", this.getClass(), bean.getClass(), beanName);
			executionChain.addInterceptor((HandlerInterceptor)bean);
		}
	}

}
