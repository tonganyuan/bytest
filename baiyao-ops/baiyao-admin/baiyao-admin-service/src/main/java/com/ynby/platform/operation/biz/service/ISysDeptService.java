package com.ynby.platform.operation.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ynby.platform.operation.api.pojo.entity.SysDept;
import com.ynby.platform.operation.api.pojo.vo.DeptTreeVO;
import com.ynby.platform.operation.api.pojo.vo.TreeSelectVO;

import java.util.List;

/**
 * <p>
 * 部门表(暂不用) 服务类
 * </p>
 *
 * @author gaozhenyu
 * @since 2021-01-06
 */
public interface ISysDeptService extends IService<SysDept> {

	/**
	 * 创建部门树
	 *
	 * @param deptId
	 * @param deptName
	 * @return
	 */
	List<DeptTreeVO> makeTree(Long deptId, String deptName);

	/**
	 * 根据角色ID查询部门树信息
	 *
	 * @param roleId 角色ID
	 * @return 选中部门列表
	 */
	List<Long> selectDeptListByRoleId(Long roleId);

	/**
	 * 构建前端所需要下拉树结构
	 *
	 * @param depts 部门列表
	 * @return 下拉树结构列表
	 */
	List<TreeSelectVO> buildDeptTreeSelect(List<SysDept> depts);

}
