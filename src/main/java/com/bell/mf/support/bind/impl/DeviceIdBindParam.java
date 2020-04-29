package com.bell.mf.support.bind.impl;

import com.bell.mf.handler.MessageFrameRequest;
import com.bell.mf.repository.ParameterName;
import com.bell.mf.support.bind.BindParam;

public class DeviceIdBindParam implements BindParam {
    @Override
    public boolean bind(String parameterName, Class<?> parameterType, MessageFrameRequest request, Object[] args, int i) {
        ParameterName deviceId = ParameterName.DEVICE_ID;
        if (deviceId.getName().equals(parameterName) && deviceId.getClazz().isAssignableFrom(parameterType)) {
            args[i] = request.getDeviceId();
            return true;
        }
        return false;
    }
}
