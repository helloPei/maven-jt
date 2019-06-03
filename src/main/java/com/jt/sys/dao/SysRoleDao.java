package com.jt.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jt.common.vo.CheckBox;
import com.jt.sys.entity.SysRole;

/**
 * Role持久层接口
 * 
 * @author Administrator
 *
 */
public interface SysRoleDao {// SysRoleMapper.xml

	/**
	 * 查询所有角色的id,name字段的值，每行记录封装一个checbox对象
	 * 
	 * @return
	 */
	List<CheckBox> findObjects();

	/**
	 * 修改角色自身信息
	 * 
	 * @param entity
	 * @return
	 */
	int updateObject(SysRole entity);

	/**
	 * 基于角色id查询关系表中角色id对应菜单id
	 * 
	 * @param id
	 * @return
	 */
	SysRole findObjectById(Integer id);
	// SysRoleMenuResult findObjectById(Integer id);

	/**
	 * 保存角色自身信息
	 * 
	 * @param entity
	 * @return
	 */
	int insertObject(SysRole entity);

	/**
	 * 基于角色id删除角色自身信息
	 * 
	 * @param id
	 * @return 删除的行数
	 */
	int deleteObject(Integer id);

	/**
	 * 查询当前页数据
	 * 
	 * @param name
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	List<SysRole> findPageObjects(@Param("name") String name, @Param("startIndex") Integer startIndex,
			@Param("pageSize") Integer pageSize);

	/**
	 * 依据条件查询总记录数(要依据这个值计算总页数)
	 * 
	 * @param name 当参数应用在了动态sql中时,这个参数最好使用
	 * @Param 注解进行修饰.
	 */
	int getRowCount(@Param("name") String name);

}