package com.ynby.platform.operation.biz.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ynby.platform.operation.api.pojo.entity.SysOperLog;
import com.ynby.platform.operation.api.pojo.entity.SysUser;
import com.ynby.platform.operation.api.pojo.param.LogParam;
import com.ynby.platform.operation.biz.dao.SysOperLogMapper;
import com.ynby.platform.operation.biz.service.ISysOperLogService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 操作日志记录 服务实现类
 * </p>
 *
 * @author gaozhenyu
 * @since 2021-01-18
 */
@Service
public class SysOperLogServiceImpl extends ServiceImpl<SysOperLogMapper, SysOperLog> implements ISysOperLogService {

	/**
	 * 查询操作日志列表
	 *
	 * @param param
	 * @return
	 */
	@Override
	public Page<SysOperLog> selectLogList(IPage<SysUser> page, LogParam param) {
		return baseMapper.selectLogPage(page, param);
	}
}
