package com.ynby.platform.operation.api.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 运营用户表
 * </p>
 *
 * @author lianghui
 * @since 2021-04-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="SysUser对象", description="运营用户表")
public class SysUser extends Model<SysUser> {

	private static final long serialVersionUID = 1L;

	@TableId(value = "user_id", type = IdType.AUTO)
	private Long userId;

	@ApiModelProperty(value = "用户名")
	private String username;

	@ApiModelProperty(value = "昵称")
	private String nickname;

	@ApiModelProperty(value = "密码")
	private String password;

	@ApiModelProperty(value = "盐值")
	private String salt;

	@ApiModelProperty(value = "头像url")
	private String avatar;

	@ApiModelProperty(value = "邮箱")
	private String email;

	@ApiModelProperty(value = "手机号")
	private String phone;

	@ApiModelProperty(value = "用户性别, 默认未知")
	private String sex;

	@ApiModelProperty(value = "是否有效（1正常 0停用）")
	private Boolean status;

	@ApiModelProperty(value = "删除标志（0代表存在 1代表删除）")
	private Boolean delFlag;

	private LocalDateTime gmtCreate;

	private LocalDateTime gmtModified;


	@Override
	protected Serializable pkVal() {
		return this.userId;
	}

}
