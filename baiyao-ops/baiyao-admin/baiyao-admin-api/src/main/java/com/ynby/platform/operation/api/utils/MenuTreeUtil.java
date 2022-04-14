package com.ynby.platform.operation.api.utils;

import cn.hutool.core.collection.CollUtil;
import com.google.common.collect.Lists;
import com.ynby.platform.operation.api.pojo.vo.MenuTreeVO;

import java.util.List;
import java.util.Objects;

/**
 * @author gaozhenyu
 * @Copyright 合肥
 * @Description
 * @date 2021/1/14 11:10 上午
 */
public class MenuTreeUtil {

	/**
	 * 构建部门树
	 *
	 * @param menus
	 * @return
	 */
	public static List<MenuTreeVO> buildMenuTree(List<MenuTreeVO> menus) {
		List<MenuTreeVO> returnList = Lists.newArrayList();
		List<Long> tempList = Lists.newArrayList();
		for (MenuTreeVO menu : menus) {
			tempList.add(menu.getMenuId());
		}
		for (MenuTreeVO menu : menus) {
			// 如果是顶级节点, 遍历该父节点的所有子节点
			if (!tempList.contains(menu.getParentId())) {
				recursionFn(menus, menu);
				returnList.add(menu);
			}
		}
		if (returnList.isEmpty()) {
			returnList = menus;
		}
		return returnList;
	}


	/**
	 * 递归列表
	 */
	private static void recursionFn(List<MenuTreeVO> list, MenuTreeVO t) {
		// 得到子节点列表
		List<MenuTreeVO> childList = getChildList(list, t);
		t.setChildren(childList);
		for (MenuTreeVO tChild : childList) {
			if (hasChild(list, tChild)) {
				recursionFn(list, tChild);
			}
		}
	}


	/**
	 * 得到子节点列表
	 */
	private static List<MenuTreeVO> getChildList(List<MenuTreeVO> list, MenuTreeVO t) {
		List<MenuTreeVO> treeNodes = Lists.newArrayList();
		for (MenuTreeVO n : list) {
			if (!Objects.isNull(n.getParentId()) && n.getParentId().longValue() == t.getMenuId().longValue()) {
				treeNodes.add(n);
			}
		}
		return treeNodes;
	}

	/**
	 * 判断是否有子节点
	 */
	private static boolean hasChild(List<MenuTreeVO> list, MenuTreeVO t) {
		return CollUtil.isNotEmpty(getChildList(list, t));
	}

}
