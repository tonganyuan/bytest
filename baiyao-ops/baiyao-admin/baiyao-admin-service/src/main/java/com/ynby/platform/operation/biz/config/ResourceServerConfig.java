package com.ynby.platform.operation.biz.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

/**
 * 资源服务器配置
 *
 * @author gaozhenyu
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JwtAuthenticationConverter jwtAuthenticationConverter;


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.oauth2ResourceServer(
			jwt -> jwt.jwt().jwtAuthenticationConverter(jwtAuthenticationConverter)
		);
		http
			.authorizeRequests(authReq -> authReq
				.mvcMatchers("/**").permitAll()
				.anyRequest().authenticated()).csrf().disable()
			.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
	}

}
