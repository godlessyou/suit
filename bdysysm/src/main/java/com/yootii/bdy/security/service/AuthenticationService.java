package com.yootii.bdy.security.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.common.ReturnInfo;
import com.yootii.bdy.customer.model.Customer;
import com.yootii.bdy.user.model.User;

public interface AuthenticationService {

	public ReturnInfo authorize(HttpServletRequest request,GeneralCondition gcon);
	
    public ReturnInfo login(User user,GeneralCondition gcon);
   
    public ReturnInfo logout(HttpServletRequest request, GeneralCondition gcon);
    
    public ReturnInfo queryUsername(GeneralCondition gcon);
    
    public User authentication(User user,
    		GeneralCondition gcon, boolean checkPassword);
    
    public String encodePassword(String rawPassword);
    
	public Map<String,String> generatePassowrdEncoded(String password) ;

	public Object loginAsSome(HttpServletRequest request, GeneralCondition gcon);

	public ReturnInfo customerin(Customer customer, GeneralCondition gcon);
	
	public Customer authenticationCustomer(Customer customer,
    		GeneralCondition gcon, boolean checkPassword);

}
