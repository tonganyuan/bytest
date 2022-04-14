package com.ynby.platform.operation.biz.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ynby.platform.operation.api.pojo.entity.SysRole;

import java.util.Set;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author gaozhenyu
 * @since 2021-01-06
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

	/**
	 * 通过用户ID查询角色信息
	 * @param userId
	 * @return
	 */
	Set<SysRole> listRolesByUserId(Long userId);

}
