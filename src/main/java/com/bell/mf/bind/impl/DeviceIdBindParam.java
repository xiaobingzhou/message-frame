package com.bell.mf.bind.impl;

import com.bell.mf.handler.MessageFrameRequest;
import com.bell.mf.enums.ParameterNameEnum;
import com.bell.mf.bind.BindParam;

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
    public String bind(MessageFrameRequest request) {
        return request.getDeviceId();
    }
}
