package com.ynby.platform.operation.biz.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.ynby.platform.operation.api.pojo.entity.SysDept;
import com.ynby.platform.operation.api.pojo.vo.DeptTreeVO;
import com.ynby.platform.operation.api.pojo.vo.TreeSelectVO;
import com.ynby.platform.operation.api.utils.DeptTreeUtil;
import com.ynby.platform.operation.biz.dao.SysDeptMapper;
import com.ynby.platform.operation.biz.service.ISysDeptService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 部门表(暂不用) 服务实现类
 * </p>
 *
 * @author gaozhenyu
 * @since 2021-01-06
 */
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements ISysDeptService {

	/**
	 * 创建部门树
	 *
	 * @param deptId
	 * @param deptName
	 * @return
	 */
	@Override
	public List<DeptTreeVO> makeTree(Long deptId, String deptName) {
		List<DeptTreeVO> deptTrees = Lists.newArrayList();
		List<SysDept> deptTreeList = list(Wrappers.<SysDept>lambdaQuery()
			.eq(deptId != null, SysDept::getDeptId, deptId)
			.like(StrUtil.isNotBlank(deptName), SysDept::getName, deptName)
			.orderByDesc(SysDept::getGmtCreate)
			.orderByAsc(SysDept::getSort)
		);
		if (CollUtil.isNotEmpty(deptTreeList)) {
			deptTrees = deptTreeList.stream().map(dept -> {
				DeptTreeVO deptTreeVO = new DeptTreeVO();
				BeanUtils.copyProperties(dept, deptTreeVO);
				return deptTreeVO;
			}).collect(Collectors.toList());
		}
		return DeptTreeUtil.buildDeptTree(deptTrees);
	}

	/**
	 * 根据角色ID查询部门树信息
	 *
	 * @param roleId 角色ID
	 * @return
	 */
	@Override
	public List<Long> selectDeptListByRoleId(Long roleId) {
		return baseMapper.selectMenuListByRoleId(roleId, false);
	}

	/**
	 * 构建前端所需要下拉树结构
	 *
	 * @param depts 部门列表
	 * @return
	 */
	@Override
	public List<TreeSelectVO> buildDeptTreeSelect(List<SysDept> depts) {
		List<DeptTreeVO> deptVos = Lists.newArrayList();
		if (CollUtil.isNotEmpty(depts)) {
			deptVos = depts.stream().map(this::convertDeptVO).collect(Collectors.toList());
		}
		List<DeptTreeVO> deptTreeVos = DeptTreeUtil.buildDeptTree(deptVos);
		return deptTreeVos.stream().map(TreeSelectVO::new).collect(Collectors.toList());
	}

	/* -------------------------------------- private -------------------------------------- */


	/**
	 * 转换menuVO对象
	 *
	 * @param dept
	 * @return
	 */
	private DeptTreeVO convertDeptVO(SysDept dept) {
		DeptTreeVO deptTreeVO = new DeptTreeVO();
		BeanUtils.copyProperties(dept, deptTreeVO);
		return deptTreeVO;
	}
}
