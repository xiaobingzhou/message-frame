package com.bell.mf.request;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.JSONObject;
import com.bell.mf.IMessageFrame;

import com.bell.mf.request.HandlerRequest;
import lombok.Builder;
import lombok.Data;

/**
 * MessageFrameRequest接口的简单实现
 * @author bell.zhouxiaobing
 * @since 1.2
 * @see HandlerRequest
 */
@Builder
@Data
public class HandlerRequestImpl implements Serializable, HandlerRequest {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3358325538683680300L;
	
	private String deviceId;
	private IMessageFrame messageFrame;
	private String message;
	private Date systemDate;
	private JSONObject bodyJson;
}
