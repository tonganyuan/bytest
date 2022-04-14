package com.ynby.platform.operation.biz.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ynby.platform.operation.api.pojo.entity.SysOperLog;
import com.ynby.platform.operation.api.pojo.entity.SysUser;
import com.ynby.platform.operation.api.pojo.param.LogParam;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 操作日志记录 Mapper 接口
 * </p>
 *
 * @author gaozhenyu
 * @since 2021-01-18
 */
public interface SysOperLogMapper extends BaseMapper<SysOperLog> {

	/**
	 * 查询操作日志列表
	 *
	 * @param param
	 * @return
	 */
	Page<SysOperLog> selectLogPage(IPage<SysUser> page, @Param("param") LogParam param);
}
