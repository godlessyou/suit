package com.yootii.bdy.role.test;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.permission.model.Permission;
import com.yootii.bdy.permission.service.PermissionService;
import com.yootii.bdy.role.model.Role;
import com.yootii.bdy.user.model.User;
import com.yootii.bdy.user.service.UserService;
import com.yootii.bdy.util.JsonUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-mybatis.xml" })
public class TestPermissionService {
	@Resource
	private PermissionService permissionService;
	@Resource
	private UserService userService;
	private static final Logger logger = Logger.getLogger(TestPermissionService.class);
	
	@Test	
	public void queryPermissionListTest() {
		Permission permission = new Permission();
		//permission.setPermission("role");
		permission.setAgencyId(1);
		User user=new User();
		user.setUserId(1);
		user.setUsername("test");
		GeneralCondition gcon = new GeneralCondition();
		gcon.setOffset(0);
		gcon.setRows(10);
		logger.info(JsonUtil.toJson(permission));
		//gcon.setTokenID(getToken().getTokenID());
		gcon.setPageNo(1);
		System.out.println(1);
		Object info=null;
		//roleService.createRole(role, gcon, user);
		 info = permissionService.queryPermissionList(permission, gcon,"permission"); 
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("queryPermissionListTest测试通过");
		}
	}
	
	@Test	
	public void ceatePermissionTest() {
		Permission permission = new Permission();
		permission.setPermission("2");
		permission.setAgencyId(1);
		User user=new User();
		user.setUserId(1);
		user.setUsername("test");
		GeneralCondition gcon = new GeneralCondition();
		gcon.setOffset(0);
		gcon.setRows(10);
		logger.info(JsonUtil.toJson(permission));
		//gcon.setTokenID(getToken().getTokenID());
		gcon.setPageNo(1);
		System.out.println(1);
		Object info=null;
		//roleService.createRole(role, gcon, user);
		 info = permissionService.createPermission(permission);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("ceatePermissionTest测试通过");
		}
	}
	
	@Test	
	public void motifyPermissionTest() {
		Permission permission = new Permission();
		permission.setId(10);
		permission.setPermission("role:delete");
		User user=new User();
		user.setUserId(1);
		user.setUsername("test");
		GeneralCondition gcon = new GeneralCondition();
		gcon.setOffset(0);
		gcon.setRows(10);
		logger.info(JsonUtil.toJson(permission));
		//gcon.setTokenID(getToken().getTokenID());
		gcon.setPageNo(1);
		System.out.println(1);
		Object info=null;
		//roleService.createRole(role, gcon, user);
		 info = permissionService.modifyPermission(permission);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("motifyPermissionTest测试通过");
		}
	}
	
	//@Test	
	public void deletePermissionTest() {
		Permission permission = new Permission();
		permission.setId(7);
		permission.setPermission("role");
		User user=new User();
		user.setUserId(1);
		user.setUsername("test");
		GeneralCondition gcon = new GeneralCondition();
		gcon.setOffset(0);
		gcon.setRows(10);
		logger.info(JsonUtil.toJson(permission));
		//gcon.setTokenID(getToken().getTokenID());
		gcon.setPageNo(1);
		System.out.println(1);
		Object info=null;
		//roleService.createRole(role, gcon, user);
		 info = permissionService.deletePermission(permission);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("deletePermissionTest测试通过");
		}
	}
	
	@Test	
	public void hasPermissionTest() {
		/*Permission permission = new Permission();
		permission.setId(7);
		permission.setPermission("role:create");
		User user=new User();
		user.setUserId(1);
		user.setUsername("test");
		GeneralCondition gcon = new GeneralCondition();
		gcon.setOffset(0);
		gcon.setRows(10);
		logger.info(JsonUtil.toJson(permission));
		//gcon.setTokenID(getToken().getTokenID());
		gcon.setPageNo(1);
		System.out.println(1);*/
		User user=new User();
		user.setUserId(1);
		Object info=null;
		//roleService.createRole(role, gcon, user);
		 info = userService.hasPermission("代理机构开通:启动", user);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("测试通过");
		}
	}
}
