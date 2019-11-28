package com.bell.mf.mapper;

import java.util.Calendar;
import java.util.Date;

/**
 * @ClassName MapperFieldPostHandler
 * @Description MapperField后置处理器
 * @author bell.zhouxiaobing
 * @date 2019年11月28日
 * @since 1.4.0
 * @see com.bell.mf.mapper.Mapper
 * @see com.bell.mf.mapper.MapperField
 */
@FunctionalInterface
public interface MapperFieldPostHandler {
	
	/**
	 * @Description MapperField后置处理器的处理方法
	 * @param value	当前需要进行后置处理的值，是MapperField中name对应的值
	 * @param result
	 * @return
	 * @see com.bell.mf.mapper.Mapper#mapper(String, java.util.List)
	 */
	public Object postHandle(String value, Object result);
	
	// ===============static method================
//	/**
//	 * @Description 什么也不处理
//	 * @return
//	 */
//	static MapperFieldPostHandler original() {
//		return (v, r) -> v;
//	}
//	
//	/**
//	 * @Description 16进制转2进制字符串
//	 * @return
//	 */
//	static MapperFieldPostHandler hex2Bin() {
//		return (v, r) -> Integer.toBinaryString(Integer.valueOf(v, 16));
//	}
//	
//	/**
//	 * @Description 16进制转10进制整型
//	 * @return
//	 */
//	static MapperFieldPostHandler hex2Oct() {
//		return (v, r) -> Integer.valueOf(v, 16);
//	}
//	
//	/**
//	 * @Description 16进制转10进制字符串
//	 * @return
//	 */
//	static MapperFieldPostHandler hex2OctStr() {
//		return (v, r) -> Integer.valueOf(v, 16).toString();
//	}
//	
//	/**
//	 * @Description 截取value字符串
//	 * @param beginIndex
//	 * @param endIndex
//	 * @return
//	 */
//	static MapperFieldPostHandler substring(int beginIndex, int endIndex) {
//		return (v, r) -> v.substring(beginIndex, endIndex);
//	}
//	
//	/**
//	 * @Description 解析为Date对象
//	 * @return
//	 */
//	static MapperFieldPostHandler datetime() {
//		return (value, r) -> {
//			int year = Integer.valueOf(value.substring(0, 2), 16) + 2000;// 18年
//			int month = Integer.valueOf(value.substring(2, 4), 16) - 1;
//			int day = Integer.valueOf(value.substring(4, 6), 16);
//			int hour = Integer.valueOf(value.substring(6, 8), 16);
//			int minute = Integer.valueOf(value.substring(8, 10), 16);
//			int second = Integer.valueOf(value.substring(10, 12), 16);
//			Calendar calendar = Calendar.getInstance();
//			calendar.set(year, month, day, hour, minute, second);
//			calendar.set(Calendar.MILLISECOND, 0);
//			Date time = calendar.getTime();
//			return time;
//		};
//	}
}
