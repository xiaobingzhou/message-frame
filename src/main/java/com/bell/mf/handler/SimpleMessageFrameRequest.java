package com.bell.mf.handler;

import java.io.Serializable;
import java.util.Date;

import com.bell.mf.IMessageFrame;

import lombok.Builder;
import lombok.Data;

/**
 * MessageFrameRequest接口的简单实现
 * @author bell.zhouxiaobing
 * @since 1.2
 * @see MessageFrameRequest
 */
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
