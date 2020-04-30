package com.github.xiaobingzhou.messageframe.mapper;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import com.github.xiaobingzhou.messageframe.codec.BodyCodec;
import com.github.xiaobingzhou.messageframe.repository.BodyCodecRepository;
import com.github.xiaobingzhou.messageframe.request.HandlerRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 映射工具
 * 2019年11月28日
 * @author bell.zhouxiaobing
 * @since 1.4.0
 */
@Slf4j
public class Mapper {

	private static BodyCodecRepository bodyCodecRepository;

	public Mapper(BodyCodecRepository bodyCodecRepository) {
		Mapper.bodyCodecRepository = bodyCodecRepository;
	}

	/**
	 * 根据targetClass字段上标注的@MFMapperField注解解析body字符串
	 * @param body
	 * @param targetClass
	 * @return <T> T
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public static <T> T mapper(final String body, Class<T> targetClass) throws InstantiationException, IllegalAccessException {
		// init size
		MapperField[] bodyFields = new MapperField[targetClass.getDeclaredFields().length];

		// 遍历所有字段，判断是否有标注@MapperField注解
		for (Field field : targetClass.getDeclaredFields()) {
			com.github.xiaobingzhou.messageframe.annotation.MapperField annotation = field.getAnnotation(com.github.xiaobingzhou.messageframe.annotation.MapperField.class);
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
	
	private static MapperFieldHandler getPostHandler(com.github.xiaobingzhou.messageframe.annotation.MapperField annotation) throws InstantiationException, IllegalAccessException {
		if (annotation.postHandle() == MapperFieldHandler.class) {
			return annotation.anonymousMethod().getPostHandler();
		}

		return annotation.postHandle().newInstance();
	}

	/**
	 * 根据MapperField的list集合解析body字符串
	 * @param body
	 * @param list
	 * @return JSONObject
	 */
	public static JSONObject mapper(final String body, List<MapperField> list) {
		if (StringUtils.isEmpty(body) || list.isEmpty())
			return null;
		
		JSONObject result = new JSONObject(list.size());

		int start = 0;
		for (int i = 0; i < list.size(); i++) {
			MapperField field = list.get(i);
			int length = field.getLength();

			if (length <= 0) {// 表示截取body剩余的部分
				String val = body.substring(start, body.length());
				result.put(field.getName(), val);
				break;
			}

			String val = body.substring(start, start + length);
			result.put(field.getName(), val);
			start += length;
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


	/**
     * <pre>
     * 解码结果会通过 <code>setBodyJson</code> 方法设置回 <code>HandlerRequest</code>
     * </pre>
	 * @param request
	 * @return
	 */
	public static JSONObject mapper(HandlerRequest request) {
		// 判断是否已解码
		if (request.getBodyJson() != null) {
			return request.getBodyJson();
		}

		if (Mapper.bodyCodecRepository == null) {
			throw new NullPointerException("Mapper.bodyCodecRepository is null");
		}

		// 从bodyCodecRepository仓库中获取该指令码的解码器
		String commandCode = request.getMessageFrame().getCommandCode();
		BodyCodec bodyCodec = Mapper.bodyCodecRepository.getBodyCodec(commandCode);
		if (bodyCodec == null) {
			log.warn("指令码 [{}] bodyCodec解码器未找到", commandCode);
			return null;
		}

		List<MapperField> mapperFields = bodyCodec.getMapperFields();
		String body = request.getMessageFrame().getBody();
		JSONObject bodyJson = Mapper.mapper(body, mapperFields);
		request.setBodyJson(bodyJson);
		return bodyJson;
	}

}
