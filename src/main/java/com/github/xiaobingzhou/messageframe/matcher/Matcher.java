package com.github.xiaobingzhou.messageframe.matcher;

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
     * 根据key匹配keyMap
     * @param keyGenerator
     * @param keyMap
     * @return Object 匹配到keyMap的value值
     */
    Object match(KeyGenerator keyGenerator, Map<String, ?> keyMap);

}
