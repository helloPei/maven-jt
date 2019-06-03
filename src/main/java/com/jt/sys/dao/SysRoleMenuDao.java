package com.jt.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * 此DAO对象主要用于操作sys_role_menus中间表(关系表)
 * 
 * @author Administrator
 *
 */
public interface SysRoleMenuDao {// 与SysRoleMenuMapper.xml文件绑定
	/**
	 * 修改角色和菜单的关系数据
	 * 
	 * @param roleId
	 * @param menuIds
	 * @return
	 */
	int updateObject(@Param("roleId") Integer roleId, @Param("menuIds") Integer[] menuIds);

	/**
	 * 插入角色和菜单的关系数据(为角色授权操作)
	 * 
	 * @param roleId
	 * @param menuIds
	 * @return
	 */
	int insertObject(@Param("roleId") Integer roleId, @Param("menuIds") Integer[] menuIds);

	/**
	 * 基于角色id删除角色菜单关系中的记录
	 * 
	 * @param roleId
	 * @return
	 */
	int deleteObjectsByRoleId(Integer roleIds);

	/**
	 * 基于菜单id删除角色与菜单的关系数据
	 * 
	 * @param menuId
	 * @return
	 */
	int deleteObjectsByMenuId(Integer menuId);

	/**
	 * 基于角色id查询关系表中角色
	 * 
	 * @param roleId
	 * @return
	 */
	List<Integer> findMenuIdsByRoleId(@Param("roleIds") Integer... roleId);

}