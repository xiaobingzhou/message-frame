package com.bell.mf.bind.impl;

import com.bell.mf.IMessageFrame;
import com.bell.mf.request.HandlerRequest;
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
    public IMessageFrame bind(HandlerRequest request) {
        return request.getMessageFrame();
    }
}
