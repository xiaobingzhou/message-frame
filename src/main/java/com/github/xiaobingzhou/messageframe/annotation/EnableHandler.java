package com.github.xiaobingzhou.messageframe.annotation;

import com.github.xiaobingzhou.messageframe.HandlerAutoConfiguration;
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
@Import(HandlerAutoConfiguration.class)
public @interface EnableHandler {
}
