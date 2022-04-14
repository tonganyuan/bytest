package com.ynby.platform.operation.biz.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ynby.platform.common.core.exception.CommonException;
import com.ynby.platform.operation.api.pojo.entity.SysDictData;
import com.ynby.platform.operation.api.pojo.entity.SysDictType;
import com.ynby.platform.operation.biz.dao.SysDictTypeMapper;
import com.ynby.platform.operation.biz.service.ISysDictDataService;
import com.ynby.platform.operation.biz.service.ISysDictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 字典类型表 服务实现类
 * </p>
 *
 * @author gaozhenyu
 * @since 2021-01-18
 */
@Service
public class SysDictTypeServiceImpl extends ServiceImpl<SysDictTypeMapper, SysDictType> implements ISysDictTypeService {

	@Autowired
	private ISysDictDataService dictDataService;

	/**
	 * 检查字典类型是否存在
	 *
	 * @param dictId
	 * @param dictType
	 */
	@Override
	public void checkTypeExist(Long dictId, String dictType) {
		int count = count(Wrappers.<SysDictType>lambdaQuery()
			.ne(dictId != null, SysDictType::getDictId, dictId)
			.eq(SysDictType::getDictType, dictType)
		);
		if (count > 0) {
			throw new CommonException("字典类型已存在");
		}
	}

	/**
	 * 检查字典类型有没有被分配
	 *
	 * @param dictType
	 */
	@Override
	public void checkTypeDistribution(String dictType) {
		int count = dictDataService.count(Wrappers.<SysDictData>lambdaQuery()
			.eq(SysDictData::getDictType, dictType)
		);
		if (count > 0) {
			throw new CommonException(String.format("字典类型[%s]已分配,无法删除", dictType));
		}
	}
}
