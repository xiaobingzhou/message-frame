package com.github.xiaobingzhou.messageframe.repository.impl;

import com.github.xiaobingzhou.messageframe.codec.BodyCodec;
import com.github.xiaobingzhou.messageframe.matcher.Matcher;
import com.github.xiaobingzhou.messageframe.repository.BodyCodecRepository;
import com.github.xiaobingzhou.messageframe.request.HandlerRequest;
import org.springframework.beans.factory.BeanCreationException;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * bodyCodec解码器的仓库
 * 需要匹配版本号
 * @author bell.zhouxiaobing
 * @since 1.6.3
 */
public class BodyCodecWithVersionRepositoryImpl extends BodyCodecRepositoryImpl {

    protected Map<String, String> bodyCodecMap = new ConcurrentHashMap<>(128);

    protected Set<String> commandCodes = new HashSet<>();

    protected Matcher matcher;

    public BodyCodecWithVersionRepositoryImpl(Matcher matcher) {
        this.matcher = matcher;
    }

    @Override
    public BodyCodec getBodyCodec(HandlerRequest request) {
        String beanName = (String) matcher.match(request, bodyCodecMap);
        return getBodyCodecByBeanName(beanName);
    }

    protected BodyCodec getBodyCodecByBeanName(String beanName) {
        return beanName != null ? getApplicationContext().getBean(beanName, BodyCodec.class) : null;
    }

    @Override
    public void setBodyCodec(BodyCodec bodyCodec, String beanName) {
        // 检查解码器
        check(bodyCodec, beanName);
        String version = bodyCodec.getVersion();

        for (String commandCode : bodyCodec.getCommandCodes()) {
            String generateKey = matcher.getKeyGenerator().generateKey(commandCode, version);
            String exist = bodyCodecMap.put(generateKey, beanName);

            if (exist != null) {
                throw new BeanCreationException(String.format("指令码:[%s] 版本号:[%s] 匹配到两个BodyCodec解码器:[%s] 和 [%s]",
                        commandCode, version, beanName, exist));
            }

            this.commandCodes.add(commandCode);
        }
    }

    @Override
    protected void check(BodyCodec bodyCodec, String beanName) {
        super.check(bodyCodec, beanName);
        String version = bodyCodec.getVersion();
        String match = "*";
        if (version.length() > 1 && version.startsWith(match)) {
            throw new BeanCreationException(String.format("BodyCodec解码器:[%s] 版本号:[%s] 不能以[%s]开头",
                    beanName, version, match));
        }
    }

    @Override
    public Set<String> getCommandCodes() {
        return Collections.unmodifiableSet(commandCodes);
    }

    @Override
    public Set<String> keys() {
        return Collections.unmodifiableSet(bodyCodecMap.keySet());
    }

    @Override
    public BodyCodec value(String key) {
        return getBodyCodecByBeanName(bodyCodecMap.get(key));
    }

    @Override
    public void destroy() throws Exception {
        super.destroy();
        bodyCodecMap.clear();
        commandCodes.clear();
    }
}
