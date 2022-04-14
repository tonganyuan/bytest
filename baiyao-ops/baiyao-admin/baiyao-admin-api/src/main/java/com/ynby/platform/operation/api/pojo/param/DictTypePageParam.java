package com.ynby.platform.operation.api.pojo.param;

import com.ynby.platform.common.core.pojo.BasePage;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author gaozhenyu
 * @Copyright 合肥
 * @Description
 * @date 2020-06-01 18:31
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DictTypePageParam extends BasePage {

	@ApiModelProperty(value = "字典名称")
	private String dictName;

	@ApiModelProperty(value = "字典类型")
	private String dictType;

}
