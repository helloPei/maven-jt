package com.jt.sys.service;

import java.util.List;
import java.util.Map;

import com.jt.common.vo.Node;
import com.jt.sys.entity.SysMenu;

/**
 * Menu业务层接口
 * 
 * @author Administrator
 *
 */
public interface SysMenuService {

	/** 将菜单信息按照一定的业务进行更新 */
	int updateObject(SysMenu entity);

	/** 将菜单信息按照一定的业务进行保存 */
	int saveObject(SysMenu entity);

	/** 查询菜单节点信息 */
	List<Node> findZtreeMenuNodes();

	/** 基于菜单id删除菜单元素 */
	int deleteObject(Integer id);

	/** 通过数据层对象获取所有菜单信息 */
	List<Map<String, Object>> findObjects();

}