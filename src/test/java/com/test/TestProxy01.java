package com.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

interface LogService{
	void doSave();
}
interface UserService{
	void doUpdate();
}

class UserServiceImpl implements UserService{//目标对象
	@Override
	public void doUpdate() {
		System.out.println("update user");	
	}
}

class LogServiceImpl implements LogService{//目标对象
	/**
	 * 不修改代码在目标对象进行扩展，遵循AOP(切面)
	 */
	@Override
	public void doSave() {
		System.out.println("save log");
	}
}

class LogAspect{//Aspect(切面)
	public void before(){//@Before
		System.out.println("method start");
	}
	public void after(){//@After
		System.out.println("method end");
	}
}

public class TestProxy01 {
	//在此类的对象方法中调用目标对象方法，添加业务扩展功能
	private static class ServiceHandler implements InvocationHandler{
		private Object target;//目标对象
		private LogAspect logAspect = new LogAspect();
		public ServiceHandler(Object target){
			this.target=target;
		}
		@Override
		public Object invoke(Object proxy, 
				Method method, Object[] args) throws Throwable {
			//执行日志切面方法(before)
			logAspect.before();
			//执行目标对象方法(save)
			Object result = method.invoke(target, args);
			//执行日志切面方法(after)
			logAspect.after();
			return result;
		}//负责核心业务调用与扩展业务调用
	}
	/** 接祖JDK API(Proxy)为目标对象创建动态代理对象*/
//	static LogService newLogServiceProxy(LogService target){
//		LogService logService = (LogService)Proxy.newProxyInstance(
//				target.getClass().getClassLoader(), //loader (类的加载器)
//				target.getClass().getInterfaces(), //interfaces(目标对象实现的接口)
//				new ServiceHandler(target));//handler
//		return logService;
//	}
	/** 接祖JDK API(Proxy)为目标对象创建动态代理对象*/
	static Object newServiceProxy(Object target){
		return Proxy.newProxyInstance(
				target.getClass().getClassLoader(), //loader (类的加载器)
				target.getClass().getInterfaces(), //interfaces(目标对象实现的接口)
				new ServiceHandler(target));//handler
	}
	
	public static void main(String[] args) {
		//LogService logService = newLogServiceProxy(new LogServiceImpl(target));
		LogService logService = (LogService)
				newServiceProxy(new LogServiceImpl());
		System.out.println(logService.getClass().getName());
		logService.doSave();
		//当调用doSave方法，系统底层会调用Handler对象的invoke方法，执行核心业务与扩展业务的整合
		//=====================
		
	}
}
