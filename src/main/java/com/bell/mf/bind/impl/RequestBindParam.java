package com.bell.mf.bind.impl;

import com.bell.mf.request.HandlerRequest;
import com.bell.mf.enums.ParameterNameEnum;
import com.bell.mf.bind.BindParam;

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
