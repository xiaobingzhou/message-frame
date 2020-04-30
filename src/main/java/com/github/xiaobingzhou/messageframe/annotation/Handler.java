package com.github.xiaobingzhou.messageframe.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Mark as a Handler class
 * 标记为一个Handler类
 * @author bell.zhouxiaobing
 * @since 1.3
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Handler {
}
