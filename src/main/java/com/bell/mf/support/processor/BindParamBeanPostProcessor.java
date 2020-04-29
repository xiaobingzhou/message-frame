package com.bell.mf.support.processor;

import com.bell.mf.bind.BindParam;
import com.bell.mf.support.repository.BindParamRepository;
import lombok.Setter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;

/**
 * BindParm参数绑定后置处理器
 * @author bell.zhouxiaobing
 * @since 1.5.5
 */
public class BindParamBeanPostProcessor implements BeanPostProcessor, Ordered {

    @Setter private BindParamRepository repository;

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof BindParam) {
            repository.addBindParam((BindParam) bean);
        }
        return bean;
    }

}
