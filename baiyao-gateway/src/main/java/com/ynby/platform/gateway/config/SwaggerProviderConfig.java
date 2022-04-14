package com.ynby.platform.gateway.config;

import com.google.common.collect.Lists;
import com.ynby.platform.common.core.constant.CommonConstants;
import lombok.AllArgsConstructor;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.support.NameUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * swagger文档
 * <p>
 *
 * @author lianghui
 * @date 2021年06月24日 9:47
 */
@Primary
@Component
@AllArgsConstructor
public class SwaggerProviderConfig implements SwaggerResourcesProvider {

	private final RouteLocator routeLocator;

	private final GatewayProperties gatewayProperties;

	/**
	 * 获取系统白名单
	 *
	 * @return
	 */
	public List<String> getSystemWhitePaths() {
		List<String> paths = Lists.newArrayList();
		List<String> locationList = getLocation();
		locationList.forEach(location -> {
			CommonConstants.SWAGGER_WHITES.forEach(swaggerWhite -> {
				paths.add(location + swaggerWhite);
			});
		});
		paths.addAll(CommonConstants.SWAGGER_WHITES);
		return paths;
	}

	/**
	 * swagger路由
	 *
	 * @return
	 */
	public List<String> getLocation() {
		List<String> locationList = new ArrayList<>();
		//获取请求的Path路径集合
		gatewayProperties.getRoutes().forEach(routeDefinition -> {
			routeDefinition.getPredicates().forEach(predicateDefinition -> {
				locationList.add(predicateDefinition.getArgs().get(NameUtils.GENERATED_NAME_PREFIX + "0").replace("/**", ""));
			});
		});
		return locationList;
	}

	@Override
	public List<SwaggerResource> get() {
		List<SwaggerResource> resources = new ArrayList<>();
		List<String> routes = new ArrayList<>();
		//取出gateway的route
		routeLocator.getRoutes().subscribe(route -> routes.add(route.getId()));
		//结合配置的route-路径(Path)，和route过滤，只获取有效的route节点
		gatewayProperties.getRoutes().stream().filter(routeDefinition -> routes.contains(routeDefinition.getId()))
			.forEach(routeDefinition -> routeDefinition.getPredicates().stream()
				.filter(predicateDefinition -> ("Path").equalsIgnoreCase(predicateDefinition.getName()))
				.forEach(predicateDefinition -> resources.add(swaggerResource(routeDefinition.getId(),
					predicateDefinition.getArgs().get(NameUtils.GENERATED_NAME_PREFIX + "0")
						.replace("/**", CommonConstants.SWAGGER2URL)))));
		return resources;
	}

	private SwaggerResource swaggerResource(String name, String location) {
		SwaggerResource swaggerResource = new SwaggerResource();
		swaggerResource.setName(name);
		swaggerResource.setLocation(location);
		swaggerResource.setSwaggerVersion("2.0");
		return swaggerResource;
	}

}
