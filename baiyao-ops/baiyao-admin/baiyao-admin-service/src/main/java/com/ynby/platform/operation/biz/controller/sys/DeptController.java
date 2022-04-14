package com.ynby.platform.operation.biz.controller.sys;

import com.google.common.collect.Maps;
import com.ynby.platform.common.core.pojo.R;
import com.ynby.platform.operation.api.pojo.entity.SysDept;
import com.ynby.platform.operation.api.pojo.vo.DeptTreeVO;
import com.ynby.platform.operation.api.pojo.vo.TreeSelectVO;
import com.ynby.platform.operation.biz.service.ISysDeptService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author gaozhenyu
 * @des
 * @date 2020-01-15 18:17
 */
@Api(tags = "部门管理接口")
@Slf4j
@RestController
@RequestMapping("/dept")
public class DeptController {

	@Autowired
	private ISysDeptService deptService;

	/**
	 * 查询部门树形结构
	 *
	 * @param deptId
	 * @param deptName
	 * @return
	 */
	@GetMapping("/treeSelect")
	public R<List<TreeSelectVO>> treeSelect(@RequestParam(required = false) Long deptId,
											@RequestParam(required = false) String deptName) {
		List<DeptTreeVO> deptTreeVOList = deptService.makeTree(deptId, deptName);
		return R.ok(deptTreeVOList.stream().map(TreeSelectVO::new).collect(Collectors.toList()));
	}


	/**
	 * 加载对应角色部门列表树
	 *
	 * @param roleId
	 * @return
	 */
	@GetMapping("/roleDeptTreeSelect/{roleId}")
	public R<Map<String, Object>> roleDeptTreeSelect(@PathVariable("roleId") Long roleId) {
		List<SysDept> depts = deptService.list();
		Map<String, Object> resMap = Maps.newHashMap();
		resMap.put("checkedKeys", deptService.selectDeptListByRoleId(roleId));
		resMap.put("depts", deptService.buildDeptTreeSelect(depts));
		return R.ok(resMap);
	}


}
