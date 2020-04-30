package com.github.xiaobingzhou.messageframe.bind.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.xiaobingzhou.messageframe.mapper.Mapper;
import com.github.xiaobingzhou.messageframe.request.HandlerRequest;
import com.github.xiaobingzhou.messageframe.enums.ParameterNameEnum;
import com.github.xiaobingzhou.messageframe.bind.BindParam;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BodyJsonBindParam implements BindParam<JSONObject> {

    @Override
    public boolean support(String parameterName) {
        return ParameterNameEnum.BODY_JSON.getName().equals(parameterName);
    }

    @Override
    public JSONObject bind(HandlerRequest request) {
        // 按需解码，如果方法上没有request和bodyJson参数就不执行解码操作
        return Mapper.mapper(request);
    }
}
