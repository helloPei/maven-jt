package com.jt.common.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
/**
 * 定义缓存方法
 * @author Administrator
 *
 */
@Component
public class ServiceMapCache {
	private ServiceMapCache(){
		System.out.println("ServiceMapCache()");
	}
	private Map<CacheKey,Object> cache = 
				new ConcurrentHashMap<>();
	/**
	 * 向缓存放数据
	 * @param key
	 * @param value
	 */
	public void put(CacheKey key,Object value){
		cache.put(key, value);
	}
	/**
	 * 从缓存中取数据
	 * @param key
	 * @return Object对象
	 */
	public Object get(CacheKey key){
		return cache.get(key);
	}
	/**
	 * 清空缓存
	 */
	public void clear(){
		cache.clear();
	}
}
