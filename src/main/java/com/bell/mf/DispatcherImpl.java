package com.bell.mf;

import com.alibaba.fastjson.JSONObject;
import com.bell.mf.bind.BindParam;
import com.bell.mf.codec.BodyCodec;
import com.bell.mf.handler.HandlerException;
import com.bell.mf.mapper.Mapper;
import com.bell.mf.mapper.MapperField;
import com.bell.mf.repository.BindParamRepository;
import com.bell.mf.repository.BodyCodecRepository;
import com.bell.mf.request.HandlerRequest;
import com.bell.mf.handler.AbstractHandler;
import com.bell.mf.interceptor.ExecutionChain;
import com.bell.mf.repository.HandlerRepository;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MessageFrameHandler调度器
 * @author bell.zhouxiaobing
 * @since 1.3
 */
@Slf4j
public class DispatcherImpl extends AbstractHandler implements Dispatcher{

	@Setter
	@Getter
	private HandlerRepository handlerRepository;
	
	@Setter private ExecutionChain executionChain;

	@Setter private BodyCodecRepository bodyCodecRepository;

	@Setter private BindParamRepository bindParamRepository;

	private Map<Method, BindParam[]> argsCache = new HashMap<>(128);

	@Override
	public void dispatch(HandlerRequest request) throws HandlerException {
		// 执行前
		executionChain.applyPreHandle(request);
		// 执行处理
		doHandle(request);
		// 执行后
		executionChain.applyPostHandle(request);
	}

	@Override
	protected Object[] getMethodArgs(HandlerRequest request) {
		// body 字段解码
		bodyCodec(request);

		// 绑定参数
		return bindParams(request);
	}

	/**
	 * 绑定参数
	 * @param request
	 * @return Object[]
	 * @since 1.5.5
	 */
	protected Object[] bindParams(HandlerRequest request) {
		Method method = getMethod(request);
		Class<?>[] parameterTypes = method.getParameterTypes();
		String[] parameterNames = getParameterNames(request);

		int argsLength = parameterNames.length;
		Object[] args = new Object[argsLength];
		BindParam[] bindParamsCache = argsCache.getOrDefault(method, new BindParam[argsLength]);
		for (int i = 0; i < argsLength; i++) {
			if (bindParamsCache[i] != null) {
				log.debug("使用已缓存的参数绑定器{}", bindParamsCache);
				args[i] = bindParamsCache[i].bind(request);
				continue;
			}
			for (BindParam bindParam : bindParamRepository.getBindParamList()) {
				if (bindParam.support(parameterNames[i], parameterTypes[i])) {
					log.debug("参数绑定器{}, 绑定参数{}", bindParam, parameterNames[i]);
					args[i] = bindParam.bind(request);
					bindParamsCache[i] = bindParam;
					break;
				}
			}
		}
		// 设置缓存
		argsCache.putIfAbsent(method, bindParamsCache);
		return args;
	}

	protected void bodyCodec(HandlerRequest request) {
		String commandCode = request.getMessageFrame().getCommandCode();
		String body = request.getMessageFrame().getBody();
		BodyCodec bodyCodec = bodyCodecRepository.getBodyCodec(commandCode);
		if (bodyCodec == null) {
			log.info("指令码 [{}] bodyCodec解码器未找到", commandCode);
			return;
		}
		List<MapperField> mapperFields = bodyCodec.getMapperFields();
		JSONObject bodyJson = Mapper.mapper(body, mapperFields);
		request.setBodyJson(bodyJson);
	}

	protected String[] getParameterNames(HandlerRequest request) {
		return handlerRepository.getHandlerMethodParameterNames(request.getMessageFrame().getCommandCode());
	}

	protected Method getMethod(HandlerRequest request) {
		return handlerRepository.getHandlerMethod(request.getMessageFrame().getCommandCode());
	}
}
