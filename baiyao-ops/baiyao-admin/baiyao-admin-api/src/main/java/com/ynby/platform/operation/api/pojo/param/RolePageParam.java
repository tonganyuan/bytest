package com.ynby.platform.operation.api.pojo.param;

import com.ynby.platform.common.core.pojo.BasePage;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author gaozhenyu
 * @Copyright 合肥
 * @Description
 * @date 2020-06-01 18:31
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RolePageParam extends BasePage {

	@ApiModelProperty(value = "角色名称")
	private String roleName;

	@ApiModelProperty(value = "权限字符")
	private String roleCode;

	@ApiModelProperty(value = "角色状态 （1正常 0停用）")
	private Integer status;

}
