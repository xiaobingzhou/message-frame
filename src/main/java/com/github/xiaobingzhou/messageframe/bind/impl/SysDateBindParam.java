package com.github.xiaobingzhou.messageframe.bind.impl;

import com.github.xiaobingzhou.messageframe.request.HandlerRequest;
import com.github.xiaobingzhou.messageframe.enums.ParameterNameEnum;
import com.github.xiaobingzhou.messageframe.bind.BindParam;

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
