package com.ynby.platform.operation.api.utils;


import cn.hutool.core.collection.CollUtil;
import com.ynby.platform.operation.api.pojo.vo.MenuTreeVO;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author gaozhenyu
 * @Copyright 合肥
 * @Description
 * @date 2020-06-05 11:26
 */
public class RouterTreeUtil {

	/**
	 * 把菜单转换成树形结构
	 *
	 * @param menus
	 * @return
	 */
	public static List<MenuTreeVO> makeTree(List<MenuTreeVO> menus) {
		//根节点
		List<MenuTreeVO> rootMenus = new ArrayList<>();
		for (MenuTreeVO menu : menus) {
			if (null == menu.getParentId() || menu.getParentId() == 0) {
				rootMenus.add(menu);
			}
		}
		rootMenus.sort(order());
		for (MenuTreeVO node : rootMenus) {
			List<MenuTreeVO> childList = getChild(node, menus);
			node.setChildren(childList);
			if (CollUtil.isNotEmpty(childList)) {
				node.setAlwaysShow(true);
			}
		}
		return rootMenus;
	}

	/**
	 * 递归查找子节点
	 *
	 * @param node
	 * @param allMenu
	 * @return
	 */
	private static List<MenuTreeVO> getChild(MenuTreeVO node, List<MenuTreeVO> allMenu) {
		//子菜单
		List<MenuTreeVO> childList = new ArrayList<>();
		for (MenuTreeVO nav : allMenu) {
			if (null != nav.getParentId() && nav.getParentId().equals(node.getMenuId())) {
				nav.setPath(node.getPath() + nav.getPath());
				childList.add(nav);
			}
		}
		// 递归查询
		for (MenuTreeVO nav : childList) {
			nav.setChildren(getChild(nav, allMenu));
		}
		childList.sort(order());
		if (childList.size() == 0) {
			return new ArrayList<>();
		}
		return childList;
	}

	/**
	 * 排序
	 *
	 * @return
	 */
	private static Comparator<MenuTreeVO> order() {
		return (o1, o2) -> {
			if (!o1.getSort().equals(o2.getSort())) {
				return o1.getSort() - o2.getSort();
			}
			return 0;
		};
	}
}
