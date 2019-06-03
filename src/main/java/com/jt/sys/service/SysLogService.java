package com.jt.sys.service;

import com.jt.common.vo.PageObject;
import com.jt.sys.entity.SysLog;

/**
 * Log业务层接口
 * 
 * @author Administrator
 *
 */
public interface SysLogService {

	/** 基于id执行删除操作 */
	int deleteObjects(Integer... ids);

	/** 按照条件分页查询用户日志数据 */
	PageObject<SysLog> findPageObjects(String username, Integer pageCurrent);

}