package com.ynby.platform.operation.biz;

import com.ynby.platform.common.config.annotation.EnablePlatformFeignClients;
import com.ynby.platform.common.config.annotation.PlatformBootApplication;
import org.springframework.boot.SpringApplication;

/**
 * @author gaozhenyu
 * @des
 * @date 2020-02-18 14:27
 */
@PlatformBootApplication
@EnablePlatformFeignClients
public class OperationApplication {
	public static void main(String[] args) {
		SpringApplication.run(OperationApplication.class, args);
	}
}
