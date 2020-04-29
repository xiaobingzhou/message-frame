package com.bell.mf.support.codec;

import com.bell.mf.mapper.MapperField;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * BodyCodec解码器实现类
 * @author bell.zhouxiaobing
 * @since 1.5.4
 */
@NoArgsConstructor
public class BodyCodecBuilder implements BodyCodec {

    @Getter private List<String> commandCodes = new ArrayList<>();
    @Getter private List<MapperField> mapperFields = new ArrayList<>();

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

    public static void main(String[] args) {
        BodyCodecBuilder.build().commandCode("1111")
                .nextField(MapperField.builder().build())
                .nextField(MapperField.builder().build());
    }
}
