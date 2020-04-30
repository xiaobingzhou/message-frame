package com.github.xiaobingzhou.messageframe.bind.impl;

import com.github.xiaobingzhou.messageframe.request.HandlerRequest;
import com.github.xiaobingzhou.messageframe.enums.ParameterNameEnum;
import com.github.xiaobingzhou.messageframe.bind.BindParam;

public class RequestBindParam implements BindParam<HandlerRequest> {

    @Override
    public boolean support(String parameterName) {
        return ParameterNameEnum.REQUEST.getName().equals(parameterName);
    }

    @Override
    public HandlerRequest bind(HandlerRequest request) {
        return request;
    }
}
