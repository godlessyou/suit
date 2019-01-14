package com.yootii.bdy.user.service;


import javax.servlet.http.HttpServletRequest;

import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.common.ReturnInfo;
import com.yootii.bdy.customer.model.Customer;
import com.yootii.bdy.user.model.User;

public interface UserResetPassWordService {
	
	public ReturnInfo validUser(HttpServletRequest request,User userParam,
			 GeneralCondition gcon);
	
	public ReturnInfo toFindPassword(HttpServletRequest request);
	
	public ReturnInfo resetPassword(HttpServletRequest request);	

}