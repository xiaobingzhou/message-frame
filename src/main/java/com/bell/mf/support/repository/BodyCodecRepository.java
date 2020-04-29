package com.bell.mf.support.repository;

import com.bell.mf.support.codec.BodyCodec;

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
     * @param commandCode
     * @return BodyCodec
     */
    BodyCodec setBodyCodec(String commandCode, BodyCodec bodyCodec);

    /**
     * 获取保存的指令码
     * @return Set<String>
     * @since 1.5.4
     */
    Set<String> getCommandCodes();

}
