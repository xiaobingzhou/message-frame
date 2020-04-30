package com.github.xiaobingzhou.messageframe.bind.impl;

import com.github.xiaobingzhou.messageframe.request.HandlerRequest;
import com.github.xiaobingzhou.messageframe.enums.ParameterNameEnum;
import com.github.xiaobingzhou.messageframe.bind.BindParam;

public class MessageBindParam implements BindParam<String> {

    @Override
    public boolean support(String parameterName) {
        return ParameterNameEnum.MESSAGE.getName().equals(parameterName);
    }

    @Override
    public String bind(HandlerRequest request) {
        return request.getMessage();
    }
}
