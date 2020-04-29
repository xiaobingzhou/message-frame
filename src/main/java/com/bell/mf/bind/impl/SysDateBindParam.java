package com.bell.mf.bind.impl;

import com.bell.mf.request.HandlerRequest;
import com.bell.mf.enums.ParameterNameEnum;
import com.bell.mf.bind.BindParam;

import java.util.Date;

public class SysDateBindParam implements BindParam<Date> {

    @Override
    public boolean support(String parameterName, Class<Date> parameterType) {
        ParameterNameEnum sysDate = ParameterNameEnum.SYS_DATE;
        if (sysDate.getName().equals(parameterName)
                && sysDate.getClazz().isAssignableFrom(parameterType)) {
            return true;
        }
        return false;
    }

    @Override
    public Date bind(HandlerRequest request) {
        return request.getSystemDate();
    }
}
