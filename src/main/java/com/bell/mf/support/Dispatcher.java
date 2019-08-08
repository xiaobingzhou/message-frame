package com.bell.mf.support;

import com.bell.mf.handler.MessageFrameHandlerException;
import com.bell.mf.handler.MessageFrameRequest;
/**
 * Dispatcher调度器接口，用于分发指令码处理请求
 * @author bell.zhouxiaobing
 * @since 1.3
 */
public interface Dispatcher {

	void dispatch(MessageFrameRequest request) throws MessageFrameHandlerException;

}
