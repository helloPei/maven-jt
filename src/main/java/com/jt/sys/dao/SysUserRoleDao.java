package com.jt.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * 用户和角色关系对应的Dao
 * 
 * @author Administrator
 *
 */
public interface SysUserRoleDao {
	/**
	 * 基于用户id获取用户对应的角色id
	 * 
	 * @param id
	 * @return
	 */
	List<Integer> findRoleIdsByUserId(Integer id);

	/**
	 * 负责将用户与角色的关系数据写入到数据库
	 * 
	 * @param userId  用户id
	 * @param roleIds 多个角色id
	 * @return
	 */
	int insertObject(@Param("userId") Integer userId, @Param("roleIds") Integer[] roleIds);

	/**
	 * 基于角色id删除用户和角色关系数据
	 * 
	 * @param roleId 角色id
	 * @return 删除的行数
	 */
	int deleteObjectsByRoleId(Integer roleId);

	/**
	 * 基于用户id删除用户和角色关系数据
	 * 
	 * @param userId
	 * @return
	 */
	int deleteObjectsByUserId(Integer userId);

}