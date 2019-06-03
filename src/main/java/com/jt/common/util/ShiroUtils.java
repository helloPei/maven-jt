package com.jt.common.util;

import org.apache.shiro.SecurityUtils;

import com.jt.sys.entity.SysUser;

/**
 * Shiro工具类
 * 
 * @author Administrator
 *
 */
public class ShiroUtils {
	/**
	 * 获取当前登录用户
	 * 
	 * @return
	 */
	public static SysUser getPrincipal() {
		return (SysUser) SecurityUtils.getSubject().getPrincipal();
	}

}