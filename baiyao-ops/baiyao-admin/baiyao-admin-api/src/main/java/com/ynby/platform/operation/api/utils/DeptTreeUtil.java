package com.ynby.platform.operation.api.utils;

import cn.hutool.core.collection.CollUtil;
import com.google.common.collect.Lists;
import com.ynby.platform.operation.api.pojo.vo.DeptTreeVO;

import java.util.List;
import java.util.Objects;

/**
 * @author gaozhenyu
 * @Copyright 合肥
 * @Description
 * @date 2021/1/14 11:10 上午
 */
public class DeptTreeUtil {

	/**
	 * 构建部门树
	 *
	 * @param depts
	 * @return
	 */
	public static List<DeptTreeVO> buildDeptTree(List<DeptTreeVO> depts) {
		List<DeptTreeVO> returnList = Lists.newArrayList();
		List<Long> tempList = Lists.newArrayList();
		for (DeptTreeVO dept : depts) {
			tempList.add(dept.getDeptId());
		}
		for (DeptTreeVO dept : depts) {
			// 如果是顶级节点, 遍历该父节点的所有子节点
			if (!tempList.contains(dept.getParentId())) {
				recursionFn(depts, dept);
				returnList.add(dept);
			}
		}
		if (returnList.isEmpty()) {
			returnList = depts;
		}
		return returnList;
	}


	/**
	 * 递归列表
	 */
	private static void recursionFn(List<DeptTreeVO> list, DeptTreeVO t) {
		// 得到子节点列表
		List<DeptTreeVO> childList = getChildList(list, t);
		t.setChildren(childList);
		for (DeptTreeVO tChild : childList) {
			if (hasChild(list, tChild)) {
				recursionFn(list, tChild);
			}
		}
	}


	/**
	 * 得到子节点列表
	 */
	private static List<DeptTreeVO> getChildList(List<DeptTreeVO> list, DeptTreeVO t) {
		List<DeptTreeVO> treeNodes = Lists.newArrayList();
		for (DeptTreeVO n : list) {
			if (!Objects.isNull(n.getParentId()) && n.getParentId().longValue() == t.getDeptId().longValue()) {
				treeNodes.add(n);
			}
		}
		return treeNodes;
	}

	/**
	 * 判断是否有子节点
	 */
	private static boolean hasChild(List<DeptTreeVO> list, DeptTreeVO t) {
		return CollUtil.isNotEmpty(getChildList(list, t));
	}

}
