package com.ynby.platform.gateway.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;

/**
 * @author ml
 * @create 2017-10-27--15:53
 */
@Configuration
public class GatewayRestConfig {

	@Bean
	@LoadBalanced
	@ConditionalOnMissingBean({RestTemplate.class})
	public RestTemplate restTemplate(ClientHttpRequestFactory factory) {
		RestTemplate restTemplate = new RestTemplate(factory);
		//换上fastjson
		List<HttpMessageConverter<?>> httpMessageConverterList = restTemplate.getMessageConverters();
		Iterator<HttpMessageConverter<?>> iterator = httpMessageConverterList.iterator();
		if (iterator.hasNext()) {
			HttpMessageConverter<?> converter = iterator.next();
			//原有的String是ISO-8859-1编码 去掉
			if (converter instanceof StringHttpMessageConverter) {
				iterator.remove();
			}
		}
		httpMessageConverterList.add(new StringHttpMessageConverter(StandardCharsets.UTF_8));
		FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		fastJsonConfig.setSerializerFeatures(
			SerializerFeature.WriteMapNullValue, // 保留值为null字段
			SerializerFeature.WriteNullStringAsEmpty, // 将String类型的null转成""
			SerializerFeature.WriteNullListAsEmpty, // 将List类型的null转成[]
			SerializerFeature.DisableCircularReferenceDetect);

		fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
		httpMessageConverterList.add(0, fastJsonHttpMessageConverter);

		return restTemplate;

	}

	@Bean
	@ConditionalOnMissingBean({ClientHttpRequestFactory.class})
	public ClientHttpRequestFactory requestFactory() {
		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
		factory.setConnectTimeout(6000);
		factory.setReadTimeout(6000);
		return factory;
	}
}
