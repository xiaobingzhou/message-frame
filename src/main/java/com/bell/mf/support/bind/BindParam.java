package com.bell.mf.support.bind;

import com.bell.mf.handler.MessageFrameRequest;

public interface BindParam {

    boolean bind(String parameterName, Class<?> parameterType, MessageFrameRequest request, Object[] args, int i);

}
