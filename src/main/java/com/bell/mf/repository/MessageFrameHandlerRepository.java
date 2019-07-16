package com.bell.mf.repository;

import java.lang.reflect.Method;

import com.bell.mf.handler.MessageFrameHandler;

/**
 * 保存消息帧处理器的仓库
 * @author bell.zhouxiaobing
 *
 */
public interface MessageFrameHandlerRepository {
	
	/**
	 * 设置消息帧处理器到仓库里
	 * @param messageFrameHandler
	 * @param beanName
	 * @return
	 */
	void setHandler(MessageFrameHandler messageFrameHandler, String beanName);
	
	/**
	 * 根据指令码从仓库里或消息帧处理器
	 * @param commandCode
	 * @return
	 */
	MessageFrameHandler getHandler(String commandCode);
	
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
	
}
