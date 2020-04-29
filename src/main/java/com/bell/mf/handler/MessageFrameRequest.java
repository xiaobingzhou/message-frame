package com.bell.mf.handler;

import java.util.Date;

import com.alibaba.fastjson.JSONObject;
import com.bell.mf.IMessageFrame;

/**
 * MessageFrameRequest接口，封装请求参数</br>
 * {@link com.bell.mf.support.Dispatcher}
 * {@link com.bell.mf.support.handler.AbstractHandler}
 * @author bell.zhouxiaobing
 * @since 1.2
 */
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

	/**
	 * bodyJson
	 */
	JSONObject getBodyJson();
	/**
	 * bodyJson
	 */
	void setBodyJson(JSONObject bodyJson);
}
