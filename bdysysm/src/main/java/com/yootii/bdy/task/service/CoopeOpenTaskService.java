package com.yootii.bdy.task.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.yootii.bdy.agency.model.Agency;
import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.common.ReturnInfo;
import com.yootii.bdy.invite.model.AgencyInvite;
import com.yootii.bdy.invite.model.CustomerInvite;
import com.yootii.bdy.user.model.User;

public interface CoopeOpenTaskService {  
	//
	//
	public ReturnInfo startProCoopeOpen(HttpServletRequest request,User user, String toagencyid,String agencyid);
	
	public ReturnInfo proCoopeOpencheck(HttpServletRequest request,String taskId,User user);
	
	public ReturnInfo doTaskCOConfirm (HttpServletRequest request,Integer trId,User user,Integer reply);
	
}