package com.ynby.platform.operation.api.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author : Cao Guangwen
 * @date : 2021/7/30
 * @since : 1.0
 */
@Data
public class SendConfigVO {

	@ApiModelProperty(value = "发送配置id")
	private Long id;

	@ApiModelProperty(value = "发送配置编号")
	private String sendCode;

	@ApiModelProperty(value = "服务商名称")
	private String channelName;

	@ApiModelProperty(value = "商户名称")
	private String merchantName;

	@ApiModelProperty(value = "商户编码")
	private String merchantCode;
	@ApiModelProperty(value = "签名名称")
	private String signName;

	@ApiModelProperty(value = "模板名称")
	private String templateName;

	@ApiModelProperty(value = "模板内容")
	private String templateContent;

	@ApiModelProperty(value = "模板类型(0:验证码,1:通知,2:推广,3:国际/港澳台,4:语音)")
	private Integer templateType;

	@ApiModelProperty(value = "创建人名称")
	private String createName;

	@ApiModelProperty(value = "创建时间")
	private LocalDateTime gmtCreate;

	@ApiModelProperty(value = "用途说明")
	private String sendDesc;

}
