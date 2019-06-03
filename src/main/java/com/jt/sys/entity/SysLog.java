
package com.jt.sys.entity;

import java.io.Serializable;
import java.util.Date;

/***
 * 日志POJO对象:主要用于实现与jtsys数据库 中sys_logs表之间数据的映射. SysLog对象用户封装用户在系统中的操作日志
 */
public class SysLog implements Serializable {
	private static final long serialVersionUID = 1L;
	/** 日志id */
	private Integer id;
	/** 用户名 */
	private String username;
	/** 用户操作 */
	private String operation;
	/** 请求方法 */
	private String method;
	/** 请求参数 */
	private String params;
	/** 执行时长(毫秒) */
	private Long time;
	/** IP地址 */
	private String ip;
	/** 创建时间 */
	private Date createdTime;

	/**
	 * 设置：
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 获取：
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * 设置：用户名
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * 获取：用户名
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * 设置：用户操作
	 */
	public void setOperation(String operation) {
		this.operation = operation;
	}

	/**
	 * 获取：用户操作
	 */
	public String getOperation() {
		return operation;
	}

	/**
	 * 设置：请求方法
	 */
	public void setMethod(String method) {
		this.method = method;
	}

	/**
	 * 获取：请求方法
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * 设置：请求参数
	 */
	public void setParams(String params) {
		this.params = params;
	}

	/**
	 * 获取：请求参数
	 */
	public String getParams() {
		return params;
	}

	/**
	 * 设置：IP地址
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * 获取：IP地址
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * 设置：创建时间
	 */
	public void setCreateDate(Date createdTime) {
		this.createdTime = createdTime;
	}

	/**
	 * 获取：创建时间
	 */
	public Date getCreatedTime() {
		return createdTime;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}
	
}