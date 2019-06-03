package com.jt.common.web;

import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
/**
 * @ControllerAdvice注解修饰的类为spring mvc中的全局异常处理类
 */
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.JsonResult;
/**
 * @ControllerAdvice 注解修饰的类为全局异常处理类
 * @author ta
 */
@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ShiroException.class)
	@ResponseBody
	public JsonResult doShiroException(
			ShiroException exp){
		exp.printStackTrace();//控制台输出
		JsonResult r = new JsonResult();
		r.setState(0);//Exception
		if(exp instanceof UnknownAccountException){
			r.setMessage("用户名不存在");
		}else if(exp instanceof LockedAccountException){
			r.setMessage("用户已被禁用");
		}else if(exp instanceof IncorrectCredentialsException){
			r.setMessage("密码不正确");
		}else if(exp instanceof AuthorizationException){
			r.setMessage("没有此权限");
		}else{
			r.setMessage(exp.getMessage());
		}
		return r;
	}
	 /**
	   * @ExceptionHandler 注解描述的方法为一个异常处理
	   * 方法
	   * @param e
	   * @return 封装了异常信息的JsonResult对象
	   */
	  @ExceptionHandler(RuntimeException.class)
	  @ResponseBody
	  public JsonResult doHandleRuntimeException(
			  RuntimeException e){
		  e.printStackTrace();
		  return new JsonResult(e);
	  }//jackson
}





