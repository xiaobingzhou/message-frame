package com.github.xiaobingzhou.messageframe.matcher;

@FunctionalInterface
public interface KeyGenerator {

    /**
     * 生成key
     * @return String 生成的key
     */
    String generateKey();

}
