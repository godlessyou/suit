package com.yootii.bdy.role.test;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.permission.model.Permission;
import com.yootii.bdy.role.model.Role;
import com.yootii.bdy.role.model.RolePermission;
import com.yootii.bdy.role.service.RoleService;
import com.yootii.bdy.user.model.User;
import com.yootii.bdy.user.service.UserService;
import com.yootii.bdy.util.JsonUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring-mybatis.xml" })
public class TestRoleService {
	@Resource
	private RoleService roleService;
	@Resource
	private UserService userService;
	private static final Logger logger = Logger.getLogger(TestRoleService.class);
	@Test	
	public void queryRoleListTest() {
		Role role = new Role();
		role.setId(1);
		role.setName("代理人");
		User user=new User();
		user.setUserId(1);
		user.setUsername("test");
		GeneralCondition gcon = new GeneralCondition();
		gcon.setOffset(0);
		gcon.setRows(10);
		logger.info(JsonUtil.toJson(role));
		//gcon.setTokenID(getToken().getTokenID());
		gcon.setPageNo(1);
		System.out.println(1);
		Object info=null;
		//roleService.createRole(role, gcon, user);
		 info = roleService.queryRoleList(role, gcon);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("queryRoleListTest测试通过");
		}
	}
	//@Test	
	public void createRoleListTest() {
		Role role = new Role();
		role.setName("代理机人");
		role.setAgencyId(1);
		User user=new User();
		user.setUserId(1);
		user.setUsername("test");
		Permission permission=new Permission();
		permission.setPermission("role");
		List<Permission> permissions=new ArrayList<Permission>();
		permissions.add(permission);
		role.setPermission(permissions);
		GeneralCondition gcon = new GeneralCondition();
		logger.info(JsonUtil.toJson(role));
		//gcon.setTokenID(getToken().getTokenID());
		gcon.setPageNo(1);
		System.out.println(1);
		Object info=null;
		//roleService.createRole(role, gcon, user);
		 info = roleService.createRole(role);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("createRoleListTest测试通过");
		}
	}
	@Test	
	public void modifyRoleListTest() {
		Role role = new Role();
		role.setId(11);
		role.setName("代理机构管理员2");
		role.setAgencyId(2);
		User user=new User();
		user.setUserId(1);
		user.setUsername("test");
		Permission permission=new Permission();
		permission.setPermission("role");
		List<Permission> permissions=new ArrayList<Permission>();
		permissions.add(permission);
		role.setPermission(permissions);
		GeneralCondition gcon = new GeneralCondition();
		logger.info(JsonUtil.toJson(role));
		//gcon.setTokenID(getToken().getTokenID());
		gcon.setPageNo(1);
		System.out.println(1);
		Object info=null;
		//roleService.createRole(role, gcon, user);
		 info = roleService.modifyRole(role);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("modifyRoleListTest测试通过");
		}
	}
	//@Test	
	public void deleteroleTest() {
		Role role = new Role();
		role.setId(10);
		role.setName("代理机构管理员");
		role.setAgencyId(1);
		User user=new User();
		user.setUserId(1);
		user.setUsername("test");
		Permission permission=new Permission();
		permission.setPermission("role");
		List<Permission> permissions=new ArrayList<Permission>();
		permissions.add(permission);
		role.setPermission(permissions);
		GeneralCondition gcon = new GeneralCondition();
		logger.info(JsonUtil.toJson(role));
		//gcon.setTokenID(getToken().getTokenID());
		gcon.setPageNo(1);
		System.out.println(1);
		Object info=null;
		//roleService.createRole(role, gcon, user);
		 info = roleService.deleteRole(role);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("deleteroleTest测试通过");
		}
	}
	//@Test	
	public void bindresource() {
		RolePermission rolePermission = new RolePermission();
		Object info=null;
		rolePermission.setPermissionId(10);
		rolePermission.setRoleId(10);
		//roleService.createRole(role, gcon, user);
		 info = roleService.bindrePermission(rolePermission);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("bindresource测试通过");
		}
	}
	//@Test	
	public void unbindresource() {
		RolePermission rolePermission = new RolePermission();
		Object info=null;
		rolePermission.setPermissionId(10);
		rolePermission.setRoleId(10);
		//roleService.createRole(role, gcon, user);
		 info = roleService.unbindPermission(rolePermission);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("queryApplicantList测试通过");
		}
	}
	
	@Test	
	public void hasRoleTest() {
		Role role =new Role();
		role.setName("代理机构管理员1");
		User user=new User();
		user.setUserId(1);
		user.setUsername("test");
		//roleService.createRole(role, gcon, user);
		Object info = userService.hasRole("代理机构管理员1", user);
		//Object info=userService.getUserByUsername("test");
		//Object info = roleService.getRoleByUserName("test");
		//Object info = roleService.getRoleByUserId(1);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("hasRoleTest测试通过");
		}
	}
}
