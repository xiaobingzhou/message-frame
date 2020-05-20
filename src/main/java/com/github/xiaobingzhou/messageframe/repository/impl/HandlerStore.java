package com.github.xiaobingzhou.messageframe.repository.impl;

import com.github.xiaobingzhou.messageframe.repository.Store;
import lombok.Data;

import java.lang.reflect.Method;

@Data
public class HandlerStore implements Store {

    private String beanName;
    private Method method;
    private String[] parameterNames;

}
