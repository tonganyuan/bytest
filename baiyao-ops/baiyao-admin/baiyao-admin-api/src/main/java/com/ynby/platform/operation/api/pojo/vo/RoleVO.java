package com.ynby.platform.operation.api.pojo.vo;

import com.ynby.platform.operation.api.pojo.entity.SysRole;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * @author gaozhenyu
 * @date 2020/2/1
 */
@Data
@Builder
public class RoleVO implements Serializable {
	/**
	 * 用户基本信息
	 */
	private SysRole sysRole;

	/**
	 * 权限标识集合
	 */
	private Set<String> perms;

	/**
	 * 菜单id集合
	 */
	private List<Long> menuIdList;

}
