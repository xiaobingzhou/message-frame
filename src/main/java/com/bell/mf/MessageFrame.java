package com.bell.mf;

/**
 * 报文帧格式
 * @author bell.zhouxiaobing
 *
 */
public class MessageFrame {
	// 控制器激活后心跳报文			0x4401
	public static final Integer HEART_BEAT = 0x4401;
	// 控制器及下挂设备状态上报报文	0x4402
	public static final Integer REPORT_STATUS = 0x4402;
	// 控制器激活唤醒报文			0x4403
	public static final Integer WAKE_UP = 0x4403;
	// 控制器定时自检报文			0x4404
	public static final Integer SELF_CHECKING = 0x4404;
	// 控制器控制开锁报文			0x4411
	public static final Integer UNLOCK = 0x4411;
	// 控制器NB-IOT升级报文			0x4412
	public static final Integer UPGRADE = 0x4412;
	// 控制器基本控制和锁控配置报文	0x4413
	public static final Integer CONFIG = 0x4413;
	public static final Integer CONFIG_SENSOR = 0x4421;
	
	//公寓锁 门锁登记注册
	public static final Integer GY_REGISTER = 0x9101;
	
	//公寓锁  门锁激活唤醒（0x9102）
	public static final Integer GY_WAKE_UP = 0x9102;
	
	//公寓锁 门锁激活后心跳（0x9103）
	public static final Integer GY_HEART_BEAT = 0x9103;
	
	//公寓锁  门锁鉴权上报（0x9104）
	public static final Integer GY_AUTHEN = 0x9104;
	
	//公寓锁 门锁状态变化（0x9105）
	public static final Integer GY_REPORT_STATUS = 0x9105;
	
	//公寓锁  门锁定时自检（0x9106）
	public static final Integer GY_SELF_CHECKING = 0x9106;
	
	//公寓锁  门锁远程开锁（0x9111）
	public static final Integer GY_UNLOCK = 0x9111;
	
	//公寓锁  门锁基本配置（0x9112）
	public static final Integer GY_CONFIG = 0x9112;
	
	//公寓锁  门锁IC卡权限配置（0x9113）
	public static final Integer GY_IC_CONFIG = 0x9113;
	
	//公寓锁   门锁密码权限配置（0x9114）
	public static final Integer GY_PASSWORD_CONFIG = 0x9114;
	
	//公寓锁   门锁NB-IOT升级流程（0x9115）
	public static final Integer GY_UPGRADE = 0x9115;
	
	// 控制器安装注册报文			0x4414
	public static final Integer REGISTER = 0x4414;

	//  帧头	       协议版本号  报文序列号	地址信息	 指令码	  帧源              消息体	CRC校验	   帧尾
	// 1byte	2byte	2byte	12byte	2byte	1byte	0~nbyte	2byte	1byte
	private String head;// 帧头
	private String protocolVer;// 协议版本号
	private String messageSN;// 报文序列号
	private String imei;// 地址信息IMEI号
	private String commandCode;// 指令码
	private String from;// 帧源
	private String body;// 消息体
	private String crc;// CRC校验
	private String tail;// 帧尾 
	
	public String getHead() {
		return head;
	}
	public void setHead(String head) {
		this.head = head;
	}
	public String getProtocolVer() {
		return protocolVer;
	}
	public void setProtocolVer(String protocolVer) {
		this.protocolVer = protocolVer;
	}
	public String getMessageSN() {
		return messageSN;
	}
	public void setMessageSN(String messageSN) {
		this.messageSN = messageSN;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getCommandCode() {
		return commandCode;
	}
	public void setCommandCode(String commandCode) {
		this.commandCode = commandCode;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getCrc() {
		return crc;
	}
	public void setCrc(String crc) {
		this.crc = crc;
	}
	public String getTail() {
		return tail;
	}
	public void setTail(String tail) {
		this.tail = tail;
	}
	@Override
	public String toString() {
		return "MessageFrame [head=" + head + ", protocolVer=" + protocolVer + ", messageSN=" + messageSN + ", imei="
				+ imei + ", commandCode=" + commandCode + ", from=" + from + ", body=" + body + ", crc=" + crc
				+ ", tail=" + tail + "]";
	}
}
