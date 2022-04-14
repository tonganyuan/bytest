package com.ynby.platform.operation.biz.log.aspect;

import com.ynby.platform.common.core.constant.CommonConstants;
import com.ynby.platform.common.core.utils.IpUtil;
//import com.ynby.platform.common.security.utils.ContextUtil;
import com.ynby.platform.operation.api.pojo.entity.SysOperLog;
import com.ynby.platform.operation.biz.service.ISysOperLogService;
import com.ynby.platform.operation.biz.log.annotation.Log;
import cz.mallat.uasparser.OnlineUpdater;
import cz.mallat.uasparser.UASparser;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * 操作日志记录处理
 *
 * @author ruoyi
 */
@Component
@Aspect
@Slf4j
public class LogAspect {

	@Autowired
	private ISysOperLogService operLogService;

	static UASparser uasParser = null;

	// 初始化uasParser对象
	static {
		try {
			uasParser = new UASparser(OnlineUpdater.getVendoredInputStream());
		} catch (IOException e) {
			log.error("UASparser初始化异常, ", e);
		}
	}

	/**
	 * 织入
	 */
	@Pointcut("@annotation(com.ynby.platform.operation.biz.log.annotation.Log)")
	public void logPointCut() {
	}

	/**
	 * 处理完请求后执行
	 *
	 * @param joinPoint 切点
	 */
	@AfterReturning(pointcut = "logPointCut()", returning = "jsonResult")
	public void doAfterReturning(JoinPoint joinPoint, Object jsonResult) {
		handleLog(joinPoint, null, jsonResult);
	}

	/**
	 * 拦截异常操作
	 *
	 * @param joinPoint 切点
	 * @param e         异常
	 */
	@AfterThrowing(value = "logPointCut()", throwing = "e")
	public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
		handleLog(joinPoint, e, null);
	}

	protected void handleLog(final JoinPoint joinPoint, final Exception inputE, Object jsonResult) {
		try {
			// 获得注解
			Log controllerLog = getAnnotationLog(joinPoint);
			if (controllerLog == null) {
				return;
			}
			// *========操作日志=========*//
			SysOperLog operLog = new SysOperLog();
			// 请求的地址
			String ip = IpUtil.getIpAddr(Objects.requireNonNull(getRequest()));
			operLog.setOperIp(ip);
			// 用户名和姓名
//			operLog.setUsername(ContextUtil.getOpUserName());
//			operLog.setFullname(ContextUtil.getOpFullName());
			// 浏览器信息
			operLog.setBrowser(LogAspect.uasParser.parse(getRequest().getHeader(CommonConstants.USER_AGENT)).getUaName());
			// 操作动作
			operLog.setBusinessType(controllerLog.businessType().ordinal());
			// 标题
			operLog.setTitle(controllerLog.title());
			// 保存数据库
			operLogService.save(operLog);
		} catch (Exception ex) {
			// 记录本地异常日志
			log.error("操作日志异常", ex);
		}
	}

	/**
	 * 是否存在注解，如果存在就获取
	 */
	private Log getAnnotationLog(JoinPoint joinPoint) throws Exception {
		Signature signature = joinPoint.getSignature();
		MethodSignature methodSignature = (MethodSignature) signature;
		Method method = methodSignature.getMethod();

		if (method != null) {
			return method.getAnnotation(Log.class);
		}
		return null;
	}

	/**
	 * 获取request
	 */
	public static HttpServletRequest getRequest() {
		return Objects.requireNonNull(getRequestAttributes()).getRequest();
	}

	public static ServletRequestAttributes getRequestAttributes() {
		RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
		return (ServletRequestAttributes) attributes;
	}
}
