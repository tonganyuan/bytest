package com.ynby.platform.operation.biz.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.ynby.platform.common.core.constant.CommonConstants;
import com.ynby.platform.common.core.enums.OperationEnums;
import com.ynby.platform.common.core.exception.CommonException;
import com.ynby.platform.operation.api.pojo.dto.QueryMenuDTO;
import com.ynby.platform.operation.api.pojo.entity.SysMenu;
import com.ynby.platform.operation.api.pojo.entity.SysRoleMenu;
import com.ynby.platform.operation.api.pojo.param.MenuParam;
import com.ynby.platform.operation.api.pojo.vo.MenuTreeVO;
import com.ynby.platform.operation.api.pojo.vo.MetaVO;
import com.ynby.platform.operation.api.pojo.vo.TreeSelectVO;
import com.ynby.platform.operation.api.utils.MenuTreeUtil;
import com.ynby.platform.operation.biz.dao.SysMenuMapper;
import com.ynby.platform.operation.biz.service.ISysMenuService;
import com.ynby.platform.operation.biz.service.ISysRoleMenuService;
import com.ynby.platform.operation.biz.service.ISysRoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜单权限表 服务实现类
 * </p>
 *
 * @author gaozhenyu
 * @since 2021-01-06
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {

	@Autowired
	private ISysRoleMenuService roleMenuService;

	@Autowired
	private ISysRoleService roleService;

	/**
	 * 根据角色id查询菜单权限
	 *
	 * @param roleId 角色ID
	 * @return
	 */
	@Override
	public Set<SysMenu> getMenuByRoleId(Long roleId) {
		return baseMapper.listMenusByRoleId(roleId);
	}

	/**
	 * 条件查询菜单路由树形列表
	 *
	 * @return
	 */
	@Override
	public List<MenuTreeVO> selectRouterListTree(QueryMenuDTO dto) {
		List<SysMenu> menus;
		Long userId = dto.getUserId();
		// 超管获取全部菜单
		if (userId.equals(CommonConstants.ADMIN_USER_ID)) {
			menus = list(Wrappers.<SysMenu>lambdaQuery()
				.in(SysMenu::getMenuType, Lists.newArrayList(OperationEnums.MenuType.DIRECTORY.getCode(), OperationEnums.MenuType.MENU.getCode())));
		} else {
			menus = baseMapper.selectMenuList(dto);
		}
		return menus.stream().map(this::convertMenuVO).collect(Collectors.toList());
	}

	/**
	 * 条件查询菜单列表树
	 *
	 * @param dto
	 * @return
	 */
	@Override
	public List<TreeSelectVO> makeTree(QueryMenuDTO dto) {
		List<MenuTreeVO> menuTrees = Lists.newArrayList();
		List<SysMenu> menus = selectMenuList(dto);
		if (CollUtil.isNotEmpty(menus)) {
			menuTrees = menus.stream().map(this::convertMenuVO).collect(Collectors.toList());
		}
		List<MenuTreeVO> menuTreeVOS = MenuTreeUtil.buildMenuTree(menuTrees);
		return menuTreeVOS.stream().map(TreeSelectVO::new).collect(Collectors.toList());
	}

	/**
	 * 条件查询菜单列表
	 *
	 * @param dto
	 * @return
	 */
	@Override
	public List<SysMenu> selectMenuList(QueryMenuDTO dto) {
		List<SysMenu> menus;
		Long userId = dto.getUserId();
		// 超管获取全部菜单
		if (userId.equals(CommonConstants.ADMIN_USER_ID)) {
			menus = list(Wrappers.<SysMenu>lambdaQuery()
				.eq(dto.getStatus() != null, SysMenu::getStatus, dto.getStatus())
				.like(StrUtil.isNotBlank(dto.getMenuName()), SysMenu::getMenuName, dto.getMenuName())
			);
		} else {
			menus = baseMapper.selectMenuList(dto);
		}
		return menus;
	}

	/**
	 * 新增菜单
	 *
	 * @param param
	 */
	@Override
	public void addMenu(MenuParam param) {
		verifyForm(param);
		SysMenu menu = getOne(Wrappers.<SysMenu>lambdaQuery()
			.eq(SysMenu::getMenuName, param.getMenuName())
//			.or()
//			.eq(SysMenu::getPerms, param.getPerms())
//			.or()
//			.eq(SysMenu::getPath, param.getPath())
		);
		if (null != menu) {
			throw new CommonException("菜单名称权限和路径或有重复");
		}
		SysMenu sysMenu = new SysMenu();
		BeanUtils.copyProperties(param, sysMenu);
		save(sysMenu);
	}

	/**
	 * 修改菜单
	 *
	 * @param param
	 */
	@Override
	public void modifyMenu(MenuParam param) {
		verifyForm(param);
		SysMenu menu = getOne(Wrappers.<SysMenu>lambdaQuery()
			.ne(SysMenu::getMenuId, param.getMenuId())
			.and(wrapper ->
				wrapper.eq(SysMenu::getMenuName, param.getMenuName())
//					.or()
//					.eq(StrUtil.isNotBlank(param.getPerms()), SysMenu::getPerms, param.getPerms())
//					.or()
//					.eq(StrUtil.isNotBlank(param.getPath()), SysMenu::getPath, param.getPath())
			));
		if (null != menu) {
			throw new CommonException("菜单名称权限和路径或有重复");
		}
		SysMenu sysMenu = new SysMenu();
		BeanUtils.copyProperties(param, sysMenu);
		updateById(sysMenu);
	}

	/**
	 * 删除菜单
	 *
	 * @param menuId
	 */
	@Override
	public void delMenu(Long menuId) {
		//判断是否有子菜单或按钮
		int hasSubMenuCount = count(new QueryWrapper<SysMenu>().eq("parent_id", menuId));
		if (hasSubMenuCount > 0) {
			throw new CommonException("请先删除子菜单或按钮");
		}
		//删除菜单
		removeById(menuId);
		//删除菜单与角色关联
		roleMenuService.remove(Wrappers.<SysRoleMenu>lambdaQuery().eq(SysRoleMenu::getMenuId, menuId));
	}

	/**
	 * 根据角色ID查询菜单树信息
	 *
	 * @param roleId
	 * @return
	 */
	@Override
	public List<Long> selectMenuListByRoleId(Long roleId) {
		return baseMapper.selectMenuListByRoleId(roleId, false);
	}

	/**
	 * 菜单状态切换
	 *
	 * @param param
	 * @return
	 */
	@Override
	public void modifyStatus(MenuParam param) {
		//递归修改子节点状态
		SysMenu sysMenu = getById(param.getMenuId());
		sysMenu.setStatus(param.getStatus());
		recurseStatus(sysMenu);
	}



	/* -------------------------------------- private -------------------------------------- */

	private void recurseStatus(SysMenu sysMenu) {
		//修改当前menu状态
		updateById(sysMenu);
		//查询是否有下级
		List<SysMenu> sysMenuList = list(Wrappers.<SysMenu>lambdaQuery().eq(SysMenu::getParentId, sysMenu.getMenuId()));
		if (CollUtil.isNotEmpty(sysMenuList)) {
			sysMenuList.forEach(sm -> {
				sm.setStatus(sysMenu.getStatus());
				recurseStatus(sm);
			});
		}
	}

	/**
	 * 转换menuVO对象
	 *
	 * @param menu
	 * @return
	 */
	private MenuTreeVO convertMenuVO(SysMenu menu) {
		MenuTreeVO menuTreeVO = new MenuTreeVO();
		BeanUtils.copyProperties(menu, menuTreeVO);
		MetaVO metaVO = new MetaVO();
		metaVO.setTitle(menu.getMenuName());
		metaVO.setIcon(menu.getIcon());
		if (StrUtil.isBlank(menu.getComponent())) {
			menuTreeVO.setComponent(CommonConstants.LAYOUT);
		}
		menuTreeVO.setMeta(metaVO);
		menuTreeVO.setHidden(menu.getVisible() == 1);
		menuTreeVO.setName(menuTreeVO.getPath());
		return menuTreeVO;
	}

	/**
	 * 验证参数是否正确
	 */
	private void verifyForm(MenuParam param) {
		//上级菜单类型
		String parentType = OperationEnums.MenuType.DIRECTORY.getCode();
		if (null != param.getParentId() && param.getParentId() != 0) {
			SysMenu parentMenu = getById(param.getParentId());
			parentType = parentMenu.getMenuType();
		}
		//目录、菜单
		if (param.getMenuType().equals(OperationEnums.MenuType.DIRECTORY.getCode()) ||
			param.getMenuType().equals(OperationEnums.MenuType.MENU.getCode())) {
			if (!parentType.equals(OperationEnums.MenuType.DIRECTORY.getCode())) {
				throw new CommonException("目录或菜单的上级菜单只能为目录类型");
			}
		}
		//按钮
		if (param.getMenuType().equals(OperationEnums.MenuType.BUTTON.getCode())) {
			if (!parentType.equals(OperationEnums.MenuType.MENU.getCode())) {
				throw new CommonException("按钮的上级菜单只能为菜单类型");
			}
		}
	}
}
