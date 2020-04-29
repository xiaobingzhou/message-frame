package com.bell.mf.bind.impl;

import com.bell.mf.IMessageFrame;
import com.bell.mf.handler.MessageFrameRequest;
import com.bell.mf.enums.ParameterNameEnum;
import com.bell.mf.bind.BindParam;

public class MessageFrameBindParam implements BindParam<IMessageFrame> {

    @Override
    public boolean support(String parameterName, Class<IMessageFrame> parameterType) {
        ParameterNameEnum messageFrame = ParameterNameEnum.MESSAGE_FRAME;
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
