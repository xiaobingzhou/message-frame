package com.bell.mf.support.repository;

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
	 * @param messageFrameHandler
	 * @param beanName
	 * @return
	 */
	void setHandler(Object messageFrameHandler, String beanName);
	
	/**
	 * 根据指令码从仓库里或消息帧处理器
	 * @param commandCode
	 * @return
	 */
	Object getHandler(String commandCode);
	
	/**
	 * 根据指令码从仓库里获取对应的处理方法
	 * @param commandCode
	 * @return
	 */
	Method getHandlerMethod(String commandCode);
	
	/**
	 * 根据指令码从仓库里获取对应的处理方法的参数名
	 * @param commandCode
	 * @return
	 */
	String[] getHandlerMethodParameterNames(String commandCode);

	/**
	 * 获取保存的指令码
	 * @return
	 */
	Set<String> getCommandCodes();
}
