package com.bell.mf.support.bind.impl;

import com.bell.mf.IMessageFrame;
import com.bell.mf.handler.MessageFrameRequest;
import com.bell.mf.repository.ParameterName;
import com.bell.mf.support.bind.BindParam;

public class MessageFrameBindParam implements BindParam<IMessageFrame> {

    @Override
    public boolean support(String parameterName, Class<IMessageFrame> parameterType) {
        ParameterName messageFrame = ParameterName.MESSAGE_FRAME;
        if (messageFrame.getName().equals(parameterName)
                && messageFrame.getClazz().isAssignableFrom(parameterType)) {
            return true;
        }
        return false;
    }

    @Override
    public IMessageFrame bind(MessageFrameRequest request) {
        return request.getMessageFrame();
    }
}
