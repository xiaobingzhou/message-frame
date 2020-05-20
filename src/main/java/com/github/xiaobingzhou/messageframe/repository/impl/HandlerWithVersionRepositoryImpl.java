package com.github.xiaobingzhou.messageframe.repository.impl;

import com.github.xiaobingzhou.messageframe.annotation.CommandCode;
import com.github.xiaobingzhou.messageframe.annotation.Handler;
import com.github.xiaobingzhou.messageframe.matcher.Matcher;
import com.github.xiaobingzhou.messageframe.repository.HandlerRepository;
import com.github.xiaobingzhou.messageframe.repository.Store;
import com.github.xiaobingzhou.messageframe.request.HandlerRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanCreationException;

import java.lang.reflect.Method;
import java.util.*;

/**
 * 请求处理器仓库，需要匹配指令码和版本号
 * @author bell.zhouxiaobing
 * @since 1.6.3
 * @see HandlerRepositoryImpl
 */
@Slf4j
public class HandlerWithVersionRepositoryImpl extends HandlerRepositoryImpl {

    protected Map<String, Store> keyStoreMap = new HashMap<>(128);

    protected Set<String> commandCodes = new HashSet<>();

    protected Matcher matcher;

    public HandlerWithVersionRepositoryImpl(Matcher matcher) {
        this.matcher = matcher;
    }

    @Override
    public void setHandler(Object bean, String beanName) {
        Method[] declaredMethods = bean.getClass().getDeclaredMethods();
        String globalVersion = bean.getClass().getDeclaredAnnotation(Handler.class).version();

        for (Method method : declaredMethods) {
            CommandCode annotation = method.getAnnotation(CommandCode.class);
            if (annotation == null) {
                continue;
            }

            HandlerStore handlerStore = new HandlerStore();
            handlerStore.setBeanName(beanName);
            handlerStore.setMethod(method);
            handlerStore.setParameterNames(getParameterNames(method));

            String version = getVersion(annotation.version(), globalVersion);

            for (String commandCode : annotation.value()) {
                checkVersion(version, method, beanName);
                Store exist = keyStoreMap.put(getKey(commandCode, version), handlerStore);

                if (exist != null) {
                    String methodName = getMethodName(method);
                    String OtherMethodName = getMethodName(exist.getMethod());
                    throw new BeanCreationException(beanName,
                            String.format("%s()和%s()指令码重复，指令码是：%s 版本号是：%s",
                                    methodName, OtherMethodName, commandCode, version));
                }

                this.commandCodes.add(commandCode);
            }
        }
    }

    protected String getVersion(String version, String globalVersion) {
        // commandCode版本是默认值，handler的全局版本不是默认值
        if ("*".equals(version) && !"*".equals(globalVersion))
            version = globalVersion;
        return version;
    }

    protected String getMethodName(Method method) {
        return method.getDeclaringClass().getName() + "." + method.getName();
    }

    protected void checkVersion(String ver, Method method, String beanName) {
        if (ver.length() > 1 && ver.startsWith("*")) {
            String methodName = getMethodName(method);
            throw new BeanCreationException(beanName, String.format("%s() 匹配的版本号 [%s] 不能以 * 开头",
                    methodName, ver));
        }
    }

    @Override
    public Set<String> getCommandCodes() {
        return Collections.unmodifiableSet(commandCodes);
    }

    @Override
    public Set<String> keys() {
        return Collections.unmodifiableSet(keyStoreMap.keySet());
    }

    @Override
    public Store value(String key) {
        return keyStoreMap.get(key);
    }

    @Override
    public Object getHandler(HandlerRequest request) {
        Store store = getStore(request);
        if (store == null) return null;

        String beanName = store.getBeanName();
        return beanName == null ? null : getApplicationContext().getBean(beanName);
    }

    @Override
    public Method getHandlerMethod(HandlerRequest request) {
        Store store = getStore(request);

        if (store == null) return null;

        return store.getMethod();
    }

    @Override
    public String[] getHandlerMethodParameterNames(HandlerRequest request) {
        Store store = getStore(request);

        if (store == null) return null;

        return store.getParameterNames();
    }

    protected Store getStore(HandlerRequest request) {
        String commandCode = request.getCommandCode();
        String protocolVer = request.getProtocolVer();
        return (Store) matcher.match(() -> getKey(commandCode, protocolVer), keyStoreMap);

    }

    protected String getKey(String commandCode, String protocolVer) {
        return HandlerRepository.class.getSimpleName() + ":" + commandCode + ":" + protocolVer;
    }

    @Override
    public void destroy() throws Exception {
        super.destroy();
        keyStoreMap.clear();
    }
}
