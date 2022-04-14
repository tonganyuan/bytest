package com.ynby.platform.operation.api.pojo.param;

import com.ynby.platform.common.core.pojo.BasePage;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 日志管理查询入参
 * <p>
 *
 * @Author: lianghui
 * @Date: 2021/4/22/0022 14:08
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class LogParam extends BasePage {

	@ApiModelProperty(value = "用户名")
	private String username;

	@ApiModelProperty(value = "操作时间")
	private String operTime;

	@ApiModelProperty(value = "操作记录id")
	private String actionId;
}
