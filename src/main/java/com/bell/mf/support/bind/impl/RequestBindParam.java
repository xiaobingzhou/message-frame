package com.bell.mf.support.bind.impl;

import com.bell.mf.handler.MessageFrameRequest;
import com.bell.mf.repository.ParameterName;
import com.bell.mf.support.bind.BindParam;

public class RequestBindParam implements BindParam {
    @Override
    public boolean bind(String parameterName, Class<?> parameterType, MessageFrameRequest request, Object[] args, int i) {
        ParameterName requestEnum = ParameterName.REQUEST;
        if (requestEnum.getName().equals(parameterName) && requestEnum.getClazz().isAssignableFrom(parameterType)) {
            args[i] = request;
            return true;
        }
        return false;
    }
}
