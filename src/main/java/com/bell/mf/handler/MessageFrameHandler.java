package com.bell.mf.handler;

/**
 * 定义消息帧处理器接口
 * @author bell.zhouxiaobing
 *
 */
public interface MessageFrameHandler {
	
	/**
	 * 处理消息帧的总入口
	 */
	void handle(MessageFrameRequest request);
	
}
