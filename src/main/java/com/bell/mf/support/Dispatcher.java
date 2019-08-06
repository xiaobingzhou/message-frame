package com.bell.mf.support;

import com.bell.mf.handler.MessageFrameHandlerException;
import com.bell.mf.handler.MessageFrameRequest;
/**
 * 
 * @author bell.zhouxiaobing
 * @since 1.3
 */
public interface Dispatcher {

	void dispatch(MessageFrameRequest request) throws MessageFrameHandlerException;

}
