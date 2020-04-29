package com.bell.mf.support;

import com.alibaba.fastjson.JSONObject;
import com.bell.mf.handler.MessageFrameRequest;
import com.bell.mf.mapper.Mapper;
import com.bell.mf.mapper.MapperField;
import com.bell.mf.support.bind.BindParam;
import com.bell.mf.support.codec.BodyCodec;
import com.bell.mf.support.repository.BindParamRepository;
import com.bell.mf.support.repository.BodyCodecRepository;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 增强参数功能
 * @author bell.zhouxiaobing
 * @since 1.5.4
 */
@Slf4j
public class DispatcherHandlerEnhanceMethodParam extends DispatcherMessageFrameHandler{

    @Setter private BodyCodecRepository bodyCodecRepository;

    @Setter private BindParamRepository bindParamRepository;

    private Map<Method, BindParam[]> argsCache = new HashMap<>(128);

    @Override
    protected Object[] getMethodArgs(MessageFrameRequest request, Method method) {
        String[] parameterNames = getParameterNames(request);
        Class<?>[] parameterTypes = method.getParameterTypes();

        // body 字段解码
        bodyCodec(request);

        // 绑定参数
        return bindParams(request, method, parameterNames, parameterTypes);
    }

    /**
     * 绑定参数
     * @param request
     * @param method
     * @param parameterNames
     * @param parameterTypes
     * @return Object[]
     * @since 1.5.5
     */
    protected Object[] bindParams(MessageFrameRequest request, Method method, String[] parameterNames, Class<?>[] parameterTypes) {
        int argsLength = parameterNames.length;
        Object[] args = new Object[argsLength];
        BindParam[] cacheList = argsCache.getOrDefault(method, new BindParam[argsLength]);
        for (int i = 0; i < argsLength; i++) {
            BindParam cache = cacheList[i];
            if (cache != null) {
                cache.bind(parameterNames[i], parameterTypes[i], request, args, i);
                continue;
            }
            for (BindParam bindParam : bindParamRepository.getBindParamList()) {
                if (bindParam.bind(parameterNames[i], parameterTypes[i], request, args, i)) {
                    log.debug("参数绑定，不使用缓存" + bindParam);
                    cacheList[i] = (bindParam);
                    break;
                }
            }
        }
        // 设置缓存
        argsCache.putIfAbsent(method, cacheList);
        return args;
    }

    protected void bodyCodec(MessageFrameRequest request) {
        String commandCode = request.getMessageFrame().getCommandCode();
        String body = request.getMessageFrame().getBody();
        BodyCodec bodyCodec = bodyCodecRepository.getBodyCodec(commandCode);
        if (bodyCodec == null) {
            log.info("指令码 [{}] bodyCodec解码器未找到", commandCode);
            return;
        }
        List<MapperField> mapperFields = bodyCodec.getMapperFields();
        JSONObject bodyJson = Mapper.mapper(body, mapperFields);
        request.setBodyJson(bodyJson);
    }

}
