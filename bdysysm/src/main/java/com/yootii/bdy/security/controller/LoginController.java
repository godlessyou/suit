package com.yootii.bdy.security.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.common.ReturnInfo;
import com.yootii.bdy.customer.model.Customer;
import com.yootii.bdy.security.service.AuthenticationService;
import com.yootii.bdy.user.model.User;




@Controller
@RequestMapping("/interface/auth")
public class LoginController {

	@Resource
	protected AuthenticationService authenticationService;	

	@RequestMapping(value = "/login", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo login(HttpServletRequest request, User user,GeneralCondition gcon) {
		try {
			ReturnInfo login1 = authenticationService.login(user, gcon);
			return login1;
		} catch (Exception e) {
			e.printStackTrace();
			ReturnInfo rtnInfo = new ReturnInfo();
			rtnInfo.setSuccess(false);
			rtnInfo.setMessage("没有权限");
			return rtnInfo;
		}

	}
	@RequestMapping(value = "/customerin", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo customerin(HttpServletRequest request, Customer customer,GeneralCondition gcon) {
		try {
			ReturnInfo login1 = authenticationService.customerin(customer, gcon);
			return login1;
		} catch (Exception e) {
			ReturnInfo rtnInfo = new ReturnInfo();
			rtnInfo.setSuccess(false);
			rtnInfo.setMessage("没有权限");
			return rtnInfo;
		}

	}
	@RequestMapping(value = "/logout", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo logout(HttpServletRequest request,GeneralCondition gcon) {
		return authenticationService.logout(request, gcon);
	}

	@RequestMapping(value = "/authenticate", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo authorize(HttpServletRequest request, GeneralCondition gcon) {
		return authenticationService.authorize(request, gcon);
	}
	
	
	@RequestMapping(value = "/queryusername", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo queryUsername(GeneralCondition gcon) {
		try {
			ReturnInfo login1 = authenticationService.queryUsername(gcon);
			return login1;
		} catch (Exception e) {
			e.printStackTrace();
			ReturnInfo rtnInfo = new ReturnInfo();
			rtnInfo.setSuccess(false);
			rtnInfo.setMessage("没有权限");
			return rtnInfo;
		}

	}
}
