package com.bell.mf.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.bell.mf.mapper.MapperFieldHandler;
import com.bell.mf.mapper.MapperFieldEnum;

/**
 * @ClassName MapperFieldEnum
 * @Description 映射字段注解
 * @author bell.zhouxiaobing
 * @date 2019年11月28日
 * @since 1.4.0
 * 
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MapperField {
	/**
	 * @Description 该字段的下标
	 * @return
	 */
	int index();
	
	/**
	 * @Description 该字段的长度
	 * @return
	 */
	int length();

	/**
	 * @Description 该字段的后置处理类
	 * 如果设置该字段，那么postHandle不能是接口或抽象类
	 * 如果该字段为默认值，则调用innerMethod枚举中你的匿名后置处理类
	 * @return
	 */
	Class<? extends MapperFieldHandler> postHandle() default MapperFieldHandler.class;
	
	/**
	 * @Description 该字段的后置处理方法枚举，MapperFieldEnum返回后置处理类
	 * 如果postHandle值为MapperFieldHandler，即调用MapperFieldEnum的getPostHandler获取后置处理类
	 * @return
	 */
	MapperFieldEnum anonymousMethod() default MapperFieldEnum.original;
	
}