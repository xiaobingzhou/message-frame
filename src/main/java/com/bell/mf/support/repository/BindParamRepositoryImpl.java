package com.bell.mf.support.repository;

import com.bell.mf.support.bind.BindParam;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BindParamRepositoryImpl implements BindParamRepository {

    /**
     * 保存指令码和bodyCodec解码器的对应关系
     * body_codec_map
     */
    private static List<BindParam> BIND_PARAM_LIST = new ArrayList<>();

    /**
     * 获取参数绑定集
     *
     * @return List<BindParam>
     */
    @Override
    public List<BindParam> getBindParamList() {
        return Collections.unmodifiableList(BIND_PARAM_LIST);
    }

    /**
     * 添加参数绑定类
     *
     * @param bindParam
     * @return BindParam
     */
    @Override
    public boolean addBindParam(BindParam bindParam) {
        boolean add = BIND_PARAM_LIST.add(bindParam);
        AnnotationAwareOrderComparator.sort(BIND_PARAM_LIST);// 排序
        return add;
    }
}
