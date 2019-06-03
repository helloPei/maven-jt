package com.jt.sys.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.jt.common.exception.ServiceException;
import com.jt.common.vo.Node;
import com.jt.sys.dao.SysMenuDao;
import com.jt.sys.dao.SysRoleMenuDao;
import com.jt.sys.entity.SysMenu;
import com.jt.sys.service.SysMenuService;

/**
 * Menu业务层接口实现类
 * 
 * @author Administrator
 *
 */
@Service
public class SysMenuServiceImpl implements SysMenuService {
	@Autowired
	private SysMenuDao sysMenuDao;
	@Autowired
	private SysRoleMenuDao sysRoleMenuDao;

	@Override
	public int updateObject(SysMenu entity) {
		// 1.对参数进行有效性校验
		if (entity == null)
			throw new IllegalArgumentException("保存对象不能为空");
		if (StringUtils.isEmpty(entity.getName()))
			throw new IllegalArgumentException("菜单名不允许为空");
		// ....
		// 2.将参数持久化到数据库
		int rows = sysMenuDao.updateObject(entity);
		// 3.对结果进行校验
		if (rows == 0)
			throw new ServiceException("记录可能已经不存在");
		return rows;
	}

	@Override
	public int saveObject(SysMenu entity) {
		// 1.对参数进行有效性校验
		if (entity == null)
			throw new IllegalArgumentException("保存对象不能为空");
		if (StringUtils.isEmpty(entity.getName()))
			throw new IllegalArgumentException("菜单名不允许为空");
		// ....
		// 2.将参数持久化到数据库
		int rows = sysMenuDao.insertObject(entity);
		// 3.对结果进行校验
		if (rows != 1)
			throw new ServiceException("save error");
		return rows;
	}

	@Override
	public List<Node> findZtreeMenuNodes() {
		List<Node> list = sysMenuDao.findZtreeMenuNodes();
		if (list == null || list.size() == 0)
			throw new ServiceException("还有没有记录");
		return list;
	}

	@Override
	public int deleteObject(Integer id) {
		// 1.验证参数有效性
		if (id == null || id < 1)
			throw new IllegalArgumentException("id值无效");
		// 2.基于id统计菜单子元素的个数
		int count = sysMenuDao.getChildCount(id);
		// 3.判定是否有子元素,有则抛出异常,没有则删除
		if (count > 0)
			throw new ServiceException("请先删除子菜单");
		int rows = sysMenuDao.deleteObject(id);
		if (rows == 0)
			throw new ServiceException("此记录可能已经不存在");
		// 4.基于菜单id删除菜单与角色的关系数据
		sysRoleMenuDao.deleteObjectsByMenuId(id);
		// 5.返回结果
		return rows;
	}

	@Override
	public List<Map<String, Object>> findObjects() {
		List<Map<String, Object>> list = sysMenuDao.findObjects();
		if (list == null || list.size() == 0)
			throw new ServiceException("没有记录");
		return list;
	}

}