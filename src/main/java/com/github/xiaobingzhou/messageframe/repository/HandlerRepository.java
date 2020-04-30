package com.github.xiaobingzhou.messageframe.repository;

import java.lang.reflect.Method;
import java.util.Set;

/**
 * HandlerRepository接口
 * @author bell.zhouxiaobing
 * @since 1.3
 */
public interface HandlerRepository {

	/**
	 * 设置消息帧处理器到仓库里
	 * @param handler
	 * @param beanName
	 */
	void setHandler(Object handler, String beanName);
	
	/**
	 * 根据指令码从仓库里或消息帧处理器
	 * @param commandCode
	 * @return Object
	 */
	Object getHandler(String commandCode);
	
	/**
	 * 根据指令码从仓库里获取对应的处理方法
	 * @param commandCode
	 * @return Method
	 */
	Method getHandlerMethod(String commandCode);
	
	/**
	 * 根据指令码从仓库里获取对应的处理方法的参数名
	 * @param commandCode
	 * @return String[]
	 */
	String[] getHandlerMethodParameterNames(String commandCode);

	/**
	 * 获取保存的指令码
	 * @return Set<String>
	 * @since 1.5.4
	 */
	Set<String> getCommandCodes();
}
