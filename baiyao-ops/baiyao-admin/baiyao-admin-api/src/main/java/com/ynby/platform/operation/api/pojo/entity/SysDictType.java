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
 * 字典类型表
 * </p>
 *
 * @author gaozhenyu
 * @since 2021-01-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "SysDictType对象", description = "字典类型表")
public class SysDictType extends Model<SysDictType> {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "字典主键")
	@TableId(value = "dict_id", type = IdType.AUTO)
	private Long dictId;

	@ApiModelProperty(value = "字典名称")
	private String dictName;

	@ApiModelProperty(value = "字典类型")
	private String dictType;

	@ApiModelProperty(value = "排序")
	private Integer sort;

	@ApiModelProperty(value = "备注")
	private String remark;

	@ApiModelProperty(value = "有效状态（1正常 0停用）")
	private Boolean status;

	private LocalDateTime gmtCreate;

	private LocalDateTime gmtModified;


	@Override
	protected Serializable pkVal() {
		return this.dictId;
	}

}
