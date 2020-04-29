package com.bell.mf.handler;

/**
 * 定义MessageFrameHandlerException异常
 * @author bell.zhouxiaobing
 * @since 1.2
 */
public class HandlerException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1192892909380452945L;
	
	public HandlerException(String message) {
		super(message);
	}
	public HandlerException(Throwable throwable) {
		super(throwable);
	}
	public HandlerException(String message, Throwable throwable) {
		super(message, throwable);
	}
	
	

}
