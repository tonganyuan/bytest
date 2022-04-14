package com.ynby.platform.operation.api.pojo.vo;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author gaozhenyu
 * @Copyright 合肥
 * @Description
 * @date 2020-06-05 11:25
 */
@Data
public class DeptTreeVO {

	@ApiModelProperty(value = "部门id")
	private Long deptId;

	@ApiModelProperty(value = "部门名称")
	private String name;

	@ApiModelProperty(value = "上级部门id")
	private Long parentId;

	@ApiModelProperty(value = "祖籍列表")
	private String ancestors;

	@ApiModelProperty(value = "负责人")
	private String leader;

	@ApiModelProperty(value = "联系电话")
	private String phone;

	@ApiModelProperty(value = "排序")
	private Integer sort;

	@ApiModelProperty(value = "部门状态（1正常 0停用）")
	private Integer status;

	private LocalDateTime gmtCreate;

	private LocalDateTime gmtModified;

	private List<DeptTreeVO> children;

	public DeptTreeVO() {
		this.children = Lists.newArrayList();
	}

	public void addNode(DeptTreeVO node) {
		this.children.add(node);
	}

}
