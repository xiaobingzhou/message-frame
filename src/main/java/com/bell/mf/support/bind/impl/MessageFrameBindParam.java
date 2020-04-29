package com.bell.mf.support.bind.impl;

import com.bell.mf.handler.MessageFrameRequest;
import com.bell.mf.repository.ParameterName;
import com.bell.mf.support.bind.BindParam;

public class MessageFrameBindParam implements BindParam {
    @Override
    public boolean bind(String parameterName, Class<?> parameterType, MessageFrameRequest request, Object[] args, int i) {
        ParameterName messageFrame = ParameterName.MESSAGE_FRAME;
        if (messageFrame.getName().equals(parameterName) && messageFrame.getClazz().isAssignableFrom(parameterType)) {
            args[i] = request.getMessageFrame();
            return true;
        }
        return false;
    }
}
