package com.github.xiaobingzhou.messageframe.repository;

import com.github.xiaobingzhou.messageframe.repository.impl.HandlerStore;
import com.github.xiaobingzhou.messageframe.request.HandlerRequest;

import java.lang.reflect.Method;
import java.util.*;

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

	// ==============匹配版本号=================

	/**
	 * 根据请求从仓库里或消息帧处理器
	 * 匹配版本号
	 * @param request
	 * @return Object
	 * @since 1.6.3
	 */
	default Object getHandler(HandlerRequest request) {
		return this.getHandler(request.getCommandCode());
	}

	/**
	 * 根据请求从仓库里获取对应的处理方法
	 * 匹配版本号
	 * @param request
	 * @return Method
	 * @since 1.6.3
	 */
	default Method getHandlerMethod(HandlerRequest request) {
		return this.getHandlerMethod(request.getCommandCode());
	}

	/**
	 * 根据请求从仓库里获取对应的处理方法的参数名
	 * 匹配版本号
	 * @param request
	 * @return String[]
	 * @since 1.6.3
	 */
	default String[] getHandlerMethodParameterNames(HandlerRequest request){
		return this.getHandlerMethodParameterNames(request.getCommandCode());
	}

    /**
     * 获取所有的key
     * @return Set 所有key
     * @since 1.6.3
     */
	default Set<String> keys(){
	    return this.getCommandCodes();
    }

    /**
     * 根据key获取Store
     * @return Store
     * @since 1.6.3
     */
    default Store value(String key) {
        HandlerStore handlerStore = new HandlerStore();
        handlerStore.setMethod(getHandlerMethod(key));
        handlerStore.setParameterNames(getHandlerMethodParameterNames(key));
        return handlerStore;
    }
}
