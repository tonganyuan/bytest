package com.ynby.platform.operation.biz.log.annotation.enums;

import lombok.Getter;

/**
 * 操作人类别
 *
 * @author ruoyi
 */
@Getter
public enum OperatorType {

	OTHER("其它", 0),
	MANAGE("后台用户", 1),
	MOBILE("手机端用户", 1);

	private final String value;
	private final Integer code;

	OperatorType(String value, Integer code) {
		this.value = value;
		this.code = code;
	}
}
