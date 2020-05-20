package com.github.xiaobingzhou.messageframe.codec;

import com.github.xiaobingzhou.messageframe.mapper.MapperField;

import java.util.List;

/**
 * BodyCodec解码器
 * @author bell.zhouxiaobing
 * @since 1.5.4
 */
public interface BodyCodec {

    /**
     * 解码器支持的指令码
     * @return List<String>
     */
    List<String> getCommandCodes();

    /**
     * 解码时body的字段
     * @return List<MapperField>
     */
    List<MapperField> getMapperFields();

    /**
     * 解码器支持的版本
     * @return String
     * @since 1.6.3
     */
    String getVersion();
}
