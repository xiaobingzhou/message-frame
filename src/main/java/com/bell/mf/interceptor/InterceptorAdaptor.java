package com.bell.mf.interceptor;

import com.bell.mf.handler.MessageFrameRequest;

/**
 * MessageFrameHandlerInterceptor接口的适配器
 * @author bell.zhouxiaobing
 * @since 1.5.1
 */
public class InterceptorAdaptor implements MessageFrameHandlerInterceptor {
    @Override
    public void preHandle(MessageFrameRequest request){
    }

    @Override
    public void postHandle(MessageFrameRequest request){
    }
}
