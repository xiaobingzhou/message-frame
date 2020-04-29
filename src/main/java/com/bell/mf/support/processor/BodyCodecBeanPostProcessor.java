package com.bell.mf.support.processor;

import com.bell.mf.support.codec.BodyCodec;
import com.bell.mf.support.repository.BodyCodecRepository;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;

/**
 * BodyCodec接口的后置处理器
 * @author bell.zhouxiaobing
 * @since 1.5.4
 */
public class BodyCodecBeanPostProcessor implements BeanPostProcessor, Ordered {

    private BodyCodecRepository repository;

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    public BodyCodecRepository getRepository() {
        return repository;
    }

    public void setRepository(BodyCodecRepository repository) {
        this.repository = repository;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof BodyCodec) {
            BodyCodec bodyCodec = (BodyCodec)bean;
            bodyCodec.getCommandCodes().forEach(c -> {
                repository.setBodyCodec(c, bodyCodec);
            });
        }
        return bean;
    }
}
