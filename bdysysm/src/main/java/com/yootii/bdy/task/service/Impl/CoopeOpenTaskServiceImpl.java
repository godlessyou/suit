package com.yootii.bdy.task.service.Impl;

import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yootii.bdy.agency.model.Agency;
import com.yootii.bdy.agency.model.CooperationAgency;
import com.yootii.bdy.agency.service.AgencyService;
import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.common.ReturnInfo;
import com.yootii.bdy.invite.model.AgencyInvite;
import com.yootii.bdy.invite.model.CustomerInvite;
import com.yootii.bdy.invite.service.AgencyInviteService;
import com.yootii.bdy.process.service.ProcessService;
import com.yootii.bdy.task.service.AgentOpenTaskService;
import com.yootii.bdy.task.service.CoopeOpenTaskService;
import com.yootii.bdy.taskremind.model.TaskRemind;
import com.yootii.bdy.taskremind.service.TaskRemindService;
import com.yootii.bdy.user.model.User;
import com.yootii.bdy.user.service.UserService;
import com.yootii.bdy.util.GraspUtil;
import com.yootii.bdy.util.JsonUtil;

@Service
public class CoopeOpenTaskServiceImpl implements CoopeOpenTaskService {

	@Autowired
	private ProcessService processService;
		
	@Autowired
	private AgencyService agencyService;
	
	@Autowired
	private TaskRemindService taskRemindService;
	
	@Override
	public ReturnInfo startProCoopeOpen(HttpServletRequest request,User user, String toagencyid,String agencyid) {
		ReturnInfo res = new ReturnInfo();
		String proKey = "coope_open";
		String permission = "合作机构开通:启动";
		String nextpermission = "合作机构开通:审核";
		try {
			//权限测试
			Map<String, Object> runMap = new HashMap<String, Object>();
			ReturnInfo checkret = processService.checkuserstart(permission, user.getUserId().toString());
			if(!checkret.getSuccess()) throw new Exception(checkret.getMessage());
			
			Agency agency = agencyService.selectAgencyByUserId(user.getUserId());
			//添加流程参数
			runMap.put("fromAgencyid", agency.getId());
			runMap.put("agencyId", toagencyid);
			runMap.put("permission", nextpermission);
			
			//
			
			
			//
			//启动流程			
			res = processService.startProcessByKey(proKey, runMap);
			if(!res.getSuccess()) {
				throw new Exception("流程启动失败|"+res.getMessage());
			}
			//启动流程成功
			Map<String, Object> resData = (Map<String, Object>) res.getData();

			String processInstanceId =resData.get("ProcessInstanceId").toString();
			
			List<Map<String,Object>> tasklistret = (List<Map<String, Object>>) resData.get("taskList");
			String taskid = tasklistret.get(0).get("taskId").toString();
			

			
//			
			TaskRemind taskRemind = new TaskRemind(); 
			taskRemind.setAgencyid(Integer.valueOf(toagencyid));
			taskRemind.setType(1);
			taskRemind.setMessage("代理机构"+agency.getName()+"希望与贵所建立合作关系，是否同意");
			taskRemind.setTitle("合作机构申请");
			taskRemind.setTaskid(taskid);
			taskRemindService.insertTaskRemind(taskRemind);
			

			
		} catch (Exception e) {
			e.printStackTrace();
			res.setSuccess(false);
			res.setMessage(e.getMessage());
			return res;
		}

		return res;
	}


	@Override
	public ReturnInfo proCoopeOpencheck(HttpServletRequest request,String taskId,User user) {
		ReturnInfo res =new ReturnInfo();
		Map<String, Object> runMap = new HashMap<String, Object>();
		String permission = "合作机构开通:审核";
		try {
			ReturnInfo checkret = processService.checkuserstart(permission, user.getUserId().toString());
			if(!checkret.getSuccess()) throw new Exception(checkret.getMessage());
			
			//获取参数   
			ReturnInfo r1 = processService.showtaskvariables(taskId);
		   	if(r1.getSuccess()!=true) {
		   		throw new Exception("参数获取失败|"+r1.getMessage());
		   	}
		   	
		   	Map<String, Object> proMap = (Map<String, Object>) r1.getData();


		   	//
		   	Map<String, Object> resMap = new HashMap<String, Object>();
		   	res.setSuccess(true);
		   	//resMap.put("AgentName",proMap.get("AgentName"));
		   	res.setData(resMap);
		}
		catch(Exception e) {
			e.printStackTrace();
			res.setSuccess(false);
			res.setMessage(e.getMessage());
			return res;
		}
		return res;
	}


	@Override
	public ReturnInfo doTaskCOConfirm(HttpServletRequest request,Integer trId,User user,Integer reply) {
		ReturnInfo res =new ReturnInfo();
		String permission = "合作机构开通:审核";
		String nextpermission = "合作机构开通:结束";
		try {
			
			TaskRemind taskremind = taskRemindService.selectTaskRemindById(trId);
			String taskId = taskremind.getTaskid();
			ReturnInfo checkret = processService.checkTask(taskId, user);
			if(!checkret.getSuccess()) throw new Exception(checkret.getMessage());
			//获取参数
			Map<String, Object> runMap = new HashMap<String, Object>();
			ReturnInfo r1 = processService.showtaskvariables(taskId);
		   	if(r1.getSuccess()!=true) {
		   		throw new Exception("参数获取失败|"+r1.getMessage());
		   	}
			Map<String, Object> proMap = (Map<String, Object>) r1.getData();
			Integer fromAgencyid = Integer.valueOf(proMap.get("fromAgencyid").toString());
			Integer toAgencyid = Integer.valueOf(proMap.get("agencyId").toString());
		   	//测试权限
		   	if(reply == 1) {
		   		CooperationAgency cooperationAgency =new CooperationAgency();
		   		cooperationAgency.setAgencyId(fromAgencyid);
		   		cooperationAgency.setCooperationAgencyId(toAgencyid);
		   		agencyService.bindcooperationagency(cooperationAgency, user);
		   	}else {
		   		
		   	}
			//做任务
			runMap.put("permission", nextpermission);
			res = processService.doTask(taskId, runMap);
			if(!res.getSuccess()) {
				throw new Exception("流程做任务失败|"+res.getMessage());
			}
		   	taskRemindService.deleteTaskRemind(trId);
		}
		catch(Exception e) {
			//关闭流程
			e.printStackTrace();
			res.setSuccess(false);
			res.setMessage(e.getMessage());
			return res;
		}
		return res;
	}




	
}