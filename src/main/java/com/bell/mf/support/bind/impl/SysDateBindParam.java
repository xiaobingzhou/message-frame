package com.bell.mf.support.bind.impl;

import com.bell.mf.handler.MessageFrameRequest;
import com.bell.mf.repository.ParameterName;
import com.bell.mf.support.bind.BindParam;

public class SysDateBindParam implements BindParam {
    @Override
    public boolean bind(String parameterName, Class<?> parameterType, MessageFrameRequest request, Object[] args, int i) {
        ParameterName sysDate = ParameterName.SYS_DATE;
        if (sysDate.getName().equals(parameterName) && sysDate.getClazz().isAssignableFrom(parameterType)) {
            args[i] = request.getSystemDate();
            return true;
        }
        return false;
    }
}
