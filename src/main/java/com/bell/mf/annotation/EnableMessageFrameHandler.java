package com.bell.mf.annotation;

import com.bell.mf.support.MessageFrameHandlerAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 启用MessageFrameHandler功能
 * @author bell.zhouxiaobing
 * @since 1.5.1
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(MessageFrameHandlerAutoConfiguration.class)
public @interface EnableMessageFrameHandler {
}
