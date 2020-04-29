package com.bell.mf.repository;

import java.lang.reflect.Method;
import java.util.*;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

import com.bell.mf.annotation.CommandCode;

/**
 * 实现接口</br>
 * {@link HandlerRepository}</br>
 * {@link ApplicationContextAware}</br>
 * {@link DisposableBean}</br>
 * @author bell.zhouxiaobing
 * @since 1.3
 */
public abstract class AbstractHandlerRepository implements HandlerRepository,  ApplicationContextAware, DisposableBean{

	private ApplicationContext applicationContext;
	
	/**
	 * 保存指令码和spring中的HandlerStore的对应关系
	 * command_code_match__handler_store_map
	 */
	private static Map<String, Store> COMMAND_CODE_MATCH_HANDLER_STORE_MAP = new HashMap<>(128);
	
	@Override
	public void setHandler(Object messageFrameHandler, String beanName) {
		storeCommandCodeWithMap(messageFrameHandler, beanName);
	}

	protected void storeCommandCodeWithMap(Object messageFrameHandler, String beanName) {
		Method[] declaredMethods = messageFrameHandler.getClass().getDeclaredMethods();
		for (Method method : declaredMethods) {
			CommandCode annotation = method.getAnnotation(CommandCode.class);
			if (annotation != null) {
				String[] parameterNames = getParameterNames(method);
				String[] value = annotation.value();
				for (String commandCode : value) {
					HandlerStore handlerStore = new HandlerStore();
					handlerStore.setBeanName(beanName);
					handlerStore.setMethod(method);
					handlerStore.setParameterNames(parameterNames);
					Store put = COMMAND_CODE_MATCH_HANDLER_STORE_MAP.put(commandCode, handlerStore);
					if (put != null) {
						String methodName = method.getDeclaringClass().getName()+"."+method.getName();
						String OtherMethodName = put.getMethod().getDeclaringClass().getName()+"."+put.getMethod().getName();
						throw new BeanCreationException(beanName, String.format("%s()和%s()指令码重复，指令码是：%s", methodName, OtherMethodName, commandCode));
					}
				}
			}
		}
	}

	protected String[] getParameterNames(Method method) {
		ParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
		String[] parameterNames = parameterNameDiscoverer.getParameterNames(method);
		return parameterNames;
	}

	@Override
	public Object getHandler(String commandCode) {
		if (isEmpty(commandCode)) {
			return null;
		}
		Store store = getStore(commandCode);
		if (store == null) {
			return null;
		}
		String beanName = store.getBeanName();
		return beanName == null ? null : applicationContext.getBean(beanName);
	}
	@Override
	public Method getHandlerMethod(String commandCode) {
		if (isEmpty(commandCode)) {
			return null;
		}
		Store store = getStore(commandCode);
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
		Store store = getStore(commandCode);
		if (store == null) {
			return null;
		}
		return store.getParameterNames();
	}

    @Override
    public Set<String> getCommandCodes() {
        return COMMAND_CODE_MATCH_HANDLER_STORE_MAP.keySet();
    }

    /**
	 * @param commandCode
	 * @return
	 */
	private Store getStore(String commandCode) {
		Store store = COMMAND_CODE_MATCH_HANDLER_STORE_MAP.get(commandCode);
		return store;
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
