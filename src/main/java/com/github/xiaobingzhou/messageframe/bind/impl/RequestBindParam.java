package com.github.xiaobingzhou.messageframe.bind.impl;

import com.github.xiaobingzhou.messageframe.mapper.Mapper;
import com.github.xiaobingzhou.messageframe.request.HandlerRequest;
import com.github.xiaobingzhou.messageframe.enums.ParameterNameEnum;
import com.github.xiaobingzhou.messageframe.bind.BindParam;

public class RequestBindParam implements BindParam<HandlerRequest> {

    @Override
    public boolean support(String parameterName) {
        return ParameterNameEnum.REQUEST.getName().equals(parameterName);
    }

    @Override
    public HandlerRequest bind(HandlerRequest request) {
        // 可能在BodyJsonBindParam进行解码操作
        // 按需解码，如果处理方法上没有request和bodyJson参数就不执行解码操作
        Mapper.mapper(request);// 解码
        return request;
    }
}
