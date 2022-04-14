package com.ynby.platform.operation.biz.controller.sys;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ynby.platform.common.core.enums.OperationEnums;
import com.ynby.platform.common.core.exception.CommonException;
import com.ynby.platform.common.core.pojo.R;
import com.ynby.platform.operation.api.pojo.entity.SysRole;
import com.ynby.platform.operation.api.pojo.entity.SysRoleMenu;
import com.ynby.platform.operation.api.pojo.param.RolePageParam;
import com.ynby.platform.operation.api.pojo.param.RoleParam;
import com.ynby.platform.operation.api.pojo.vo.RoleVO;
import com.ynby.platform.operation.biz.log.annotation.Log;
import com.ynby.platform.operation.biz.log.annotation.enums.BusinessType;
import com.ynby.platform.operation.biz.service.ISysRoleMenuService;
import com.ynby.platform.operation.biz.service.ISysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author gaozhenyu
 * @des
 * @date 2020-01-15 18:17
 */
@Api(tags = "角色管理接口")
@Slf4j
@RestController
@RequestMapping("/role")
public class RoleController {

	@Autowired
	private ISysRoleService roleService;

	@Autowired
	private ISysRoleMenuService roleMenuService;

	/**
	 * 角色分页列表
	 *
	 * @return
	 */
	@ApiOperation("角色分页列表")
//	@PreAuthorize("hasAuthority('sys:role:query')")
	@Log(title = "角色管理", businessType = BusinessType.LIST)
	@GetMapping("/page")
	public R<IPage<SysRole>> page(RolePageParam param) {
		Map<String, Object> dateParams = param.getParams();
		IPage<SysRole> page = new Page<>(param.getPageNum(), param.getPageSize());
		LambdaQueryWrapper<SysRole> queryWrapper = Wrappers.<SysRole>lambdaQuery()
			.select(SysRole::getRoleId, SysRole::getRoleName, SysRole::getRoleCode, SysRole::getRoleDesc,
				SysRole::getGmtCreate, SysRole::getStatus, SysRole::getSort)
			.eq(StrUtil.isNotBlank(param.getRoleCode()), SysRole::getRoleCode, param.getRoleCode())
			.eq(param.getStatus() != null, SysRole::getStatus, param.getStatus())
			.eq(SysRole::getDelFlag, false)
			.like(StrUtil.isNotBlank(param.getRoleName()), SysRole::getRoleName,
				param.getRoleName())
			.orderByAsc(SysRole::getSort).orderByDesc(SysRole::getGmtCreate);
		if (dateParams != null && dateParams.containsKey("beginTime") && dateParams.containsKey("endTime")) {
			queryWrapper.apply(
				"date_format(create_time,'%y%m%d') between date_format({0},'%y%m%d') and date_format({1},'%y%m%d')",
				dateParams.get("beginTime"), dateParams.get("endTime"));
		}
		return R.ok(roleService.page(page, queryWrapper));
	}

	/**
	 * 角色列表
	 *
	 * @return
	 */
	@ApiOperation("角色列表")
	@Log(title = "角色管理", businessType = BusinessType.LIST)
	@GetMapping("/selectList")
	public R<List<RoleVO>> selectList() {
		List<RoleVO> result = new ArrayList<>();
		List<SysRole> sysRoleList = roleService.list(Wrappers.<SysRole>lambdaQuery()
			.eq(SysRole::getStatus, OperationEnums.RoleStatus.YES.getValue())
			.eq(SysRole::getDelFlag, false)
		);
		sysRoleList.forEach(role -> {
			RoleVO.RoleVOBuilder roleVO = RoleVO.builder();
			roleVO.sysRole(role);
			result.add(roleVO.build());
		});
		return R.ok(result);
	}


	/**
	 * 根据id获取角色详情
	 *
	 * @param roleId
	 * @return
	 */
	@ApiOperation("根据id获取角色详情")
	@Log(title = "角色管理", businessType = BusinessType.OTHER)
	@GetMapping("/{roleId}")
	public R<RoleVO> info(@PathVariable Long roleId) {
		SysRole tmpRole = roleService.getById(roleId);
		SysRole role = Optional.ofNullable(tmpRole).orElseThrow(() -> new CommonException("角色不存在"));
		RoleVO roleVO = roleService.getRoleInfo(role);
		roleVO.setMenuIdList(roleMenuService.list(Wrappers.<SysRoleMenu>lambdaQuery().eq(SysRoleMenu::getRoleId, roleId))
			.stream().map(SysRoleMenu::getMenuId).collect(Collectors.toList()));
		return R.ok(roleVO);
	}

	/**
	 * 添加角色
	 *
	 * @param param
	 * @return
	 */
	@ApiOperation("添加角色")
	@Log(title = "角色管理", businessType = BusinessType.INSERT)
//	@PreAuthorize("hasAuthority('sys:role:add')")
	@PostMapping
	public R<?> add(@RequestBody RoleParam param) {
		roleService.checkRoleNotAdmin(param.getRoleId());
		roleService.checkRoleCodeExist(null, param.getRoleCode());
		roleService.checkRoleNameExist(null, param.getRoleName());
		roleService.addRole(param);
		return R.ok();
	}

	/**
	 * 修改角色
	 *
	 * @param param
	 * @return
	 */
	@ApiOperation("修改角色")
	@Log(title = "角色管理", businessType = BusinessType.UPDATE)
//	@PreAuthorize("hasAuthority('sys:role:edit')")
	@PutMapping
	public R<?> modify(@RequestBody RoleParam param) {
		roleService.checkRoleNotAdmin(param.getRoleId());
		roleService.checkRoleCodeExist(param.getRoleId(), param.getRoleCode());
		roleService.checkRoleNameExist(param.getRoleId(), param.getRoleName());
		roleService.modifyRole(param);
		return R.ok();
	}

	/**
	 * 修改保存数据权限
	 *
	 * @param param
	 * @return
	 */
	@ApiOperation("修改保存数据权限")
//	@PreAuthorize("hasAuthority('sys:role:edit')")
	@Log(title = "角色管理", businessType = BusinessType.UPDATE)
	@PutMapping("/dataScope")
	public R<?> dataScope(@RequestBody RoleParam param) {
		roleService.checkRoleNotAdmin(param.getRoleId());
		roleService.authDataScope(param);
		return R.ok();
	}

	/**
	 * 状态修改
	 *
	 * @param role
	 * @return
	 */
	@ApiOperation("状态修改")
//	@PreAuthorize("hasAuthority('sys:role:changeStatus')")
	@Log(title = "角色管理", businessType = BusinessType.UPDATE)
	@PutMapping("/changeStatus")
	public R<?> changeStatus(@RequestBody SysRole role) {
		roleService.checkRoleNotAdmin(role.getRoleId());
		roleService.updateById(role);
		return R.ok();
	}

	/**
	 * 删除一个或多个角色
	 *
	 * @param roleIds
	 * @return
	 */
	@ApiOperation("删除一个或多个角色")
//	@PreAuthorize("hasAuthority('sys:role:remove')")
	@Log(title = "角色管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{roleIds}")
	public R<?> remove(@PathVariable List<Long> roleIds) {
		roleService.removeRoles(roleIds);
		return R.ok();
	}

}
