package com.ynby.platform.operation.api.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * OAuth2客户端明细表
 * </p>
 *
 * @author ynby
 * @since 2021-07-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="OauthClientDetails对象", description="OAuth2客户端明细表")
public class OauthClientDetails extends Model<OauthClientDetails> {


	private static final long serialVersionUID = -6276907006068232764L;

	@ApiModelProperty(value = "主键")
	@TableId(value = "id", type = IdType.AUTO)
	private Long id;

    @ApiModelProperty(value = "产品id")
    private Long productId;

    @ApiModelProperty(value = "客户端id")
    private String clientId;

    @ApiModelProperty(value = "授权可以访问的资源服务器集合")
    private String resourceIds;

    @ApiModelProperty(value = "客户端密钥")
    private String clientSecret;

    @ApiModelProperty(value = "作用域")
    private String scope;

    @ApiModelProperty(value = "授权方式")
    private String authorizedGrantTypes;

    @ApiModelProperty(value = "重定向uri")
    private String webServerRedirectUri;

    @ApiModelProperty(value = "授权")
    private String authorities;

    @ApiModelProperty(value = "token有效时长(ms)")
    private Integer accessTokenValidity;

    @ApiModelProperty(value = "r-token有效时长(ms)")
    private Integer refreshTokenValidity;

    private String additionalInformation;

    private String autoapprove;

    private LocalDateTime gmtCreate;

    private LocalDateTime gmtModified;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
