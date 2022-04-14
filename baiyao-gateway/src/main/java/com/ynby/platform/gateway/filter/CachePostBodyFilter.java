package com.ynby.platform.gateway.filter;

import com.ynby.platform.common.core.constant.CommonConstants;
import com.ynby.platform.common.core.enums.GatewayEnums;
import io.netty.buffer.UnpooledByteBufAllocator;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.DefaultServerRequest;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static com.ynby.platform.gateway.filter.LogFilter.isUploadFile;

/**
 * <p>
 * 缓存POST或PUT请求的body
 * <p>
 *
 * @author lianghui
 * @date 2021年06月25日 15:26
 */
@Component
public class CachePostBodyFilter implements GlobalFilter, Ordered {

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		ServerHttpRequest serverHttpRequest = exchange.getRequest();
		String requestType = serverHttpRequest.getMethodValue();
		HttpHeaders headers = serverHttpRequest.getHeaders();
		MediaType mediaType = headers.getContentType();

		if (Objects.nonNull(mediaType) && isUploadFile(mediaType)) {
			return chain.filter(exchange);
		} else if (HttpMethod.POST.name().equals(requestType) || HttpMethod.PUT.name().equals(requestType)) {
			ServerRequest serverRequest = new DefaultServerRequest(exchange);
			Mono<String> bodyToMono = serverRequest.bodyToMono(String.class).defaultIfEmpty("");
			return bodyToMono.flatMap(body -> {
				exchange.getAttributes().put(CommonConstants.CACHE_POST_BODY, body);
				ServerHttpRequest newRequest = new ServerHttpRequestDecorator(serverHttpRequest) {
					@Override
					public HttpHeaders getHeaders() {
						HttpHeaders httpHeaders = new HttpHeaders();
						httpHeaders.putAll(super.getHeaders());
						httpHeaders.set(HttpHeaders.TRANSFER_ENCODING, "chunked");
						return httpHeaders;
					}
					@Override
					public Flux<DataBuffer> getBody() {
						NettyDataBufferFactory nettyDataBufferFactory = new NettyDataBufferFactory(new UnpooledByteBufAllocator(false));
						DataBuffer bodyDataBuffer = nettyDataBufferFactory.wrap(body.getBytes());
						return Flux.just(bodyDataBuffer);
					}
				};
				return chain.filter(exchange.mutate().request(newRequest).build());
			});
		}
		return chain.filter(exchange);
	}

	/**
	 * 过滤器顺序
	 *
	 * @return
	 */
	@Override
	public int getOrder() {
		return GatewayEnums.FilterOrderEnum.CACHE_POST_BODY_FILTER.getSort();
	}
}
