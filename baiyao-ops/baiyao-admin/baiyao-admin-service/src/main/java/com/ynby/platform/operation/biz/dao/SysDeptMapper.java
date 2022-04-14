package com.ynby.platform.operation.biz.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ynby.platform.operation.api.pojo.entity.SysDept;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 部门表(暂不用) Mapper 接口
 * </p>
 *
 * @author gaozhenyu
 * @since 2021-01-06
 */
public interface SysDeptMapper extends BaseMapper<SysDept> {

	/**
	 * 根据角色ID查询部门树信息
	 *
	 * @param roleId
	 * @param deptCheckStrictly
	 * @return
	 */
	List<Long> selectMenuListByRoleId(@Param("roleId") Long roleId, @Param("deptCheckStrictly") boolean deptCheckStrictly);
}
