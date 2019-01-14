package com.yootii.bdy.customer.test;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.customer.model.Applicant;
import com.yootii.bdy.customer.model.Customer;
import com.yootii.bdy.customer.service.ApplicantService;
import com.yootii.bdy.customer.service.CustomerService;
import com.yootii.bdy.department.model.Department;
import com.yootii.bdy.department.service.DepartmentService;
import com.yootii.bdy.role.model.Role;
import com.yootii.bdy.user.model.User;
import com.yootii.bdy.user.service.UserService;
import com.yootii.bdy.util.JsonUtil;


@RunWith(SpringJUnit4ClassRunner.class)
//表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = { "classpath:spring-mybatis.xml" })
public class TestApplicantService {
	@Resource
	private ApplicantService ApplicantService;

	
	private static final Logger logger = Logger.getLogger(TestApplicantService.class);

	private MockHttpServletRequest request= new MockHttpServletRequest();
	
	
	@Test
	public void queryAllApplicantByCustIdTest() {
		
		GeneralCondition gcon = new GeneralCondition();
		gcon.setOffset(0);
		gcon.setRows(10);
		
		Integer custId=null;
		Object info = ApplicantService.queryAllApplicantByCustId(gcon,custId);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("queryAllApplicantByCustId测试通过");
		}
	}
	
	
//	@Test
	public void queryTmCountByAppIdListTest() {
		
		GeneralCondition gcon = new GeneralCondition();
		gcon.setOffset(0);
		gcon.setRows(4);
		
		String appIdList="14,15,16,19";
		Object info = ApplicantService.queryTmCountByAppIdList(gcon,appIdList);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("queryTmCountByAppIdList测试通过");
		}
	}
	
	

//	@Test
	public void queryOwnCustomerListTest() {
		Applicant applicant = new Applicant();
		
//		applicant.setApplicantAddress("");
//		applicant.setApplicantEnAddress("");
//		applicant.setApplicantEnName("");
//		applicant.setApplicantName("");

//		
//		Object info = ApplicantService.unBindApplicant(341);
		Object info = ApplicantService.selectAppAndMaterialByPrimaryKey(15);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("queryUserList测试通过");
		}
	}
	
	
	@Test
	public void createApplicantTest() {
		Applicant applicant = new Applicant();
		applicant.setApplicantName("达能(中国)食品饮料有限公司");		
		applicant.setApplicantAddress("广东省中山市小榄镇菊城大道东37号");
//		applicant.setApplicantEnAddress("eee Enaddress");
//		applicant.setApplicantEnName("");
		
		Integer customerId=3;

		GeneralCondition gcon = new GeneralCondition();

		gcon.setPageNo(1);
		gcon.setOffset(0);
		gcon.setRows(10);
//		
//		Object info = ApplicantService.unBindApplicant(341);
//		Object info = ApplicantService.ceateApplicant(applicant, gcon, customerId);
//		if (info != null) {
//			logger.info(JsonUtil.toJson(info));
//			logger.info("createApplicant测试通过");
//		}
	}
	
	
	@Test
	public void queryApplicantByCustIdTest() {
		
		Integer customerId=3;

		GeneralCondition gcon = new GeneralCondition();

		gcon.setPageNo(1);
		gcon.setOffset(0);
		gcon.setRows(10);
		gcon.setOrderAsc("asc");
		

		Object info = ApplicantService.queryApplicantByCustId(gcon, customerId);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("queryUserList测试通过");
		}
	}
	
	
//	@Test
	public void queryApplicantNameByCustIdTest() {
		
		Integer customerId=5;

		GeneralCondition gcon = new GeneralCondition();

		gcon.setPageNo(1);
		gcon.setOffset(0);
		gcon.setRows(10);
		gcon.setOrderAsc("asc");
		

		Object info = ApplicantService.queryApplicantNameByCustId(gcon, customerId);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("queryApplicantNameByCustId测试通过");
		}
	}
	
	
	
	
	
//	@Test
	public void modifyApplicantTest() {
		Applicant applicant = new Applicant();
		
		applicant.setId(395);
		applicant.setApplicantAddress("teste22 address");
		
		Integer customerId=6;

		GeneralCondition gcon = new GeneralCondition();


//		Object info = ApplicantService.motifyApplicant(applicant, gcon, customerId);
//		if (info != null) {
//			logger.info(JsonUtil.toJson(info));
//			logger.info("modifyApplicantTest测试通过");
//		}
	}
	
	
	
	
	@Test
	public void bindApplicantTest() {

		
//		String idlist="399";
//
//		Integer mainId=8;
//
//		Object info = ApplicantService.bindApplicant(idlist, mainId);
//		if (info != null) {
//			logger.info(JsonUtil.toJson(info));
//			logger.info("modifyApplicantTest测试通过");
//		}
	}
	

}
