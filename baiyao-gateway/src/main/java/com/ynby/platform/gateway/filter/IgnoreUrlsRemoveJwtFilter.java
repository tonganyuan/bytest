package com.ynby.platform.gateway.filter;

import cn.hutool.core.util.StrUtil;
import com.ynby.platform.common.core.constant.CommonConstants;
import com.ynby.platform.gateway.config.SwaggerProviderConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;


/**
 * @author gaozhenyu
 */
@Slf4j
public class IgnoreUrlsRemoveJwtFilter implements WebFilter {

	private List<String> whitePaths;

	private SwaggerProviderConfig swaggerProviderConfig;

	public IgnoreUrlsRemoveJwtFilter(List<String> whitePaths, SwaggerProviderConfig swaggerProviderConfig) {
		this.whitePaths = whitePaths;
		this.swaggerProviderConfig = swaggerProviderConfig;
		this.whitePaths.addAll(swaggerProviderConfig.getSystemWhitePaths());
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		ServerHttpRequest request = exchange.getRequest();
		HttpHeaders headers = request.getHeaders();
		URI uri = request.getURI();
		String basicToken = headers.getFirst(HttpHeaders.AUTHORIZATION);
		PathMatcher pathMatcher = new AntPathMatcher();
		//白名单路径移除JWT请求头
		for (String ignoreUrl : whitePaths) {
			if (pathMatcher.match(ignoreUrl, uri.getPath())) {
				if (!StrUtil.startWith(basicToken, CommonConstants.BASIC_TOKEN_PRE)) {
					request = exchange.getRequest().mutate().header("Authorization", "").build();
				}
				exchange = exchange.mutate().request(request).build();
				return chain.filter(exchange);
			}
		}
		return chain.filter(exchange);
	}


}
