package com.github.xiaobingzhou.messageframe;

import com.github.xiaobingzhou.messageframe.bind.BindParam;
import com.github.xiaobingzhou.messageframe.handler.HandlerException;
import com.github.xiaobingzhou.messageframe.repository.BindParamRepository;
import com.github.xiaobingzhou.messageframe.request.HandlerRequest;
import com.github.xiaobingzhou.messageframe.handler.AbstractHandler;
import com.github.xiaobingzhou.messageframe.interceptor.ExecutionChain;
import com.github.xiaobingzhou.messageframe.repository.HandlerRepository;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * MessageFrameHandler调度器
 * @author bell.zhouxiaobing
 * @since 1.3
 */
@Slf4j
public class DispatcherImpl extends AbstractHandler implements Dispatcher{

	@Setter private HandlerRepository handlerRepository;
	
	@Setter private ExecutionChain executionChain;

	@Setter private BindParamRepository bindParamRepository;

	private static Map<Method, BindParam[]> BIND_PARAMS_CACHE_MAP = new ConcurrentHashMap<>(128);

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
	protected HandlerRepository getHandlerRepository() {
		return handlerRepository;
	}

	@Override
	protected Object[] getMethodArgs(HandlerRequest request) {
		// 绑定参数
		return bindParams(request);
	}

	/**
	 * 绑定参数
	 * @param request 处理请求
	 * @return Object[]
	 * @since 1.5.5
	 */
	protected Object[] bindParams(HandlerRequest request) {
	    // 通过request从handlerRepository获取方法
		Method method = getMethod(request);
		Class<?>[] parameterTypes = method.getParameterTypes();

		// 通过request从handlerRepository获取方法参数名
		String[] parameterNames = getParameterNames(request);

		int argsLength = parameterNames.length;
		// 通过方法从缓存中获取参数绑定器
		BindParam[] bindParamsCache = BIND_PARAMS_CACHE_MAP.getOrDefault(method, new BindParam[argsLength]);

		Object[] args = new Object[argsLength];
		for (int i = 0; i < argsLength; i++) {
			if (bindParamsCache[i] != null) {
				log.debug("使用已缓存的参数绑定器{}", bindParamsCache[i]);
				args[i] = bindParamsCache[i].bind(request);
				continue;
			}

			for (BindParam bindParam : bindParamRepository.getBindParamList()) {
				if (bindParam.matchGenricType(parameterTypes[i])
						&& bindParam.support(parameterNames[i])) {
					log.debug("参数绑定器{}, 绑定参数{}", bindParam, parameterNames[i]);
					args[i] = bindParam.bind(request);
					bindParamsCache[i] = bindParam;
					break;
				}
			}
		}

		// 设置缓存
		BIND_PARAMS_CACHE_MAP.putIfAbsent(method, bindParamsCache);

		return args;
	}

	protected String[] getParameterNames(HandlerRequest request) {
		return handlerRepository.getHandlerMethodParameterNames(request);
	}

	protected Method getMethod(HandlerRequest request) {
		return handlerRepository.getHandlerMethod(request);
	}

}
