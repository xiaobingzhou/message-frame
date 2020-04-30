# message-frame

### 当前(1.6.0)版本支持功能
> com.github.xiaobingzhou.messageframe.annotation.Handler 处理器

> com.github.xiaobingzhou.messageframe.interceptor.HandlerInterceptor 处理器拦截器

> com.github.xiaobingzhou.messageframe.bind.BindParam 参数绑定器

> com.github.xiaobingzhou.messageframe.codec.BodyCodec 解码器

### 使用例子(最低版本1.6.0.RELEASE)
###### https://github.com/xiaobingzhou/message-frame-sample

> 解析字符串body：14041D08383B00FFFF

> 解析完成后：{"date":1588121819000,"type":"00","other":"FFFF"}

`@Handler` 需要配合 `@CommandCode` 注解一起使用
```java
@RestController
@RequestMapping("/mf")
@EnableHandler// 启用处理器功能
public class MFController {

    /**
     * 自动装配com.github.xiaobingzhou.messageframe.Dispatcher实现类
     * 默认是实现是com.github.xiaobingzhou.messageframe.DispatcherImpl
     * 通过配置类自动配置HandlerAutoConfiguration
     * @see com.github.xiaobingzhou.messageframe.HandlerAutoConfiguration
     * @see com.github.xiaobingzhou.messageframe.DispatcherImpl
     */
    @Autowired
    Dispatcher dispatcher;

    @PostMapping("/{deviceId}")
    public String dispatcher(@RequestBody MessageFrame messageFrame,
                             @PathVariable String deviceId) {
        HandlerRequest handlerRequest = HandlerRequestImpl
                .builder()
                .deviceId(deviceId)
                .messageFrame(messageFrame)
                .systemDate(new Date())
                .build();
        try {
            // 自动分发到到对应的处理器进行处理，底层使用反射方式调用处理方法
            dispatcher.dispatch(handlerRequest);
        } catch (HandlerException e) {
            e.printStackTrace();
        }
        return "ok";
    }

}
```

```java
@Service
@Handler// 标注这是一个处理器类
public class HandlerServiceImpl {

    /**
     * 处理方法
     * 默认支持参数绑定器 com.github.xiaobingzhou.messageframe.bind.impl*
     * 自定义参数绑定器需要实现 com.github.xiaobingzhou.messageframe.bind.BindParam 并将其添加到spring容器中
     * 
     * 参数bodyJson需要BodyCodec解码器才能正常工作，如果指令码1111没有解码器则bodyJson为null
     * 需要自定义实现BodyCodec解码器并添加到spring容器中
     * @param request
     * @see com.github.xiaobingzhou.messageframe.enums.ParameterNameEnum
     * @see com.github.xiaobingzhou.messageframe.bind.impl*
     * @see com.github.xiaobingzhou.messageframe.bind.BindParam
     * @see com.github.xiaobingzhou.messageframe.codec.BodyCodec
     * @see com.github.xiaobingzhou.messageframe.codec.BodyCodecBuilder
     */
    @CommandCode({"1111"})// 标注这个方法处理指令码：1111
    public void handle(HandlerRequest request, JSONObject bodyJson) {
        // 处理指令
        System.out.println(request);
    }

    /**
     * 处理方法
     * @param request
     */
    @CommandCode({"2222"})// 标注这个方法处理指令码：2222
    public void heartBeat(HandlerRequest request) {
        // 处理指令
        System.out.println(request);
    }

}
```

```java
/**
 * 解码器配置类
 */
@Configuration
public class BodyCodecConfig {

    MapperField date = MapperField.builder()
            .length(12)// 该字段截取的长度
            .name("date")// 设置字段名
            .postHandler(MapperFieldEnum.datetime.getPostHandler())// 字段的后置处理器
            .build();
    MapperField type = MapperField.builder()
            .length(2)
            .name("type")
            .postHandler(MapperFieldEnum.original.getPostHandler()).build();
    MapperField other = MapperField.builder()
            .length(0)// 可变长度字段，长度设置为0，表示截取剩余的部分
            .name("other")
            .postHandler(MapperFieldEnum.original.getPostHandler()).build();


    @Bean
    public BodyCodec bodyCodec() {// 自定义bodyCodec解码器
        // 解码前：14041D08383B00FFFFFFFFFFFFFFFF , 解码后：{"date":1588121819000,"type":"00","other":"FFFFFFFFFFFFFFFF"}   
        // 解码前：14041D08383B00FFFF , 解码后：{"date":1588121819000,"type":"00","other":"FFFF"}
        return BodyCodecBuilder.build()
                .commandCode("1111")// 这个解码器支持的指令码
                .nextField(date)// 字段有前后顺序关系
                .nextField(type)
                .nextField(other);
    }
}
```

### Maven 引用方式

> 注意：`1.5.*.RELEASE 不兼容升级到 1.6.*.RELEASE`
### 1.6.0 版本重新命名了包路径
```
之前的包路径：com.bell.mf
现在的包路径：com.github.xiaobingzhou.messageframe
```
```xml
<dependency>	
    <groupId>com.github.xiaobingzhou</groupId>
    <artifactId>message-frame</artifactId>
    <version>1.6.0.RELEASE</version>
</dependency>
```

> 注意：项目从1.5.0.RELEASE才开始发布到maven中央仓库

```xml
<dependency>	
    <groupId>com.github.xiaobingzhou</groupId>
    <artifactId>message-frame</artifactId>
    <version>1.5.0.RELEASE</version>
</dependency>
```

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

#### 1、使用继承AbstractMessageFrameHandler抽象类（不推荐）

```java
@Component
public class MessageFrameDispather extends AbstractMessageFrameHandler{
    
    @Autowired
    private MessageFrameHandlerRepository messageFrameHandlerRepository;
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

#### 2、使用注解方式（推荐）

```java
@Component
@MessageFrameHandler
public class MessageFrameDispather{
    
    @Autowired
	private Dispatcher dispatcher;
    
	/**
	 * 根据不同指令码调用不同的解析方法
	 * 需要对多个if进行重构
	 */
	public void analysis(String deviceId, MessageFrame frame, String message) {
		// 重构后
		SimpleMessageFrameRequest request = SimpleMessageFrameRequest.builder()
				.deviceId(deviceId)
				.messageFrame(messageFrame)
				.message(message)
				.systemDate(new Date())
				.build();
		try {
			dispatcher.dispatch(request);
		} catch (MessageFrameHandlerException e) {
			logger.error("执行handle方法异常:"+e.getLocalizedMessage(), e);
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
### 2、使用maven安装到本地仓库(需要跳过gpg检查)
```java
mvn clean install -Dgpg.skip
```
### 3、获取jar包
```java
cd target
```
