package com.ynby.platform.operation.biz.log.service;

import com.ynby.platform.operation.api.feign.ISysOperLogClient;
import com.ynby.platform.operation.api.pojo.entity.SysOperLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 异步调用日志服务
 *
 * @author ruoyi
 */
@Component
public class AsyncLogService {

	@Autowired
	private ISysOperLogClient operLogClient;

	/**
	 * 保存系统日志记录
	 */
	@Async
	public void saveSysLog(SysOperLog sysOperLog) {
		operLogClient.saveLog(sysOperLog);
	}
}
