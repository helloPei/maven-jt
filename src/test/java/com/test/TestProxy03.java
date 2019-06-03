package com.test;

import java.lang.reflect.Method;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

//目标对象(target object)
//如何为此对象创建代理对象？(借助CGLIB，第三方库)
class HelloServiceImpl{
	public void sayHello(String name){
		System.out.println("hello "+name);
	}
}
//问题?
//1)如何借助CGLTB为如上对象创建一个代理对象
//2)通过代理对象扩展如上对象的功能
public class TestProxy03 {
	public static void main(String[] args) {
		//创建Enhancer(此对象为使用CGLIB入口对象)
		//要借助此对象为目标对象创建代理对象
		//这里的代理对象是目标对象的一个子类类型对象
		Enhancer e = new Enhancer();
		e.setSuperclass(HelloServiceImpl.class);
		//设置回调函数?(当指定代理对象的方法时会执行此Callback对象的invoke函数)
		e.setCallback(new MethodInterceptor() {//继承Callback
			@Override
			public Object intercept(
					Object obj,//指向代理对象
					Method method,//目标对象的方法
					Object[] args,//方法执行时传入的实际参数
					MethodProxy proxy)
							throws Throwable {
				System.out.println("method start");
				//执行obj父类对象的method方法
				Object result = proxy.invokeSuper(obj, args);
				System.out.println("method end");
				return result;
			}
		});//类似JDK动态代理的InvocationHandler
		Object obj = e.create();
		System.out.println(obj);
		HelloServiceImpl h = (HelloServiceImpl) obj;
		h.sayHello("CGB1807");//invoke proxy method
	}
}
//mybatis 底层二级缓存中
//spring bean对象没有实现接口时,假如需要代理对象