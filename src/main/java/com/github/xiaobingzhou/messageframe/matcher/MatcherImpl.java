package com.github.xiaobingzhou.messageframe.matcher;

import com.github.xiaobingzhou.messageframe.request.HandlerRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * 匹配器实现类
 * @author bell.zhouxiaobing
 * @since 1.6.3
 */
@Slf4j
public class MatcherImpl implements Matcher {

    @Setter
    @Getter
    private KeyGenerator keyGenerator = new KeyGenerator(){};

    @Override
    public Object match(HandlerRequest request, Map keyMap) {
        if (keyMap == null)
            return null;

        String key = keyGenerator.generateKey(request.getCommandCode(), request.getProtocolVer());

        Object result = keyMap.get(key);

        // 全值匹配
        if (result != null)
            return result;

        // 匹配*号
        int end = key.length() - 4;
        for (int i = key.length() - 1; i >= end; i--) {
            String matchKey = key.substring(0, i) + MATCH_ALL;
            result = keyMap.get(matchKey);
            if (result != null) {
                break;
            }
        }

        // 匹配到值，将结果设置回到map中，方便下次直接使用key获取
        if (result != null)
            keyMap.put(key, result);

        log.debug("[last match] key:[{}] value:[{}]", key, result);

        return result;
    }

}
