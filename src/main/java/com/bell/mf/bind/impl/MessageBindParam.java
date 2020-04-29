package com.bell.mf.bind.impl;

import com.bell.mf.handler.MessageFrameRequest;
import com.bell.mf.enums.ParameterNameEnum;
import com.bell.mf.bind.BindParam;

public class MessageBindParam implements BindParam<String> {

    @Override
    public boolean support(String parameterName, Class<String> parameterType) {
        ParameterNameEnum message = ParameterNameEnum.MESSAGE;
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
