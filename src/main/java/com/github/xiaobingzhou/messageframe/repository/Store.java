package com.github.xiaobingzhou.messageframe.repository;

import java.lang.reflect.Method;

public interface Store {

    String getBeanName();
    Method getMethod();
    String[] getParameterNames();

}
