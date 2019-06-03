package com.jt.sys.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.JsonResult;

/**
 * 负责执行登录操作的控制层方法
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping("/")
public class SysLoginController {

	/***
	 * 负责执行登录操作的控制层方法
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	@RequestMapping("doLogin")
	@ResponseBody
	public JsonResult doLogin(String username, String password, Integer isRememberMe) {
		// 1.封装用户信息
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		//1.1记住我
		if(isRememberMe == 1)token.setRememberMe(true);//可选用
		// 2.获取Subject对象(通过此对象提交令牌)
		Subject subject = SecurityUtils.getSubject();
		// 2.执行用户身份认证(将令牌提交给Shiro中的SecurityManager)
		subject.login(token);// 此方法运行时可能会出现异常
		// 分析:
		// 1)token会传给shiro的SecurityManager
		// 2)SecurityManager将token传递给认证管理器
		// 3)认证管理器会将token传递给realm
		return new JsonResult("login ok");
	}
	
}