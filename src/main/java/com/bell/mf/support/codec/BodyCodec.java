package com.bell.mf.support.codec;

import com.bell.mf.mapper.MapperField;

import java.util.List;

/**
 * BodyCodec解码器
 * @author bell.zhouxiaobing
 * @since 1.5.4
 */
public interface BodyCodec {

    /**
     * 解码器支持的指令码
     * @return
     */
    List<String> getCommandCodes();

    /**
     * 解码时body的字段
     * @return
     */
    List<MapperField> getMapperFields();

}
