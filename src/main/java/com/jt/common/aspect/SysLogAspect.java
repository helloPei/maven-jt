package com.jt.common.aspect;

import java.lang.reflect.Method;
import java.util.Date;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.annotation.RequiredLog;
import com.jt.common.util.IPUtils;
import com.jt.common.util.ShiroUtils;
import com.jt.sys.dao.SysLogDao;
import com.jt.sys.entity.SysLog;

/**
 * 借助@Aspect注解描述此类为一个切面对象，在此类实现日志操作
 * 例如：
 * 用户添加，修改等业务操作执行要进行日志记录
 * @author Administrator
 *
 */
@Aspect
@Service
public class SysLogAspect {
	@Autowired
	private SysLogDao sysLogDao;
	/**
	 * 切入点的定义(借助@Pointcut注解进行描述)
	 */
	@Pointcut("bean(sysUserServiceImpl)")
	public void doPointCut(){}//切入点
	/**
	 * @Around 修饰的方法为一个环绕通知
	 * 可以在目标方法执行之前和之后添加扩展业务
	 * @param jp	连接点(封装了要执行的具体方法对象信息)
	 * @return
	 */
	//@Around("bean(sysUserServiceImpl)")//切入点
	@Around("doPointCut()")
	public Object aroundMethod(ProceedingJoinPoint jp)
		throws Throwable{
//		String clsName = jp.getTarget().getClass().getName();
//		Signature s = jp.getSignature();
//		System.out.println("class.method="+clsName+"");
		//1.添加扩展功能
		long startTime = System.currentTimeMillis();
		//System.out.println("method.start:"+startTime);
		//2.执行目标方法(可以基于业务不执行目标方法)
		//result为目标方法的返回值
		//System.out.println("invoke target method");
		Object result = jp.proceed();//通过反射机制调用目标方法
		//3.添加扩展功能
		long endTime = System.currentTimeMillis();
		//System.out.println("menthod.end:"+endTime);
		saveObject(jp,endTime-startTime);
		return result;
		//return null;
	}
	/**
	 * 将用户行为数据写入到数据库中
	 * @param jp
	 * @param time
	 * @throws JsonProcessingException 
	 * @throws NoSuchMethodException 
	 */
	private void saveObject(ProceedingJoinPoint jp,long time)
		throws JsonProcessingException, NoSuchMethodException{
		//1.获取要记录的用户行为数据
		String ip = IPUtils.getIpAddr();
		//1.2获取登录用户名
		String username = ShiroUtils.getPrincipal().getUsername();
		//1.3获取用户操作
		System.out.println(jp.getSignature().getClass().getName());
		//1.3.1获取目标方法对象(先获取目标类，再获取目标方法对象)
		Class<?> targetCls = jp.getTarget().getClass();
		Method targetMethod = getTargetMethod(targetCls,jp);
		//1.3.2获取目标方法对象上的注解
		RequiredLog rLog=
				targetMethod.getDeclaredAnnotation(RequiredLog.class);
		//1.3.3获取注解中定义的操作名
		String operation=
				rLog!=null?rLog.operation():"unkown";
		//1.4获取方法名
		String method = 
				targetCls.getSimpleName()+"."+targetMethod.getName();
		String params = new ObjectMapper()//jackson
				.writeValueAsString(jp.getArgs());
		//2.封装用户的行为数据
		SysLog log = new SysLog();
		log.setIp(ip);
		log.setUsername(username);
		log.setOperation(operation);
		log.setMethod(method);
		log.setParams(params);
		log.setTime(time);
		log.setCreateDate(new Date());
		//3.将用户行为数据写入到数据库中
		sysLogDao.insertObject(log);
	}
	//快速提取方法快捷键：alt+shift+m
	private Method getTargetMethod(Class<?> targetCls, ProceedingJoinPoint jp)
			throws NoSuchMethodException{
		//获取方法签名对象
		MethodSignature ms = 
				(MethodSignature)jp.getSignature();
		
		//ms.getMethod();接口方法对象
		//获取方法参数类型
		Class<?>[] paramTypes = ms.getMethod().getParameterTypes();
		//获取目标方法(先获取目标对象，再获取目标方法)
		Method targetMethod=
				targetCls.getDeclaredMethod(
				ms.getName(),paramTypes);
		return targetMethod;
	}
}