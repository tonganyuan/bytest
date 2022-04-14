package com.ynby.platform.operation.biz.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ynby.platform.operation.api.pojo.entity.SysUser;
import com.ynby.platform.operation.api.pojo.param.UserPageParam;
import com.ynby.platform.operation.api.pojo.vo.UserVO;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

/**
 * <p>
 * 运营用户表	 Mapper 接口
 * </p>
 *
 * @author gaozhenyu
 * @since 2021-01-06
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

	/**
	 * 根据userId获取权限列表
	 *
	 * @param userId
	 * @return
	 */
	Set<String> listPermByUserId(@Param("userId") String userId);

	/**
	 * 用户分页
	 *
	 * @param page
	 * @param param
	 * @return
	 */
	Page<UserVO> selectUserPage(@Param("page") IPage<?> page, @Param("param") UserPageParam param);
}
