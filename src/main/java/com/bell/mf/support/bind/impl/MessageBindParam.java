package com.bell.mf.support.bind.impl;

import com.bell.mf.handler.MessageFrameRequest;
import com.bell.mf.repository.ParameterName;
import com.bell.mf.support.bind.BindParam;

public class MessageBindParam implements BindParam<String> {

    @Override
    public boolean support(String parameterName, Class<String> parameterType) {
        ParameterName message = ParameterName.MESSAGE;
        if (message.getName().equals(parameterName)
                && message.getClazz().isAssignableFrom(parameterType)) {
            return true;
        }
        return false;
    }

    @Override
    public String bind(MessageFrameRequest request) {
        return request.getMessage();
    }
}
