package com.bell.mf.support;

import com.alibaba.fastjson.JSONObject;
import com.bell.mf.handler.MessageFrameRequest;
import com.bell.mf.mapper.Mapper;
import com.bell.mf.mapper.MapperField;
import com.bell.mf.repository.ParameterName;
import com.bell.mf.support.codec.BodyCodec;
import com.bell.mf.support.repository.BodyCodecRepository;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 增强参数功能
 * @author bell.zhouxiaobing
 * @since 1.5.4
 */
public class DispatcherHandlerEnhanceMethodParam extends DispatcherMessageFrameHandler{

    private BodyCodecRepository bodyCodecRepository;

    public BodyCodecRepository getBodyCodecRepository() {
        return bodyCodecRepository;
    }

    public void setBodyCodecRepository(BodyCodecRepository bodyCodecRepository) {
        this.bodyCodecRepository = bodyCodecRepository;
    }

    @Override
    protected Object[] getMethodArgs(MessageFrameRequest request, Method method) {
        String[] parameterNames = getParameterNames(request);
        Class<?>[] parameterTypes = method.getParameterTypes();

        if (!isNeedEnhance(parameterNames, parameterTypes)) {
            return super.getMethodArgs(request, method);
        }

        // body 字段解码
        bodyCodec(request);

        // 增强
        return new Object[] {request};
    }

    protected void bodyCodec(MessageFrameRequest request) {
        String commandCode = request.getMessageFrame().getCommandCode();
        String body = request.getMessageFrame().getBody();
        BodyCodec bodyCodec = bodyCodecRepository.getBodyCodec(commandCode);
        if (bodyCodec == null) {
            throw new RuntimeException(String.format("指令码 [%s] bodyCodec解码器未找到", commandCode));
        }
        List<MapperField> mapperFields = bodyCodec.getMapperFields();
        JSONObject bodyJson = Mapper.mapper(body, mapperFields);
        request.setBodyJson(bodyJson);
    }

    /**
     * 是否支持增强
     * @param parameterNames
     * @param parameterTypes
     * @return boolean
     */
    protected boolean isNeedEnhance(String[] parameterNames, Class<?>[] parameterTypes) {
        boolean needEnhance = false;
        for (int i = 0; i < parameterNames.length; i++) {
            // 参数类型和名称都是MessageFrameRequest
            if (nameEquals(parameterNames[i]) && typeEquals(parameterTypes[i])) {
                needEnhance = true;
                break;
            }
        }
        return needEnhance;
    }

    private boolean nameEquals(String parameterName) {
        return ParameterName.REQUEST.getName().equals(parameterName);
    }

    private boolean typeEquals(Class<?> parameterType) {
        return ParameterName.REQUEST.getClazz().isAssignableFrom(parameterType);
    }
}
