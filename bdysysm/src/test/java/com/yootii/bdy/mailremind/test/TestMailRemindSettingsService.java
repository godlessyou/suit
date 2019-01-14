package com.yootii.bdy.mailremind.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yootii.bdy.common.ReturnInfo;
import com.yootii.bdy.mailremind.model.MailRemindSettings;
import com.yootii.bdy.mailremind.service.MailRemindSettingsService;
import com.yootii.bdy.util.JsonUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring-mybatis.xml"})
public class TestMailRemindSettingsService {
	@Resource
	private MailRemindSettingsService mailRemindSettingsService;
//	@Test
	public void createExistCustSettings(){
		ReturnInfo info = mailRemindSettingsService.createExistCustDefaultSettings(null);
		if(info!=null){
			System.out.println(JsonUtil.toJson(info));
		}
	}
//	@Test
	public void createSettings(){
		MailRemindSettings settings = new MailRemindSettings();
		settings.setCustId(2);
		settings.setRemindType(1);
		ReturnInfo info = mailRemindSettingsService.createMailRemindSettings(settings);
		if(info!=null){
			System.out.println(JsonUtil.toJson(info));
		}
	}
//	@Test
	public void deleteSettings(){
		ReturnInfo info = mailRemindSettingsService.deleteMailRemindSettings(3);
		if(info!=null){
			System.out.println(JsonUtil.toJson(info));
		}
	}
	@Test
	public void querySettingsTest(){
		ReturnInfo info = mailRemindSettingsService.queryMailRemindSettings(2);
		if(info!=null){
			System.out.println(JsonUtil.toJson(info));
		}
	}
}
