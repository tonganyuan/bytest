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
 * 操作日志记录
 * </p>
 *
 * @author lianghui
 * @since 2021-04-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="SysOperLog对象", description="操作日志记录")
public class SysOperLog extends Model<SysOperLog> {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "日志主键")
	@TableId(value = "oper_id", type = IdType.AUTO)
	private Long operId;

	@ApiModelProperty(value = "账户名称，即用户名")
	private String username;

	@ApiModelProperty(value = "姓名")
	private String fullname;

	@ApiModelProperty(value = "主机地址")
	private String operIp;

	@ApiModelProperty(value = "模块标题")
	private String title;

	@ApiModelProperty(value = "业务类型（0其它 1新增 2修改 3删除）")
	private Integer businessType;

	@ApiModelProperty(value = "浏览器")
	private String browser;

	private LocalDateTime gmtCreate;


	@Override
	protected Serializable pkVal() {
		return this.operId;
	}

}
