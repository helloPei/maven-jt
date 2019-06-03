package com.jt.sys.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.JsonResult;
import com.jt.sys.entity.SysMenu;
import com.jt.sys.service.SysMenuService;

/**
 * Menu控制层
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/menu/")
@RequiresPermissions("menu")
public class SysMenuController {
	@Autowired
	private SysMenuService sysMenuService;

	@RequestMapping("doMenuListUI")
	public String doMenuListUI() {
		return "sys/menu_list";
	}

	@RequestMapping("doMenuEditUI")
	//@RequiresPermissions(value = {"addMenu", "updateMenu"})
	public String doMenuEditUI() {
		return "sys/menu_edit";
	}

	@RequestMapping("doUpdateObject")
	@ResponseBody
	public JsonResult doUpdateObject(SysMenu entity) {
		System.out.println("menuName=" + entity.getName());
		sysMenuService.updateObject(entity);
		return new JsonResult("update ok");
	}

	@RequestMapping("doSaveObject")
	@ResponseBody
	public JsonResult doSaveObject(SysMenu entity) {
		System.out.println("menuName=" + entity.getName());
		sysMenuService.saveObject(entity);
		return new JsonResult("save ok");
	}

	/**
	 * 查询菜单的节点信息
	 * 
	 * @return
	 */
	@RequestMapping("doFindZtreeMenuNodes")
	@ResponseBody
	public JsonResult doFindZtreeMenuNodes() {
		return new JsonResult(sysMenuService.findZtreeMenuNodes());
	}

	@RequestMapping("doDeleteObject")
	@ResponseBody
	public JsonResult doDeleteObject(Integer id) {
		sysMenuService.deleteObject(id);
		return new JsonResult("delete ok");
	}

	@RequestMapping("doFindObjects")
	@ResponseBody
	public JsonResult doFindObjects() {
		return new JsonResult(sysMenuService.findObjects());
	}

}