package com.ynby.platform.operation.biz.controller.sys;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ynby.platform.common.core.constant.CommonConstants;
import com.ynby.platform.common.core.enums.CommonEnums;
import com.ynby.platform.common.core.exception.CommonException;
import com.ynby.platform.common.core.pojo.R;
import com.ynby.platform.common.core.pojo.TokenVO;
import com.ynby.platform.common.unit.manager.AuthHttpManager;
import com.ynby.platform.operation.api.pojo.dto.UserDTO;
import com.ynby.platform.operation.api.pojo.entity.SysUser;
import com.ynby.platform.operation.api.pojo.param.PasswordParam;
import com.ynby.platform.operation.api.pojo.param.UserPageParam;
import com.ynby.platform.operation.api.pojo.param.UserParam;
import com.ynby.platform.operation.api.pojo.vo.UserVO;
import com.ynby.platform.operation.biz.handler.LogHandler;
import com.ynby.platform.operation.biz.log.annotation.Log;
import com.ynby.platform.operation.biz.log.annotation.enums.BusinessType;
import com.ynby.platform.operation.biz.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author gaozhenyu
 * @des
 * @date 2020-01-15 18:17
 */
@Api(tags = "用户管理接口")
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private ISysUserService userService;

	@Autowired
	private LogHandler logSaveHandler;

	@Autowired
	private AuthHttpManager authHttpManager;

	/**
	 * 用户认证和获取权限列表
	 *
	 * @param request
	 * @param username
	 * @param password
	 * @return
	 */
	@ApiOperation("登录")
	@PostMapping("/login")
	public R<?> login(HttpServletRequest request, @RequestParam String username, @RequestParam String password) {
		//todo 更换认证中心url，详细情况请联系 平台架构su-高震宇申请 clientId,clientSecret ,auth_url: http://192.168.32.66:9500/uaa
		TokenVO tokenVO = authHttpManager.getUsernamePasswordToken("http://192.168.32.66:9500/uaa", "88d284138646a6cb", "0111234444666677888888aabbcccdff",
			username, password, CommonEnums.UserType.PLATFORM.getCode());
		Map<String, Object> resMap = BeanUtil.beanToMap(tokenVO);
		logSaveHandler.asyncSaveLog(request, username, "用户管理", BusinessType.IN.getCode());
		return R.ok(resMap);
	}

	/**
	 * 用户登出，清除相关信息
	 *
	 * @param
	 * @return
	 */
	@ApiOperation("登出")
	@Log(title = "用户管理", businessType = BusinessType.OUT)
	@PostMapping("/logout")
	public R<?> logout() {
		//FIXME 用户登出，清除相关信息

		return R.ok();
	}

	/**
	 * 根据用户名查询用户全量信息
	 * 对内
	 *
	 * @param username
	 * @return
	 */
	@ApiOperation("根据用户名查询用户全量信息")
	@GetMapping("/infos/{username}")
	public R<UserDTO> userInfo(@PathVariable String username) {

		SysUser tmpUser = userService.getOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUsername, username).eq(SysUser::getDelFlag, false));
		SysUser user = Optional.ofNullable(tmpUser).orElseThrow(() -> new CommonException("该用户不存在"));
		UserDTO userInfo = userService.getUserInfo(user);
		return R.ok(userInfo);
	}

	/**
	 * 根据用户名查询用户手机号
	 *
	 * @param username
	 * @return
	 */
	@ApiOperation("根据用户名查询用户手机号")
	@GetMapping("/phone/{username}")
	public R<String> getUserPhone(@PathVariable String username) {
		SysUser tmpUser = userService.getOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUsername, username).eq(SysUser::getDelFlag, false));
		SysUser user = Optional.ofNullable(tmpUser).orElseThrow(() -> new CommonException("该用户不存在"));
		return R.ok(user.getPhone());
	}

	/**
	 * 根据用户id查询用户全量信息
	 * 对内
	 *
	 * @param userId
	 * @return
	 */
	@ApiOperation("根据用户id查询用户全量信息")
	@GetMapping("/info")
	@Log(title = "用户管理", businessType = BusinessType.OTHER)
	public R<UserDTO> userInfo(@RequestParam(required = false) Long userId) {
		return R.ok(userService.getUserDTO(userId));
	}

	/**
	 * 查询用户全量信息
	 * status从整型变成字符串为了和字典表里的值相对应
	 * 对外,需要token
	 *
	 * @param
	 * @return
	 */
	@GetMapping(value = {"/personInfo/", "/personInfo/{userId}"})
	public R<UserDTO> personInfo(@PathVariable(required = false) Long userId) {
		UserDTO userDTO = userService.getUserDTO(userId);
		return R.ok(userDTO);
	}

	/**
	 * 用户分页列表
	 *
	 * @return
	 */
	@ApiOperation("用户分页列表")
	@Log(title = "用户管理", businessType = BusinessType.LIST)
//	@PreAuthorize("hasAuthority('sys:user:query')")
	@GetMapping("/page")
	public R<IPage<UserVO>> page(UserPageParam param) {
		IPage<SysUser> page = new Page<>(param.getPageNum(), param.getPageSize());
		Page<UserVO> sysUserPage = userService.pageUsers(page, param);
		return R.ok(sysUserPage);
	}

	/**
	 * 添加用户
	 *
	 * @param param
	 */
	@ApiOperation("添加用户")
//	@PreAuthorize("hasAuthority('sys:user:add')")
	@Log(title = "用户管理", businessType = BusinessType.INSERT)
	@PostMapping
	public R<?> add(@Valid @RequestBody UserParam param) {
		userService.checkUsernameExist(param.getUsername());
		userService.checkPhoneExist(null, param.getPhone());
		userService.addUser(param);
		return R.ok();
	}

	/**
	 * 修改用户
	 *
	 * @param param
	 * @return
	 */
	@ApiOperation("修改用户")
//	@PreAuthorize("hasAuthority('sys:user:edit')")
	@Log(title = "用户管理", businessType = BusinessType.UPDATE)
	@PutMapping
	public R<?> modify(@Valid @RequestBody UserParam param) {
		log.info("修改用户,参数：{}", JSON.toJSONString(param));
		userService.checkUserNotAdmin(param.getUserId());
		userService.checkPhoneExist(param.getUserId(), param.getPhone());
		userService.modifyUser(param);
		return R.ok();
	}

	/**
	 * 主动修改密码
	 *
	 * @param param
	 * @return
	 */
	@ApiOperation("修改密码")
	@Log(title = "用户管理", businessType = BusinessType.UPDATE)
	@PutMapping("/changePassword")
	public R<?> changePassword(@Valid @RequestBody PasswordParam param) {
		userService.checkOldPassword(param.getUsername(), param.getOldPassword());
		userService.changePassword(param);
		return R.ok();
	}

	/**
	 * 忘记密码
	 *
	 * @param param
	 * @return
	 */
	@ApiOperation("忘记密码")
	@Log(title = "用户管理", businessType = BusinessType.UPDATE)
	@PutMapping("/forgetPassword")
	public R<?> forgetPassword(@Valid @RequestBody PasswordParam param) {
		userService.changePassword(param);
		return R.ok();
	}



	/**
	 * 重置密码
	 *
	 * @param param
	 * @return
	 */
	@ApiOperation("重置密码")
//	@PreAuthorize("hasAuthority('sys:user:resetPwd')")
	@Log(title = "用户管理", businessType = BusinessType.UPDATE)
	@PutMapping("/resetPwd")
	public R<?> resetPwd(@RequestBody UserParam param) {
		userService.checkUserNotAdmin(param.getUserId());
		userService.resetPwd(param);
		return R.ok();
	}

	/**
	 * 状态修改
	 *
	 * @param param
	 * @return
	 */
	@ApiOperation("状态修改")
//	@PreAuthorize("hasAuthority('sys:user:changeStatus')")
	@Log(title = "用户管理", businessType = BusinessType.UPDATE)
	@PutMapping("/changeStatus")
	public R<?> changeStatus(@RequestBody UserParam param) {
		userService.checkUserNotAdmin(param.getUserId());
		SysUser user = new SysUser();
		user.setUserId(param.getUserId());
		user.setStatus(param.getStatus() == 1);
		userService.updateById(user);
		return R.ok();
	}

	/**
	 * 删除一个或多个用户
	 *
	 * @param userIds
	 * @return
	 */
	@ApiOperation("删除一个或多个用户")
//	@PreAuthorize("hasAuthority('sys:user:remove')")
	@Log(title = "用户管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{userIds}")
	public R<?> remove(@PathVariable List<Long> userIds) {
		userService.removeUsers(userIds);
		return R.ok();
	}

}
