package com.yootii.bdy.process.service;

import java.util.List;
import java.util.Map;

import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.common.ReturnInfo;
import com.yootii.bdy.customer.model.Customer;
import com.yootii.bdy.user.model.User;

public interface ProcessService {  
	
	public ReturnInfo startProcessByKey(String prokey,Map<String,Object> runMap);
	
	public ReturnInfo startProcessById(String proid,Map<String,Object> runMap);
	
	public ReturnInfo resumeProcess(String processInstanceId, String activityId, String messageName, Map<String, Object> runMap);

	public ReturnInfo checkNextTask(String processInstanceId);
	
	public ReturnInfo doTask(String taskId,Map<String,Object> runMap);
	
	public ReturnInfo doTaskByPro(String proId,Map<String,Object> runMap);
	
	public ReturnInfo showtaskvariablesbypro(String proId);
	
	public ReturnInfo showtaskvariables(String taskId);

	public ReturnInfo checkTask(String taskId,User user);
	
	public ReturnInfo checkTaskbByPro(String proId,User user);
	
	public ReturnInfo checkuserstart(String permission,String userId);
	
	public ReturnInfo checkcuststart(String proKey,Customer customer, Map<String, Object> runMap);
	
	public ReturnInfo custstartProByKey(String prokey,Map<String,Object> runMap);
	
	public ReturnInfo custstartPro(String proid,Map<String,Object> runMap);

	public ReturnInfo custdoTask(String taskId,Map<String,Object> runMap);
	
	public ReturnInfo custdoTaskByPro(String proId,Map<String,Object> runMap);

	public List<Map<String, Object>> findUserTaskUrl(String userId) throws Exception;

	public ReturnInfo deleteProcess(String processInstanceId);

	public ReturnInfo setTaskUser(List<String> proIds, List<String> adduserIds, List<String> deluserIds);
}