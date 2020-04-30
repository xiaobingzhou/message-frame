package com.github.xiaobingzhou.messageframe.bind.impl;

import com.github.xiaobingzhou.messageframe.request.HandlerRequest;
import com.github.xiaobingzhou.messageframe.enums.ParameterNameEnum;
import com.github.xiaobingzhou.messageframe.bind.BindParam;

import java.util.Date;

public class SysDateBindParam implements BindParam<Date> {

    @Override
    public boolean support(String parameterName) {
        return ParameterNameEnum.SYS_DATE.getName().equals(parameterName);
    }

    @Override
    public Date bind(HandlerRequest request) {
        return request.getSystemDate();
    }
}
