package com.ynby.platform.operation.api.pojo.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Set;

/**
 * @author gaozhenyu
 * @Copyright 合肥
 * @Description
 * @date 2020-06-01 18:31
 */
@Data
public class RoleParam {

	@ApiModelProperty(value = "角色id, 新增操作勿传")
	private Long roleId;

	@ApiModelProperty(value = "角色唯一标示")
	private String roleCode;

	@ApiModelProperty(value = "角色名称")
	private String roleName;

	@ApiModelProperty(value = "角色描述")
	private String roleDesc;

	@ApiModelProperty(value = "排序")
	private Integer sort;

	@ApiModelProperty(value = "角色状态（1正常 0停用）")
	private Integer status;

	@ApiModelProperty(value = "菜单列表")
	private Set<Long> menuIds;

	@ApiModelProperty(value = "部门列表")
	private Set<Long> deptIds;

}
