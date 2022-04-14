package com.ynby.platform.operation.api.feign;

import com.ynby.platform.common.config.http.FeignLogConfig;
import com.ynby.platform.common.core.constant.ServiceNameConstants;
import com.ynby.platform.common.core.pojo.R;
import com.ynby.platform.operation.api.feign.fallbackFactory.SysUserFallbackFactory;
import com.ynby.platform.operation.api.pojo.entity.SysOperLog;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 日志服务
 *
 * @author ruoyi
 */
@FeignClient(
	contextId = "ISysOperLogClient",
	value = ServiceNameConstants.OPERATION,
	configuration = FeignLogConfig.class,
	fallbackFactory = SysUserFallbackFactory.class
)
public interface ISysOperLogClient {
	/**
	 * 保存系统日志
	 *
	 * @param sysOperLog 日志实体
	 * @return 结果
	 */
	@PostMapping("/operlog")
	R<String> saveLog(@RequestBody SysOperLog sysOperLog);

}
