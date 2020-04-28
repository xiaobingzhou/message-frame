package com.bell.mf.support.interceptor;

import java.util.*;

import com.bell.mf.annotation.CommandCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bell.mf.handler.MessageFrameRequest;
import org.springframework.core.OrderComparator;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.core.annotation.AnnotationUtils;

/**
 * ExecutionChain接口实现
 * @author bell.zhouxiaobing
 * @since 1.3
 */
public class MessageFrameHandlerExecutionChain implements ExecutionChain {

	private static final Logger logger = LoggerFactory.getLogger(MessageFrameHandlerExecutionChain.class);

	private List<MessageFrameHandlerInterceptor> interceptors = new ArrayList<>();

    /**
     * 指令码对应的拦截器
     */
	private Map<String, List<MessageFrameHandlerInterceptor>> commandCodeInterceptorsMap = new HashMap<>();

    /**
     * 添加指令码拦截器
     */
    public boolean addCommandCodeInterceptor(MessageFrameHandlerInterceptor interceptor, CommandCode commandCode) {
        Arrays.stream(commandCode.value()).forEach(c -> {
            List<MessageFrameHandlerInterceptor> list = commandCodeInterceptorsMap.get(c);
            if (list == null) {
                list = new ArrayList<>();
            }
            list.add(interceptor);
            AnnotationAwareOrderComparator.sort(list);// 排序
            commandCodeInterceptorsMap.put(c, list);
        });
        return true;
    }

    public boolean addInterceptor(MessageFrameHandlerInterceptor interceptor) {
		if (interceptor == null) {
			logger.info("interceptor is null!");
			return false;
		}
		// 是否有CommandCode注解标注
        CommandCode commandCode = getAnnotation(interceptor);
		if (Objects.isNull(commandCode)) {
            boolean add = this.interceptors.add(interceptor);
            AnnotationAwareOrderComparator.sort(interceptors);// 排序
            return add;
        }
		// 指令码拦截器
		return addCommandCodeInterceptor(interceptor, commandCode);
	}

    private CommandCode getAnnotation(MessageFrameHandlerInterceptor interceptor) {
        return AnnotationUtils.findAnnotation(interceptor.getClass(), CommandCode.class);
    }

    public void applyPreHandle(MessageFrameRequest request) {
        // 指令码拦截器-前置处理
        for (MessageFrameHandlerInterceptor interceptor : commandCodeInterceptors(request)) {
            preHandle(request, interceptor);
        }

        // 全局拦截器-前置处理
		for (MessageFrameHandlerInterceptor interceptor : interceptors) {
            preHandle(request, interceptor);
        }

	}

    private void preHandle(MessageFrameRequest request, MessageFrameHandlerInterceptor interceptor) {
        if (interceptor.support(request)) {
            interceptor.preHandle(request);
        }
    }

    public void applyPostHandle(MessageFrameRequest request) {
        // 指令码拦截器-后置处理
        for (MessageFrameHandlerInterceptor interceptor : commandCodeInterceptors(request)) {
            postHandle(request, interceptor);
        }

        // 全局拦截器-后置处理
		for (MessageFrameHandlerInterceptor interceptor : interceptors) {
            postHandle(request, interceptor);
        }

    }

    private void postHandle(MessageFrameRequest request, MessageFrameHandlerInterceptor interceptor) {
        if (interceptor.support(request)) {
            interceptor.postHandle(request);
        }
    }

    private List<MessageFrameHandlerInterceptor> commandCodeInterceptors(MessageFrameRequest request) {
        String commandCode = request.getMessageFrame().getCommandCode();
        List<MessageFrameHandlerInterceptor> list = commandCodeInterceptorsMap.get(commandCode);
        return Objects.isNull(list) ? Collections.emptyList() : list;
    }

    @Override
    public List<MessageFrameHandlerInterceptor> getInterceptors() {
        return interceptors;
    }

    @Override
    public List<MessageFrameHandlerInterceptor> getCommandCodeInterceptors(String commandCode) {
        return commandCodeInterceptorsMap.get(commandCode);
    }
}
