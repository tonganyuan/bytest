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
 * 参数配置表
 * </p>
 *
 * @author gaozhenyu
 * @since 2021-02-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "SysConfig对象", description = "参数配置表")
public class SysConfig extends Model<SysConfig> {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "参数主键")
	@TableId(value = "config_id", type = IdType.AUTO)
	private Long configId;

	@ApiModelProperty(value = "参数名称")
	private String configName;

	@ApiModelProperty(value = "参数键名")
	private String configKey;

	@ApiModelProperty(value = "参数键值")
	private String configValue;

	@ApiModelProperty(value = "系统内置（Y是 N否）")
	private String configType;

	@ApiModelProperty(value = "创建者")
	private String createBy;

	@ApiModelProperty(value = "更新者")
	private String updateBy;

	@ApiModelProperty(value = "备注")
	private String remark;

	private LocalDateTime gmtCreate;

	private LocalDateTime gmtModified;


	@Override
	protected Serializable pkVal() {
		return this.configId;
	}

}
