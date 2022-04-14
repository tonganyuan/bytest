package com.ynby.platform.operation.api.pojo.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * @author gaozhenyu
 * @Copyright 合肥
 * @Description
 * @date 2020-06-01 18:31
 */
@Data
public class UserParam {

	@ApiModelProperty(value = "用户id, 新增操作勿传")
	private Long userId;

	@ApiModelProperty(value = "用户名")
	private String username;

	@ApiModelProperty(value = "密码")
	private String password;

	@ApiModelProperty(value = "昵称")
	private String nickname;

	@ApiModelProperty(value = "邮箱")
	private String email;

	@ApiModelProperty(value = "手机号")
	private String phone;

	@ApiModelProperty(value = "用户性别, 默认未知")
	private String sex;

	@ApiModelProperty(value = "头像url")
	private String avatar;

	@ApiModelProperty(value = "角色列表")
	@NotEmpty(message = "所属角色不可为空")
	private Set<Long> roleIds;

	@ApiModelProperty(value = "有效状态（1正常 0停用）")
	private Integer status;

}
