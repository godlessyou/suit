package com.yootii.bdy.ipservice.test;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yootii.bdy.agency.service.AgencyContactService;
import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.ipservice.model.AgencyService;
import com.yootii.bdy.ipservice.model.PlatformService;
import com.yootii.bdy.ipservice.service.AgencyServiceService;
import com.yootii.bdy.security.model.Token;
import com.yootii.bdy.util.JsonUtil;

@RunWith(SpringJUnit4ClassRunner.class)
//表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = { "classpath:spring-mybatis.xml" })
public class TestAgencyService {
	private static final Logger logger = Logger.getLogger(TestAgencyService.class);
	@Resource
	private AgencyServiceService agencyServiceService;
	
	@Resource
	private AgencyContactService agencyContactService;
	
	@Test
	public void queryAgencyContactByIdTest() {
		
		Integer agencyId = 1;
		Integer custId = 5;
		Object info = agencyContactService.queryAgencyContactById(agencyId, custId);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("create测试通过");
		}
	}
	
	
	
//	@Test
	public void createServiceTest() {
		AgencyService agencyService = new AgencyService();
		agencyService.setPrice(new BigDecimal(1235));
		agencyService.setCurrency("人民币");
		Integer userId = 27;
		Object info = agencyServiceService.createAgencyService(agencyService, userId,1,11);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("create测试通过");
		}
	}
//	@Test
	public void modifyServiceTest() {
		AgencyService agencyService = new AgencyService();
		agencyService.setAgencyServiceId(11);
		agencyService.setServiceId(1);
		agencyService.setPrice(new BigDecimal(1235.4));
		agencyService.setReduction(new BigDecimal(20.6));
		agencyService.setCurrency("人民币");
		Integer userId = 3;
		Object info = agencyServiceService.modifyAgencyService(agencyService, userId);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("modify测试通过");
		}
	}
//	@Test
	public void deleteServiceTest() {
		AgencyService agencyService = new AgencyService();
		agencyService.setAgencyServiceId(9);
		agencyService.setServiceId(1);
		agencyService.setPrice(new BigDecimal(1235.234));
		agencyService.setReduction(new BigDecimal(20.456));
		Integer userId = 3;
		Object info = agencyServiceService.deleteAgencyService(agencyService, userId);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("delete测试通过");
		}
	}
//	@Test
	public void queryServiceListTest() {
		AgencyService agencyService = new AgencyService();
		agencyService.setAgencyServiceId(11);
//		agencyService.setServiceId(1);
		agencyService.setPrice(new BigDecimal(1235.234));
		agencyService.setReduction(new BigDecimal(20.456));
		GeneralCondition gcon = new GeneralCondition();
		gcon.setOffset(0);
		gcon.setRows(10);
		Token token = new Token();
		token.setUserID(3);
		Object info = agencyServiceService.queryAgencyServiceList(agencyService, gcon, token, null);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("queryList测试通过");
		}
	}
	@Test
	public void queryServiceDetailTest() {
		AgencyService agencyService = new AgencyService();
		agencyService.setAgencyServiceId(1);
//		agencyService.setServiceId(1);
//		agencyService.setPrice(new BigDecimal(1235.234));
//		agencyService.setReduction(new BigDecimal(20.456));
		Object info = agencyServiceService.queryAgencyServiceDetail(agencyService);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("queryDetail测试通过");
		}
	}
//	@Test
	public void custQueryServiceTest() {
		AgencyService agencyService = new AgencyService();
//		agencyService.setServiceId(1);
		GeneralCondition gcon = new GeneralCondition();
		gcon.setOffset(0);
		gcon.setRows(10);
		Token token = new Token();
		token.setCustomerID(2);
		Object info = agencyServiceService.custQueryAgencyServiceList(agencyService, gcon, token);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("queryDetail测试通过");
		}
	}
	//@Test
	public void custQueryAgencyServiceListByCaseTypeIdTest() {
		GeneralCondition gcon = new GeneralCondition();
		gcon.setOffset(0);
		gcon.setRows(10);
		Token token = new Token();
		token.setCustomerID(6);
		Object info = agencyServiceService.custQueryAgencyServiceListByCaseTypeId(1, gcon, token);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("queryDetail测试通过");
		}
	}
	
	
	@Test
	public void queryAgencyServiceTest() {
		Object info = agencyServiceService.queryAgencyService(12, 1, 12);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("queryDetail测试通过");
		}
	}
}
