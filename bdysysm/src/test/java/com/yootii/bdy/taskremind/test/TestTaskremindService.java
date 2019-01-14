package com.yootii.bdy.taskremind.test;

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
import com.yootii.bdy.user.model.User;
import com.yootii.bdy.util.JsonUtil;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-mybatis.xml" })
public class TestTaskremindService{
	
	private static final Logger logger = Logger.getLogger(TestRoleService.class);
	
	@Resource
	private TaskRemindService taskRemindService;
	
	@Test
	public void testinsert() {
		try {
		TaskRemind taskRemind = new TaskRemind();
		taskRemind.setAgencyid(1);
		taskRemind.setType(1);
		taskRemind.setMessage("测试");
		taskRemind.setTitle("测试");
//		Object obj = taskRemindService.insertTaskRemind(taskRemind);		
//		if (obj != null) {
//			logger.info(JsonUtil.toJson(obj));
//			logger.info("insertTaskRemindTest测试通过");
//		}
//		
//		
//		GeneralCondition gcon = new GeneralCondition();
//		gcon.setRows(10);
//		List<TaskRemind> list = taskRemindService.selectTaskRemindList(taskRemind, gcon);
//		Long cont = taskRemindService.selectTaskRemindListCount(taskRemind, gcon);
//		Integer taskRemindId = 0;
//		if (list != null) {
//			logger.info(JsonUtil.toJson(list));
//			logger.info(cont);
//			logger.info("selectTaskRemindTest测试通过");
//			taskRemindId  = list.get(0).getTrid();
//		}
		
		
//		obj = taskRemindService.deleteTaskRemind(taskRemindId);
//		if (obj != null) {
//			logger.info(JsonUtil.toJson(obj));
//			logger.info("deleteTaskRemindTest测试通过");
//		}
		}catch (Exception e) {
			System.out.println(JsonUtil.toJson(e.getMessage()));
		}
	}


	
}