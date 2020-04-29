package com.bell.mf.processor;

import com.bell.mf.mapper.MapperField;
import com.bell.mf.codec.BodyCodec;
import com.bell.mf.repository.BodyCodecRepository;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;

import java.util.ArrayList;
import java.util.List;

/**
 * BodyCodec接口的后置处理器
 * @author bell.zhouxiaobing
 * @since 1.5.4
 */
public class BodyCodecBeanPostProcessor implements BeanPostProcessor, Ordered {

    private BodyCodecRepository repository;

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 2;// 在HandlerBeanPostProcessor和InterceptorBeanPostProcessor之后执行
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
            // 检查字段
            check(beanName, bodyCodec);
            bodyCodec.getCommandCodes().forEach(c -> {
                repository.setBodyCodec(c, bodyCodec);
            });
        }
        return bean;
    }

    protected void check(String beanName, BodyCodec bodyCodec) {
        List<MapperField> mapperFields = bodyCodec.getMapperFields();
        List<String> existNames = new ArrayList<>();
        for (MapperField mapperField : mapperFields) {
            String name = mapperField.getName();
            // 检查length
            if (mapperField.getLength() % 2 != 0) {
                throw new BeanCreationException(
                        String.format("BodyCodec解码器:[%s], 字段:[%s], 长度:[%s] 是奇数",
                                beanName, name, mapperField.getLength()));
            }
            // 检查name是否相同
            if (existNames.contains(name)) {
                throw new BeanCreationException(
                        String.format("BodyCodec解码器:[%s], 字段:[%s] 有重复", beanName, name));
            }
            existNames.add(name);
        }
    }
}
