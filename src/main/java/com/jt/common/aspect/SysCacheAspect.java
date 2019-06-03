package com.jt.common.aspect;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.cache.CacheKey;
import com.jt.common.cache.ServiceMapCache;
/**
 * 通过此切面对象,将业务层查询数据存储缓存,需要时从缓存取,相对于直接从数据库获取,会有很大性能提升
 * @author Administrator
 *
 */
@Aspect
@Service
public class SysCacheAspect {
	@Autowired
	private ServiceMapCache mapCache;
	
	@Around("@annotation(com.jt.common.annotation.RequiredCache)")
	public Object aroundMethod(ProceedingJoinPoint jp)
			throws Throwable{
		//关键：如何设计key
		CacheKey key = obtainKey(jp);
		//1.从cache取数据
		Object value = mapCache.get(key);
		if(value!=null)return value;
		//2.执行目标方法
		Object result = jp.proceed();
		//3.向cache中放数据
		mapCache.put(key, result);
		return result;
	}
	private CacheKey obtainKey(ProceedingJoinPoint jp) 
			throws NoSuchMethodException, SecurityException, JsonProcessingException{
		//1.构建CacheKey对象
		CacheKey key = new CacheKey();
		//2.封装CacheKey信息
		Class<?> targetCls = jp.getTarget().getClass();
		MethodSignature ms = (MethodSignature) jp.getSignature();
		Method targetMethod = 
				targetCls.getDeclaredMethod(
						ms.getName(), //name "MethodSignature ms.getName()"
						ms.getParameterTypes());//parameterTypes "MethodSignature ms.getParameterTypes()"
		key.setTargetClass(targetCls);//targetClass "Class<?> targetCls"
		key.setTargetMethod(targetMethod);//targetMethod "Method targetMethod"
		key.setActualArgs(new ObjectMapper().writeValueAsString(jp.getArgs()));
		//3.返回CacheKey对象
		return key;
	}
}
