package com.test;

import java.util.List;

import org.junit.Test;

import com.jt.sys.dao.SysLogDao;
import com.jt.sys.entity.SysLog;

public class TestSysLogDao extends TestBase{
	
	@Test
	public void testFindPageObjects(){
		SysLogDao dao=
		ctx.getBean("sysLogDao", SysLogDao.class);
		List<SysLog> list=dao.findPageObjects("admin",
				0,//startIndex 
				3);//pageSize
		System.out.println(list.size());
	}
	@Test
	public void testGetRowCount(){
		//1.获取dao对象
		SysLogDao dao=
		ctx.getBean("sysLogDao", SysLogDao.class);
		//2.调用dao方法
		int count=
		dao.getRowCount("admin");
		//3.输出结果
		System.out.println(count);
	}
}




