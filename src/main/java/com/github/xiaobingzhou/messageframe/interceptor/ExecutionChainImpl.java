package com.github.xiaobingzhou.messageframe.interceptor;

import java.util.*;

import com.github.xiaobingzhou.messageframe.annotation.CommandCode;
import com.github.xiaobingzhou.messageframe.request.HandlerRequest;
import com.github.xiaobingzhou.messageframe.response.HandlerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.core.annotation.AnnotationUtils;

/**
 * ExecutionChain接口实现
 * @author bell.zhouxiaobing
 * @since 1.3
 */
public class ExecutionChainImpl implements ExecutionChain {

	private static final Logger logger = LoggerFactory.getLogger(ExecutionChainImpl.class);

	private List<HandlerInterceptor> interceptors = new ArrayList<>();

    /**
     * 指令码对应的拦截器
     */
	private Map<String, List<HandlerInterceptor>> commandCodeInterceptorsMap = new HashMap<>();

    /**
     * 添加指令码拦截器
     */
    public boolean addCommandCodeInterceptor(HandlerInterceptor interceptor, CommandCode commandCode) {
        Arrays.stream(commandCode.value()).forEach(c -> {
            List<HandlerInterceptor> list = commandCodeInterceptorsMap.get(c);
            if (list == null) {
                list = new ArrayList<>();
            }
            list.add(interceptor);
            AnnotationAwareOrderComparator.sort(list);// 排序
            commandCodeInterceptorsMap.put(c, list);
        });
        return true;
    }

    public boolean addInterceptor(HandlerInterceptor interceptor) {
		if (interceptor == null) {
			logger.info("interceptor is null!");
			return false;
		}
		// 是否有CommandCode注解标注
        CommandCode commandCode = getAnnotation(interceptor);
		if (Objects.isNull(commandCode)) {
            boolean add = this.interceptors.add(interceptor);// 全局拦截器
            AnnotationAwareOrderComparator.sort(interceptors);// 排序
            return add;
        }
        // 指令码拦截器
		return addCommandCodeInterceptor(interceptor, commandCode);
	}

    private CommandCode getAnnotation(HandlerInterceptor interceptor) {
        return AnnotationUtils.findAnnotation(interceptor.getClass(), CommandCode.class);
    }

    public void applyPreHandle(HandlerRequest request) {
        // 全局拦截器-前置处理
		for (HandlerInterceptor interceptor : interceptors) {
            preHandle(request, interceptor);
        }

        // 指令码拦截器-前置处理
        for (HandlerInterceptor interceptor : commandCodeInterceptors(request)) {
            preHandle(request, interceptor);
        }

	}

    private void preHandle(HandlerRequest request, HandlerInterceptor interceptor) {
        if (interceptor.support(request)) {
            interceptor.preHandle(request);
        }
    }

    public void applyPostHandle(HandlerRequest request) {
        // 全局拦截器-后置处理
		for (HandlerInterceptor interceptor : interceptors) {
            postHandle(request, interceptor);
        }

        // 指令码拦截器-后置处理
        for (HandlerInterceptor interceptor : commandCodeInterceptors(request)) {
            postHandle(request, interceptor);
        }

    }

    private void postHandle(HandlerRequest request, HandlerInterceptor interceptor) {
        if (interceptor.support(request)) {
            interceptor.postHandle(request);
        }
    }

    @Override
    public void triggerAfterSend(HandlerResponse response) {
        // 全局拦截器-后置处理
        for (HandlerInterceptor interceptor : interceptors) {
            afterSend(response, interceptor);
        }

        // 指令码拦截器-后置处理
        for (HandlerInterceptor interceptor : commandCodeInterceptors(response.getMessageFrame().getCommandCode())) {
            afterSend(response, interceptor);
        }
    }

    private void afterSend(HandlerResponse response, HandlerInterceptor interceptor) {
        interceptor.afterSend(response);
    }

    private List<HandlerInterceptor> commandCodeInterceptors(HandlerRequest request) {
        String commandCode = request.getMessageFrame().getCommandCode();
        return commandCodeInterceptors(commandCode);
    }

    private List<HandlerInterceptor> commandCodeInterceptors(String commandCode) {
        List<HandlerInterceptor> list = commandCodeInterceptorsMap.get(commandCode);
        return Objects.isNull(list) ? Collections.emptyList() : list;
    }

    @Override
    public List<HandlerInterceptor> getInterceptors() {
        return interceptors;
    }

    @Override
    public List<HandlerInterceptor> getCommandCodeInterceptors(String commandCode) {
        return commandCodeInterceptorsMap.get(commandCode);
    }
}
