package com.ynby.platform.operation.biz.log.annotation.enums;

import lombok.Getter;

/**
 * 业务操作类型枚举
 *
 * @author lianghui
 */
@Getter
public enum BusinessType {

	/**
	 * 业务操作类型枚举
	 */
	OTHER("其它", 0),
	LIST("列表", 1),
	INSERT("新增", 2),
	UPDATE("修改", 3),
	DELETE("删除", 4),
	GRANT("授权", 5),
	EXPORT("导出", 6),
	IMPORT("导入", 7),
	FORCE("强退", 8),
	GENCODE("生成代码", 9),
	CLEAN("清空数据", 10),
	IN("登录", 11),
	OUT("登出", 12);

	private final String value;
	private final Integer code;

	BusinessType(String value, Integer code) {
		this.value = value;
		this.code = code;
	}
}
