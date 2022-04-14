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
 * 字典数据表
 * </p>
 *
 * @author gaozhenyu
 * @since 2021-01-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "SysDictData对象", description = "字典数据表")
public class SysDictData extends Model<SysDictData> {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "字典数据编码")
	@TableId(value = "dict_code", type = IdType.AUTO)
	private Long dictCode;

	@ApiModelProperty(value = "字典类型")
	private String dictType;

	@ApiModelProperty(value = "字典标签")
	private String dictLabel;

	@ApiModelProperty(value = "字典键值")
	private String dictValue;

	@ApiModelProperty(value = "字典排序")
	private Integer sort;

	@ApiModelProperty(value = "是否默认（Y是 N否）")
	private String defaultFlag;

	@ApiModelProperty(value = "字典状态（1正常 0停用）")
	private Integer status;

	private LocalDateTime gmtCreate;

	private LocalDateTime gmtModified;


	@Override
	protected Serializable pkVal() {
		return this.dictCode;
	}

}
