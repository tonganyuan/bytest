package com.ynby.platform.operation.biz.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.ynby.platform.common.core.constant.CommonConstants;
import com.ynby.platform.common.core.exception.CommonException;
import com.ynby.platform.operation.api.pojo.entity.*;
import com.ynby.platform.operation.api.pojo.param.RoleParam;
import com.ynby.platform.operation.api.pojo.vo.RoleVO;
import com.ynby.platform.operation.biz.dao.SysRoleMapper;
import com.ynby.platform.operation.biz.service.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author gaozhenyu
 * @since 2021-01-06
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

	/**
	 * 角色编码前缀
	 * 直接适配springSecurity的默认权限控制
	 */
	public static final String ROLE_PREFIX = "ROLE_";

	@Autowired
	private ISysRoleMenuService roleMenuService;

	@Autowired
	private ISysUserRoleService userRoleService;

	@Autowired
	private ISysMenuService menuService;

	@Autowired
	private ISysRoleDeptService roleDeptService;

	/**
	 * 通过用户ID查询角色信息
	 *
	 * @param userId
	 * @return
	 */
	@Override
	public Set<SysRole> listRolesByUserId(Long userId) {
		return baseMapper.listRolesByUserId(userId);
	}

	/**
	 * 根据id获取角色详情
	 *
	 * @param role
	 * @return
	 */
	@Override
	public RoleVO getRoleInfo(SysRole role) {
		role.setRoleCode(role.getRoleCode().replace(ROLE_PREFIX, ""));
		Set<String> perms = menuService.getMenuByRoleId(role.getRoleId())
			.stream()
			.map(SysMenu::getPerms)
			.filter(StringUtils::isNotEmpty)
			.collect(Collectors.toSet());
		return RoleVO.builder().sysRole(role).perms(perms).build();
	}

	/**
	 * 超管角色不能被操作
	 *
	 * @param roleId
	 */
	@Override
	public void checkRoleNotAdmin(Long roleId) {
		if (roleId != null && roleId.equals(CommonConstants.ADMIN_ROLE_ID)) {
			throw new CommonException("不允许操作超级管理员角色");
		}
	}

	/**
	 * 检测角色编码是否存在
	 *
	 * @param roleId
	 * @param roleCode
	 */
	@Override
	public void checkRoleCodeExist(Long roleId, String roleCode) {
		int count = count(Wrappers.<SysRole>lambdaQuery()
			.ne(roleId != null, SysRole::getRoleId, roleId)
			.eq(SysRole::getRoleCode, ROLE_PREFIX + roleCode)
		);
		if (count > 0) {
			throw new CommonException("角色编码已存在");
		}
	}

	/**
	 * 检测角色名称是否存在
	 *
	 * @param roleId
	 * @param roleName
	 */
	@Override
	public void checkRoleNameExist(Long roleId, String roleName) {
		int count = count(Wrappers.<SysRole>lambdaQuery()
			.ne(roleId != null, SysRole::getRoleId, roleId)
			.eq(SysRole::getRoleName, roleName)
		);
		if (count > 0) {
			throw new CommonException("角色名已存在");
		}
	}

	/**
	 * 添加角色
	 *
	 * @param param
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void addRole(RoleParam param) {
		SysRole role = new SysRole();
		BeanUtils.copyProperties(param, role, "roleId");
		role.setRoleCode(ROLE_PREFIX + role.getRoleCode());
		save(role);
		// 加入角色和菜单关系
		param.setRoleId(role.getRoleId());
		saveRoleMenu(param);
	}

	/**
	 * 更新角色
	 *
	 * @param param
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void modifyRole(RoleParam param) {
		SysRole role = new SysRole();
		BeanUtils.copyProperties(param, role);
		String prependRoleCode = StrUtil.prependIfMissing(role.getRoleCode(), ROLE_PREFIX);
		role.setRoleCode(prependRoleCode);
		updateById(role);
		// 删除该用户id下的所有角色菜单关系
		roleMenuService.remove(Wrappers.<SysRoleMenu>lambdaQuery().eq(SysRoleMenu::getRoleId, param.getRoleId()));
		// 加入角色和菜单关系
		saveRoleMenu(param);
	}

	/**
	 * 删除角色
	 *
	 * @param roleIds
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void removeRoles(List<Long> roleIds) {
		// 检查角色可否被删除
		roleIds.forEach(roleId -> {
			checkRoleNotAdmin(roleId);
			int roleUsedByUserCount = userRoleService.count(
				Wrappers.<SysUserRole>lambdaQuery().eq(SysUserRole::getRoleId, roleId));
			if (roleUsedByUserCount > 0) {
				throw new CommonException(String.format("角色id: %s 已分配, 不能删除", roleId));
			}
		});
		// 删除角色与菜单关联
		roleMenuService.remove(Wrappers.<SysRoleMenu>lambdaQuery().in(SysRoleMenu::getRoleId, roleIds));
		// 删除角色与部门关联
		roleDeptService.remove(Wrappers.<SysRoleDept>lambdaQuery().in(SysRoleDept::getRoleId, roleIds));
		// 删除角色
		for (Long roleId : roleIds) {
			update(Wrappers.<SysRole>lambdaUpdate().eq(SysRole::getRoleId, roleId).set(SysRole::getDelFlag, true));
		}
	}

	/**
	 * 修改保存数据权限
	 *
	 * @param param
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void authDataScope(RoleParam param) {
		SysRole role = new SysRole();
		BeanUtils.copyProperties(param, role);
		// 修改角色信息
		updateById(role);
		// 删除角色与部门关联
		roleDeptService.remove(Wrappers.<SysRoleDept>lambdaQuery().eq(SysRoleDept::getRoleId, param.getRoleId()));
		// 新增角色和部门信息（数据权限）
		saveRoleDept(param);
	}

	/* -------------------------------------- private -------------------------------------- */

	/**
	 * 批量新增角色与权限的关系
	 *
	 * @param param
	 */
	private void saveRoleMenu(RoleParam param) {
		List<SysRoleMenu> roleMenus = Lists.newArrayList();
		if (CollUtil.isNotEmpty(param.getMenuIds())) {
			param.getMenuIds().forEach(menuId -> {
				SysRoleMenu roleMenu = new SysRoleMenu();
				roleMenu.setRoleId(param.getRoleId());
				roleMenu.setMenuId(menuId);
				roleMenus.add(roleMenu);
			});
		}
		roleMenuService.saveBatch(roleMenus);
	}

	/**
	 * 批量新增角色与部门的关系
	 *
	 * @param param
	 */
	private void saveRoleDept(RoleParam param) {
		List<SysRoleDept> roleDepts = Lists.newArrayList();
		if (CollUtil.isNotEmpty(param.getDeptIds())) {
			param.getDeptIds().forEach(deptId -> {
				SysRoleDept roleDept = new SysRoleDept();
				roleDept.setRoleId(param.getRoleId());
				roleDept.setDeptId(deptId);
				roleDepts.add(roleDept);
			});
		}
		roleDeptService.saveBatch(roleDepts);
	}

}
