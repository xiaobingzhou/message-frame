package com.github.xiaobingzhou.messageframe;

import com.github.xiaobingzhou.messageframe.bind.BindParam;
import com.github.xiaobingzhou.messageframe.codec.BodyCodec;
import com.github.xiaobingzhou.messageframe.enums.ParameterNameEnum;
import com.github.xiaobingzhou.messageframe.interceptor.ExecutionChain;
import com.github.xiaobingzhou.messageframe.interceptor.HandlerInterceptor;
import com.github.xiaobingzhou.messageframe.repository.BindParamRepository;
import com.github.xiaobingzhou.messageframe.repository.BodyCodecRepository;
import com.github.xiaobingzhou.messageframe.repository.HandlerRepository;
import com.github.xiaobingzhou.messageframe.repository.Store;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.lang.reflect.Method;
import java.util.*;

/**
 * 初始化检查器
 * @author bell.zhouxiaobing
 * @since 1.5.4
 */
@Slf4j
public class InitChecker implements ApplicationListener<ContextRefreshedEvent> {

    private HandlerProperties handlerProperties;

    private ApplicationContext applicationContext;

    public InitChecker(HandlerProperties handlerProperties) {
        this.handlerProperties = handlerProperties;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        applicationContext = event.getApplicationContext();

        Set<String> commandCodes = applicationContext.getBean(HandlerRepository.class).getCommandCodes();

        log.debug("支持的指令码: " + commandCodes);

        // 检查参数绑定器
        checkBindParams(commandCodes);

        // 指令码拦截器
        checkInterceptors(commandCodes);

        // 检查解码器
        checkBodyCodec(commandCodes);

    }

    private void checkBindParams(Set<String> commandCodes) {
        HandlerRepository handlerRepository = applicationContext.getBean(HandlerRepository.class);
        List<BindParam> bindParamList = applicationContext.getBean(BindParamRepository.class).getBindParamList();

        // 遍历指令码，method去重
        List<Method> checkedMethods = new ArrayList<>();
        for (String key : handlerRepository.keys()) {
            log.debug("Checked ==> handlerRepository.key: [{}]", key);

            Store store = handlerRepository.value(key);
            log.debug("Checked ==> handlerRepository.value: [{}]", store);

            Method method = store.getMethod();

            // 方法去重
            if (checkedMethods.contains(method)) continue;
            checkedMethods.add(method);

            // 参数名
            String[] parameterNames = store.getParameterNames();
            Class<?>[] parameterTypes = method.getParameterTypes();
            for (int i = 0; i < parameterNames.length; i++) {
                boolean support = false;
                for (BindParam bindParam : bindParamList) {
                    if (bindParam.matchGenricType(parameterTypes[i])
                            && bindParam.support(parameterNames[i])) {
                        support = true;
                        break;
                    }
                }

                if (!support) {
                    throw new ApplicationContextException(
                            String.format("[%s.%s()] 方法的参数名 [%s] 没有匹配到参数绑定器, 请检查参数名是否正确; " +
                                            "或者想使用自定义的参数绑定器, 那请实现 BindParam 接口并将其添加到spring容器中",
                                    method.getDeclaringClass().getName(), method.getName(), parameterNames[i]));
                }
            }
        }
    }

    private void checkInterceptors(Set<String> commandCodes) {
        ExecutionChain executionChain = applicationContext.getBean(ExecutionChain.class);
        List<HandlerInterceptor> interceptors = executionChain.getInterceptors();

        log.debug("全局拦截器: " + interceptors);

        HashSet<String> residueCommandCodes = new HashSet<>();

        for (String commandCode : commandCodes) {
            if (executionChain.getCommandCodeInterceptors(commandCode) == null) {
                residueCommandCodes.add(commandCode);
            }
        }

        log.info("Checked ==> No Match HandlerInterceptor: {}", residueCommandCodes);
    }

    private void checkBodyCodec(Set<String> commandCodes) {
        HandlerRepository handlerRepository = applicationContext.getBean(HandlerRepository.class);
        BodyCodecRepository bodyCodecRepository = applicationContext.getBean(BodyCodecRepository.class);
        Set<String> bodyCodecRepositoryCommandCodes = bodyCodecRepository.getCommandCodes();

        log.debug("BodyCodec解码器: " + bodyCodecRepositoryCommandCodes);

        HashSet<String> residueCommandCodes = new HashSet<>(commandCodes);
        if (residueCommandCodes.removeAll(bodyCodecRepositoryCommandCodes)) {
            log.info("Checked ==> No Match BodyCodec: {}", residueCommandCodes);
        }

    }

}
