package com.ynby.platform.operation.api.pojo.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author gaozhenyu
 * @Copyright 合肥
 * @Description
 * @date 2020-06-01 18:31
 */
@Data
public class MenuParam {

	@ApiModelProperty(value = "菜单id")
	private Long menuId;

	@ApiModelProperty(value = "父目录id")
	private Long parentId;

	@ApiModelProperty(value = "菜单名称")
	private String menuName;

	@ApiModelProperty(value = "授权(多个用逗号分隔，如：user:list,user:create)")
	private String perms;

	@ApiModelProperty(value = "前端跳转URL")
	private String path;

	@ApiModelProperty(value = "组件地址")
	private String component;

	@ApiModelProperty(value = "地址")
	private String icon;

	@ApiModelProperty(value = "排序")
	private Integer sort;

	@ApiModelProperty(value = "菜单类型（M目录 C菜单 F按钮")
	private String menuType;

	@ApiModelProperty(value = "显示状态（0显示 1隐藏）")
	private Integer visible;

	@ApiModelProperty(value = "菜单状态（1正常 0停用）")
	private Integer status;

}
