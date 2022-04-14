package com.ynby.platform.operation.api.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p>
 * 操作日志
 * <p>
 *
 * @Author: lianghui
 * @Date: 2021/4/22/0022 15:06
 */
@Data
public class OperLogVO {

	@ApiModelProperty(value = "账户名称，即用户名")
	private String username;

	@ApiModelProperty(value = "姓名")
	private String fullname;

	@ApiModelProperty(value = "主机地址")
	private String operIp;

	@ApiModelProperty(value = "模块标题")
	private String title;

	@ApiModelProperty(value = "业务类型（0其它 1新增 2修改 3删除）")
	private Integer businessType;

	@ApiModelProperty(value = "浏览器")
	private String browser;

	private LocalDateTime gmtCreate;

}
