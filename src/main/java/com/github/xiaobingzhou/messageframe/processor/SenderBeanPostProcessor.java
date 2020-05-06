package com.github.xiaobingzhou.messageframe.processor;

import com.github.xiaobingzhou.messageframe.interceptor.ExecutionChain;
import com.github.xiaobingzhou.messageframe.response.HandlerResponse;
import com.github.xiaobingzhou.messageframe.response.Sender;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.cglib.proxy.Proxy;
import org.springframework.core.Ordered;

import java.lang.reflect.Method;

@Slf4j
public class SenderBeanPostProcessor implements BeanPostProcessor, Ordered{

    private String otherBeanName;

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    @Setter
    private ExecutionChain executionChain;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof Sender) {
            return proxySender((Sender) bean, beanName);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    private Object proxySender(Sender bean, String beanName) {
        if (otherBeanName != null) {
            throw new BeanCreationException(String.format("发现两个Sender实例, [%s] 和 [%s]", beanName, otherBeanName));
        }

        log.debug("Sender target instance" + bean);

        final Sender sender = bean;
        Class<? extends Sender> clazz = sender.getClass();

        Sender proxyInstance = (Sender) Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Object invoke = method.invoke(sender, args);

                if ("send".equals(method.getName())){
                    HandlerResponse resp = (HandlerResponse) args[0];
                    executionChain.triggerAfterSend(resp);
                }

                return invoke;
            }
        });

        log.debug("Sender proxy instance" + proxyInstance);

        return proxyInstance;
    }

}
