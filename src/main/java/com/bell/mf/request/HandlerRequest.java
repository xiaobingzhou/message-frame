package com.bell.mf.request;

import java.util.Date;

import com.alibaba.fastjson.JSONObject;
import com.bell.mf.IMessageFrame;
import com.bell.mf.handler.AbstractHandler;

/**
 * HandlerRequest接口，封装请求参数</br>
 * {@link com.bell.mf.support.Dispatcher}
 * {@link AbstractHandler}
 * @author bell.zhouxiaobing
 * @since 1.2
 */
public interface HandlerRequest {

	/**
	 *  解析过的消息帧
	 * @return IMessageFrame
	 */
	IMessageFrame getMessageFrame();

	/**
	 * 设备uuid
	 * @return String
	 */
	String getDeviceId();

	/**
	 *  原始消息帧
	 * @return String
	 */
	String getMessage();

	/**
	 * 系统时间
	 * @return Date
	 */
	Date getSystemDate();

	/**
	 * 获取bodyJson
	 * @return JSONObject
	 */
	JSONObject getBodyJson();
	/**
	 * 设置bodyJson
	 */
	void setBodyJson(JSONObject bodyJson);
}
