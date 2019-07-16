package com.bell.mf.repository;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.stereotype.Component;

import com.bell.mf.annotation.CommandCode;
import com.bell.mf.handler.MessageFrameHandler;

@Component
public class SpringMessageFrameHandlerRepository implements MessageFrameHandlerRepository, ApplicationContextAware, DisposableBean{
	
	private ApplicationContext applicationContext;
	
	/**
	 * 保存指令码和spring中的HandlerStore的对应关系
	 * command_code_match__handler_store_map
	 */
	private static Map<String, Store> COMMAND_CODE_MATCH_HANDLER_STORE_MAP = new HashMap<>(128);
	
	@Override
	public void setHandler(MessageFrameHandler messageFrameHandler, String beanName) {
		storeCommandCodeWithMap(messageFrameHandler.getClass().getDeclaredMethods(), beanName);
	}

	protected void storeCommandCodeWithMap(Method[] declaredMethods, String beanName) {
		for (Method method : declaredMethods) {
			CommandCode annotation = method.getAnnotation(CommandCode.class);
			if (annotation != null) {
				String[] value = annotation.value();
				for (String commandCode : value) {
					HandlerStore handlerStore = new HandlerStore();
					handlerStore.setBeanName(beanName);
					handlerStore.setMethod(method);
					handlerStore.setParameterNames(getParameterNames(method));
					Store put = COMMAND_CODE_MATCH_HANDLER_STORE_MAP.put(commandCode, handlerStore);
					if (put != null) {
						String methodName = method.getDeclaringClass().getName()+"."+method.getName();
						String OtherMethodName = put.getMethod().getDeclaringClass().getName()+"."+put.getMethod().getName();
						throw new BeanCreationException(beanName, String.format("%s()和%s()指令码重复，指令码是：%s", methodName, OtherMethodName, annotation.value()));
					}
				}
			}
		}
	}

	protected String[] getParameterNames(Method method) {
		ParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
		String[] parameterNames = parameterNameDiscoverer.getParameterNames(method);
		checkMethodParameterNames(method, parameterNames);
		return parameterNames;
	}

	
	/**
	 * 检查方法的参数名是否支持
	 * @param parameterNames
	 */
	private void checkMethodParameterNames(Method method, String[] parameterNames) {
		for (String parameterName : parameterNames) {
			if (ParameterName.DEVICE_ID.getName().equals(parameterName)) {
			} else if (ParameterName.MESSAGE_FRAME.getName().equals(parameterName)) {
			} else if (ParameterName.MESSAGE.getName().equals(parameterName)) {
			} else if (ParameterName.SYS_DATE.getName().equals(parameterName)) {
			} else {
				throw new BeanCreationException(String.format("[%s]()方法的[%s]参数名不支持，参数名只支持ParameterName枚举类参数%s", method.getName(), parameterName, ParameterName.getAllName()));
			}
		}
	}

	@Override
	public MessageFrameHandler getHandler(String commandCode) {
		if (isEmpty(commandCode)) {
			return null;
		}
		Store store = COMMAND_CODE_MATCH_HANDLER_STORE_MAP.get(commandCode);
		if (store == null) {
			return null;
		}
		String beanName = store.getBeanName();
		return beanName == null ? null : (MessageFrameHandler) applicationContext.getBean(beanName);
	}

	@Override
	public Method getHandlerMethod(String commandCode) {
		if (isEmpty(commandCode)) {
			return null;
		}
		Store store = COMMAND_CODE_MATCH_HANDLER_STORE_MAP.get(commandCode);
		if (store == null) {
			return null;
		}
		return store.getMethod();
	}
	
	@Override
	public String[] getHandlerMethodParameterNames(String commandCode) {
		if (isEmpty(commandCode)) {
			return null;
		}
		Store store = COMMAND_CODE_MATCH_HANDLER_STORE_MAP.get(commandCode);
		if (store == null) {
			return null;
		}
		return store.getParameterNames();
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
		COMMAND_CODE_MATCH_HANDLER_STORE_MAP.clear();
	}
	
	protected interface Store{
		String getBeanName();
		Method getMethod();
		String[] getParameterNames();
	}
	class HandlerStore implements Store{
		private String beanName;
		private Method method;
		private String[] parameterNames;
		public String getBeanName() {
			return beanName;
		}
		public void setBeanName(String beanName) {
			this.beanName = beanName;
		}
		public Method getMethod() {
			return method;
		}
		public void setMethod(Method method) {
			this.method = method;
		}
		public String[] getParameterNames() {
			return parameterNames;
		}
		public void setParameterNames(String[] parameterNames) {
			this.parameterNames = parameterNames;
		}
		@Override
		public String toString() {
			return "HandlerStore [beanName=" + beanName + ", method=" + method + ", parameterNames="
					+ Arrays.toString(parameterNames) + "]";
		}
		
	}
	
}
