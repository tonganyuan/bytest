package com.ynby.platform.operation.api.pojo.vo;

import lombok.Data;

/**
 * 路由显示信息
 *
 * @author gaozhenyu
 */
@Data
public class MetaVO {
	/**
	 * 设置该路由在侧边栏和面包屑中展示的名字
	 */
	private String title;

	/**
	 * 设置该路由的图标，对应路径src/assets/icons/svg
	 */
	private String icon;

	/**
	 * 设置为true，则不会被 <keep-alive>缓存
	 */
	private boolean noCache = true;

}
