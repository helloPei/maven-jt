package com.jt.common.aspect;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.common.cache.ServiceMapCache;

@Aspect
@Service//业务(非核心业务)
public class SysClearCacheAspect {
	@Pointcut("@annotation(com.jt.common.annotation.RequiredClearCache)")
	private void doMethod(){}
	@Autowired
	private ServiceMapCache cache;
	/**
	 * 方法成功执行结束以后才会执行
	 * @AfterReturning
	 */
	@AfterReturning("doMethod()")
	public void afterReturningMethod(){
		cache.clear();
	}
}
