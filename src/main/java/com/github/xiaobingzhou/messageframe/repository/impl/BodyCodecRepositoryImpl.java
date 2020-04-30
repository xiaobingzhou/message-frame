package com.github.xiaobingzhou.messageframe.repository.impl;

import com.github.xiaobingzhou.messageframe.codec.BodyCodec;
import com.github.xiaobingzhou.messageframe.repository.BodyCodecRepository;
import org.springframework.beans.factory.DisposableBean;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * bodyCodec解码器的仓库
 * @author bell.zhouxiaobing
 * @since 1.5.4
 */
public class BodyCodecRepositoryImpl implements BodyCodecRepository, DisposableBean {

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

    @Override
    public Set<String> getCommandCodes() {
        return BODY_CODEC_MAP.keySet();
    }

    @Override
    public void destroy() throws Exception {
        BODY_CODEC_MAP.clear();
    }
}
