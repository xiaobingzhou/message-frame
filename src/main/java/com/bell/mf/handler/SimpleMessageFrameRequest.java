package com.bell.mf.handler;

import java.io.Serializable;
import java.util.Date;

import com.bell.mf.MessageFrame;

public class SimpleMessageFrameRequest implements Serializable, MessageFrameRequest{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3358325538683680300L;
	
	private String deviceId;
	private MessageFrame messageFrame;
	private String message;
	private Date systemDate;
	
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public MessageFrame getMessageFrame() {
		return messageFrame;
	}
	public void setMessageFrame(MessageFrame messageFrame) {
		this.messageFrame = messageFrame;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Date getSystemDate() {
		return systemDate;
	}
	public void setSystemDate(Date systemDate) {
		this.systemDate = systemDate;
	}
	
}
