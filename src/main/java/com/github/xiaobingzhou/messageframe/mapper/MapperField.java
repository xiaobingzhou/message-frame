package com.github.xiaobingzhou.messageframe.mapper;

import lombok.Builder;
import lombok.Data;

/**
 * 映射字段
 * 2019年11月28日
 * @author bell.zhouxiaobing
 * @since 1.4.0
 */
@Data
@Builder
public class MapperField {
	
	private int length;
	private String name;
	private MapperFieldHandler postHandler;
	
}
