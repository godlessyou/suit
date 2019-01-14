package com.yootii.bdy.customer.test;



import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.customer.model.Region;
import com.yootii.bdy.customer.service.RegionService;
import com.yootii.bdy.util.JsonUtil;


@RunWith(SpringJUnit4ClassRunner.class)
//表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = { "classpath:spring-mybatis.xml" })
public class TestRegionService {
	@Resource
	private RegionService regionService;
		
	private static final Logger logger = Logger.getLogger(TestRegionService.class);

	
	@Test
	public void queryRegionListTest() {
		
		Region region=new Region();
//		region.setRegionName("富民县");
		GeneralCondition gcon = new GeneralCondition();
		gcon.setPageNo(1);
		gcon.setOffset(0);
		gcon.setRows(10);
		Object info = regionService.queryRegionList(region, gcon);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("queryRegionList测试通过");
		}
	}

}
