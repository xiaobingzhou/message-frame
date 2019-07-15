package com.bell.mf.handler;

import com.bell.mf.MessageFrame;

/**
 * 定义消息帧处理器接口
 * @author bell.zhouxiaobing
 *
 */
public interface MessageFrameHandler {
	
	/**
	 * 处理消息帧的总入口
	 * @param deviceId	设备uuid
	 * @param messageFrame	解析过的消息帧
	 * @param message 原始消息
	 */
	void handle(String deviceId, MessageFrame messageFrame, String message);
	
}
