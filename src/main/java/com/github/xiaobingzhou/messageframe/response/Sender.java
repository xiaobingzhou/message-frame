package com.github.xiaobingzhou.messageframe.response;

import java.util.Map;

/**
 * 响应发送器
 * @author bell.zhouxiaobing
 * @since 1.6.1
 */
public interface Sender {

    /**
     * 实现发送响应方法
     * @param response
     */
    Map<String, String> send(HandlerResponse response);

}
