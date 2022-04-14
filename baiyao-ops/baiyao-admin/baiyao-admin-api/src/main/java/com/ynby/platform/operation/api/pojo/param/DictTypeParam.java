package com.ynby.platform.operation.api.pojo.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author gaozhenyu
 * @Copyright 合肥
 * @Description
 * @date 2020-06-01 18:31
 */
@Data
public class DictTypeParam {

	private Long dictId;

	@NotBlank(message = "字典名称不能为空")
	@ApiModelProperty(value = "字典名称")
	private String dictName;

	@NotBlank(message = "字典类型不能为空")
	@ApiModelProperty(value = "字典类型")
	private String dictType;

	@ApiModelProperty(value = "备注")
	private String remark;

	@ApiModelProperty(value = "字典状态（1正常 0停用）")
	private Integer status;

}
