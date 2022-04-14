package com.ynby.platform.operation.api.pojo.dto;

import lombok.Data;

/**
 * @author gaozhenyu
 * @Copyright 合肥
 * @Description
 * @date 2020-06-01 18:31
 */
@Data
public class QueryMenuDTO {

	/**
	 * 用户id
	 */
	private Long userId;

	/**
	 * 菜单名
	 */
	private String menuName;

	/**
	 * 菜单状态（1正常 0停用）
	 */
	private Integer status;

}
