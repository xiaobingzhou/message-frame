package com.github.xiaobingzhou.messageframe.bind.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.xiaobingzhou.messageframe.request.HandlerRequest;
import com.github.xiaobingzhou.messageframe.enums.ParameterNameEnum;
import com.github.xiaobingzhou.messageframe.bind.BindParam;

public class BodyJsonBindParam implements BindParam<JSONObject> {
    @Override
    public boolean support(String parameterName) {
        return ParameterNameEnum.BODY_JSON.getName().equals(parameterName);
    }

    @Override
    public JSONObject bind(HandlerRequest request) {
        return request.getBodyJson();
    }
}
