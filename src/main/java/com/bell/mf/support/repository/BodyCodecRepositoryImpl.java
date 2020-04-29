package com.bell.mf.support.repository;

import com.bell.mf.support.codec.BodyCodec;

import java.util.HashMap;
import java.util.Map;

/**
 * bodyCodec解码器的仓库
 * @author bell.zhouxiaobing
 * @since 1.5.4
 */
public class BodyCodecRepositoryImpl implements BodyCodecRepository {

    /**
     * 保存指令码和bodyCodec解码器的对应关系
     * body_codec_map
     */
    private static Map<String, BodyCodec> BODY_CODEC_MAP = new HashMap<>(128);

    @Override
    public BodyCodec getBodyCodec(String commandCode) {
        return BODY_CODEC_MAP.get(commandCode);
    }

    @Override
    public BodyCodec setBodyCodec(String commandCode, BodyCodec bodyCodec) {
        return BODY_CODEC_MAP.put(commandCode, bodyCodec);
    }
}
