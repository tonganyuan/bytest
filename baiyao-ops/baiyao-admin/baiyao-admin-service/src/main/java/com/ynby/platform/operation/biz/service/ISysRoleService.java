package com.ynby.platform.operation.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ynby.platform.operation.api.pojo.entity.SysRole;
import com.ynby.platform.operation.api.pojo.param.RoleParam;
import com.ynby.platform.operation.api.pojo.vo.RoleVO;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author gaozhenyu
 * @since 2021-01-06
 */
public interface ISysRoleService extends IService<SysRole> {

	/**
	 * 通过用户ID，查询角色信息
	 *
	 * @param userId
	 * @return
	 */
	Set<SysRole> listRolesByUserId(Long userId);

	/**
	 * 根据id获取角色详情
	 *
	 * @param role
	 * @return
	 */
	RoleVO getRoleInfo(SysRole role);

	/**
	 * 超管角色不能被操作
	 *
	 * @param roleId
	 */
	void checkRoleNotAdmin(Long roleId);

	/**
	 * 检测角色编码是否存在
	 *
	 * @param roleId
	 * @param roleCode
	 */
	void checkRoleCodeExist(Long roleId, String roleCode);

	/**
	 * 检测角色名字是否存在
	 *
	 * @param roleId
	 * @param roleName
	 */
	void checkRoleNameExist(Long roleId, String roleName);

	/**
	 * 添加角色
	 *
	 * @param param
	 */
	void addRole(RoleParam param);

	/**
	 * 更新角色
	 *
	 * @param param
	 */
	void modifyRole(RoleParam param);

	/**
	 * 删除角色
	 *
	 * @param roleIds
	 */
	void removeRoles(List<Long> roleIds);

	/**
	 * 修改数据权限信息
	 *
	 * @param param
	 */
	void authDataScope(RoleParam param);
}
