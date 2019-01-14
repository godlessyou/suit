package com.yootii.bdy.department.test;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.customer.model.Customer;
import com.yootii.bdy.department.model.Department;
import com.yootii.bdy.department.service.DepartmentService;
import com.yootii.bdy.role.model.Role;
import com.yootii.bdy.user.model.User;
import com.yootii.bdy.user.service.UserService;
import com.yootii.bdy.util.JsonUtil;


@RunWith(SpringJUnit4ClassRunner.class)
//表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = { "classpath:spring-mybatis.xml" })
public class TestDeptService {
	@Resource
	private DepartmentService departmentService;
	private static final Logger logger = Logger.getLogger(TestDeptService.class);

	@Test
	public void queryDeptListTest() {
		Department department = new Department();
		GeneralCondition gcon = new GeneralCondition();
//		gcon.setKeyword("test");
		gcon.setPageNo(1);
		gcon.setOffset(0);
		gcon.setRows(10);
		User caller= new User();
		caller.setUserId(3);
//		caller.setUserType(2);
		Object info = departmentService.queryDepts(caller);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("queryUserList测试通过");
		}
	}
//	@Test
//	public void createDeptTest() {
//		Department department = new Department();
//		department.setName("专利部");
//		department.setLevel((short)0);
//		GeneralCondition gcon = new GeneralCondition();
////		gcon.setKeyword("test");
//		gcon.setPageNo(1);
//		gcon.setOffset(0);
//		gcon.setRows(10);
//		User caller= new User();
//		caller.setUserId(3);
////		caller.setUserType(2);
//		Object info = departmentService.createDept(department, caller);
//		if (info != null) {
//			logger.info(JsonUtil.toJson(info));
//			logger.info("queryUserList测试通过");
//		}
//	}
//	@Test
	public void modifyDeptTest() {
		Department department = new Department();
		department.setId(7);
		department.setName("赵菲部");
		department.setLevel((short)2);
		department.setParent(3);
		GeneralCondition gcon = new GeneralCondition();
//		gcon.setKeyword("test");
		gcon.setPageNo(1);
		gcon.setOffset(0);
		gcon.setRows(10);
		User caller= new User();
		caller.setUserId(3);
//		caller.setUserType(2);
		Object info = departmentService.modifyDept(department);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("queryUserList测试通过");
		}
	}
//	@Test
	public void deleteDeptTest() {
		Department department = new Department();
		department.setId(8);
		department.setName("赵菲部");
		department.setLevel((short)2);
		department.setParent(3);
		GeneralCondition gcon = new GeneralCondition();
//		gcon.setKeyword("test");
		gcon.setPageNo(1);
		gcon.setOffset(0);
		gcon.setRows(10);
		User caller= new User();
		caller.setUserId(3);
//		caller.setUserType(2);
		Object info = departmentService.deleteDept(department);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("queryUserList测试通过");
		}
	}
	
	@Test
	public void queryDeptByUserIdTest() {
		Object info = departmentService.queryDepartmentByUserIdAndLevel(4, 1);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("queryUserList测试通过");
		}
	}
	
	@Test
	public void queryDeptByParentTest() {
		Object info = departmentService.queryDepartmentByDeptId(1);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("queryUserList测试通过");
		}
	}
}
