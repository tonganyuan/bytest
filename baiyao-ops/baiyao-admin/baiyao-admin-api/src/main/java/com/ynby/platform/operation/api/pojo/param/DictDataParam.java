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
public class DictDataParam {

	@ApiModelProperty(value = "字典数据编码")
	private Long dictCode;

	@NotBlank(message = "字典类型不能为空")
	@ApiModelProperty(value = "字典类型")
	private String dictType;

	@NotBlank(message = "字典标签不能为空")
	@ApiModelProperty(value = "字典标签")
	private String dictLabel;

	@NotBlank(message = "字典键值不能为空")
	@ApiModelProperty(value = "字典键值")
	private String dictValue;

	@ApiModelProperty(value = "字典排序")
	private Integer sort;

	@ApiModelProperty(value = "字典状态（1正常 0停用）")
	private Integer status;

}
