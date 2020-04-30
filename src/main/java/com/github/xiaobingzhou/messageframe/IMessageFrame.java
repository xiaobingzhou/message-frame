package com.github.xiaobingzhou.messageframe;

/**
 * 报文帧格式
 * @author bell.zhouxiaobing
 * @since 1.2
 */
public interface IMessageFrame {
	//  帧头	       协议版本号  报文序列号	地址信息	 指令码	  帧源              消息体	CRC校验	   帧尾
	// 1byte	2byte	2byte	12byte	2byte	1byte	0~nbyte	2byte	1byte
//	String head;// 帧头
//	String protocolVer;// 协议版本号
//	String messageSN;// 报文序列号
//	String imei;// 地址信息IMEI号
//	String commandCode;// 指令码
//	String from;// 帧源
//	String body;// 消息体
//	String crc;// CRC校验
//	String tail;// 帧尾 
	
	String getHead();
	void setHead(String head);
	
	String getProtocolVer();
	void setProtocolVer(String protocolVer);
	
	String getMessageSN();
	void setMessageSN(String messageSN);
	
	String getImei();
	void setImei(String imei);
	
	String getCommandCode();
	void setCommandCode(String commandCode);
	
	String getFrom();
	void setFrom(String from);
	
	String getBody();
	void setBody(String body);
	
	String getCrc();
	void setCrc(String crc);
	
	String getTail();
	void setTail(String tail);
	
}
