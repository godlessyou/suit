package com.yootii.bdy.role.test;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yootii.bdy.agency.model.Agency;
import com.yootii.bdy.agency.service.AgencyService;
import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.permission.model.Permission;
import com.yootii.bdy.user.model.User;
import com.yootii.bdy.util.JsonUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring-mybatis.xml" })
public class TestAgencyService {
	@Resource
	private AgencyService agencyService;
	private static final Logger logger = Logger.getLogger(TestAgencyService.class);
	

	
	@Test
	public void queryAgencyContactByIdTest() {
		
		GeneralCondition gcon = new GeneralCondition();
		Object info = agencyService.queryAgencyChannel(gcon);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("create测试通过");
		}
	}
	
//	@Test	
	public void queryAgencySimpleListTest() {
		Agency agency = new Agency();
		agency.setName("test");
		User user=new User();
		user.setUserId(1);
		user.setUsername("test");
		GeneralCondition gcon = new GeneralCondition();
		gcon.setOffset(0);
		gcon.setRows(10);
		logger.info(JsonUtil.toJson(agency));
		//gcon.setTokenID(getToken().getTokenID());
		gcon.setPageNo(1);
		System.out.println(1);
		Object info=null;
		//roleService.createRole(role, gcon, user);
		 info = agencyService.queryAgencySimpleList(agency, gcon);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("queryAgencySimpleListTest测试通过");
		}
	}
	
//	@Test	
	public void queryAgencyDetailTest() {
		Agency agency = new Agency();
		agency.setName("test");
		agency.setId(1);
		User user=new User();
		user.setUserId(3);
		user.setUsername("test");
		GeneralCondition gcon = new GeneralCondition();
		gcon.setOffset(0);
		gcon.setRows(10);
		logger.info(JsonUtil.toJson(agency));
		//gcon.setTokenID(getToken().getTokenID());
		gcon.setPageNo(1);
		System.out.println(1);
		Object info=null;
		//roleService.createRole(role, gcon, user);
		 info = agencyService.queryAgencyDetailByUser(agency, gcon, user);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("queryAgencySimpleListTest测试通过");
		}
	}
	
	@Test	
	public void createAgencyTest() {
		Agency agency = new Agency();
		agency.setName("测试代理机构111");
		User user=new User();
		user.setUserId(1);
		user.setUsername("test");
		GeneralCondition gcon = new GeneralCondition();
		gcon.setOffset(0);
		gcon.setRows(10);
		logger.info(JsonUtil.toJson(agency));
		//gcon.setTokenID(getToken().getTokenID());
		gcon.setPageNo(1);
		System.out.println(1);
		Object info=null;
		//roleService.createRole(role, gcon, user);
		 info = agencyService.createAgency(agency);
		 System.out.println(agency.getId());
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("queryAgencySimpleListTest测试通过");
		}
	}
	//@Test	
	public void deleteAgencyTest() {
		Agency agency = new Agency();
		agency.setName("测试代理机构1");
		agency.setId(1);
		agency.setAddress("北京");
		User user=new User();
		user.setUserId(1);
		user.setUsername("test");
		GeneralCondition gcon = new GeneralCondition();
		gcon.setOffset(0);
		gcon.setRows(10);
		logger.info(JsonUtil.toJson(agency));
		//gcon.setTokenID(getToken().getTokenID());
		gcon.setPageNo(1);
		System.out.println(1);
		Object info=null;
		//roleService.createRole(role, gcon, user);
		 info = agencyService.deleteAgency(agency);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("deleteAgencyTest测试通过");
		}
	}
	//@Test	
	public void modifyAgencyTest() {
		Agency agency = new Agency();
		agency.setName("测试代理机构1");
		agency.setId(1);
		agency.setAddress("北京");
		User user=new User();
		user.setUserId(1);
		user.setUsername("test");
		GeneralCondition gcon = new GeneralCondition();
		gcon.setOffset(0);
		gcon.setRows(10);
		logger.info(JsonUtil.toJson(agency));
		//gcon.setTokenID(getToken().getTokenID());
		gcon.setPageNo(1);
		System.out.println(1);
		Object info=null;
		//roleService.createRole(role, gcon, user);
		 info = agencyService.modifyAgency(agency, gcon, user);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("modifyAgencyTest测试通过");
		}
	}
}
