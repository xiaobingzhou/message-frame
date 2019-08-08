package com.bell.mf.handler;

/**
 * 定义消息帧处理器接口
 * @author bell.zhouxiaobing
 * @since 1.2
 */
public interface MessageFrameHandler {
	
	/**
	 * 处理消息帧的总入口
	 * @param request
	 * @throws MessageFrameHandlerException
	 */
	void handle(MessageFrameRequest request) throws MessageFrameHandlerException;
	
}
