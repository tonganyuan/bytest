package com.ynby.platform.operation.biz.controller.sys;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ynby.platform.common.core.enums.CommonEnums;
import com.ynby.platform.common.core.pojo.R;
import com.ynby.platform.operation.api.pojo.entity.SysDictData;
import com.ynby.platform.operation.api.pojo.param.DictDataPageParam;
import com.ynby.platform.operation.api.pojo.param.DictDataParam;
import com.ynby.platform.operation.biz.service.ISysDictDataService;
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
@Api(tags = "字典数据接口")
@Slf4j
@RestController
@RequestMapping("/dict/data")
public class DictDataController {

	@Autowired
	private ISysDictDataService dictDataService;

	/**
	 * 字典数据分页
	 *
	 * @return
	 */
	@ApiOperation("字典数据分页")
	@GetMapping("/page")
	public R<IPage<SysDictData>> page(DictDataPageParam param) {
		IPage<SysDictData> page = new Page<>(param.getPageNum(), param.getPageSize());
		return R.ok(dictDataService.page(page,
			Wrappers.<SysDictData>lambdaQuery()
				.like(StrUtil.isNotBlank(param.getDictLabel()), SysDictData::getDictLabel, param.getDictLabel())
				.orderByAsc(SysDictData::getSort).orderByDesc(SysDictData::getGmtCreate)
		));
	}

	/**
	 * 根据code获取字典数据详情
	 *
	 * @param dictCode
	 * @return
	 */
	@ApiOperation("根据code获取字典数据详情")
	@GetMapping("/{dictCode}")
	public R<SysDictData> info(@PathVariable Long dictCode) {
		return R.ok(dictDataService.getById(dictCode));
	}

	/**
	 * 根据字典类型查询字典数据信息
	 *
	 * @param dictType
	 * @return
	 */
	@ApiOperation("根据字典类型查询字典数据信息")
	@GetMapping("/type/{dictType}")
	public R<List<SysDictData>> info(@PathVariable String dictType) {
		return R.ok(dictDataService.list(Wrappers.<SysDictData>lambdaQuery()
			.eq(SysDictData::getDictType, dictType)
			.eq(SysDictData::getStatus, CommonEnums.BasicEnum.YES.getCode())
			.orderByAsc(SysDictData::getSort)
		));
	}

	/**
	 * 新增字典数据
	 *
	 * @param param
	 * @return
	 */
	@ApiOperation("新增字典数据")
	@PostMapping
	public R<?> add(@Validated @RequestBody DictDataParam param) {
		SysDictData dictData = new SysDictData();
		BeanUtils.copyProperties(param, dictData);
		dictDataService.save(dictData);
		return R.ok();
	}

	/**
	 * 修改字典数据
	 *
	 * @param param
	 * @return
	 */
	@ApiOperation("修改字典数据")
	@PutMapping
	public R<?> modify(@Validated DictDataParam param) {
		SysDictData dictData = new SysDictData();
		BeanUtils.copyProperties(param, dictData);
		dictDataService.updateById(dictData);
		return R.ok();
	}

	/**
	 * 删除字典数据
	 *
	 * @param dictCodes
	 * @return
	 */
	@ApiOperation("删除字典数据")
	@DeleteMapping
	public R<?> remove(@RequestParam List<Long> dictCodes) {
		dictDataService.removeByIds(dictCodes);
		return R.ok();
	}

}
