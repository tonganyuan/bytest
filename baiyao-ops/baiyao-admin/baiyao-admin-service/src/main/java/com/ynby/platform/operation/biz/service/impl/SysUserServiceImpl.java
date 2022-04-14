package com.ynby.platform.operation.biz.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.ynby.platform.common.core.constant.CommonConstants;

import com.ynby.platform.common.core.enums.OperationEnums;
import com.ynby.platform.common.core.exception.CommonException;
import com.ynby.platform.common.core.utils.SystemUtil;
import com.ynby.platform.operation.api.pojo.dto.UserDTO;
import com.ynby.platform.operation.api.pojo.entity.SysMenu;
import com.ynby.platform.operation.api.pojo.entity.SysRole;
import com.ynby.platform.operation.api.pojo.entity.SysUser;
import com.ynby.platform.operation.api.pojo.entity.SysUserRole;
import com.ynby.platform.operation.api.pojo.param.PasswordParam;
import com.ynby.platform.operation.api.pojo.param.UserPageParam;
import com.ynby.platform.operation.api.pojo.param.UserParam;
import com.ynby.platform.operation.api.pojo.vo.UserVO;
import com.ynby.platform.operation.biz.config.SmsConfig;
import com.ynby.platform.operation.biz.dao.SysUserMapper;
import com.ynby.platform.operation.biz.service.ISysMenuService;
import com.ynby.platform.operation.biz.service.ISysRoleService;
import com.ynby.platform.operation.biz.service.ISysUserRoleService;
import com.ynby.platform.operation.biz.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 运营用户表	 服务实现类
 * </p>
 *
 * @author gaozhenyu
 * @since 2021-01-06
 */
@Slf4j
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

	@Autowired
	private SmsConfig smsConfig;

	@Autowired
	private ISysRoleService roleService;

	@Autowired
	private ISysMenuService menuService;

	@Autowired
	private ISysUserRoleService userRoleService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * 根据userId获取权限列表
	 *
	 * @param userId
	 * @return
	 */
	@Override
	public Set<String> listPermByUserId(String userId) {
		log.info("根据userId:{}获取权限列表", userId);
		return baseMapper.listPermByUserId(userId);
	}

	/**
	 * 查询用户全量信息
	 *
	 * @param sysUser
	 * @return
	 */
	@Override
	public UserDTO getUserInfo(SysUser sysUser) {
		UserDTO userInfo = new UserDTO();
		userInfo.setSysUser(sysUser);
		return selectUserPerms(userInfo, sysUser.getUserId());
	}

	/**
	 * 查询用户权限集合
	 *
	 * @param userInfo
	 * @param userId
	 * @return
	 */
	private UserDTO selectUserPerms(UserDTO userInfo, Long userId) {
		//设置角色集合
		Set<SysRole> sysRoles = roleService.listRolesByUserId(userId);
		Set<Long> roleIds = sysRoles
			.stream()
			.map(SysRole::getRoleId)
			.collect(Collectors.toSet());
		Set<String> roleCodes = sysRoles
			.stream()
			.map(SysRole::getRoleCode)
			.collect(Collectors.toSet());
		userInfo.setRoles(roleCodes);
		userInfo.setRoleIds(roleIds);

		//设置权限集合
		Set<String> permissions = new HashSet<>();
		roleIds.forEach(roleId -> {
			if (roleId.equals(CommonConstants.ADMIN_ROLE_ID)) {
				Set<String> adminPerms = menuService.list()
					.stream()
					.map(SysMenu::getPerms)
					.filter(StringUtils::isNotEmpty)
					.collect(Collectors.toSet());
				permissions.addAll(adminPerms);
			} else {
				Set<String> otherPerms = menuService.getMenuByRoleId(roleId)
					.stream()
					.filter(sysMenu -> sysMenu.getStatus().equals(OperationEnums.MenuStatus.YES.getCode()))
					.map(SysMenu::getPerms)
					.filter(StringUtils::isNotEmpty)
					.collect(Collectors.toSet());
				permissions.addAll(otherPerms);
			}
		});
		userInfo.setPermissions(permissions);
		return userInfo;
	}

	/**
	 * 查询用户菜单集合
	 *
	 * @param userInfo
	 * @param userId
	 * @return
	 */
	private UserDTO selectUserMenus(UserDTO userInfo, Long userId) {
		//设置角色集合
		Set<SysRole> sysRoles = roleService.listRolesByUserId(userId);
		Set<Long> roleIds = sysRoles
			.stream()
			.map(SysRole::getRoleId)
			.collect(Collectors.toSet());
		Set<String> roleCodes = sysRoles
			.stream()
			.map(SysRole::getRoleCode)
			.collect(Collectors.toSet());
		userInfo.setRoles(roleCodes);
		userInfo.setRoleIds(roleIds);

		//设置权限集合
		List<String> menus = new ArrayList<>();
		roleIds.forEach(roleId -> {
			if (roleId.equals(CommonConstants.ADMIN_ROLE_ID)) {
				List<String> adminMenus = menuService.list()
					.stream()
					.sorted(Comparator.comparing(SysMenu::getSort))
					.map(SysMenu::getPath)
					.filter(StringUtils::isNotEmpty)
					.collect(Collectors.toList());
				menus.addAll(adminMenus);
			} else {
				menus.addAll(menuService.getMenuByRoleId(roleId)
					.stream()
					.sorted(Comparator.comparing(SysMenu::getSort))
					.map(SysMenu::getPath)
					.filter(StringUtils::isNotEmpty)
					.collect(Collectors.toList()));
			}
		});
		userInfo.setMenus(menus);
		return userInfo;
	}

	/**
	 * 查询用户信息
	 *
	 * @param userId
	 * @return
	 */
	@Override
	public UserDTO getUserDTO(@RequestParam(required = false) Long userId) {
		log.info("查询用户信息，用户id：{}", userId);
//		userId = Optional.ofNullable(userId).orElse(ContextUtil.getOpUserId());
		SysUser tmpUser = getOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUserId, userId).eq(SysUser::getDelFlag, false));
		SysUser user = Optional.ofNullable(tmpUser).orElseThrow(() -> new CommonException("该用户不存在"));
		UserDTO userDTO = getUserInfo(user);
		List<SysRole> roles = roleService.list();
		// 返回全部角色中不带超管
		userDTO.setRoleInfos(roles.stream().filter(r -> !SystemUtil.isAdminRole(r.getRoleId())
			&& !r.getDelFlag()).collect(Collectors.toList()));
		return userDTO;
	}

	/**
	 * 校验用户是否被允许操作
	 *
	 * @param userId 超管用户不能被操作
	 */
	@Override
	public void checkUserNotAdmin(Long userId) {
		log.info("校验用户:{}是否被允许操作", userId);
		if (userId != null && userId.equals(CommonConstants.ADMIN_USER_ID)) {
			throw new CommonException("不允许操作超级管理员用户");
		}
	}

	/**
	 * 校验用户名是否存在
	 * （用户名不允许被修改）
	 *
	 * @param username
	 */
	@Override
	public void checkUsernameExist(String username) {
		log.info("校验用户名:{}是否存在", username);
		int count = count(Wrappers.<SysUser>lambdaQuery()
			.eq(SysUser::getUsername, username)
		);
		if (count > 0) {
			throw new CommonException("用户名已存在");
		}
	}

	/**
	 * 校验是否还有相同手机号
	 *
	 * @param userId 有userId的情况下，除当前用户外校验是否还有相同手机号; 没有userId的情况下，校验是否还有相同手机号;
	 * @param phone
	 */
	@Override
	public void checkPhoneExist(Long userId, String phone) {
		log.info("校验是否还有相同手机号，用户id：{}， 手机号：{}", userId, phone);
		int count = count(Wrappers.<SysUser>lambdaQuery()
			.ne(userId != null, SysUser::getUserId, userId)
			.eq(SysUser::getPhone, phone)
		);
		if (count > 0) {
			throw new CommonException("手机号已存在");
		}
	}

	/**
	 * 校验除当前用户外是否还有相同邮箱
	 *
	 * @param userId 有userId的情况下，除当前用户外校验是否还有相同邮箱 没有userId的情况下，校验是否还有相同邮箱;
	 * @param email
	 */
	@Override
	public void checkEmailExist(Long userId, String email) {
		int count = count(Wrappers.<SysUser>lambdaQuery()
			.ne(userId != null, SysUser::getUserId, userId)
			.eq(SysUser::getEmail, email)
		);
		if (count > 0) {
			throw new CommonException("email已存在");
		}
	}

	/**
	 * 新增用户
	 *
	 * @param param
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void addUser(UserParam param) {
		// 新增用户
		SysUser user = new SysUser();
		BeanUtils.copyProperties(param, user, "userId", "password");
		user.setPassword(passwordEncoder.encode(param.getPhone().substring(param.getPhone().length() - 6)));
		user.setStatus(true);
		save(user);
		// 加入用户和角色关系
		param.setUserId(user.getUserId());
		saveUserRole(param);
	}

	/**
	 * 更新用户
	 *
	 * @param param
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void modifyUser(UserParam param) {
		SysUser sysUser = new SysUser();
		BeanUtils.copyProperties(param, sysUser);
		updateById(sysUser);
		// 删除该用户id下的所有角色用户关系
		userRoleService.remove(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, param.getUserId()));
		// 加入用户和角色关系
		saveUserRole(param);
	}

	/**
	 * 删除用户
	 *
	 * @param userIds
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void removeUsers(List<Long> userIds) {
		userIds.forEach(this::checkUserNotAdmin);
		for (Long userId : userIds) {
			update(Wrappers.<SysUser>lambdaUpdate().eq(SysUser::getUserId, userId).set(SysUser::getDelFlag, true));
		}
		userRoleService.remove(Wrappers.<SysUserRole>lambdaQuery().in(SysUserRole::getUserId, userIds));
	}

	/**
	 * 用户分页
	 *
	 * @param page
	 * @param param
	 * @return
	 */
	@Override
	public Page<UserVO> pageUsers(IPage<SysUser> page, UserPageParam param) {
		return baseMapper.selectUserPage(page, param);
	}


	/**
	 * 重置密码
	 *
	 * @param param
	 */
	@Override
	public void resetPwd(UserParam param) {
		SysUser user = new SysUser();
		BeanUtils.copyProperties(param, user, "password");
		String salt = StrUtil.isNotBlank(user.getSalt()) ? user.getSalt() : MD5.create().digestHex(user.getPhone());
		user.setSalt(salt);
		user.setPassword(passwordEncoder.encode(param.getPhone().substring(param.getPhone().length() - 6)));
		updateById(user);
	}

	/**
	 * 校验旧密码正确性
	 *
	 * @param username
	 * @param oldPassword
	 */
	@Override
	public void checkOldPassword(String username, String oldPassword) {
		SysUser user = getOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUsername, username));
		if (null == user) {
			throw new CommonException("用户不存在，请输入正确用户名");
		} else if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
			throw new CommonException("密码错误，请重新输入");
		}
	}

	/**
	 * 修改密码
	 *
	 * @param param
	 */
	@Override
	public void changePassword(PasswordParam param) {
		String username = param.getUsername();
		SysUser user = getOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUsername, username));
		if (StrUtil.isNotBlank(param.getCode()) && !param.getPhone().equals(user.getPhone())) {
			throw new CommonException("手机号和用户名不一致");
		}
		user.setPassword(passwordEncoder.encode(param.getNewPassword()));
		updateById(user);
	}

	/**
	 * 根据用户名获取用户菜单列表
	 *
	 * @param username
	 * @return
	 */
	@Override
	public List<String> getUserMenusByUserName(String username) {
		SysUser sysUser = getOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUsername, username));
		return selectUserMenus(new UserDTO(), sysUser.getUserId()).getMenus();
	}

	/**
	 * 根据用户名获取用户权限列表
	 *
	 * @param username
	 * @return
	 */
	@Override
	public Set<String> getUserPermsByUserName(String username) {
		SysUser sysUser = getOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUsername, username));
		return selectUserPerms(new UserDTO(), sysUser.getUserId()).getPermissions();
	}



	/* -------------------------------------- private -------------------------------------- */

	/**
	 * 批量新增用户与角色的关系
	 *
	 * @param param
	 */
	private void saveUserRole(UserParam param) {
		List<SysUserRole> userRoles = Lists.newArrayList();
		if (CollUtil.isNotEmpty(param.getRoleIds())) {
			param.getRoleIds().forEach(roleId -> {
				SysUserRole userRole = new SysUserRole();
				userRole.setRoleId(roleId);
				userRole.setUserId(param.getUserId());
				userRoles.add(userRole);
			});
		}
		userRoleService.saveBatch(userRoles);
	}

}
