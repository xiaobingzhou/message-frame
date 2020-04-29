package com.bell.mf.support.bind.impl;

import com.bell.mf.handler.MessageFrameRequest;
import com.bell.mf.repository.ParameterName;
import com.bell.mf.support.bind.BindParam;

public class BodyJsonBindParam implements BindParam {
    @Override
    public boolean bind(String parameterName, Class<?> parameterType, MessageFrameRequest request, Object[] args, int i) {
        ParameterName bodyJson = ParameterName.BODY_JSON;
        if (bodyJson.getName().equals(parameterName) && bodyJson.getClazz().isAssignableFrom(parameterType)) {
            args[i] = request.getBodyJson();
            return true;
        }
        return false;
    }
}
