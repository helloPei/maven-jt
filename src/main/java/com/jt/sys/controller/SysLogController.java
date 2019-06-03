package com.jt.sys.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jt.common.vo.JsonResult;
import com.jt.common.vo.PageObject;
import com.jt.sys.entity.SysLog;
import com.jt.sys.service.SysLogService;

/**
 * 用户日志控制层对象定义
 * 
 * @author ta http://localhost/CGB-JT-SYS-V1.01/log/
 *         doFindPageObjects.do?pageCurrent=1
 */
@Controller
@RequestMapping("/log/")
@RequiresPermissions("log")
public class SysLogController {// Class<?> c=SysLogController.class;//存储类的结构信息,成员信息
	@Autowired
	private SysLogService sysLogService;

	@RequestMapping("doLogListUI")
	public String doLogListUI() {
		// return "jsp/log";//.jsp
		// return "jsp/log-ajax";//.jsp
		return "sys/log_list";// .html-->div
	}
	
	@RequestMapping("doFindPageLogs")
	public ModelAndView doFindPageLogs(String username, Integer pageCurrent) {
		// 模拟耗时操作
		try {
			Thread.sleep(5000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		PageObject<SysLog> pageObject = sysLogService.findPageObjects(username, pageCurrent);
		ModelAndView mv = new ModelAndView();
		mv.addObject("pageObject", pageObject);
		mv.setViewName("jsp/log");
		return mv;
	}

	@RequestMapping("doDeleteObjects")
	@ResponseBody
	public JsonResult doDeleteObjects(Integer... ids) {
		sysLogService.deleteObjects(ids);
		return new JsonResult("delete ok");// state=1
	}

	@RequestMapping("doFindPageObjects")
	@ResponseBody
	public JsonResult doFindPageObjects(String username, Integer pageCurrent) {
		// 模拟耗时操作
		// try{Thread.sleep(5000);}
		// catch(Exception e){e.printStackTrace();}
		PageObject<SysLog> data = sysLogService.findPageObjects(username, pageCurrent);
		// return null;
		return new JsonResult(data);
	}

}