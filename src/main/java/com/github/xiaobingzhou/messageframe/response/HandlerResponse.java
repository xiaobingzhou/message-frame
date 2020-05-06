package com.github.xiaobingzhou.messageframe.response;

import com.github.xiaobingzhou.messageframe.IMessageFrame;

import java.util.Date;

/**
 * 处理器响应
 * @author bell.zhouxiaobing
 * @since 1.6.1
 */
public interface HandlerResponse {

    /**
     *  消息帧
     * @return IMessageFrame
     */
    IMessageFrame getMessageFrame();

    /**
     * 设备uuid
     * @return String
     */
    String getDeviceId();

    /**
     *  编码后消息帧
     * @return String
     */
    String getMessage();

    /**
     * 系统时间
     * @return Date
     */
    Date getSystemDate();
}
