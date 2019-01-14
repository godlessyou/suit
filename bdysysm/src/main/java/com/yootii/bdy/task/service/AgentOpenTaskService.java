package com.yootii.bdy.task.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.yootii.bdy.agency.model.Agency;
import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.common.ReturnInfo;
import com.yootii.bdy.invite.model.AgencyInvite;
import com.yootii.bdy.invite.model.CustomerInvite;
import com.yootii.bdy.user.model.User;

public interface AgentOpenTaskService {  
	//
	//
	
	public ReturnInfo proAgentOpencheck(HttpServletRequest request,String ProcessInstanceId,String sid);
	
	public ReturnInfo doTaskAOEmail(HttpServletRequest request,String proId,String sid,User user,Agency agency);

	public ReturnInfo startProAgentOpen(HttpServletRequest request, User user, Agency agency, AgencyInvite agencyInvite,
			String Email2, String userId);
	
	

	
}