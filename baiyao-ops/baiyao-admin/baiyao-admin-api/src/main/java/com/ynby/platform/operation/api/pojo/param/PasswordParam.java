package com.ynby.platform.operation.api.pojo.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * <p>
 * 密码参数
 * <p>
 *
 * @Author: lianghui
 * @Date: 2021/4/21/0021 16:58
 */
@Data
public class PasswordParam {

	@ApiModelProperty(value = "用户名")
	private String username;

	@ApiModelProperty(value = "旧密码")
	private String oldPassword;

	@NotBlank(message = "新密码不能为空")
	@Size(min = 4, max = 12, message = "密码长度（4~12位）")
	@ApiModelProperty(value = "新密码")
	private String newPassword;

	@ApiModelProperty(value = "手机号")
	private String phone;

	@ApiModelProperty(value = "验证码")
	private String code;
}
