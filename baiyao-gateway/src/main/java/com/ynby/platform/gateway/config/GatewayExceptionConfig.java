package com.ynby.platform.gateway.config;

import com.google.common.collect.Lists;
import com.ynby.platform.gateway.handler.GatewayExceptionHandler;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.result.view.ViewResolver;

import java.util.List;

/**
 * @author gaozhenyu
 * @Copyright 合肥
 * @Description
 * @date 2021/7/31 10:44 上午
 */
@Configuration
public class GatewayExceptionConfig {

	@Primary
	@Bean
	@Order(Ordered.HIGHEST_PRECEDENCE)
	public GatewayExceptionHandler errorWebExceptionHandler(ObjectProvider<List<ViewResolver>> viewResolversProvider,
															ServerCodecConfigurer serverCodecConfigurer) {
		GatewayExceptionHandler gatewayExceptionHandler = new GatewayExceptionHandler();
		gatewayExceptionHandler.setViewResolvers(viewResolversProvider.getIfAvailable(Lists::newArrayList));
		gatewayExceptionHandler.setMessageWriters(serverCodecConfigurer.getWriters());
		gatewayExceptionHandler.setMessageReaders(serverCodecConfigurer.getReaders());
		return gatewayExceptionHandler;
	}

}
