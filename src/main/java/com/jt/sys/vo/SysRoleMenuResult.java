package com.jt.sys.vo;

import java.util.List;

import com.jt.sys.entity.SysRole;

public class SysRoleMenuResult {
	/** 角色对象 */
	private SysRole role;
	/** 角色对应的菜单 */
	private List<Integer> menuIds;

	public SysRole getRole() {
		return role;
	}

	public void setRole(SysRole role) {
		this.role = role;
	}

	public List<Integer> getMenuIds() {
		return menuIds;
	}

	public void setMenuIds(List<Integer> menuIds) {
		this.menuIds = menuIds;
	}
}