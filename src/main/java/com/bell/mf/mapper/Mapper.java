package com.bell.mf.mapper;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @ClassName Mapper
 * @Description 映射工具
 * @author bell.zhouxiaobing
 * @date 2019年11月28日
 * @since 1.4.0
 */
public class Mapper {

	/**
	 * @Description 根据targetClass字段上标注的@MFMapperField注解解析body字符串
	 * @param body
	 * @param targetClass
	 * @return
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public static <T> T mapper(final String body, Class<T> targetClass) throws InstantiationException, IllegalAccessException {
		// init size
		MapperField[] bodyFields = new MapperField[targetClass.getDeclaredFields().length];
		// 遍历所有字段，判断是否有标注@MapperField注解
		for (Field field : targetClass.getDeclaredFields()) {
			com.bell.mf.annotation.MapperField annotation = field.getAnnotation(com.bell.mf.annotation.MapperField.class);
			if (annotation != null) {
				MapperField mapperField = MapperField.builder().length(annotation.length())
						.name(field.getName()).postHandler(getPostHandler(annotation)).build();
				if (bodyFields[annotation.index()] != null) {
					throw new RuntimeException(String.format("[%s#%s] @MapperField index [%s] duplicate", targetClass.getName(), field.getName(), annotation.index()));
				}
				bodyFields[annotation.index()] = mapperField;
			}
		}
		JSONObject mapper = mapper(body, Arrays.asList(bodyFields));
		if (mapper == null) { return null; }
		return JSON.parseObject(mapper.toJSONString(), targetClass);
	}
	
	private static MapperFieldHandler getPostHandler(com.bell.mf.annotation.MapperField annotation) throws InstantiationException, IllegalAccessException {
		if (annotation.postHandle() == MapperFieldHandler.class) {
			return annotation.anonymousMethod().getPostHandler();
		}
		return annotation.postHandle().newInstance();
	}

	/**
	 * @Description 根据MapperField的list集合解析body字符串
	 * @param body
	 * @param list
	 * @return
	 */
	public static JSONObject mapper(final String body, List<MapperField> list) {
		if (StringUtils.isEmpty(body) || list.isEmpty())
			return null;
		
		JSONObject result = new JSONObject(list.size());
		int start = 0;
		for (int i = 0; i < list.size(); i++) {
			MapperField field = list.get(i);
			String val = body.substring(start, start + field.getLength());
			result.put(field.getName(), val);
			start += field.getLength();
		}
		// postHandle
		for (int i = 0; i < list.size(); i++) {
			MapperField field = list.get(i);
			MapperFieldHandler handler = field.getPostHandler();
			if (handler != null) {
				String key = field.getName();
				String val = (String) result.get(key);
				result.put(key, handler.postHandle(val, result));
			}
		}
		return result;
	}

}
