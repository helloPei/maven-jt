package com.jt.sys.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.JsonResult;
import com.jt.sys.entity.SysUser;
import com.jt.sys.service.SysUserService;

/**
 * User控制层
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/user/")
@RequiresPermissions("user")
public class SysUserController {
	@Autowired
	SysUserService sysUserService;

	@RequestMapping("doUserListUI")
	public String doUserListUI() {
		return "sys/user_list";
	}

	@RequestMapping("doUserEditUI")
	//@RequiresPermissions(value={"addUser","updateUser"})
	public String doUserEditUI() {
		return "sys/user_edit";
	}

	@RequestMapping("doFindPageObjects")
	@ResponseBody
	public JsonResult doFindPageObjects(String username, Integer pageCurrent) {
		return new JsonResult(sysUserService.findPageObjects(username, pageCurrent));
	}

	@RequestMapping("doValidById")
	@ResponseBody
	public JsonResult doValidById(Integer id, Integer valid) {
		SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();// session
		sysUserService.validById(id, valid, user.getUsername());
//				sysUserService.validById(
//						//admin用户将来是登录用户
//						id, valid, "admin");
		return new JsonResult("update ok");
	}

	@RequestMapping("doSaveObject")
	@ResponseBody
	public JsonResult doSaveObject(SysUser entity, // 参数值注入时会调用对象set方法
			Integer[] roleIds) {
		sysUserService.saveObject(entity, roleIds);
		return new JsonResult("save ok");
	}

	@RequestMapping("doFindObjectById")
	@ResponseBody
	public JsonResult doFindObjectById(Integer id) {
		return new JsonResult(sysUserService.findObjectById(id));
	}

	@RequestMapping("doUpdateObject")
	@ResponseBody
	public JsonResult doUpdateObject(SysUser entity, // 参数值注入时会调用对象set方法
			Integer[] roleIds) {
		sysUserService.updateObject(entity, roleIds);
		return new JsonResult("update ok");
	}

}