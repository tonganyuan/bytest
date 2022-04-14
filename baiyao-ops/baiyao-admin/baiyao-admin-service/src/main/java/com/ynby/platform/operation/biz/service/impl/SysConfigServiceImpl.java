package com.ynby.platform.operation.biz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ynby.platform.operation.api.pojo.entity.SysConfig;
import com.ynby.platform.operation.biz.dao.SysConfigMapper;
import com.ynby.platform.operation.biz.service.ISysConfigService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 参数配置表 服务实现类
 * </p>
 *
 * @author gaozhenyu
 * @since 2021-02-20
 */
@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements ISysConfigService {

}
