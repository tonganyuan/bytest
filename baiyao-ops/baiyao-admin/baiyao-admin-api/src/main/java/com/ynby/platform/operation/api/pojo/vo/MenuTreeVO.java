package com.ynby.platform.operation.api.pojo.vo;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author gaozhenyu
 * @Copyright 合肥
 * @Description
 * @date 2020-06-05 11:25
 */
@Data
public class MenuTreeVO {

	/**
	 * 是否隐藏路由，当设置 true 的时候该路由不会再侧边栏出现
	 * 暂未设置
	 */
	@ApiModelProperty(value = "菜单id")
	private Boolean hidden;

	@ApiModelProperty(value = "菜单id")
	private Long menuId;

	@ApiModelProperty(value = "元数据")
	private MetaVO meta;

	@ApiModelProperty(value = "菜单父id")
	private Long parentId;

	@ApiModelProperty(value = "权限标识")
	private String perms;

	@ApiModelProperty(value = "前端跳转URL")
	private String path;

	@ApiModelProperty(value = "保持keepAlive")
	private String name;

	@ApiModelProperty(value = "组件地址")
	private String component;

	@ApiModelProperty(value = "当你一个路由下面的 children 声明的路由大于1个时，自动会变成嵌套的模式--如组件页面")
	private Boolean alwaysShow;

	@ApiModelProperty(value = "类型   0：目录   1：菜单   2：按钮")
	private int type;

	@ApiModelProperty(value = "排序")
	private Integer sort;

	@ApiModelProperty(value = "菜单状态（1正常 0停用）")
	private Integer status;

    private List<MenuTreeVO> children;

    public MenuTreeVO() {
        this.children = Lists.newArrayList();
    }

    public void addNode(MenuTreeVO node) {
        this.children.add(node);
    }

}
