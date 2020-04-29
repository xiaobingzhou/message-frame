package com.bell.mf.support;

import com.bell.mf.handler.HandlerException;
import com.bell.mf.request.HandlerRequest;
/**
 * Dispatcher调度器接口，用于分发指令码处理请求
 * @author bell.zhouxiaobing
 * @since 1.3
 */
public interface Dispatcher {

	void dispatch(HandlerRequest request) throws HandlerException;

}
