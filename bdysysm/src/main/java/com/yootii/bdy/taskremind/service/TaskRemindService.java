package com.yootii.bdy.taskremind.service;

import java.util.List;

import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.common.ReturnInfo;
import com.yootii.bdy.task.model.UserTask;
import com.yootii.bdy.taskremind.model.TaskRemind;

public interface TaskRemindService {
	
	public Boolean deleteTaskRemind(Integer taskRemindId);
	
	public Boolean insertTaskRemind(TaskRemind taskRemind);
		
	public TaskRemind selectTaskRemindById(Integer taskRemindId);

	public ReturnInfo queryTaskRemindList(TaskRemind taskRemind, Integer userId, GeneralCondition gcon) throws Exception;

	public Long selectTaskRemindListCount(TaskRemind taskRemind, List<UserTask> userlist, GeneralCondition gcon);

	List<TaskRemind> selectTaskRemindList(TaskRemind taskRemind, List<UserTask> userlist, GeneralCondition gcon);
	
}