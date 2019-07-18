package com.bell.mf.handler;

import java.io.Serializable;
import java.util.Date;

import com.bell.mf.IMessageFrame;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SimpleMessageFrameRequest implements Serializable, MessageFrameRequest{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3358325538683680300L;
	
	private String deviceId;
	private IMessageFrame messageFrame;
	private String message;
	private Date systemDate;
}
