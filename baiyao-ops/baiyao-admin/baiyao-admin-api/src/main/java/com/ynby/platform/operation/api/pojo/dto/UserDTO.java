package com.ynby.platform.operation.api.pojo.dto;

import com.ynby.platform.operation.api.pojo.entity.SysRole;
import com.ynby.platform.operation.api.pojo.entity.SysUser;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * @author gaozhenyu
 * @date 2020/2/1
 */
@Data
public class UserDTO implements Serializable {
	/**
	 * 用户基本信息
	 */
	private SysUser sysUser;

	/**
	 * 权限标识集合
	 */
	private Set<String> permissions;

	/**
	 * 菜单集合
	 */
	private List<String> menus;

	/**
	 * 角色codes集合
	 */
	private Set<String> roles;

	/**
	 * 角色id集合
	 */
	private Set<Long> roleIds;

	/**
	 * 角色信息集合
	 */
	private List<SysRole> roleInfos;

}
