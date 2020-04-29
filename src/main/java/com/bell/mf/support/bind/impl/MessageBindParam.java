package com.bell.mf.support.bind.impl;

import com.bell.mf.handler.MessageFrameRequest;
import com.bell.mf.repository.ParameterName;
import com.bell.mf.support.bind.BindParam;

public class MessageBindParam implements BindParam {
    @Override
    public boolean bind(String parameterName, Class<?> parameterType, MessageFrameRequest request, Object[] args, int i) {
        ParameterName message = ParameterName.MESSAGE;
        if (message.getName().equals(parameterName) && message.getClazz().isAssignableFrom(parameterType)) {
            args[i] = request.getMessage();
            return true;
        }
        return false;
    }
}
