package com.bell.mf.support.bind.impl;

import com.bell.mf.handler.MessageFrameRequest;
import com.bell.mf.repository.ParameterName;
import com.bell.mf.support.bind.BindParam;

public class DeviceIdBindParam implements BindParam<String> {

    @Override
    public boolean support(String parameterName, Class<String> parameterType) {
        ParameterName deviceId = ParameterName.DEVICE_ID;
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
