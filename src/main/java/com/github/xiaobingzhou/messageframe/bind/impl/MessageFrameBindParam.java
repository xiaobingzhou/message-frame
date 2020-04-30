package com.github.xiaobingzhou.messageframe.bind.impl;

import com.github.xiaobingzhou.messageframe.IMessageFrame;
import com.github.xiaobingzhou.messageframe.request.HandlerRequest;
import com.github.xiaobingzhou.messageframe.enums.ParameterNameEnum;
import com.github.xiaobingzhou.messageframe.bind.BindParam;

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
