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
 * 菜单权限表
 * </p>
 *
 * @author gaozhenyu
 * @since 2021-01-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="SysMenu对象", description="菜单权限表")
public class SysMenu extends Model<SysMenu> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "菜单id")
    @TableId(value = "menu_id", type = IdType.AUTO)
    private Long menuId;

	@ApiModelProperty(value = "父目录id")
	private Long parentId;

	@ApiModelProperty(value = "菜单名称")
	private String menuName;

	@ApiModelProperty(value = "授权(多个用逗号分隔，如：user:list,user:create)")
	private String perms;

	@ApiModelProperty(value = "组件地址")
	private String component;

	@ApiModelProperty(value = "前端跳转URL")
	private String path;

	@ApiModelProperty(value = "图标")
	private String icon;

	@ApiModelProperty(value = "排序")
	private Integer sort;

	@ApiModelProperty(value = "菜单类型（M目录 C菜单 F按钮")
	private String menuType;

	@ApiModelProperty(value = "显示状态（0显示 1隐藏）")
	private Integer visible;

	@ApiModelProperty(value = "菜单状态（1正常 0停用）")
	private Integer status;

	private LocalDateTime gmtCreate;

	private LocalDateTime gmtModified;


	@Override
	protected Serializable pkVal() {
		return this.menuId;
	}

}
