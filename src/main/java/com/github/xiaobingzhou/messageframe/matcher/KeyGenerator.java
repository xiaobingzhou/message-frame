package com.github.xiaobingzhou.messageframe.matcher;

/**
 * keyGenerator
 * @author bell.zhouxiaobing
 * @since 1.6.3
 */
public interface KeyGenerator {

    /**
     * 生成key
     * @return String 生成的key
     */
    default String generateKey(String commandCode, String version) {
        return commandCode + "@" + version;
    }

    default String getCommandCode(String key) {
        if (key == null)
            return null;

        return key.split("@")[0];
    }

    default String getVersion(String key) {
        if (key == null)
            return null;

        return key.split("@")[1];
    }
}
