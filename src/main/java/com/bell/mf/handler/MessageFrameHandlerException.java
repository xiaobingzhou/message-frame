package com.bell.mf.handler;

/**
 * 定义MessageFrameHandlerException异常
 * @author bell.zhouxiaobing
 * @since 1.2
 */
public class MessageFrameHandlerException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1192892909380452945L;
	
	public MessageFrameHandlerException(String message) {
		super(message);
	}
	public MessageFrameHandlerException(Throwable throwable) {
		super(throwable);
	}
	public MessageFrameHandlerException(String message, Throwable throwable) {
		super(message, throwable);
	}
	
	

}
