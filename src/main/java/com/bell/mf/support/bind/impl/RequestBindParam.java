package com.bell.mf.support.bind.impl;

import com.bell.mf.handler.MessageFrameRequest;
import com.bell.mf.repository.ParameterName;
import com.bell.mf.support.bind.BindParam;

public class RequestBindParam implements BindParam<MessageFrameRequest> {

    @Override
    public boolean support(String parameterName, Class<MessageFrameRequest> parameterType) {
        ParameterName request = ParameterName.REQUEST;
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
