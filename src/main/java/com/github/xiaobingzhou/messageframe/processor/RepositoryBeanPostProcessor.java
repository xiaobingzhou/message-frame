package com.github.xiaobingzhou.messageframe.processor;

import com.github.xiaobingzhou.messageframe.annotation.Handler;
import com.github.xiaobingzhou.messageframe.bind.BindParam;
import com.github.xiaobingzhou.messageframe.codec.BodyCodec;
import com.github.xiaobingzhou.messageframe.interceptor.ExecutionChain;
import com.github.xiaobingzhou.messageframe.interceptor.HandlerInterceptor;
import com.github.xiaobingzhou.messageframe.repository.BindParamRepository;
import com.github.xiaobingzhou.messageframe.repository.BodyCodecRepository;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;

import com.github.xiaobingzhou.messageframe.repository.HandlerRepository;

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

		// 添加解码器
		addBodyCodec(bean, beanName);

		// 添加参数绑定器
		addBindParam(bean, beanName);

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

	protected void addBodyCodec(Object bean, String beanName) {
		if (bean instanceof BodyCodec) {
			log.debug("{}: BodyCodec({}) ==> {}", this.getClass(), bean.getClass(), beanName);
			bodyCodecRepository.setBodyCodec((BodyCodec)bean, beanName);
		}
	}

	protected void addBindParam(Object bean, String beanName) {
		if (bean instanceof BindParam) {
			log.debug("{}: BindParam({}) {}", this.getClass(), bean.getClass(), beanName);
			bindParamRepository.addBindParam((BindParam) bean);
		}
	}

	protected void addInterceptor(Object bean, String beanName) {
		if (bean instanceof HandlerInterceptor) {
			log.debug("{}: HandlerInterceptor({}) ==> {}", this.getClass(), bean.getClass(), beanName);
			executionChain.addInterceptor((HandlerInterceptor)bean);
		}
	}

}
