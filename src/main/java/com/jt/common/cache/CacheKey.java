package com.jt.common.cache;

import java.lang.reflect.Method;
/**
 * 定义缓存key对象
 * @author Administrator
 *
 */
public class CacheKey {//借助此类的对象作为key
	/** 目标类 */
	private Class<?> targetClass;
	/** 目标方法 */
	private Method targetMethod;
	/** 实际参数 */
	private String actualArgs;
	
	public Class<?> getTargetClass() {
		return targetClass;
	}
	public void setTargetClass(Class<?> targetClass) {
		this.targetClass = targetClass;
	}
	public Method getTargetMethod() {
		return targetMethod;
	}
	public void setTargetMethod(Method targetMethod) {
		this.targetMethod = targetMethod;
	}
	public String getActualArgs() {
		return actualArgs;
	}
	public void setActualArgs(String actualArgs) {
		this.actualArgs = actualArgs;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((actualArgs == null) ? 0 : actualArgs.hashCode());
		result = prime * result + ((targetClass == null) ? 0 : targetClass.hashCode());
		result = prime * result + ((targetMethod == null) ? 0 : targetMethod.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CacheKey other = (CacheKey) obj;
		if (actualArgs == null) {
			if (other.actualArgs != null)
				return false;
		} else if (!actualArgs.equals(other.actualArgs))
			return false;
		if (targetClass == null) {
			if (other.targetClass != null)
				return false;
		} else if (!targetClass.equals(other.targetClass))
			return false;
		if (targetMethod == null) {
			if (other.targetMethod != null)
				return false;
		} else if (!targetMethod.equals(other.targetMethod))
			return false;
		return true;
	}
}
