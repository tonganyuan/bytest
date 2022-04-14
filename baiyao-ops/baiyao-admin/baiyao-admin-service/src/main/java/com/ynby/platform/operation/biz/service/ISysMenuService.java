package com.ynby.platform.operation.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ynby.platform.operation.api.pojo.dto.QueryMenuDTO;
import com.ynby.platform.operation.api.pojo.entity.SysMenu;
import com.ynby.platform.operation.api.pojo.param.MenuParam;
import com.ynby.platform.operation.api.pojo.vo.MenuTreeVO;
import com.ynby.platform.operation.api.pojo.vo.TreeSelectVO;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 菜单权限表 服务类
 * </p>
 *
 * @author gaozhenyu
 * @since 2021-01-06
 */
public interface ISysMenuService extends IService<SysMenu> {

	/**
	 * 根据角色id查询菜单权限
	 *
	 * @param roleId 角色ID
	 * @return 菜单列表
	 */
	Set<SysMenu> getMenuByRoleId(Long roleId);

	/**
	 * 条件查询菜单路由树形列表
	 *
	 * @param dto
	 * @return
	 */
	List<MenuTreeVO> selectRouterListTree(QueryMenuDTO dto);

	/**
	 * 条件查询菜单列表树
	 *
	 * @param dto
	 * @return
	 */
	List<TreeSelectVO> makeTree(QueryMenuDTO dto);

	/**
	 * 条件查询菜单列表
	 *
	 * @param dto
	 * @return
	 */
	List<SysMenu> selectMenuList(QueryMenuDTO dto);

	/**
	 * 新增菜单
	 *
	 * @param param
	 */
	void addMenu(MenuParam param);

	/**
	 * 修改菜单
	 *
	 * @param param
	 */
	void modifyMenu(MenuParam param);

	/**
	 * 删除菜单
	 *
	 * @param menuId
	 */
	void delMenu(Long menuId);

	/**
	 * 根据角色ID查询菜单树信息
	 *
	 * @param roleId
	 * @return
	 */
	List<Long> selectMenuListByRoleId(Long roleId);

	/**
	 * 菜单状态切换
	 *
	 * @param param
	 * @return
	 */
	void modifyStatus(MenuParam param);
}
