package com.yootii.bdy.applicant.test;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.customer.model.Applicant;
import com.yootii.bdy.customer.service.ApplicantService;
import com.yootii.bdy.util.JsonUtil;


@RunWith(SpringJUnit4ClassRunner.class)
//表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = { "classpath:spring-mybatis.xml" })
public class TestApplicantService {
	
	@Resource
	private ApplicantService applicantService;
	
	private static final Logger logger = Logger.getLogger(TestApplicantService.class);

	private MockHttpServletRequest request= new MockHttpServletRequest();
	
//	@Test
	public void queryApplicantTest() {
		Applicant applicant = new Applicant();
		applicant.setId(1);
		GeneralCondition gcon = new GeneralCondition();
		gcon.setKeyword("sss");
//		gcon.setKeyword("test");
		gcon.setPageNo(1);
		gcon.setOffset(0);
		gcon.setRows(10);
		//User caller= new User();
		//caller.setUserId(3);
//		caller.setUserType(2);
		Object info = applicantService.queryApplicant(applicant, gcon, 1);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("queryApplicantTest测试通过");
		}
	}
	
//	@Test
	public void queryApplicantDetilTest() {
		Applicant applicant = new Applicant();
		applicant.setId(1);
		GeneralCondition gcon = new GeneralCondition();
		gcon.setKeyword("sss");
//		gcon.setKeyword("test");
		gcon.setPageNo(1);
		gcon.setOffset(0);
		gcon.setRows(10);
		//User caller= new User();
		//caller.setUserId(3);
//		caller.setUserType(2);
		Object info = applicantService.queryApplicantDetail(applicant, gcon);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("queryApplicantDetilTest测试通过");
		}
	}
	
//	@Test
	public void createApplicantDetilTest() {
		Applicant applicant = new Applicant();
		applicant.setApplicantName("达能日尔维公司");
		applicant.setApplicantAddress("申请人1的地址");
		//applicant.setId(1);
		GeneralCondition gcon = new GeneralCondition();
		gcon.setKeyword("sss");
//		gcon.setKeyword("test");
		gcon.setPageNo(1);
		gcon.setOffset(0);
		gcon.setRows(10);
		//User caller= new User();
		//caller.setUserId(3);
//		caller.setUserType(2);
		Object info = applicantService.ceateApplicant(applicant, gcon, 1);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("queryApplicantDetilTest测试通过");
		}
	}
	
	//@Test
	public void modifyApplicantDetilTest() {
		Applicant applicant = new Applicant();
		applicant.setId(2);
		applicant.setApplicantName("申请人1");
		applicant.setApplicantAddress("申请人1的地址");
		applicant.setApplicantEnName("wws");
		//applicant.setId(1);
		GeneralCondition gcon = new GeneralCondition();
		gcon.setKeyword("sss");
//		gcon.setKeyword("test");
		gcon.setPageNo(1);
		gcon.setOffset(0);
		gcon.setRows(10);
		//User caller= new User();
		//caller.setUserId(3);
//		caller.setUserType(2);
		Integer customerId=6;
		
		Object info = applicantService.motifyApplicant(applicant, gcon, customerId);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("modifyApplicantDetilTest测试通过");
		}
	}
	
	//@Test
	public void deleteApplicantDetilTest() {
		Applicant applicant = new Applicant();
		applicant.setId(2);
		applicant.setApplicantName("申请人1");
		applicant.setApplicantAddress("申请人1的地址");
		applicant.setApplicantEnName("app1");
		//applicant.setId(1);
		GeneralCondition gcon = new GeneralCondition();
		gcon.setKeyword("sss");
//		gcon.setKeyword("test");
		gcon.setPageNo(1);
		gcon.setOffset(0);
		gcon.setRows(10);
		//User caller= new User();
		//caller.setUserId(3);
//		caller.setUserType(2);
		Object info = applicantService.deleteapplicant(applicant, gcon,1);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("deleteApplicantDetilTest测试通过");
		}
	}
	
	
	@Test
	public void getApplicantByNameTest() {
		Applicant applicant = new Applicant();

		applicant.setApplicantName("微软公司");
		Object info = applicantService.getApplicantByName(applicant);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("getApplicantByName测试通过");
		}
	}
	
}
