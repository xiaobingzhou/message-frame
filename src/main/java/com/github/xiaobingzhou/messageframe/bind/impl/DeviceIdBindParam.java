package com.github.xiaobingzhou.messageframe.bind.impl;

import com.github.xiaobingzhou.messageframe.request.HandlerRequest;
import com.github.xiaobingzhou.messageframe.enums.ParameterNameEnum;
import com.github.xiaobingzhou.messageframe.bind.BindParam;

public class DeviceIdBindParam implements BindParam<String> {

    @Override
    public boolean support(String parameterName) {
        return ParameterNameEnum.DEVICE_ID.getName().equals(parameterName);
    }

    @Override
    public String bind(HandlerRequest request) {
        return request.getDeviceId();
    }
}
