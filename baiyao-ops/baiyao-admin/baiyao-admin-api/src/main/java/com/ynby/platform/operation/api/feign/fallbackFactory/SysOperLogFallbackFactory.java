package com.ynby.platform.operation.api.feign.fallbackFactory;

import com.ynby.platform.common.core.enums.OperaEnum;
import com.ynby.platform.common.core.pojo.R;
import com.ynby.platform.operation.api.feign.ISysOperLogClient;
import com.ynby.platform.operation.api.pojo.entity.SysOperLog;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author gaozhenyu
 * @Copyright 合肥
 * @Description
 * @date 2021/1/6 6:03 下午
 */
@Component
@Slf4j
public class SysOperLogFallbackFactory implements FallbackFactory<ISysOperLogClient> {

	@Override
	public ISysOperLogClient create(Throwable throwable) {
		String reason = throwable.getMessage();
		return new ISysOperLogClient() {
			@Override
			public R<String> saveLog(SysOperLog sysOperLog) {
				return R.failed(OperaEnum.EX_1101, String.format("%s:%s", OperaEnum.EX_1101.getMsg(), reason));

			}
		};

	}

}
