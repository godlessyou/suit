package com.yootii.bdy.task.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.yootii.bdy.agency.model.Agency;
import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.common.ReturnInfo;
import com.yootii.bdy.customer.model.Customer;
import com.yootii.bdy.invite.model.AgencyInvite;
import com.yootii.bdy.invite.model.CustomerInvite;
import com.yootii.bdy.user.model.User;

public interface CustOpenTaskService {  
	//
	//
	public ReturnInfo startProCustOpen(HttpServletRequest request,User user, CustomerInvite customerInvite, Customer customer, String agencyContactId);
	
	public ReturnInfo proCustOpencheck(HttpServletRequest request,String ProcessInstanceId,String sid);
	
	public ReturnInfo doTaskCOWrite(HttpServletRequest request, String proId,Customer customer, Customer customer2, String sid);

	public ReturnInfo doTaskCOReview(HttpServletRequest request, Integer trId, User user, Boolean review);

	public ReturnInfo doTaskCOFinancial(HttpServletRequest request, Integer trId, User user, Double discount);


	public ReturnInfo addCustomer(String username,String email,String password,String phone)throws Exception;
	
	public ReturnInfo updateCustomer(Customer customer) throws Exception;
	
	public ReturnInfo validCaller(String username,String password);
}