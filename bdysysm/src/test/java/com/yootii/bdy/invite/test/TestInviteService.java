package com.yootii.bdy.invite.test;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.invite.model.AgencyInvite;
import com.yootii.bdy.invite.model.CustomerInvite;
import com.yootii.bdy.invite.service.AgencyInviteService;
import com.yootii.bdy.invite.service.CustomerInviteService;
import com.yootii.bdy.user.test.TestUserService;
import com.yootii.bdy.util.JsonUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-mybatis.xml" })
public class TestInviteService {
	@Resource
	public AgencyInviteService agencyInviteService;
	
	@Resource
	public CustomerInviteService customerInviteService;
	private static final Logger logger = Logger.getLogger(TestUserService.class);
	private MockHttpServletRequest request;    
	@Test
	public void queryAIListTest() {
		AgencyInvite agencyInvite = new AgencyInvite();
		GeneralCondition gcon = new GeneralCondition();
		gcon.setPageNo(1);
		gcon.setOffset(0);
		gcon.setRows(10);
		Object info = agencyInviteService.queryAgencyInviteList(agencyInvite, gcon);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("queryUserList测试通过");
		}
	}
//	@Test
	public void addAIListTest() {
		AgencyInvite agencyInvite = new AgencyInvite();
		agencyInvite.setEmail("kikdo@123.com");
		agencyInvite.setName("sdf");
		GeneralCondition gcon = new GeneralCondition();
		Object info = agencyInviteService.addAgencyInvite(agencyInvite);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("queryUserList测试通过");
		}
	}
//	@Test
	public void modifyAIListTest() {
		AgencyInvite agencyInvite = new AgencyInvite();
		agencyInvite.setId(16);
		agencyInvite.setEmail("zzsds@123.com");
		agencyInvite.setName("hhhh");
		GeneralCondition gcon = new GeneralCondition();
		Object info = agencyInviteService.modifyAgencyInviteStatus(agencyInvite);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("queryUserList测试通过");
		}
	}
//	@Test
	public void invateAgencyTest() {
		AgencyInvite agencyInvite = new AgencyInvite();
		agencyInvite.setId(15);
		request = new MockHttpServletRequest(); 
//		Object info = agencyInviteService.invateAgency(request, agencyInvite,"hhhh");
//		if (info != null) {
//			logger.info(JsonUtil.toJson(info));
//			logger.info("queryUserList测试通过");
//		}
	}
	
	@Test
	public void checkLinkOutdateTest() {
		AgencyInvite agencyInvite = new AgencyInvite();
		agencyInvite.setId(15);
		request = new MockHttpServletRequest();
		request.setParameter("sid", "1A073390E38B02204ADAC7B08CEFD157");
		request.setParameter("email", "zhangziwei@ipshine.com");
		Object info = agencyInviteService.checkLinkOutdate(request,null,null);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("queryUserList测试通过");
		}
	}
	
//	@Test
	public void invateCustTest() {
		CustomerInvite customerInvite = new CustomerInvite();
		customerInvite.setId(3);
		request = new MockHttpServletRequest(); 
		Object info = customerInviteService.invateCustomer(request, customerInvite, "hahhah",null,null);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("queryUserList测试通过");
		}
	}
	
	@Test
	public void checkLinkOutdateCustTest() {
		CustomerInvite customerInvite = new CustomerInvite();
		customerInvite.setId(3);
		request = new MockHttpServletRequest();
//		request.setParameter("sid", "C33E79355D3D6CEC3272B9B849237B26");
//		request.setParameter("email", "zhangziwei@ipshine.com");
//		request.setParameter("agencyId", "1");
		Object info = customerInviteService.checkLinkOutdate(request, "086B6D1C1657E506FEFF5BFC2B23D680", "zhangziwei@ipshine.com", 1);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("queryUserList测试通过");
		}
	}
}
