package com.github.xiaobingzhou.messageframe.matcher;

import com.github.xiaobingzhou.messageframe.request.HandlerRequest;

import java.util.Map;

/**
 * 匹配器
 * @author bell.zhouxiaobing
 * @since 1.6.3
 */
public interface Matcher {

    /**
     * 匹配所有
     */
    String MATCH_ALL = "*";

    /**
     * 根据request匹配keyMap
     * @param request
     * @param keyMap
     * @return Object 匹配到keyMap的value值
     */
    Object match(HandlerRequest request, Map keyMap);

    /**
     * 获取keyGenerator
     * @return
     */
    KeyGenerator getKeyGenerator();

    /**
     * 设置keyGenerator
     * @param keyGenerator
     */
    void setKeyGenerator(KeyGenerator keyGenerator);
}
