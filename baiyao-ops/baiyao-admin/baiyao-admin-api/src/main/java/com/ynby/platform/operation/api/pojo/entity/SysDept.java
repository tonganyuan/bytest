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
 * 部门表(暂不用)
 * </p>
 *
 * @author gaozhenyu
 * @since 2021-01-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="SysDept对象", description="部门表")
public class SysDept extends Model<SysDept> {

    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "部门id")
	@TableId(value = "dept_id", type = IdType.AUTO)
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

	@ApiModelProperty(value = "有效状态（1正常 0停用）")
	private Boolean status;

	@ApiModelProperty(value = "删除标志（0代表存在 1代表删除）")
	private Boolean delFlag;

	private LocalDateTime gmtCreate;

	private LocalDateTime gmtModified;


	@Override
	protected Serializable pkVal() {
		return this.deptId;
	}

}
