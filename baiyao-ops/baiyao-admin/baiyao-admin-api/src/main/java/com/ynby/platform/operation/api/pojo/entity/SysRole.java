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
 * 角色表
 * </p>
 *
 * @author gaozhenyu
 * @since 2021-01-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="SysRole对象", description="角色表")
public class SysRole extends Model<SysRole> {

    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "角色id")
	@TableId(value = "role_id", type = IdType.AUTO)
	private Long roleId;

	@ApiModelProperty(value = "角色唯一标示")
	private String roleCode;

	@ApiModelProperty(value = "角色名称")
	private String roleName;

	@ApiModelProperty(value = "数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）")
	private String dataScope;

	@ApiModelProperty(value = "角色描述")
	private String roleDesc;

	@ApiModelProperty(value = "排序")
	private Integer sort;

	@ApiModelProperty(value = "角色状态（1正常 0停用）")
	private Integer status;

	@ApiModelProperty(value = "删除标识（0-正常,1-删除）")
	private Boolean delFlag;

	private LocalDateTime gmtCreate;

	private LocalDateTime gmtModified;


	@Override
	protected Serializable pkVal() {
		return this.roleId;
	}

}
