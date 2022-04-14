package com.ynby.platform.operation.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ynby.platform.operation.api.pojo.entity.SysDictType;

/**
 * <p>
 * 字典类型表 服务类
 * </p>
 *
 * @author gaozhenyu
 * @since 2021-01-18
 */
public interface ISysDictTypeService extends IService<SysDictType> {

	/**
	 * 检查字典类型是否存在
	 *
	 * @param dictId
	 * @param dictType
	 */
	void checkTypeExist(Long dictId, String dictType);

	/**
	 * 检查字典类型有没有被分配
	 *
	 * @param dictType
	 */
	void checkTypeDistribution(String dictType);
}
