package com.jt.sys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.jt.common.exception.ServiceException;
import com.jt.common.vo.CheckBox;
import com.jt.common.vo.PageObject;

import com.jt.sys.dao.SysRoleDao;
import com.jt.sys.dao.SysRoleMenuDao;
import com.jt.sys.dao.SysUserRoleDao;
import com.jt.sys.entity.SysRole;
import com.jt.sys.service.SysRoleService;

/**
 * Role业务层接口实现类
 * 
 * @author Administrator
 *
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {
	// 角色自身表
	@Autowired
	private SysRoleDao sysRoleDao;
	// 角色和菜单关系表
	@Autowired
	private SysRoleMenuDao sysRoleMenuDao;
	// 角色和用户关系表
	@Autowired
	private SysUserRoleDao sysUserRoleDao;

	@Override
	public List<CheckBox> findObjects() {
		return sysRoleDao.findObjects();
	}

	@Override
	public Map<String, Object> findObjectById(Integer id) {
		// 1.合法性验证
		if (id == null || id < 1)
			throw new IllegalArgumentException("id的值无效");
		// 2.基于id查询角色自身信息
		SysRole sysRole = sysRoleDao.findObjectById(id);
		if (sysRole == null)
			throw new ServiceException("此记录已经不存在");
		// 3.基于id查询角色对应的菜单id
		List<Integer> menuIds = sysRoleMenuDao.findMenuIdsByRoleId(id);
		// 3.封装查询结果
		Map<String, Object> map = new HashMap<>();
		map.put("role", sysRole);
		map.put("menuIds", menuIds);
		// 4.返回
		return map;
	}

	/**
	 * 保存角色以及角色和菜单关系数据
	 */
	@Override
	public int saveObject(SysRole entity, Integer[] menuIds) {
		// 1.合法性验证
		if (entity == null)
			throw new IllegalArgumentException("角色信息不能为空");
		if (StringUtils.isEmpty(entity.getName()))
			throw new IllegalArgumentException("角色名不能为空");
		if (menuIds == null || menuIds.length == 0)
			throw new IllegalArgumentException("必须为角色分配权限");
		// 2.保存角色自身信息
		int rows = sysRoleDao.insertObject(entity);
		// 3.保存角色和菜单的关系数据
		sysRoleMenuDao.insertObject(entity.getId(), menuIds);
		return rows;
	}

	@Override
	public int updateObject(SysRole entity, Integer[] menuIds) {
		// 1.合法性验证
		if (entity == null)
			throw new IllegalArgumentException("角色信息不能为空");
		if (StringUtils.isEmpty(entity.getName()))
			throw new IllegalArgumentException("角色不能为空");
		if (menuIds == null || menuIds.length == 0)
			throw new IllegalArgumentException("必须为角色分配一个资源");
		// 2.保存自身角色数据
		int rows = sysRoleDao.updateObject(entity);
		// 3.删除旧的角色和菜单关系
		sysRoleMenuDao.deleteObjectsByRoleId(entity.getId());
		// 4.保存角色和菜单新的关系数据
		sysRoleMenuDao.insertObject(entity.getId(), menuIds);
		return rows;
	}

	// 实现删除业务
	@Override
	public int deleteObject(Integer id) {
		// 1.验证参数的有效性
		if (id == null || id < 1)
			throw new IllegalArgumentException("参数值不正确");
		// 2.删除角色自身信息
		int rows = sysRoleDao.deleteObject(id);
		if (rows == 0)
			throw new ServiceException("记录可能已经不存在");
		// 3.删除角色和菜单关系数据
		sysRoleMenuDao.deleteObjectsByRoleId(id);
		// 4.删除用户和角色关系数据
		sysUserRoleDao.deleteObjectsByRoleId(id);
		// 5.返回数据
		return rows;
	}

	@Override
	public PageObject<SysRole> findPageObjects(String name, Integer pageCurrent) {
		// 1.参数有效性验证
		if (pageCurrent == null || pageCurrent < 1)
			throw new IllegalArgumentException("参数值无效");
		// 2.基于条件查询总记录数
		int rowCount = sysRoleDao.getRowCount(name);
		// 3.判定总记录数
		if (rowCount == 0)
			throw new ServiceException("记录不存在");
		// 4.查询当前页数据
		Integer pageSize = 2;
		Integer startIndex = (pageCurrent - 1) * pageSize;// 起始位置
		List<SysRole> records = sysRoleDao.findPageObjects(name, startIndex, pageSize);
		// 5.封装结果
		PageObject<SysRole> pageObject = new PageObject<>();
		pageObject.setRowCount(rowCount);
		pageObject.setPageCurrent(pageCurrent);
		pageObject.setPageSize(pageSize);
		pageObject.setRecords(records);
		// pageCount=?
		// int pageCount=(rowCount-1)/pageSize+1;
		// pageObject.setPageCount(pageCount);
		return pageObject;
	}
	
}