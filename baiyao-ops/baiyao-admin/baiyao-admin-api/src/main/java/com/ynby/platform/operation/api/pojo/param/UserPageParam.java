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
public class UserPageParam extends BasePage {

	@ApiModelProperty(value = "用户名")
	private String username;

	@ApiModelProperty(value = "部门ID")
	private Long deptId;

	@ApiModelProperty(value = "角色ID")
	private Long roleId;

	@ApiModelProperty(value = "手机号")
	private String phone;

	@ApiModelProperty(value = "帐号状态（1正常 0停用）")
	private Integer status;

}
