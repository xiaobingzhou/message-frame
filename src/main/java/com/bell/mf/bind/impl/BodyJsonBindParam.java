package com.bell.mf.bind.impl;

import com.alibaba.fastjson.JSONObject;
import com.bell.mf.handler.MessageFrameRequest;
import com.bell.mf.enums.ParameterNameEnum;
import com.bell.mf.bind.BindParam;

public class BodyJsonBindParam implements BindParam<JSONObject> {
    @Override
    public boolean support(String parameterName, Class<JSONObject> parameterType) {
        ParameterNameEnum bodyJson = ParameterNameEnum.BODY_JSON;
        if (bodyJson.getName().equals(parameterName)
                && bodyJson.getClazz().isAssignableFrom(parameterType)) {
            return true;
        }
        return false;
    }

    @Override
    public JSONObject bind(MessageFrameRequest request) {
        return request.getBodyJson();
    }
}
