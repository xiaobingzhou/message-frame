package com.github.xiaobingzhou.messageframe.repository;

import com.github.xiaobingzhou.messageframe.codec.BodyCodec;
import com.github.xiaobingzhou.messageframe.request.HandlerRequest;

import java.util.Set;

/**
 * bodyCodec解码器的仓库
 * @author bell.zhouxiaobing
 * @since 1.5.4
 */
public interface BodyCodecRepository {

    /**
     * 根据指令码从仓库里获取对应的bodyCodec解码器
     * @param commandCode
     * @return BodyCodec
     */
    BodyCodec getBodyCodec(String commandCode);

    /**
     * 设置指令码和bodyCodec解码器对应关系
     * @param bodyCodec
     * @param beanName
     */
    void setBodyCodec(BodyCodec bodyCodec, String beanName);

    /**
     * 获取保存的指令码
     * @return Set<String>
     * @since 1.5.4
     */
    Set<String> getCommandCodes();

    // =========匹配版本号==========

    /**
     * 根据request从仓库里获取对应的bodyCodec解码器
     * @param request
     * @return BodyCodec
     * @since 1.6.3
     */
    default BodyCodec getBodyCodec(HandlerRequest request) {
        return this.getBodyCodec(request.getCommandCode());
    }

    /**
     * 获取所有的key
     * @return Set 所有key
     * @since 1.6.3
     */
    default Set<String> keys(){
        return this.getCommandCodes();
    }

    /**
     * 根据key获取BodyCodec
     * @return BodyCodec
     * @since 1.6.3
     */
    default BodyCodec value(String key) {
        return getBodyCodec(key);
    }

}
