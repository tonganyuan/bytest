package com.ynby.platform.operation.biz.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ynby.platform.operation.api.pojo.dto.QueryMenuDTO;
import com.ynby.platform.operation.api.pojo.entity.SysMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 菜单权限表 Mapper 接口
 * </p>
 *
 * @author gaozhenyu
 * @since 2021-01-06
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

	/**
	 * 根据角色id查询菜单权限
	 *
	 * @param roleId
	 * @return
	 */
	Set<SysMenu> listMenusByRoleId(Long roleId);

	/**
	 * 根据用户查询系统菜单列表
	 *
	 * @param menu
	 * @return
	 */
	List<SysMenu> selectMenuList(QueryMenuDTO menu);

	/**
	 * 根据角色ID查询菜单树信息
	 *
	 * @param roleId
	 * @param menuCheckStrictly 菜单树选择项是否关联显示
	 * @return
	 */
	List<Long> selectMenuListByRoleId(@Param("roleId") Long roleId, @Param("menuCheckStrictly") boolean menuCheckStrictly);
}
