package com.github.xiaobingzhou.messageframe.bind.impl;


import com.github.xiaobingzhou.messageframe.bind.BindParam;
import com.github.xiaobingzhou.messageframe.enums.ParameterNameEnum;
import com.github.xiaobingzhou.messageframe.request.HandlerRequest;
import com.github.xiaobingzhou.messageframe.response.Sender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;

@Slf4j
public class SenderBindParam implements BindParam<Sender> {

    @Autowired(required = false)
    private Sender sender;

    @Override
    public boolean support(String parameterName) {
        return ParameterNameEnum.RESPONSE.getName().equals(parameterName)
                && checkIfNecessary();
    }

    private boolean checkIfNecessary() {
        if (sender == null) {
            throw new ApplicationContextException("绑定 [Sender sender] 参数时异常, " +
                    "spring容器中未找到 [com.github.xiaobingzhou.messageframe.response.Sender] 接口实现类");
        }
        return true;
    }

    @Override
    public Sender bind(HandlerRequest request) {
        return sender;
    }

}
