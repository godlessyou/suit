package com.yootii.bdy.invite.service;

import javax.servlet.http.HttpServletRequest;

import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.common.ReturnInfo;
import com.yootii.bdy.invite.model.AgencyInvite;
import com.yootii.bdy.invite.model.CustomerInvite;

public interface CustomerInviteService {
	//查询客户邀请列表
	public ReturnInfo queryCustomerInviteList(CustomerInvite record,GeneralCondition gcon,Integer userId);
	
	//查询客户邀请
	public CustomerInvite queryCustomerInviteDetail(Integer id);

	//添加客户邀请信息
	public ReturnInfo addCustomerInvite(CustomerInvite record);

	//修改客户邀请信息
	public ReturnInfo modifyCustomerInviteStatus(CustomerInvite record);
	
	public ReturnInfo invateCustomer(HttpServletRequest request, CustomerInvite customerInvite,String processId,String username,String password);
	
	public ReturnInfo checkLinkOutdate(HttpServletRequest request,String sid,String email,Integer agencyId);
	
	//删除客户邀请状态
	public ReturnInfo deleteCustomerInvite(CustomerInvite record);

	public ReturnInfo modifyCustomerInviteEMail(CustomerInvite record);

	public CustomerInvite queryCustomerInviteByAgencyIdAndName(Integer agencyId, String name);
}
