package com.pig4cloud.pig.search.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * 搜索类
 * @author hanguilin
 */
@Data
public class SearchDto implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 索引名称
	 */
	private String index;

	/**
	 * 过滤map
	 */
	private Map<String, Object> filter;
	/**
	 * 文档大小限制
	 */
	private Integer size;

	/**
	 * 开始
	 */
	private Integer page;

	/**
	 * 需要显示的字段，逗号分隔（缺省为全部字段）
	 */
	private String fields;

	/**
	 * 排序字段
	 */
	private String sortField;

	/**
	 * 高亮字段
	 */
	private String highlightField;
}
