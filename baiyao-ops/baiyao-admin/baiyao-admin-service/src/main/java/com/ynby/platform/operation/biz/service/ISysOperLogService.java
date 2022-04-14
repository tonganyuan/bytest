package com.ynby.platform.operation.biz.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ynby.platform.operation.api.pojo.entity.SysOperLog;
import com.ynby.platform.operation.api.pojo.entity.SysUser;
import com.ynby.platform.operation.api.pojo.param.LogParam;

/**
 * <p>
 * 操作日志记录 服务类
 * </p>
 *
 * @author gaozhenyu
 * @since 2021-01-18
 */
public interface ISysOperLogService extends IService<SysOperLog> {

	/**
	 * 查询操作日志列表
	 *
	 * @param param
	 * @return
	 */
	Page<SysOperLog> selectLogList(IPage<SysUser> page, LogParam param);
}
