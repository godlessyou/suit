package com.yootii.bdy.user.test;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yootii.bdy.agency.model.AgencyCustomer;
import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.common.LoginReturnInfo;
import com.yootii.bdy.common.ReturnInfo;
import com.yootii.bdy.customer.model.Customer;
import com.yootii.bdy.department.model.Department;
import com.yootii.bdy.permission.model.Permission;
import com.yootii.bdy.role.model.Role;
import com.yootii.bdy.security.service.AuthenticationService;
import com.yootii.bdy.user.controller.UserController;
import com.yootii.bdy.user.model.User;
import com.yootii.bdy.user.service.UserResetPassWordService;
import com.yootii.bdy.user.service.UserService;
import com.yootii.bdy.util.JsonUtil;



@RunWith(SpringJUnit4ClassRunner.class)
//表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = { "classpath:spring-mybatis.xml" })
public class TestUserService {
	@Resource
	private UserService userService;
	
	@Resource
	private UserResetPassWordService userResetPassWordService;
	
	private static final Logger logger = Logger.getLogger(TestUserService.class);
	
	private MockHttpServletRequest request= new MockHttpServletRequest();
	
	@Resource
	private  AuthenticationService authenticationService;
	
	
	
	
	@Test
	public void loginTest() {
		
		User user=new User();
		user.setUsername("whd_wangfang");
		user.setPassword("123456");
		GeneralCondition gcon = new GeneralCondition();

		gcon.setPageNo(1);
		gcon.setOffset(0);
		gcon.setRows(10);
		Object info = authenticationService.login(user, gcon);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("loginTest测试通过");
		}
	}

//	@Test
	public void queryUserListTest() {
		User user = new User();
		user.setUsername("test");
		//user.setUserType(10);
		GeneralCondition gcon = new GeneralCondition();
//		gcon.setKeyword("test");
		gcon.setPageNo(1);
		gcon.setOffset(0);
		gcon.setRows(10);
		User caller= new User();
//		caller.setUserId(33);
//		caller.setUserType(2);
		Object info = userService.queryUserList(user, gcon,caller);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("queryUserList测试通过");
		}
	}
//	@Test
	public void queryUserDetailTest() {
		User user = new User();
		user.setUserId(3);
		//user.setUserType(10);
		GeneralCondition gcon = new GeneralCondition();
//		gcon.setKeyword("test");
		gcon.setPageNo(1);
		gcon.setOffset(0);
		gcon.setRows(10);
		User caller= new User();
//		caller.setUserId(33);
//		caller.setUserType(2);
		Object info = userService.getUserByPrimaryKey(3);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("queryUserList测试通过");
		}
	}
//	@Test
	public void queryAgencyUserListTest() {
		User user = new User();
		user.setUsername("test");
		//user.setUserType(10);
		GeneralCondition gcon = new GeneralCondition();
//		gcon.setKeyword("test");
		gcon.setPageNo(1);
		gcon.setOffset(0);
		gcon.setRows(10);
		User caller= new User();
		caller.setUserId(1);
//		caller.setUserType(2);
		Object info = userService.queryAgencyUserList(user, gcon, caller);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("queryUserList测试通过");
		}
	}
	
	
	@Test
	public void queryAgencyUserByFullNameTest() {
		User user = new User();
		user.setFullname("张亚静;王莹燕");
		//user.setUserType(10);
		GeneralCondition gcon = new GeneralCondition();
//		gcon.setKeyword("test");
		gcon.setPageNo(1);
		gcon.setOffset(0);
		gcon.setRows(10);
	
		Integer agencyId=1;
		Object info = userService.queryAgencyUserByFullName(user, gcon, agencyId);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("queryAgencyUserByFullName 测试通过");
		}
	}
	//代理机构管理员创建用户
//	@Test
	public void createUserTest(){
		User user = new User();
		user.setUsername("测试");
		user.setEmail("zhangziwei@ipshine.com");
		user.setEmailPass("123456789");
		//user.setUserType(10);
		GeneralCondition gcon = new GeneralCondition();
		gcon.setTokenID("152646939043185");
		gcon.setPageNo(1);
		gcon.setOffset(0);
		gcon.setRows(10);
		User caller= new User();
		caller.setUserId(3);
//		caller.setUserType(2);
		List<Role> roles = new ArrayList<Role>();
		Role role1 = new Role();
		role1.setId(5);
		Role role2 = new Role();
		role2.setId(8);
		roles.add(role1);
		roles.add(role2);
		user.setRoles(roles);
		Object info = userService.createUser(user, caller,gcon);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("queryUserList测试通过");
		}
	}
	//修改用户
	//@Test
	public void modifyUserTest(){
		User user = new User();
		user.setUserId(4);
		user.setUsername("lisi");
		user.setEmail("zhangziwei@ipshine.com");
		user.setUserType(10);
		GeneralCondition gcon = new GeneralCondition();
//		gcon.setKeyword("test");
		gcon.setPageNo(1);
		gcon.setOffset(0);
		gcon.setRows(10);
		User caller= new User();
		caller.setUserId(1);
//		caller.setUserType(2);
		List<Role> roles = new ArrayList<Role>();
		Role role1 = new Role();
		role1.setId(1);
		Role role2 = new Role();
		role2.setId(2);
		roles.add(role1);
		roles.add(role2);
		user.setRoles(roles);
		
		List<Customer> customers = new ArrayList<Customer>();
		Customer c1 = new Customer();
		c1.setId(1);
		Customer c2 = new Customer();
		c2.setId(2);
		customers.add(c1);
		customers.add(c2);
		user.setCustomers(customers);
		
		List<Department> departments = new ArrayList<Department>();
		Department de1 = new Department();
		de1.setId(1);
		Department de2 = new Department();
		de2.setId(2);
		departments.add(de1);
		departments.add(de2);
		user.setDepartments(departments);
		Object info = userService.modifyUser(user);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("queryUserList测试通过");
		}
	}
	//移除用户
	//@Test
	public void removeUserTest(){
		User user = new User();
		user.setUserId(4);
		user.setUsername("lisi");
		user.setEmail("zhangziwei@ipshine.com");
		user.setUserType(10);
		GeneralCondition gcon = new GeneralCondition();
		//			gcon.setKeyword("test");
		gcon.setPageNo(1);
		gcon.setOffset(0);
		gcon.setRows(10);
		User caller= new User();
		caller.setUserId(1);
		//			caller.setUserType(2);
		Object info;
		try {
			info = userService.removeUser(user);
			if (info != null) {
				logger.info(JsonUtil.toJson(info));
				logger.info("queryUserList测试通过");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	//删除用户
	//@Test
	public void deleteUserTest(){
		User user = new User();
		user.setUserId(76);
		user.setUsername("test126_com");
		user.setEmail("test@126.com");
		user.setUserType(10);
		GeneralCondition gcon = new GeneralCondition();
		//			gcon.setKeyword("test");
		gcon.setPageNo(1);
		gcon.setOffset(0);
		gcon.setRows(10);
		User caller= new User();
		caller.setUserId(1);
		caller.setUserType(1);
		Object info;
		try {
			info = userService.deleteUser(user, caller);
			if (info != null) {
				logger.info(JsonUtil.toJson(info));
				logger.info("queryUserList测试通过");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	//修改用户
//	@Test
	public void modifyUserSelfTest(){
		User user = new User();
		user.setUserId(68);
//		user.setUsername("zzw");
//		user.setEmail("zhangziwei@ipshine.com");
//		user.setUserType(1);
//		user.setPassword("123456");
		user.setEmailPass("XXX");
		GeneralCondition gcon = new GeneralCondition();
		//			gcon.setKeyword("test");
		gcon.setTokenID("15269720947723");
		gcon.setPageNo(1);
		gcon.setOffset(0);
		gcon.setRows(10);
		Object info = userService.modifyUserSelf(user,gcon);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("queryUserList测试通过");
		}
	}
	//@Test
	public void validCustTest() {
		User user = new User();
		user.setUserId(4);
		user.setUsername("zz");
		user.setEmail("zhangziwei7788@163.com");
		GeneralCondition gcon = new GeneralCondition();
		User caller= new User();
		Object info = userResetPassWordService.validUser(request, user, gcon);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("queryUserList测试通过");
		}
	}
	
	//@Test
	public void toFindPasswordTest() {
		request.setParameter("sid", "991A1844AD9E8F3D16AF09F7CCEE7D7C");
		request.setParameter("username", "zz");
		Object info = userResetPassWordService.toFindPassword(request);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("queryUserList测试通过");
		}
	}
	//@Test
	public void resetPasswordTest() {
		request.setParameter("sid", "991A1844AD9E8F3D16AF09F7CCEE7D7C");
		request.setParameter("username", "zz");
		request.setParameter("password", "123456");
		Object info = userResetPassWordService.resetPassword(request);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("queryUserList测试通过");
		}
	}
//	@Test
	public void selectUserByDeptIdTest() {
		GeneralCondition gcon = new GeneralCondition();
//		gcon.setKeyword("test");
		gcon.setPageNo(1);
		gcon.setOffset(0);
		gcon.setRows(10);
		Object info = userService.queryUserByDeptId(2,gcon);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("queryUserList测试通过");
		}
	}
	
	@Test
	public void hasPermissionTest() {
		User user = new User();
		user.setUserId(81);
		String permission="客户开通:启动";

		
		ReturnInfo rtnInfo = new ReturnInfo();
		boolean hasPermission = userService.hasPermission(permission, user);
	
		String result=hasPermission+"";
		Map<String, String> data=new HashMap<String, String>();
		data.put("hasPermission", result);				
		rtnInfo.setData(data);
		rtnInfo.setSuccess(true);
		if (rtnInfo != null) {
			logger.info(JsonUtil.toJson(rtnInfo));
			logger.info("hasPermission测试通过");
		}
		
	}
	
	//@Test
	public void testQueryAgencyUserListByPermission() {
		
		GeneralCondition gcon = new GeneralCondition();
//		gcon.setKeyword("test");
		gcon.setPageNo(1);
		gcon.setOffset(0);
		gcon.setRows(10);
				
		String permissionStr="案件分配";
		
		Permission permission=new Permission();
		
		permission.setPermission(permissionStr);
		permission.setAgencyId(1);
		
		ReturnInfo rtnInfo =userService.queryAgencyUserListByPermission(permission, gcon);
	
		if (rtnInfo != null) {
			logger.info(JsonUtil.toJson(rtnInfo));
			logger.info("queryAgencyUserListByPermission测试通过");
		}

	}
//	@Test
	public void testCheckUserInDept() {
		boolean rtnInfo =userService.checkUserInDept(10, "国内部");
		System.out.println(rtnInfo);
	}
	
//	@Test
//	public void testQueryUserByCustId() {
//		ReturnInfo rtnInfo =userService.queryUserByCustId(1);
//		
//		if (rtnInfo != null) {
//			logger.info(JsonUtil.toJson(rtnInfo));
//			logger.info("queryUserByCustId测试通过");
//		}
//	}
	
//	@Test
	public void testQueryUserByCustId() {
		AgencyCustomer agencyCustomer=new AgencyCustomer();
		agencyCustomer.setAgencyId(1);
		agencyCustomer.setCustId(3);
		ReturnInfo rtnInfo =userService.queryUserByCustId(agencyCustomer);
		
		if (rtnInfo != null) {
			logger.info(JsonUtil.toJson(rtnInfo));
			logger.info("queryUserByCustId测试通过");
		}
	}
	
	
	
	
}
