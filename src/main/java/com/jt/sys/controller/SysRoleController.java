package com.jt.sys.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.CheckBox;
import com.jt.common.vo.JsonResult;
import com.jt.sys.entity.SysRole;
import com.jt.sys.service.SysRoleService;

/**
 * Role控制层
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/role/")
@RequiresPermissions("role")
public class SysRoleController {
	@Autowired
	private SysRoleService sysRoleService;
	
	@RequestMapping("doRoleListUI")
	public String doRoleListUI() {
		return "sys/role_list";
	}

	@RequestMapping("doRoleEditUI")
	//@RequiresPermissions(value = {"addRole", "updateRole"})
	public String doRoleEditUI() {
		return "sys/role_edit";
	}

	@RequestMapping("doFindRoles")
	@ResponseBody
	public JsonResult doFindRoles() {
		List<CheckBox> list = sysRoleService.findObjects();
		return new JsonResult(list);
	}

	@RequestMapping("doFindObjectById")
	@ResponseBody
	public JsonResult doFindObjectById(Integer id) {
		Map<String, Object> map = sysRoleService.findObjectById(id);
		return new JsonResult(map);
	}

	// 保存角色
	@RequestMapping("doSaveObject")
	@ResponseBody
	public JsonResult doSaveObject(SysRole entity, Integer[] menuIds) {
		sysRoleService.saveObject(entity, menuIds);
		return new JsonResult("save ok");
	}

	@RequestMapping("doUpdateObject")
	@ResponseBody
	public JsonResult doUpdateObject(SysRole entity, Integer[] menuIds) {
		sysRoleService.updateObject(entity, menuIds);
		return new JsonResult("update ok");
	}

	// 删除角色对象
	@RequestMapping("doDeleteObject")
	@ResponseBody // 不加会有404错误
	public JsonResult doDeleteObject(Integer id) {
		sysRoleService.deleteObject(id);
		return new JsonResult("delete ok");
	}

	@RequestMapping("doFindPageObjects")
	@ResponseBody
	public JsonResult doFindPageObjects(String name, Integer pageCurrent) {
		System.out.println("name=" + name);
		return new JsonResult(sysRoleService.findPageObjects(name, pageCurrent));
	}

}