package com.jt.sys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jt.common.vo.Node;
import com.jt.sys.entity.SysMenu;

/**
 * Menu持久层接口
 * 
 * @author Administrator
 *
 */
public interface SysMenuDao {
	/** 更新菜单信息 */
	int updateObject(SysMenu entity);

	/** 添加菜单信息 */
	int insertObject(SysMenu entity);

	/** 查询所有菜单的id,parentId,菜单名 */
	List<Node> findZtreeMenuNodes();

	/** 根据菜单id统计子菜单的个数 */
	int getChildCount(Integer id);

	/** 根据id 删除菜单 */
	int deleteObject(Integer id);

	/**
	 * 查询所有菜单信息
	 * 
	 * @return 所有的菜单信息,表中的一行菜单记录映射 为一个map对象,map中的key为表中字段名,map中的 值为表中字段对应的值.
	 */
	List<Map<String, Object>> findObjects();

	/** 基于菜单id获取权限标识 */
	List<String> findPermissions(@Param("menuIds") Integer... menuIds);

}