package com.ynby.platform.operation.api.feign;

import com.ynby.platform.common.config.http.FeignLogConfig;
import com.ynby.platform.common.core.constant.ServiceNameConstants;
import com.ynby.platform.common.core.pojo.R;
import com.ynby.platform.operation.api.feign.fallbackFactory.SysUserFallbackFactory;
import com.ynby.platform.operation.api.pojo.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author gaozhenyu
 * @Copyright 合肥
 * @Description
 * @date 2021/1/6 5:33 下午
 */
@FeignClient(
	contextId = "ISysUserClient",
	value= ServiceNameConstants.OPERATION,
	configuration = FeignLogConfig.class,
	fallbackFactory = SysUserFallbackFactory.class
)
public interface ISysUserClient {

	/**
	 * 根据用户名或者用户全量信息
	 *
	 * @param username
	 * @return
	 */
	@GetMapping("/user/info/{username}")
	R<UserDTO> userInfo(@PathVariable(value = "username") String username);

}
