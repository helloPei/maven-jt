package com.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

interface SysLogDao{
	Map<String,Object> findById(Integer id);
}
class DaoProxyFactory{
	static Object newDaoProxy(Class<?> cls){
		return Proxy.newProxyInstance(
				cls.getClassLoader(), 
				new Class[]{cls},
				new DaoHandler());
	}
	
	static class DaoHandler implements InvocationHandler{
		@Override
		public Object invoke(
				Object proxy, Method method, Object[] args) throws Throwable {
			System.out.println("invoke");
			//method.invoke(obj, args);
			System.out.println(method);
			//String starement = "proxy.SysLog.findById";
			//Map<String,Object> map = session.selectiList(statement,args);
			//session.closen();
			//return map;
			Map<String,Object> map = new HashMap<>();
			map.put("id", 100);
			map.put("username", "zhangfei");
			return map;
		}
	}
}
public class TestMyBatisProxy01 {
	public static void main(String[] args) {
		SysLogDao dao= (SysLogDao) 
				DaoProxyFactory.newDaoProxy(SysLogDao.class);//代理对象
		Map<String,Object> map = dao.findById(10);
		System.out.println(map);
	}
}
