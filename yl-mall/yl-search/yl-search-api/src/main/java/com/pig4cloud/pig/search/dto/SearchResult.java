package com.pig4cloud.pig.search.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 搜索结果
 * @author hanguilin
 */
@Data
public class SearchResult<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 总数
	 */
	private Long total;

	/**
	 * 总页数
	 */
	private Long totalPages;

	/**
	 * 搜索结果
	 */
	private List<T> records;

}
