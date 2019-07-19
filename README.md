# message-frame

### 一、重构前

```java
@Component
public class MessageFrameDispather{
	/**
	 * 根据不同指令码调用不同的解析方法
	 * 需要对多个if进行重构
	 */
	public void analysis(String deviceId, MessageFrame frame, String message) {
		// 心跳流程 上报->下发
		if (MessageFrame.HEART_BEAT.equals(code)) {
			this.heartBeat(deviceId, messageFrame,"blcs");
			
		// 上报状态流程 上报->下发
		}else if (MessageFrame.REPORT_STATUS.equals(code)) {
			this.status(deviceId, messageFrame, message, code);
			
		// 唤醒流程 上报->下发
		}else if (MessageFrame.WAKE_UP.equals(code)) {
			this.wakeUp(deviceId, messageFrame);
			
		// 自检流程 下发->上报
		}else if (MessageFrame.SELF_CHECKING.equals(code)) {
			this.status(deviceId, messageFrame, message, code);
			
		// 开锁流程 下发->上报
		}else if (MessageFrame.UNLOCK.equals(code)) {
			this.unlock(deviceId, messageFrame);
			
		// 升级流程 下发->上报
		}else if (MessageFrame.UPGRADE.equals(code)) {
			this.upgrade(deviceId, messageFrame,sysDate);
			
		// 配置流程（定时自检，告警） 下发->上报
		}else if (MessageFrame.CONFIG.equals(code)) {
			this.config(deviceId, messageFrame);
			
		//配置流程（  高低温，陀螺仪等 ） 下发->上报
		}else if(MessageFrame.CONFIG_SENSOR.equals(code)){
			this.configSensor(deviceId, messageFrame);
			
		// 注册流程 下发->上报
		}else if (MessageFrame.REGISTER.equals(code)) {
			this.register(deviceId, messageFrame);
		
		//公寓锁 门锁登记注册
		}else if(MessageFrame.GY_REGISTER.equals(code)){
			this.registerGY(deviceId, messageFrame,sysDate);
			
		//公寓锁  门锁激活唤醒（0x9102）
		}else if(MessageFrame.GY_WAKE_UP.equals(code)){
			this.wakeUpGY(deviceId, messageFrame,sysDate);
			
		//公寓锁 门锁激活后心跳（0x9103）
		}else if(MessageFrame.GY_HEART_BEAT.equals(code)){
			this.heartBeat(deviceId, messageFrame,"lock");
			
		//公寓锁  门锁鉴权上报（0x9104）
		}else if(MessageFrame.GY_AUTHEN.equals(code)){
			this.authenticationGY(deviceId, messageFrame,sysDate);
			
		//公寓锁 门锁状态变化（0x9105）
		}else if(MessageFrame.GY_REPORT_STATUS.equals(code)){
			this.statusGY(deviceId, messageFrame, message, code,sysDate);
			
		//公寓锁  门锁定时自检（0x9106）
		}else if(MessageFrame.GY_SELF_CHECKING.equals(code)){
			this.statusGY(deviceId, messageFrame, message, code,sysDate);
			
		//公寓锁  门锁远程开锁（0x9111）
		}else if(MessageFrame.GY_UNLOCK.equals(code)){
			this.unlockGY(deviceId, messageFrame,sysDate);
			
		//公寓锁  门锁基本配置（0x9112）
		}else if(MessageFrame.GY_CONFIG.equals(code)){
			this.configGY(deviceId, messageFrame,sysDate);
			
		//公寓锁  门锁IC卡权限配置（0x9113）
		}else if(MessageFrame.GY_IC_CONFIG.equals(code)){
			this.icConfigGY(deviceId, messageFrame,sysDate);
			
		//公寓锁   门锁密码权限配置（0x9114）
		}else if(MessageFrame.GY_PASSWORD_CONFIG.equals(code)){
			this.passwordConfigGY(deviceId, messageFrame,sysDate);
			
		//公寓锁   门锁NB-IOT升级流程（0x9115）
		}else if(MessageFrame.GY_UPGRADE.equals(code)){
			this.upgrade(deviceId, messageFrame,sysDate);
		
		}else {
			logger.info("指令码未匹配："+code);
		}
	}
    public void wakeUp(String deviceId, MessageFrame messageFrame) {
    	// 业务逻辑....
    }
}
```

### 二、重构后

```java
@Component
public class MessageFrameDispather extends AbstractMessageFrameHandler{
	/**
	 * 根据不同指令码调用不同的解析方法
	 * 需要对多个if进行重构
	 */
	public void analysis(String deviceId, MessageFrame frame, String message) {
		// 重构后
		MessageFrameHandler handler = messageFrameHandlerRepository.getHandler(messageFrame.getCommandCode());
		if (handler == null) {
			logger.info("指令码未匹配："+messageFrame.getCommandCode());
			return;
		}
		SimpleMessageFrameRequest request = SimpleMessageFrameRequest.builder()
			.deviceId(deviceId)
			.messageFrame(messageFrame)
			.message(message)
			.systemDate(new Date())
			.build();
		try {
            // 内部使用反射实现方法的调用，逻辑是在抽象类中
			handler.handle(request);
		} catch (MessageFrameHandlerException e) {
			e.printStackTrace();
			logger.warn("执行handle方法异常:"+e.getLocalizedMessage());
			return;
		}
	}
    @CommandCode(MessageFrame.WAKE_UP)
    public void wakeUp(String deviceId, MessageFrame messageFrame) {
    	// 业务逻辑....
    }
}
```

## 使用方式
### 1、下载源码
```java
git clone https://github.com/xiaobingzhou/message-frame.git
```
### 2、使用maven打包
```java
mvn clean package
```
### 3、获取jar包
```java
cd target
```
