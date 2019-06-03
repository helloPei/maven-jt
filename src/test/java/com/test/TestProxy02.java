package com.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

public class TestProxy02 {
	public static void main(String[] args) {
		//线程不安全，版本过低
		//解决方法：动态代理
//		List<String> list = new ArrayList<>();
//		list=Collections.synchronizedList(list);
//		list.add("A");
//		list.add("B");
		List<String> target = new ArrayList<>();
		target.add("A");
		target.add("B");
		//不加注解会有警告
		@SuppressWarnings("unchecked")
		List<String> proxy=
			(List<String>) Proxy.newProxyInstance(
				target.getClass().getClassLoader(), 
				target.getClass().getInterfaces(), 
				new InvocationHandler() {//匿名内内部类			
					@Override
					public Object invoke(
							Object proxy, Method method, Object[] args) 
									throws Throwable {
						synchronized(this){	
							Object obj = method.invoke(proxy, args);
							return obj;
						}
					}
				});
		proxy.add("C");
		proxy.add("D");
		proxy.remove(0);
		System.out.println(target);
	}
}
