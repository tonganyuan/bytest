package com.ynby.platform.operation.api.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author gaozhenyu
 * @Copyright 合肥
 * @Description
 * @date 2020-06-03 15:00
 */
@Data
public class UserVO {

	private Long userId;

	@ApiModelProperty(value = "用户名")
	private String username;

	@ApiModelProperty(value = "昵称")
	private String nickname;

	@ApiModelProperty(value = "所属角色")
	private String roleName;

	@ApiModelProperty(value = "邮箱")
	private String email;

	@ApiModelProperty(value = "手机号")
	private String phone;

	@ApiModelProperty(value = "头像url")
	private String avatar;

	@ApiModelProperty(value = "帐号状态（1正常 0停用）")
	private Integer status;

	@ApiModelProperty(value = "创建时间")
	private LocalDateTime gmtCreate;

}
