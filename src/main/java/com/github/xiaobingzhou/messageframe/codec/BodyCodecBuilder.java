package com.github.xiaobingzhou.messageframe.codec;

import com.github.xiaobingzhou.messageframe.mapper.MapperField;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * BodyCodec解码器实现类
 * @author bell.zhouxiaobing
 * @since 1.5.4
 */
@NoArgsConstructor
@ToString
public class BodyCodecBuilder implements BodyCodec {

    @Getter private List<String> commandCodes = new ArrayList<>();
    @Getter private List<MapperField> mapperFields = new ArrayList<>();
    @Getter private String version = "*";

    public static BodyCodecBuilder build() {
        return new BodyCodecBuilder();
    }

    public BodyCodecBuilder commandCode(String commandCode) {
        this.commandCodes.add(commandCode);
        return this;
    }

    public BodyCodecBuilder nextField(MapperField mapperField) {
        this.mapperFields.add(mapperField);
        return this;
    }

    public BodyCodecBuilder version(String version) {
        this.version = version;
        return this;
    }

}
