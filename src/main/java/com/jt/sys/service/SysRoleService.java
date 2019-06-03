package com.jt.sys.service;

import java.util.List;
import java.util.Map;

import com.jt.common.vo.CheckBox;
import com.jt.common.vo.PageObject;
import com.jt.sys.entity.SysRole;

/**
 * Role业务层接口
 * 
 * @author Administrator
 *
 */
public interface SysRoleService {

	/** 查询所有角色的id,name字段的值，每行记录封装一个checbox对象 */
	List<CheckBox> findObjects();

	/** 基于角色id查询自身信息以及关联的菜单id */
	Map<String, Object> findObjectById(Integer id);

	int updateObject(SysRole entity, Integer[] menuIds);

	int saveObject(SysRole entity, Integer[] menuIds);

	/** 基于id删除角色以及关系数据 */
	int deleteObject(Integer id);

	/** 分页查询角色信息 */
	PageObject<SysRole> findPageObjects(String name, Integer pageCurrent);

}