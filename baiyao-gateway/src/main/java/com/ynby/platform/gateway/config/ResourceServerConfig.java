package com.ynby.platform.gateway.config;

import cn.hutool.core.util.ArrayUtil;
import com.ynby.platform.gateway.filter.IgnoreUrlsRemoveJwtFilter;
import com.ynby.platform.gateway.handler.GwServerAuthenticationEntryPoint;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import java.util.List;

/**
 * @author gaozhenyu
 * @des 资源服务器
 * @date 2020-02-05 01:18
 */
@AllArgsConstructor
@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity(order = Ordered.HIGHEST_PRECEDENCE)
public class ResourceServerConfig {
	/**
	 * 路由白名单
	 */
	@Value("${white_paths}")
	private List<String> whitePaths;

	private final GwServerAuthenticationEntryPoint serverAuthenticationEntryPoint;

	private final SwaggerProviderConfig swaggerProviderConfig;

	@Bean
	public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
		//增加swagger白名单
		whitePaths.addAll(swaggerProviderConfig.getSystemWhitePaths());
		http.oauth2ResourceServer(
			jwt -> jwt.jwt()
				.and()
				// 处理未认证
				.authenticationEntryPoint(serverAuthenticationEntryPoint)
		);
		http.addFilterBefore(new IgnoreUrlsRemoveJwtFilter(whitePaths, swaggerProviderConfig), SecurityWebFiltersOrder.AUTHENTICATION);
		http.authorizeExchange(exchanges -> exchanges
			.pathMatchers(ArrayUtil.toArray(whitePaths, String.class)).permitAll()
			.anyExchange().authenticated()
			.and()
			.exceptionHandling()
			// 处理未认证
			.authenticationEntryPoint(serverAuthenticationEntryPoint)
		).csrf(ServerHttpSecurity.CsrfSpec::disable).httpBasic(ServerHttpSecurity.HttpBasicSpec::disable);
		return http.build();
	}


}
