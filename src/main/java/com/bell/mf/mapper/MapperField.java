package com.bell.mf.mapper;

import lombok.Builder;
import lombok.Data;

/**
 * @ClassName MapperField
 * @Description 映射字段
 * @author bell.zhouxiaobing
 * @date 2019年11月28日
 * @since 1.4.0
 */
@Data
@Builder
public class MapperField {
	
	private int length;
	private String name;
	private MapperFieldPostHandler postHandler;
	
}
