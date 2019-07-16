package com.bell.mf.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bell.mf.MessageFrame;
import com.bell.mf.annotation.CommandCode;
import com.bell.mf.repository.MessageFrameHandlerRepository;

@Component
public class GYMessageFrameHandler extends AbstractMessageFrameHandler implements MessageFrameHandler{

	@Autowired
	private MessageFrameHandlerRepository messageFrameHandlerRepository;
	
	@CommandCode(value="4401")
	public void handler(String deviceId, MessageFrame messageFrame, String message) {
		System.out.println("4401..............");
	}
	@CommandCode(value="4401")
	public void heart(MessageFrame messageFrame, String message) {
		System.out.println("4401..............");
	}
	@CommandCode(value="4403")
	public void handler3(String deviceId) {
		System.out.println("4403..............");
	}

	@Override
	protected MessageFrameHandlerRepository getRepository() {
		return messageFrameHandlerRepository;
	}

}
