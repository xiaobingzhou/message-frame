package com.github.xiaobingzhou.messageframe.bind;


import com.github.xiaobingzhou.messageframe.request.HandlerRequest;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 参数绑定器接口
 * @author bell.zhouxiaobing
 * @since 1.5.5
 */
public interface BindParam<T> {

    /**
     * 是否支持参数名<code>parameterName</code>和类型<code>T</code>的参数的绑定
     * @param parameterName 参数名
     * @return boolean 是否支持参数名
     */
    boolean support(String parameterName);

    /**
     * support为ture, 返回参数名<code>parameterName</code>参数的参数值
     * @param request 处理请求
     * @return T 需要绑定的参数值
     */
    T bind(HandlerRequest request);

    /**
     * 获取泛型T
     * @return 泛型T
     */
    default Class getInterfacesGenricType(){
        String name = BindParam.class.getName();
        Type[] genericInterfaces = this.getClass().getGenericInterfaces();
        for (Type genericInterface : genericInterfaces) {
            if (!genericInterface.getTypeName().startsWith(name)
                    || !(genericInterface instanceof ParameterizedType)) {
                continue;
            }
            Type type = ((ParameterizedType) genericInterface).getActualTypeArguments()[0];
            if (type.getClass().isAssignableFrom(ParameterizedTypeImpl.class)) {
                continue;
            }
            return (Class)(type);
        }
        return Object.class;
    }

    /**
     * 匹配泛型T和genricType
     * @param genricType
     * @return 是否匹配
     */
    default boolean matchGenricType(Class genricType) {
        Class interfacesGenricType = getInterfacesGenricType();
        if (interfacesGenricType == Object.class) {
            return true;
        }

        return interfacesGenricType == genricType;
    }
}
