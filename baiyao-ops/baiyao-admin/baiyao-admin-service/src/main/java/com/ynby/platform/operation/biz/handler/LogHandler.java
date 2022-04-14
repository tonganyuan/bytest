package com.ynby.platform.operation.biz.handler;

import com.ynby.platform.common.core.constant.CommonConstants;
import com.ynby.platform.common.core.utils.IpUtil;
import com.ynby.platform.operation.api.pojo.entity.SysOperLog;
import com.ynby.platform.operation.biz.log.service.AsyncLogService;
import com.ynby.platform.operation.biz.service.ISysUserService;
import cz.mallat.uasparser.OnlineUpdater;
import cz.mallat.uasparser.UASparser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * <p>
 *  日志保存handler
 * <p>
 *
 * @author lianghui
 * @date 2021年06月28日 17:37
 */
@Slf4j
@Component
@EnableAsync
public class LogHandler {

	@Autowired
	private AsyncLogService asyncLogService;

	@Autowired
	private ISysUserService userService;

	@Async
	public void asyncSaveLog(HttpServletRequest request, String username, String title, Integer businessType) {
		try {
			//登录成功，记录一下日志
			SysOperLog sysOperLog = new SysOperLog();
			sysOperLog.setUsername(username);
			sysOperLog.setBrowser(new UASparser(OnlineUpdater.getVendoredInputStream()).parse(request.getHeader(CommonConstants.USER_AGENT)).getUaName());
			sysOperLog.setTitle(title);
			sysOperLog.setBusinessType(businessType);
			sysOperLog.setOperIp(IpUtil.getIpAddr(request));
			asyncLogService.saveSysLog(sysOperLog);
		} catch (IOException e) {
			log.error("保存用户登录日志出现异常");
		}
	}
}
