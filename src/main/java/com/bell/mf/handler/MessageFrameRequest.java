package com.bell.mf.handler;

import java.util.Date;

import com.bell.mf.IMessageFrame;


public interface MessageFrameRequest {

	/**
	 *  解析过的消息帧
	 * @return
	 */
	IMessageFrame getMessageFrame();

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
