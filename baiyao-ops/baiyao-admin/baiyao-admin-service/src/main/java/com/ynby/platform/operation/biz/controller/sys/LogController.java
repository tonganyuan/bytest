package com.ynby.platform.operation.biz.controller.sys;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ynby.platform.common.core.pojo.R;
import com.ynby.platform.operation.api.pojo.entity.SysOperLog;
import com.ynby.platform.operation.api.pojo.entity.SysUser;
import com.ynby.platform.operation.api.pojo.param.LogParam;
import com.ynby.platform.operation.biz.log.annotation.Log;
import com.ynby.platform.operation.biz.log.annotation.enums.BusinessType;
import com.ynby.platform.operation.biz.service.ISysOperLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 操作日志记录
 *
 * @author ruoyi
 */
@Api(tags = "操作日志接口")
@Slf4j
@RestController
@RequestMapping("/operlog")
public class LogController {

	@Autowired
	private ISysOperLogService operLogService;

	/**
	 * 新增操作日志
	 *
	 * @param operLog
	 * @return
	 */
	@PostMapping
	public R<String> add(@RequestBody SysOperLog operLog) {
		operLogService.save(operLog);
		return R.ok();
	}

	/**
	 * 查询操作日志列表
	 *
	 * @param param
	 * @return
	 */
	@ApiOperation("查询操作日志列表")
	@GetMapping("/list")
//	@PreAuthorize("hasAuthority('sys:log:query')")
	@Log(title = "日志管理", businessType = BusinessType.LIST)
	public R<IPage<SysOperLog>> selectLogList(LogParam param) {
		IPage<SysUser> page = new Page<>(param.getPageNum(), param.getPageSize());
		Page<SysOperLog> sysOperLogList = operLogService.selectLogList(page, param);
		return R.ok(sysOperLogList);
	}
}
