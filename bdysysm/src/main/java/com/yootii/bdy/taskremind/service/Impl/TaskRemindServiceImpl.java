package com.yootii.bdy.taskremind.service.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.common.ReturnInfo;
import com.yootii.bdy.invite.model.AgencyInvite;
import com.yootii.bdy.process.service.ProcessService;
import com.yootii.bdy.task.model.UserTask;
import com.yootii.bdy.taskremind.dao.TaskRemindMapper;
import com.yootii.bdy.taskremind.model.TaskRemind;
import com.yootii.bdy.taskremind.service.TaskRemindService;
import com.yootii.bdy.util.GraspUtil;
import com.yootii.bdy.util.JsonUtil;


@Service
public class TaskRemindServiceImpl implements TaskRemindService {

	@Resource
	private TaskRemindMapper taskRemindMapper;
	
	@Autowired
	private ProcessService processService;
	
	@Override
	public Boolean deleteTaskRemind(Integer taskRemindId) {
		TaskRemind taskRemind = new TaskRemind();
		taskRemind.setTrid(taskRemindId);
		taskRemind.setIsclose(1);
		taskRemindMapper.updateByPrimaryKeySelective(taskRemind);
		return true;
	}

	@Override
	public Boolean insertTaskRemind(TaskRemind taskRemind) {
		taskRemind.setIsclose(0);
		taskRemindMapper.insertSelective(taskRemind);
		return true;
	}

	@Override
	public List<TaskRemind> selectTaskRemindList(TaskRemind taskRemind, List<UserTask> userlist, GeneralCondition gcon) {
		return taskRemindMapper.selectTaskRemindList(taskRemind,userlist, gcon);
	}

	@Override
	public Long selectTaskRemindListCount(TaskRemind taskRemind, List<UserTask> userlist, GeneralCondition gcon) {
		
		return taskRemindMapper.selectTaskRemindCount(taskRemind,userlist, gcon).get("count");
	}
	
	@Override
	public ReturnInfo queryTaskRemindList(TaskRemind taskRemind,Integer userId, GeneralCondition gcon) throws Exception {
		ReturnInfo info = new ReturnInfo();
		List<UserTask> userlist = findUserTask(userId.toString());
		info.setData(selectTaskRemindList(taskRemind,userlist,gcon));
		info.setTotal(selectTaskRemindListCount(taskRemind,userlist,gcon));
		return info;
	}
	
	// 获取客户/用户的待办任务
	public List<UserTask> findUserTask(String userId) throws Exception {

		List<Map<String, Object>> userList = processService.findUserTaskUrl(userId);

		List<UserTask> list = new ArrayList<UserTask>();

		if (userList != null) {
			for (Map<String, Object> user2 : userList) {
				


				String caseId = (String)((Map<String, Object>) user2.get("proMap")).get("caseId");
				String billId = (String)((Map<String, Object>) user2.get("proMap")).get("billId");

				if (caseId == null && billId == null) {
					String taskId = (String) user2.get("taskId");
					String taskName = (String) user2.get("taskName");
					String remarks = (String) user2.get("remarks");


					UserTask userTask = new UserTask();
					userTask.setTaskId(taskId);
					userTask.setTaskName(taskName);
					userTask.setRemarks(remarks);

					list.add(userTask);
				}
			}
		}

		return list;
	}
	


	@Override
	public TaskRemind selectTaskRemindById(Integer taskRemindId) {
		// TODO 自动生成的方法存根
		return taskRemindMapper.selectByPrimaryKey(taskRemindId);
	}
	
}