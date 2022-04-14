package com.ynby.platform.operation.biz.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p>
 *
 * <p>
 *
 * @author lianghui
 * @date 2021年09月02日 10:58
 */
@Data
@Component
@ConfigurationProperties(prefix = "sms")
public class SmsConfig {

	/**
	 * 产品域名
	 */
	private String domain;

	/**
	 * key
	 */
	private String accessKeyId;

	/**
	 * 密钥
	 */
	private String accessKeySecret;

	/**
	 * 商户编码
	 */
	private String merchantCode;

	/**
	 * 手机验证码的发送配置编码
	 */
	private String verifyTemplateCode;
}
