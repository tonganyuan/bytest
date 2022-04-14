package com.ynby.platform.operation.biz.controller.sys;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ynby.platform.common.core.pojo.R;
import com.ynby.platform.operation.api.pojo.entity.SysDictType;
import com.ynby.platform.operation.api.pojo.param.DictTypePageParam;
import com.ynby.platform.operation.api.pojo.param.DictTypeParam;
import com.ynby.platform.operation.biz.service.ISysDictTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author gaozhenyu
 * @des
 * @date 2020-01-15 18:17
 */
@Api(tags = "字典类型接口")
@Slf4j
@RestController
@RequestMapping("/dict/type")
public class DictTypeController {

	@Autowired
	private ISysDictTypeService dictTypeService;

	/**
	 * 字典类型分页
	 *
	 * @return
	 */
	@ApiOperation("字典类型分页")
	@GetMapping("/page")
	@ResponseBody
	public R<IPage<SysDictType>> page(DictTypePageParam param) {
		IPage<SysDictType> page = new Page<>(param.getPageNum(), param.getPageSize());
		return R.ok(dictTypeService.page(page,
			Wrappers.<SysDictType>lambdaQuery()
				.eq(StrUtil.isNotBlank(param.getDictType()), SysDictType::getDictType, param.getDictType())
				.like(StrUtil.isNotBlank(param.getDictName()), SysDictType::getDictName, param.getDictName())
				.orderByAsc(SysDictType::getSort).orderByDesc(SysDictType::getGmtCreate)
		));
	}

	/**
	 * 根据id获取字典类型
	 *
	 * @param dictId
	 * @return
	 */
	@ApiOperation("根据id获取字典类型详情")
	@GetMapping("/detail/{dictId}")
	public R<SysDictType> info(@PathVariable Long dictId) {
		return R.ok(dictTypeService.getById(dictId));
	}

	/**
	 * 新增字典类型
	 *
	 * @param param
	 * @return
	 */
	@ApiOperation("新增字典类型")
	@PostMapping
	public R<?> add(@Validated DictTypeParam param) {
		dictTypeService.checkTypeExist(null, param.getDictType());
		SysDictType dictType = new SysDictType();
		BeanUtils.copyProperties(param, dictType);
		dictTypeService.save(dictType);
		return R.ok();
	}

	/**
	 * 修改字典类型
	 *
	 * @param param
	 * @return
	 */
	@ApiOperation("修改字典类型")
	@PutMapping
	public R<?> modify(@Validated DictTypeParam param) {
		dictTypeService.checkTypeExist(param.getDictId(), param.getDictType());
		SysDictType dictType = new SysDictType();
		BeanUtils.copyProperties(param, dictType);
		dictTypeService.updateById(dictType);
		return R.ok();
	}

	/**
	 * 删除字典类型
	 *
	 * @param dictIds
	 * @return
	 */
	@ApiOperation("删除字典类型")
	@DeleteMapping
	public R<?> remove(@RequestParam List<Long> dictIds) {
		dictIds.forEach(dictId -> {
			SysDictType dictType = dictTypeService.getById(dictId);
			dictTypeService.checkTypeDistribution(dictType.getDictType());
		});
		dictTypeService.removeByIds(dictIds);
		return R.ok();
	}

}
