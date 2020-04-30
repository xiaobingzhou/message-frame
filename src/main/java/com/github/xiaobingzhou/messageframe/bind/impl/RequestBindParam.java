package com.github.xiaobingzhou.messageframe.bind.impl;

import com.github.xiaobingzhou.messageframe.request.HandlerRequest;
import com.github.xiaobingzhou.messageframe.enums.ParameterNameEnum;
import com.github.xiaobingzhou.messageframe.bind.BindParam;

public class RequestBindParam implements BindParam<HandlerRequest> {

    @Override
    public boolean support(String parameterName, Class<HandlerRequest> parameterType) {
        ParameterNameEnum request = ParameterNameEnum.REQUEST;
        if (request.getName().equals(parameterName)
                && request.getClazz().isAssignableFrom(parameterType)) {
            return true;
        }
        return false;
    }

    @Override
    public HandlerRequest bind(HandlerRequest request) {
        return request;
    }
}
