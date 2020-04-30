package com.github.xiaobingzhou.messageframe.bind;

import com.github.xiaobingzhou.messageframe.request.HandlerRequest;

/**
 * 参数绑定器接口
 * @author bell.zhouxiaobing
 * @since 1.5.5
 */
public interface BindParam<T> {

    /**
     * 是否支持参数名<code>parameterName</code>和类型<code>parameterType</code>的参数的绑定
     * @param parameterName
     * @param parameterType
     * @return
     */
    boolean support(String parameterName, Class<T> parameterType);

    /**
     * support为ture, 返回参数名<code>parameterName</code>参数的参数值
     * @param request
     * @return T 需要绑定的参数值
     */
    T bind(HandlerRequest request);

}
