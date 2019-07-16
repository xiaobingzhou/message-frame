package com.bell.mf.handler;

import java.util.Date;

import com.bell.mf.MessageFrame;

public interface MessageFrameRequest {

	/**
	 *  解析过的消息帧
	 * @return
	 */
	MessageFrame getMessageFrame();

	/**
	 * 设备uuid
	 * @return
	 */
	String getDeviceId();

	/**
	 *  原始消息帧
	 * @return
	 */
	String getMessage();

	/**
	 * 系统时间
	 * @return
	 */
	Date getSystemDate();
}
