package com.jt.sys.service;

import java.util.Map;

import com.jt.common.vo.PageObject;
import com.jt.sys.entity.SysUser;
import com.jt.sys.vo.SysUserDeptResult;

/**
 * User业务层接口
 * 
 * @author Administrator
 *
 */
public interface SysUserService {

	/** 业务层分页查询用户以及对应的部门信息 */
	PageObject<SysUserDeptResult> findPageObjects(String username, Integer pageCurrent);

	/** 业务层对用户信息执行禁用或启用操作 */
	int validById(Integer id, Integer valid, String modifiedUser);

	/** 将数据保存到数据库(用户信息,用户角色关系信息) */
	int saveObject(SysUser entity, Integer[] roleIds);

	/** 基于用户id查询用户以及对应的部门，角色等信息 */
	Map<String, Object> findObjectById(Integer id);

	/** 更新用户自身信息 */
	int updateObject(SysUser entity, Integer[] roleIds);
}