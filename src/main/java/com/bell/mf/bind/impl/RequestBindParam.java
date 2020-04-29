package com.bell.mf.bind.impl;

import com.bell.mf.handler.MessageFrameRequest;
import com.bell.mf.enums.ParameterNameEnum;
import com.bell.mf.bind.BindParam;

public class RequestBindParam implements BindParam<MessageFrameRequest> {

    @Override
    public boolean support(String parameterName, Class<MessageFrameRequest> parameterType) {
        ParameterNameEnum request = ParameterNameEnum.REQUEST;
        if (request.getName().equals(parameterName)
                && request.getClazz().isAssignableFrom(parameterType)) {
            return true;
        }
        return false;
    }

    @Override
    public MessageFrameRequest bind(MessageFrameRequest request) {
        return request;
    }
}
