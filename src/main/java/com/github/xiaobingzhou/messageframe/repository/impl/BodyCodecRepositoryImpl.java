package com.github.xiaobingzhou.messageframe.repository.impl;

import com.github.xiaobingzhou.messageframe.codec.BodyCodec;
import com.github.xiaobingzhou.messageframe.repository.BodyCodecRepository;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * bodyCodec解码器的仓库
 * @author bell.zhouxiaobing
 * @since 1.5.4
 */
public class BodyCodecRepositoryImpl implements BodyCodecRepository, ApplicationContextAware, DisposableBean {

    private ApplicationContext applicationContext;

    /**
     * 保存指令码和bodyCodec解码器的对应关系
     * body_codec_map
     */
    private static Map<String, String> BODY_CODEC_MAP = new HashMap<>(128);

    @Override
    public BodyCodec getBodyCodec(String commandCode) {
        String beanName = BODY_CODEC_MAP.get(commandCode);
        return beanName != null ? applicationContext.getBean(beanName, BodyCodec.class) : null;
    }

    @Override
    public void setBodyCodec(BodyCodec bodyCodec, String beanName) {
        List<String> commandCodes = bodyCodec.getCommandCodes();
        for (String commandCode : commandCodes) {
            String exist = BODY_CODEC_MAP.put(commandCode, beanName);
            if (exist != null) {
                throw new BeanCreationException(String.format("指令码:[%s] 匹配到两个BodyCodec解码器:[%s] 和 [%s]",
                                commandCode, beanName, exist));
            }
        }
    }

    @Override
    public Set<String> getCommandCodes() {
        return BODY_CODEC_MAP.keySet();
    }

    @Override
    public void destroy() throws Exception {
        BODY_CODEC_MAP.clear();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
