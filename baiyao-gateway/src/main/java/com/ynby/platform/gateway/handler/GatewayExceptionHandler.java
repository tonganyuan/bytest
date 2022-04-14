package com.ynby.platform.gateway.handler;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.ynby.platform.common.core.enums.OperaEnum;
import com.ynby.platform.common.core.pojo.R;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.MediaType;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.reactive.result.view.ViewResolver;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.ConnectException;
import java.util.List;

/**
 * @author lengleng
 * @date 2020/5/23
 * <p>
 * 网关异常通用处理器，只作用在webflux 环境下 , 优先级低
 */
@Slf4j
@Data
public class GatewayExceptionHandler implements ErrorWebExceptionHandler {

	private List<HttpMessageReader<?>> messageReaders = Lists.newArrayList();
	private List<HttpMessageWriter<?>> messageWriters = Lists.newArrayList();
	private List<ViewResolver> viewResolvers = Lists.newArrayList();

	@Override
	public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
		ServerHttpResponse response = exchange.getResponse();
		if (response.isCommitted()) {
			return Mono.error(ex);
		}
		// header set_json响应
		response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
		//是否响应状态异常
		if (ex instanceof ResponseStatusException) {
			response.setStatusCode(((ResponseStatusException) ex).getStatus());
		}
		return response.writeWith(Mono.fromSupplier(() -> adapterException(response, ex)));
	}

	/**
	 * 网关统一响应异常特定处理
	 *
	 * @param response
	 * @param ex
	 * @return
	 */
	private DataBuffer adapterException(ServerHttpResponse response, Throwable ex) {
		DataBufferFactory bufferFactory = response.bufferFactory();
		R<String> resR;
		if (ex instanceof NotFoundException) {
			// 服务已下线一段时间，或者未启动
			resR = R.normal(OperaEnum.EX_503.getCode(), StrUtil.format("{} {}", OperaEnum.EX_503.getMsg(), ((NotFoundException) ex).getReason()));
		} else if (ex instanceof ConnectException) {
			// 服务刚下线，但注册中心尚未将该服务推出路由表，导致socket连接异常
			resR = R.normal(OperaEnum.EX_503_1);
		} else if (ex instanceof ResponseStatusException) {
			// 网关请求超时
			resR = R.normal(OperaEnum.EX_504.getCode(), StrUtil.format("{} {}", OperaEnum.EX_504.getMsg(), ((ResponseStatusException) ex).getReason()));
		} else {
			resR = R.failed(ex.getMessage());
		}
		return bufferFactory.wrap(JSON.toJSONBytes(resR));
	}

}

