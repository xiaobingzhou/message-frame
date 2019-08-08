package com.bell.mf.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记在指令码解析方法上
 * @author bell.zhouxiaobing
 * @since 1.2
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandCode {

	/**
	 * 指令码
	 * @return
	 */
	String[] value();
	
}
