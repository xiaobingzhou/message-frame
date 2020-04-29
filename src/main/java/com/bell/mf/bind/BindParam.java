package com.bell.mf.bind;

import com.bell.mf.request.HandlerRequest;

/**
 * 参数绑定器接口
 * @author bell.zhouxiaobing
 * @since 1.5.5
 */
public interface BindParam<T> {

    boolean support(String parameterName, Class<T> parameterType);

    T bind(HandlerRequest request);

}
