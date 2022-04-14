package com.ynby.platform.operation.api.pojo.vo;

import cn.hutool.core.collection.CollUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Treeselect树结构实体类
 *
 * @author ruoyi
 */
@Data
@NoArgsConstructor
public class TreeSelectVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 节点ID
	 */
	private Long id;

	/**
	 * 节点名称
	 */
	private String label;

	/**
	 * 子节点
	 */
	private List<TreeSelectVO> children;


	public TreeSelectVO(DeptTreeVO dept) {
		this.id = dept.getDeptId();
		this.label = dept.getName();
		List<TreeSelectVO> treeSelects = dept.getChildren().stream().map(TreeSelectVO::new).collect(Collectors.toList());
		this.children = CollUtil.isEmpty(treeSelects) ? null : treeSelects;
	}

	public TreeSelectVO(MenuTreeVO menu) {
		this.id = menu.getMenuId();
		this.label = menu.getMeta().getTitle();
		List<TreeSelectVO> treeSelects = menu.getChildren().stream().map(TreeSelectVO::new).collect(Collectors.toList());
		this.children = CollUtil.isEmpty(treeSelects) ? null : treeSelects;
	}

}
