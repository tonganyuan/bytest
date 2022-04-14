package com.ynby.platform.operation.biz.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ynby.platform.operation.api.pojo.dto.UserDTO;
import com.ynby.platform.operation.api.pojo.entity.SysUser;
import com.ynby.platform.operation.api.pojo.param.PasswordParam;
import com.ynby.platform.operation.api.pojo.param.UserPageParam;
import com.ynby.platform.operation.api.pojo.param.UserParam;
import com.ynby.platform.operation.api.pojo.vo.UserVO;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 运营用户表	 服务类
 * </p>
 *
 * @author gaozhenyu
 * @since 2021-01-06
 */
public interface ISysUserService extends IService<SysUser> {

	/**
	 * 根据userId获取权限列表
	 *
	 * @param userId
	 * @return
	 */
	Set<String> listPermByUserId(String userId);


	/**
	 * 查询用户全量信息
	 *
	 * @param sysUser
	 * @return
	 */
	UserDTO getUserInfo(SysUser sysUser);

	/**
	 * 查询用户信息
	 *
	 * @param userId
	 * @return
	 */
	UserDTO getUserDTO(@RequestParam(required = false) Long userId);

	/**
	 * 超管用户不能被操作
	 *
	 * @param userId
	 */
	void checkUserNotAdmin(Long userId);

	/**
	 * 校验用户名是否存在
	 *
	 * @param username
	 */
	void checkUsernameExist(String username);

	/**
	 * 校验是否还有相同手机号
	 *
	 * @param userId 有userId的情况下，除当前用户外校验是否还有相同手机号; 没有userId的情况下，校验是否还有相同手机号;
	 * @param phone
	 */
	void checkPhoneExist(Long userId, String phone);

	/**
	 * 校验除当前用户外是否还有相同邮箱
	 *
	 * @param userId 有userId的情况下，除当前用户外校验是否还有相同邮箱 没有userId的情况下，校验是否还有相同邮箱;
	 * @param email
	 */
	void checkEmailExist(Long userId, String email);

	/**
	 * 新增用户
	 *
	 * @param param
	 */
	void addUser(UserParam param);

	/**
	 * 更新用户
	 *
	 * @param param
	 */
	void modifyUser(UserParam param);

	/**
	 * 删除用户
	 *
	 * @param userId
	 */
	void removeUsers(List<Long> userId);

	/**
	 * 用户分页
	 *
	 * @param page
	 * @param param
	 * @return
	 */
	Page<UserVO> pageUsers(IPage<SysUser> page, UserPageParam param);

	/**
	 * 重置密码
	 *
	 * @param param
	 */
	void resetPwd(UserParam param);

	/**
	 * 校验旧密码正确性
	 *
	 * @param username
	 * @param oldPassword
	 */
	void checkOldPassword(String username, String oldPassword);

	/**
	 * 修改密码
	 *
	 * @param param
	 */
	void changePassword(PasswordParam param);

	/**
	 * 根据用户名获取用户菜单列表
	 *
	 * @param username
	 * @return
	 */
	List<String> getUserMenusByUserName(String username);

	/**
	 * 根据用户名获取用户权限列表
	 *
	 * @param username
	 * @return
	 */
	Set<String> getUserPermsByUserName(String username);


}
