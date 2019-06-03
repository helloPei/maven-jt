package com.jt.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 页面控制层
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/")
public class PageController {

	/** 主页 */
	@RequestMapping("doIndexUI")
	public String doIndexUI() {
		return "starter";
	}

	/** 分页页面 */
	@RequestMapping("doPageUI")
	public String doPageUI() {
		return "common/page";
	}

	/** 登录页面 */
	@RequestMapping("doLoginUI")
	public String doLoginUI() {
		return "login";
	}

}