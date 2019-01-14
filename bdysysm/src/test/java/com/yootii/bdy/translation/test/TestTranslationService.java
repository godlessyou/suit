package com.yootii.bdy.translation.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.common.ReturnInfo;
import com.yootii.bdy.invite.model.AgencyInvite;
import com.yootii.bdy.invite.service.AgencyInviteService;
import com.yootii.bdy.role.test.TestRoleService;
import com.yootii.bdy.task.service.AgentOpenTaskService;
import com.yootii.bdy.task.service.CoopeOpenTaskService;
import com.yootii.bdy.taskremind.model.TaskRemind;
import com.yootii.bdy.taskremind.service.TaskRemindService;
import com.yootii.bdy.translation.service.TranslationService;
import com.yootii.bdy.user.model.User;
import com.yootii.bdy.util.JsonUtil;
import com.yootii.bdy.util.ObjectUtil;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-mybatis.xml" })
public class TestTranslationService{
	
	private static final Logger logger = Logger.getLogger(TestRoleService.class);
	
	@Resource
	private TranslationService translationService;
	
	@Test
	public void testinsert() {
		try {

			
			AgencyInvite ag = new AgencyInvite();
			ag.setStatus("已发送");
			ag.setName("陈圣东");
			
			List<String> namelist = new ArrayList<String>();
			namelist.add("status");
			

			ReturnInfo r1 = new ReturnInfo();
			
			r1.setData(ag);
			
			Object ao =translationService.translationObject(r1,"cn","en");

			
			System.out.println(JsonUtil.toJson(ao));

		}
		catch (Exception e) {
			System.out.println(JsonUtil.toJson(e.getMessage()));
		}
	}


	
}