package com.bell.mf.mapper;

/**
 * MapperFieldHandler
 * MapperField后置处理器
 * 2019年11月28日
 * @author bell.zhouxiaobing
 * @since 1.4.0
 * @see com.bell.mf.mapper.Mapper
 * @see com.bell.mf.mapper.MapperField
 */
@FunctionalInterface
public interface MapperFieldHandler {
	
	/**
	 * MapperField后置处理器的处理方法
	 * @param value	当前需要进行后置处理的值，是MapperField中name对应的值
	 * @param result
	 * @return Object
	 * @see com.bell.mf.mapper.Mapper#mapper(String, java.util.List)
	 */
	public Object postHandle(String value, Object result);
	
}
