package com.ynby.platform.gateway.filter;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.sun.istack.Nullable;
import com.ynby.platform.common.core.constant.CommonConstants;
import com.ynby.platform.common.core.enums.GatewayEnums;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * <p>
 * 日志过滤器
 * <p>
 *
 * @author lianghui
 * @date 2021年06月24日 10:05
 */
@Slf4j
@Component
public class LogFilter implements GlobalFilter, Ordered {

	private static final String patternStr = "\\s*|\t|\r|\n";
	private static final String strDateFormat = "yyyy-MM-dd HH:mm:ss";
	private static final String format = "[requestId:%s] [requestStatus:%s] [requestType:%s] [ip:%s] [requestUri:%s] [startTime:%s] [requestBody:%s] [duration:%sms] [response:%s]";

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		long startTime = System.currentTimeMillis();
		ServerHttpRequest serverHttpRequest = exchange.getRequest();
		String requestType = serverHttpRequest.getMethodValue().toUpperCase();
		HttpHeaders headers = serverHttpRequest.getHeaders();
		MediaType mediaType = headers.getContentType();

		// 设置X-Request-Id
		AtomicReference<String> requestId = new AtomicReference<>(UUID.randomUUID().toString().replaceAll("\\-", ""));
		Consumer<HttpHeaders> httpHeadersConsumer = httpHeaders -> {
			String headerRequestId = serverHttpRequest.getHeaders().getFirst(CommonConstants.REQUEST_ID);
			if (StringUtils.isBlank(headerRequestId)) {
				httpHeaders.set(CommonConstants.REQUEST_ID, requestId.get());
			} else {
				requestId.set(headerRequestId);
			}
			httpHeaders.set(CommonConstants.START_TIME_KEY, String.valueOf(startTime));
		};
		ServerHttpRequest shr = exchange.getRequest().mutate().headers(httpHeadersConsumer).build();
		exchange.mutate().request(shr).build();
		ServerHttpResponse serverHttpResponse = exchange.getResponse();
		serverHttpResponse.beforeCommit(() -> {
			serverHttpResponse.getHeaders().add(CommonConstants.REQUEST_ID, requestId.get());
			return Mono.empty();
		});

		//处理请求体
		String reqBody = "";
		if (HttpMethod.POST.name().equals(requestType) || HttpMethod.PUT.name().equals(requestType)) {
			if (Objects.nonNull(mediaType) && isUploadFile(mediaType)) {
				reqBody = "上传文件";
			} else {
				reqBody = exchange.getAttributeOrDefault(CommonConstants.CACHE_POST_BODY, "");
			}
		}
		String finalReqBody = reqBody;
		ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(serverHttpResponse) {
			@Override
			public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
				if (body instanceof Flux) {
					return super.writeWith(
						DataBufferUtils.join(body)
							.doOnNext(dataBuffer -> {
								//请求返回内容
								String respBody = dataBuffer.toString(StandardCharsets.UTF_8);
								//计算耗时
								String duration = new DecimalFormat(".00").format(System.currentTimeMillis() - startTime);
								String status = Float.valueOf(duration) > CommonConstants.REQUEST_TIME_OUT ? CommonConstants.TIMEOUT : CommonConstants.NORMAL;
								//打印日志
								log.info(String.format(format, requestId.get(), status, requestType, getIpAddress(serverHttpRequest), serverHttpRequest.getURI()
									, new SimpleDateFormat(strDateFormat).format(new Date(startTime)), formatStr(finalReqBody), duration, formatStr(respBody)));
							})
					);
				}
				return super.writeWith(body);
			}
		};
		return chain.filter(exchange.mutate().request(shr).response(decoratedResponse).build());
	}

	/**
	 * 去掉空格,换行和制表符
	 *
	 * @param str
	 * @return
	 */
	private String formatStr(String str) {
		if (str != null && str.length() > 0) {
			Pattern p = Pattern.compile(patternStr);
			Matcher m = p.matcher(str);
			return m.replaceAll("");
		}
		return str;
	}

	/**
	 * 获取ip
	 *
	 * @param request
	 * @return
	 */
	public static String getIpAddress(ServerHttpRequest request) {
		HttpHeaders headers = request.getHeaders();
		String ip = headers.getFirst("x-forwarded-for");
		if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个ip值，第一个ip才是真实ip
			if (ip.contains(",")) {
				ip = ip.split(",")[0];
			}
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = headers.getFirst("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = headers.getFirst("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = headers.getFirst("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = headers.getFirst("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = headers.getFirst("X-Real-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddress().getAddress().getHostAddress();
		}
		return ip;
	}

	/**
	 * 判断是否是上传文件
	 *
	 * @param mediaType MediaType
	 * @return Boolean
	 */
	public static boolean isUploadFile(@Nullable MediaType mediaType) {
		if (Objects.isNull(mediaType)) {
			return false;
		}
		String mediaTypeStr = StrUtil.subBefore(mediaType.toString(), ";", false);
		return MediaType.MULTIPART_FORM_DATA_VALUE.equals(mediaTypeStr)
			|| MediaType.IMAGE_GIF_VALUE.equals(mediaTypeStr)
			|| MediaType.IMAGE_JPEG_VALUE.equals(mediaTypeStr)
			|| MediaType.IMAGE_PNG_VALUE.equals(mediaTypeStr)
			|| "multipart/mixed".equals(mediaTypeStr);
	}

	/**
	 * 过滤器顺序
	 *
	 * @return
	 */
	@Override
	public int getOrder() {
		return GatewayEnums.FilterOrderEnum.LOG_FILTER.getSort();
	}
}
