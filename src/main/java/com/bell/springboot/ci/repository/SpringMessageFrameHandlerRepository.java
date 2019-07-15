package com.bell.springboot.ci.repository;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.stereotype.Component;

import com.bell.springboot.ci.annotation.CommandCode;
import com.bell.springboot.ci.handler.MessageFrameHandler;

@Component
public class SpringMessageFrameHandlerRepository implements MessageFrameHandlerRepository, ApplicationContextAware, DisposableBean{
	
	private ApplicationContext applicationContext;
	
	/**
	 * 保存指令码和spring中的beanName的对应关系
	 */
	private static Map<String, String> MESSAGE_FRAME_HANDLER_MAP = new HashMap<>(128);
	
	/**
	 * 保存指令码和方法的对应关系
	 */
	private static Map<String, Method> COMMAND_CODE_WITH_METHOD_MAP = new HashMap<>(128);
	
	/**
	 * 保存指令码和方法的参数名对应关系
	 */
	private static Map<String, String[]> COMMAND_CODE_WITH_METHOD_PARAMETER_NAMES = new HashMap<>(128);
	
	@Override
	public void setMessageFrameHandler(MessageFrameHandler messageFrameHandler, String beanName) {
		// 存储指令码和方法对应关系
		Method[] declaredMethods = messageFrameHandler.getClass().getDeclaredMethods();
		// 存储保存
		storeCommandCodeWithMethod(declaredMethods);
		storeCommandCodeWithMethodParameterNames(declaredMethods);
		storeCommandCodeWithHandler(declaredMethods, beanName);
	}

	protected void storeCommandCodeWithHandler(Method[] declaredMethods, String beanName) {
		for (Method method : declaredMethods) {
			CommandCode annotation = method.getAnnotation(CommandCode.class);
			if (annotation != null) {
				String put = MESSAGE_FRAME_HANDLER_MAP.put(annotation.value(), beanName);
				if (put != null) {
					throw new RuntimeException(method.getName()+"指令码重复，在"+beanName+"类也有指令码"+annotation.value());
				}
			}
		}
	}

	/**
	 * 保存指令码和方法参数名对应关系
	 * @param declaredMethods
	 */
	protected void storeCommandCodeWithMethodParameterNames(Method[] declaredMethods) {
		for (Method method : declaredMethods) {
			CommandCode annotation = method.getAnnotation(CommandCode.class);
			if (annotation != null) {
				ParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
				String[] parameterNames = parameterNameDiscoverer.getParameterNames(method);
				checkMethodParameterNames(parameterNames);
				String[] put = COMMAND_CODE_WITH_METHOD_PARAMETER_NAMES.put(annotation.value(), parameterNames);
				if (put != null) {
					throw new RuntimeException(method.getName()+"的指令码重复！");
				}
			}
		}
	}
	
	/**
	 * 检查方法的参数名是否支持
	 * @param parameterNames
	 */
	private void checkMethodParameterNames(String[] parameterNames) {
		for (String parameterName : parameterNames) {
			if (ParameterName.DEVICE_ID.getName().equals(parameterName)) {
			} else if (ParameterName.MESSAGE_FRAME.getName().equals(parameterName)) {
			} else if (ParameterName.MESSAGE.getName().equals(parameterName)) {
			} else {
				ParameterName[] values = ParameterName.values();
				throw new RuntimeException(parameterName+"参数名不支持，参数名只支持ParameterName枚举类参数"+Arrays.asList(values));
			}
		}
	}

	protected void storeCommandCodeWithMethod(Method[] declaredMethods) {
		for (Method method : declaredMethods) {
			CommandCode annotation = method.getAnnotation(CommandCode.class);
			if (annotation != null) {
				Method put = COMMAND_CODE_WITH_METHOD_MAP.put(annotation.value(), method);
				if (put != null) {
					throw new RuntimeException(method.getName()+"和"+put.getName()+" 重复");
				}
			}
		}
	}

	@Override
	public MessageFrameHandler getHandler(String commandCode) {
		if (isEmpty(commandCode)) {
			return null;
		}
		String beanName = MESSAGE_FRAME_HANDLER_MAP.get(commandCode.substring(0, 2));
		return beanName == null ? null : (MessageFrameHandler) applicationContext.getBean(beanName);
	}

	@Override
	public Method getHandlerMethod(String commandCode) {
		if (isEmpty(commandCode)) {
			return null;
		}
		return COMMAND_CODE_WITH_METHOD_MAP.get(commandCode);
	}
	
	@Override
	public String[] getHandlerMethodParameterNames(String commandCode) {
		if (isEmpty(commandCode)) {
			return null;
		}
		return COMMAND_CODE_WITH_METHOD_PARAMETER_NAMES.get(commandCode);
	}
	
	private boolean isEmpty(String commandCode) {
		return commandCode == null || "".equals(commandCode);
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	public void destroy() throws Exception {
		this.applicationContext = null;
		MESSAGE_FRAME_HANDLER_MAP.clear();
		COMMAND_CODE_WITH_METHOD_MAP.clear();
		COMMAND_CODE_WITH_METHOD_PARAMETER_NAMES.clear();
	}
}
