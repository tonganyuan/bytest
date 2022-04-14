package com.ynby.platform.operation.biz.controller.sys;

import com.google.common.collect.Maps;
import com.ynby.platform.common.core.exception.CommonException;
import com.ynby.platform.common.core.pojo.R;
import com.ynby.platform.operation.api.pojo.dto.QueryMenuDTO;
import com.ynby.platform.operation.api.pojo.entity.SysMenu;
import com.ynby.platform.operation.api.pojo.param.MenuParam;
import com.ynby.platform.operation.api.pojo.vo.MenuTreeVO;
import com.ynby.platform.operation.api.pojo.vo.TreeSelectVO;
import com.ynby.platform.operation.api.utils.RouterTreeUtil;
import com.ynby.platform.operation.biz.log.annotation.Log;
import com.ynby.platform.operation.biz.log.annotation.enums.BusinessType;
import com.ynby.platform.operation.biz.service.ISysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;


/**
 * @author gaozhenyu
 * @Copyright 合肥
 * @Description
 * @date 2020-05-29 13:33
 */
@Api(tags = "菜单管理接口")
@Slf4j
@RestController
@RequestMapping("/menu")
public class MenuController {

	@Autowired
	private ISysMenuService menuService;

	/**
	 * 获取路由列表
	 *
	 * @return
	 */
	@ApiOperation("获取路由列表")
	@Log(title = "菜单管理", businessType = BusinessType.LIST)
	@GetMapping("/router")
	public R<List<MenuTreeVO>> router() {
		QueryMenuDTO queryMenuDTO = new QueryMenuDTO();
		queryMenuDTO.setUserId(1L);
		List<MenuTreeVO> menuTree = menuService.selectRouterListTree(queryMenuDTO);
		return R.ok(RouterTreeUtil.makeTree(menuTree));
	}

	/**
	 * 条件查询菜单树形列表
	 *
	 * @return
	 */
	@ApiOperation("条件查询菜单树形列表")
	@Log(title = "菜单管理", businessType = BusinessType.LIST)
	@GetMapping("/treeselect")
	public R<List<TreeSelectVO>> listTree(@RequestParam(required = false) String menuName,
										  @ApiParam("菜单状态（1正常 0停用）") @RequestParam(required = false) Integer status) {
		QueryMenuDTO queryMenuDTO = new QueryMenuDTO();
		queryMenuDTO.setUserId(1L);
		queryMenuDTO.setMenuName(menuName);
		queryMenuDTO.setStatus(status);
		return R.ok(menuService.makeTree(queryMenuDTO));
	}

	/**
	 * 加载对应角色菜单列表树
	 *
	 * @param roleId
	 * @return
	 */
	@ApiOperation("加载对应角色菜单列表树")
	@Log(title = "菜单管理", businessType = BusinessType.LIST)
	@GetMapping(value = "/roleMenuTreeselect/{roleId}")
	public R<Map<String, Object>> listTreeByRoleId(@PathVariable("roleId") Long roleId) {
		QueryMenuDTO queryMenuDTO = new QueryMenuDTO();
		queryMenuDTO.setUserId(1L);
		Map<String, Object> resMap = Maps.newHashMap();
		resMap.put("checkedKeys", menuService.selectMenuListByRoleId(roleId));
		resMap.put("menus", menuService.makeTree(queryMenuDTO));
		return R.ok(resMap);
	}

	/**
	 * 条件查询菜单列表
	 *
	 * @return
	 */
	@ApiOperation("条件查询菜单列表")
	@Log(title = "菜单管理", businessType = BusinessType.LIST)
	//@PreAuthorize("hasAuthority('sys:menu:query')")
	@GetMapping("/list")
	public R<List<SysMenu>> list(@RequestParam(required = false) String menuName,
								 @ApiParam("菜单状态（1正常 0停用）") @RequestParam(required = false) Integer status) {
		QueryMenuDTO queryMenuDTO = new QueryMenuDTO();
		queryMenuDTO.setUserId(1L);
		queryMenuDTO.setMenuName(menuName);
		queryMenuDTO.setStatus(status);
		return R.ok(menuService.selectMenuList(queryMenuDTO));
	}

	/**
	 * 根据id获取菜单详情
	 *
	 * @param menuId
	 * @return
	 */
	@ApiOperation("根据id获取菜单详情")
	@Log(title = "菜单管理", businessType = BusinessType.OTHER)
	@GetMapping("/{menuId}")
	public R<SysMenu> info(@PathVariable Long menuId) {
		SysMenu menuInfo = menuService.getById(menuId);
		return R.ok(Optional.ofNullable(menuInfo).orElseThrow(() -> new CommonException("菜单不存在")));
	}

	/**
	 * 新增菜单
	 *
	 * @param param
	 * @return
	 */
	@ApiOperation("新增菜单")
	//@PreAuthorize("hasAuthority('sys:menu:add')")
	@Log(title = "菜单管理", businessType = BusinessType.INSERT)
	@PostMapping
	public R<?> addMenu(@RequestBody MenuParam param) {
		menuService.addMenu(param);
		return R.ok();
	}

	/**
	 * 修改菜单
	 *
	 * @param param
	 * @return
	 */
	@ApiOperation("修改菜单")
	//@PreAuthorize("hasAuthority('sys:menu:edit')")
	@Log(title = "菜单管理", businessType = BusinessType.UPDATE)
	@PutMapping
	public R<?> modifyMenu(@RequestBody MenuParam param) {
		menuService.modifyMenu(param);
		return R.ok();
	}

	/**
	 * 删除菜单
	 *
	 * @param menuId
	 * @return
	 */
	@ApiOperation("删除菜单")
	//@PreAuthorize("hasAuthority('sys:menu:delete')")
	@Log(title = "菜单管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{menuId}")
	public R<?> delete(@PathVariable Long menuId) {
		menuService.delMenu(menuId);
		return R.ok();
	}

	/**
	 * 菜单状态切换
	 *
	 * @param param
	 * @return
	 */
	@ApiOperation("菜单状态切换")
	@Log(title = "菜单管理", businessType = BusinessType.UPDATE)
	@PutMapping("/modifyStatus")
	public R<?> modifyStatus(@RequestBody MenuParam param) {
		menuService.modifyStatus(param);
		return R.ok();
	}

}

