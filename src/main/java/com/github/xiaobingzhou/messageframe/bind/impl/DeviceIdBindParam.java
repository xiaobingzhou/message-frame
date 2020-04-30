package com.github.xiaobingzhou.messageframe.bind.impl;

import com.github.xiaobingzhou.messageframe.request.HandlerRequest;
import com.github.xiaobingzhou.messageframe.enums.ParameterNameEnum;
import com.github.xiaobingzhou.messageframe.bind.BindParam;

public class DeviceIdBindParam implements BindParam<String> {

    @Override
    public boolean support(String parameterName, Class<String> parameterType) {
        ParameterNameEnum deviceId = ParameterNameEnum.DEVICE_ID;
        if (deviceId.getName().equals(parameterName)
                && deviceId.getClazz().isAssignableFrom(parameterType)) {
            return true;
        }
        return false;
    }

    @Override
    public String bind(HandlerRequest request) {
        return request.getDeviceId();
    }
}
