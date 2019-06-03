package com.jt.sys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.jt.common.annotation.RequiredCache;
import com.jt.common.annotation.RequiredClearCache;
import com.jt.common.annotation.RequiredLog;
import com.jt.common.exception.ServiceException;
import com.jt.common.vo.PageObject;
import com.jt.sys.dao.SysUserDao;
import com.jt.sys.dao.SysUserRoleDao;
import com.jt.sys.entity.SysUser;
import com.jt.sys.service.SysUserService;
import com.jt.sys.vo.SysUserDeptResult;

@Transactional(rollbackFor=Throwable.class)//共性事务
@Service
public class SysUserServiceImpl implements SysUserService {
	@Autowired
	private SysUserDao sysUserDao;
	@Autowired
	private SysUserRoleDao sysUserRoleDao;
	
	//事务隔离级别：
	//事务隔离级别越高，并发性越差
	//通常默认成 @Transactional(isolation=Isolation.READ_COMMITTED)
	//READ_COMMITTED 可以提交其它用户未提交的数据
	//1)READ_UNCOMMITTED (此级别可能会出现脏读)
	//2)READ_COMMITTED(此级别可能会出现不可重复读)
	//3)REPEATABLE_READ(此级别可能会出现幻读)
	//4)SERIALIZABLE(多事务串行执行)
	@Transactional(isolation=Isolation.READ_COMMITTED)
	@Override
	public Map<String, Object> findObjectById(Integer id) {
		//1.参数有效性验证
		if(id==null||id<1)
			throw new IllegalArgumentException("id值不合法");
		//2.查询用户以及关联的部门信息
		SysUserDeptResult user = sysUserDao.findObjectById(id);
		if(user==null)
			throw new ServiceException("此用户已经不存在");
		//3.查询用户对应的角色新信息
		List<Integer> roleIds =sysUserRoleDao.findRoleIdsByUserId(id);
		//4.封装数据
		Map<String,Object> map = new HashMap<>();
		map.put("user", user);
		map.put("roleIds", roleIds);
		return map;
	}
	
	@RequiredCache
	@RequiredLog(operation="query")
	@Override
	public PageObject<SysUserDeptResult> findPageObjects(
			String username, Integer pageCurrent) {
		//1.数据合法性验证
		if(pageCurrent==null||pageCurrent<=0)
			throw new ServiceException("参数不合法");
		//2.依据条件获取总记录数
		int rowCount=sysUserDao.getRowCount(username);
		if(rowCount==0)
			throw new ServiceException("记录不存在");
		//3.计算startIndex的值
		int pageSize=3;
		int startIndex=(pageCurrent-1)*pageSize;
		//4.依据条件获取当前页数据
		List<SysUserDeptResult> records = sysUserDao.findPageObjects(
				username, startIndex, pageSize);
		//5.封装数据
		PageObject<SysUserDeptResult> pageObject = new PageObject<>();
		pageObject.setPageCurrent(pageCurrent);
		pageObject.setRowCount(rowCount);
		pageObject.setPageSize(pageSize);
		pageObject.setRecords(records);
		return pageObject;
	}
	/**
	 * 通过此注解，告诉系统，执行此方法需要进行授权操作
	 * 1)获取用户权限(登录用户):{"sys:user:update","sys:user:valid"}
	 * 2)获取注解内部定义的权限标识，例如:"sys:user:valid"
	 * 3)判定用户权限中是否包含注解内部定义的权限标识
	 * 
	 * 系统访问此方法是会获取Subject对象，然后执行如下调用
	 * Subject.isPermitted("sys:user:valid")
	 * 通过此方法验证用户是否有权限访问
	 * 
	 * @param id
	 * @param valid
	 * @param modifiedUser
	 * @return
	 */
	@RequiresPermissions("sys:user:valid")
	@Override
	public int validById(
			Integer id, Integer valid, String modifiedUser) {
		//1.有效性验证
		if(id==null||id<1)
			throw new IllegalArgumentException("id值无效："+id);
		if(valid==null||(valid!=1&&valid!=0))
			throw new IllegalArgumentException("用户状态值不合法"+valid);
//		if(StringUtils.isEmpty(modifiedUser))
//			throw new ServiceException("修改用户不能为空");
		//2.执行禁用或启动操作
		int rows=0;
		try {
			rows=sysUserDao.validById(id, valid, modifiedUser);
		} catch (Exception e) {
			e.printStackTrace();
			//报警，给维护人员发短信
			throw new ServiceException("正在维护");
		}
		if(rows==0)
			throw new ServiceException("此记录可能已经不存在");
		return rows;
	}
	@RequiredLog(operation="保存用户信息")
	@Override
	public int saveObject(SysUser entity, Integer[] roleIds) {
		//1.验证数据合法性
		if(entity==null)
			throw new IllegalArgumentException("保存对象不能为空");
		if(StringUtils.isEmpty(entity.getUsername()))
			throw new IllegalArgumentException("用户名不能为空");
		if(StringUtils.isEmpty(entity.getPassword()))
			throw new IllegalArgumentException("密码不能为空");
		if(entity.getDeptId()==null)
			throw new IllegalArgumentException("必须为用户指定部门");
		if(roleIds==null || roleIds.length==0)
			throw new ServiceException("至少要为用户分配角色");
		//其它验证...
		//2.对密码进行加密
		//2.1获取一个盐值(salt):借助UUID获得一个随机数
		String salt = UUID.randomUUID().toString();
		System.out.println("salt="+salt);
		//2.2对密码进行加密(借助Shiro框架API):MD5加密(消息摘要)
		SimpleHash sHash = new SimpleHash("MD5",//algorithmName(算法名称)
				entity.getPassword(),//Source(原密码)
				salt);//salt盐值
		String hashPassword = sHash.toHex();//将加密结果转换
		System.out.println("hashPassword"+hashPassword);
		//entity.setPassword(sHash.toHex());
		entity.setSalt(salt);
		entity.setPassword(hashPassword);
		//2.4设置对象其它属性默认值
		//entity.setCreatedTime(new Date());
		//entity.setModifiedTime(new Date());
		//2.5保存用户自身信息
		System.out.println("deptId="+entity.getDeptId());
		int rows=sysUserDao.insertObject(entity);
		//2.6保存用户与角色的关系数据
		sysUserRoleDao.insertObject(entity.getId(), roleIds);
		return rows;
	}

	@RequiredLog(operation="更新用户信息")
	@RequiredClearCache
	@Override
	public int updateObject(SysUser entity, Integer[] roleIds) {
		//1.验证数据合法性
		if(entity==null)
			throw new ServiceException("保存对象不能为空");
		if(StringUtils.isEmpty(entity.getUsername()))
			throw new ServiceException("用户名不能为空");
		if(roleIds==null || roleIds.length==0)
			throw new ServiceException("至少要为用户分配角色");
		//2.5保存用户自身信息
		int rows = sysUserDao.updateObject(entity);
		//2.6保存用户与角色的关系数据
		sysUserRoleDao.deleteObjectsByUserId(entity.getId());
		sysUserRoleDao.insertObject(entity.getId(), roleIds);
		return rows;
	}
}