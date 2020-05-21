package com.github.xiaobingzhou.messageframe.matcher;

/**
 * keyGenerator
 * @author bell.zhouxiaobing
 * @since 1.6.3
 */
public interface KeyGenerator {

    /**
     * 生成key
     * @param commandCode 指令码
     * @param version 版本号
     * @return String 生成的key
     */
    default String generateKey(String commandCode, String version) {
        return commandCode + "@" + version;
    }

    /**
     * 根据key获取指令码
     * @return String 指令码
     */
    default String getCommandCode(String key) {
        if (key == null)
            return null;

        return key.split("@")[0];
    }

    /**
     * 根据key获取版本号
     * @return String 版本号
     */
    default String getVersion(String key) {
        if (key == null)
            return null;

        return key.split("@")[1];
    }
}
