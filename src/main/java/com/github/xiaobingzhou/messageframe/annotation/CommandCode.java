package com.github.xiaobingzhou.messageframe.annotation;

import com.github.xiaobingzhou.messageframe.matcher.Matcher;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记在指令码解析方法上
 * @author bell.zhouxiaobing
 * @since 1.2
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandCode {

	/**
	 * 指令码
	 * @return String[]
	 */
	String[] value();

	/**
	 * Handler方法对应的版本号
	 * @return String[]
	 * @since 1.6.3
	 * @see com.github.xiaobingzhou.messageframe.IMessageFrame#getProtocolVer()
	 */
	String version() default Matcher.MATCH_ALL;
	
}
