package com.yootii.bdy.invite.service;

import javax.servlet.http.HttpServletRequest;

import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.common.ReturnInfo;
import com.yootii.bdy.invite.model.AgencyInvite;

public interface AgencyInviteService {
	//查询代理机构邀请列表
	public ReturnInfo queryAgencyInviteList(AgencyInvite agencyInvite,GeneralCondition gcon);
	
	public AgencyInvite queryAgencyInvite(Integer agencyInviteId);
	
	//添加代理机构邀请信息
	public ReturnInfo addAgencyInvite(AgencyInvite agencyInvite);
	
	//修改代理机构邀请信息
	public ReturnInfo modifyAgencyInviteStatus(AgencyInvite agencyInvite);
	
	//删除代理机构邀请状态
	public ReturnInfo deleteAgencyInvite(AgencyInvite agencyInvite);
	
	//发送邀请邮件
	public ReturnInfo invateAgency(HttpServletRequest request, AgencyInvite agencyInvite,String agencyId,String otherContent);
	
	public ReturnInfo checkLinkOutdate(HttpServletRequest request,String sid,String email) ;
}
