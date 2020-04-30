package com.bell.mf;

import com.bell.mf.bind.BindParam;
import com.bell.mf.codec.BodyCodec;
import com.bell.mf.interceptor.ExecutionChain;
import com.bell.mf.interceptor.HandlerInterceptor;
import com.bell.mf.mapper.MapperField;
import com.bell.mf.repository.BindParamRepository;
import com.bell.mf.repository.BodyCodecRepository;
import com.bell.mf.repository.HandlerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * 初始化检查器
 * @author bell.zhouxiaobing
 * @since 1.5.4
 */
@Slf4j
public class InitChecker implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext applicationContext = event.getApplicationContext();

        HandlerRepository handlerRepository = applicationContext.getBean(HandlerRepository.class);
        Set<String> commandCodes = handlerRepository.getCommandCodes();
        log.debug("指令码: " + commandCodes);

        // 检查参数绑定器
        checkBindParams(applicationContext, commandCodes);

        // 指令码拦截器
        checkInterceptors(applicationContext, commandCodes);

        // 检查解码器
        checkBodyCodec(applicationContext, commandCodes);

    }

    private void checkBindParams(ApplicationContext applicationContext, Set<String> commandCodes) {
        HandlerRepository handlerRepository = applicationContext.getBean(HandlerRepository.class);
        List<BindParam> bindParamList = applicationContext.getBean(BindParamRepository.class).getBindParamList();
        // 遍历指令码
        for (String commandCode : commandCodes) {
            Method method = handlerRepository.getHandlerMethod(commandCode);
            Class<?>[] parameterTypes = method.getParameterTypes();
            // 遍历参数名
            String[] parameterNames = handlerRepository.getHandlerMethodParameterNames(commandCode);
            for (int i = 0; i < parameterNames.length; i++) {
                boolean support = false;
                for (BindParam bindParam : bindParamList) {
                    if (bindParam.support(parameterNames[i], parameterTypes[i])) {
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

    private void checkInterceptors(ApplicationContext applicationContext, Set<String> commandCodes) {
        ExecutionChain executionChain = applicationContext.getBean(ExecutionChain.class);
        List<HandlerInterceptor> interceptors = executionChain.getInterceptors();
        log.debug("全局拦截器: " + interceptors);

        for (String commandCode : commandCodes) {
            if (executionChain.getCommandCodeInterceptors(commandCode) == null) {
                log.info("Checked ==> No Match HandlerInterceptor: [{}]", commandCode);
            }
        }
    }

    private void checkBodyCodec(ApplicationContext applicationContext, Set<String> commandCodes) {
        BodyCodecRepository bodyCodecRepository = applicationContext.getBean(BodyCodecRepository.class);
        Set<String> bodyCodecRepositoryCommandCodes = bodyCodecRepository.getCommandCodes();
        log.debug("BodyCodec解码器: " + bodyCodecRepositoryCommandCodes);

        for (String commandCode : commandCodes) {
            BodyCodec bodyCodec = bodyCodecRepository.getBodyCodec(commandCode);
            if (Objects.isNull(bodyCodec)) {
                log.info("Checked ==> No Match BodyCodec: [{}]", commandCode);
                continue;
            }
            check(bodyCodec);
        }
    }

    protected void check(BodyCodec bodyCodec) {
        List<MapperField> mapperFields = bodyCodec.getMapperFields();
        List<String> existNames = new ArrayList<>();
        for (MapperField mapperField : mapperFields) {
            String name = mapperField.getName();
            // 检查length
            if (mapperField.getLength() % 2 != 0) {
                throw new ApplicationContextException(
                        String.format("BodyCodec解码器:[%s], 字段:[%s], 长度:[%s] 是奇数",
                                bodyCodec.getClass(), name, mapperField.getLength()));
            }
            // 检查name是否相同
            if (existNames.contains(name)) {
                throw new ApplicationContextException(
                        String.format("BodyCodec解码器:[%s], 字段:[%s] 有重复", bodyCodec.getClass(), name));
            }
            existNames.add(name);
        }
    }
}
