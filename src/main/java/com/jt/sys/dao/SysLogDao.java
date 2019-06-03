package com.jt.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jt.sys.entity.SysLog;

/**
 * Log持久层接口
 * 
 * @author Administrator
 *
 */
public interface SysLogDao {
	/** 将日志数据写入到数据库 */
	int insertObject(SysLog entity);

	/** 基于id执行删除操作 */
	int deleteObjects(@Param("ids") Integer... ids);

	/**
	 * 依据条件查询当前页数据
	 * 
	 * @param username   查询条件(按用户名查询)
	 * @param startIndex 当前页的起始位置
	 * @param pageSize   当前模块的页面大小(每页最多显示多少条记录)
	 * @return
	 */
	List<SysLog> findPageObjects(@Param("username") String username, @Param("startIndex") Integer startIndex,
			@Param("pageSize") Integer pageSize);

	/**
	 * 按照查询条件统计用户操作日志数
	 * 
	 * @param username (用户名)
	 * @return 查询到的记录数,按照这个值以及pageSize 计算总页数
	 */
	int getRowCount(@Param("username") String username);
	
}