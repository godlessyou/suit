package com.yootii.bdy.ipservice.test;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.ipservice.dao.PlatformServiceMapper;
import com.yootii.bdy.ipservice.model.PlatformService;
import com.yootii.bdy.ipservice.service.PlatformServiceService;
import com.yootii.bdy.util.JsonUtil;

@RunWith(SpringJUnit4ClassRunner.class)
//表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = { "classpath:spring-mybatis.xml" })
public class TestPlatformService {
	private static final Logger logger = Logger.getLogger(TestPlatformService.class);
	
	@Resource
	private PlatformServiceService platformServiceService;
	@Resource
	private PlatformServiceMapper platformServiceMapper;
	private MockHttpServletRequest request= new MockHttpServletRequest();
	//@Test
	public void createServiceTest() {
		PlatformService platformService = new PlatformService();
		platformService.setServiceName("高级服务");
		platformService.setCaseType("撤销通用申请");
		platformService.setServiceDesc("代理人帮助撤销服务");
		platformService.setServiceType(12);
		Object info = platformServiceService.createPlatformService(request, platformService);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("create测试通过");
		}
	}
//	@Test
	public void modifyServiceTest() {
		PlatformService platformService = new PlatformService();
		platformService.setServiceId(12);
		platformService.setServiceName("测试XX");
		platformService.setServiceDesc("这是一个测试");
		Object info = platformServiceService.modifyPlatformService(request, platformService);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("modify测试通过");
		}
	}
	@Test
	public void deleteServiceTest() {
		PlatformService platformService = new PlatformService();
		platformService.setServiceId(12);
		platformService.setServiceName("测试XX");
		platformService.setServiceDesc("这是一个测试");
		Object info = platformServiceService.deletePlatformService(platformService);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("delete测试通过");
		}
	}
	@Test
	public void queryServiceListTest() {
		PlatformService platformService = new PlatformService();
		//platformService.setServiceName("注册");
		GeneralCondition gcon = new GeneralCondition();
		gcon.setOffset(0);
		gcon.setRows(10);
 		Object info = platformServiceService.queryPlatformServiceList(platformService, gcon);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("queryList测试通过");
		}
	}
	@Test
	public void queryServiceDetailTest() {
		PlatformService platformService = new PlatformService();
		platformService.setServiceId(11);
		GeneralCondition gcon = new GeneralCondition();
		gcon.setOffset(0);
		gcon.setRows(10);
 		Object info = platformServiceService.queryPlatformServiceDetail(platformService);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("queryDetail测试通过");
		}
	}
	@Test
	public void selectById(){
	
		Integer serviceId = 1;
		PlatformService platformService = platformServiceMapper.selectById(serviceId);
		if(platformService !=null){
			logger.info(platformService);
		}
	}
	
}
