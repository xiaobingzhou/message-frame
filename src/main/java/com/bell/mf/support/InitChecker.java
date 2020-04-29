package com.bell.mf.support;

import com.bell.mf.support.interceptor.ExecutionChain;
import com.bell.mf.support.interceptor.MessageFrameHandlerInterceptor;
import com.bell.mf.support.repository.BodyCodecRepository;
import com.bell.mf.support.repository.HandlerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.List;
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

        HandlerRepository handlerRepository = applicationContext.getBean("handlerRepository", HandlerRepository.class);
        Set<String> commandCodes = handlerRepository.getCommandCodes();
        log.debug("指令码: " + commandCodes);

        BodyCodecRepository bodyCodecRepository = applicationContext.getBean("bodyCodecRepository", BodyCodecRepository.class);
        Set<String> bodyCodecRepositoryCommandCodes = bodyCodecRepository.getCommandCodes();
        log.debug("BodyCodec解码器: " + bodyCodecRepositoryCommandCodes);

        for (String commandCode : commandCodes) {
            if (!bodyCodecRepositoryCommandCodes.contains(commandCode)) {
                log.info("Checked ==> No Match BodyCodec解码器: [{}]", commandCode);
            }
        }

        ExecutionChain executionChain = applicationContext.getBean("executionChain", ExecutionChain.class);
        List<MessageFrameHandlerInterceptor> interceptors = executionChain.getInterceptors();
        log.debug("全局拦截器: " + interceptors);

        for (String commandCode : commandCodes) {
            if (executionChain.getCommandCodeInterceptors(commandCode) == null) {
                log.info("Checked ==> No Match 指令码拦截器: [{}]", commandCode);
            }
        }

    }


}
