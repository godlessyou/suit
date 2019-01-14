package com.yootii.bdy.customer.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yootii.bdy.common.CommonController;
import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.common.ReturnInfo;
import com.yootii.bdy.customer.model.CustomerContact;
import com.yootii.bdy.customer.service.CustomerContactService;


@Controller
@RequestMapping("/interface/customer")
public class CustomerContactController extends CommonController{
	private final Logger logger = Logger.getLogger(this.getClass());

	@Resource
	protected CustomerContactService customerContactService;

	@RequestMapping(value = "/querycustomercontact", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo queryCustomerContact(HttpServletRequest request,CustomerContact customerContact, GeneralCondition gcon){
		ReturnInfo rtnInfo = this.checkUser(request, gcon);
		if(rtnInfo != null && rtnInfo.getSuccess()){//通过身份和角色验证
			makeOffsetAndRows(gcon);
			try {
				rtnInfo =customerContactService.queryCustomerContact(customerContact,gcon);
			}catch (Exception e) {
				logger.error(e.getMessage());
				rtnInfo.setSuccess(false);
				rtnInfo.setMessageType(-2);
				rtnInfo.setMessage(e.getMessage());
				return rtnInfo;
			}
		}
		return rtnInfo ;
	}

	@RequestMapping(value = "/ceatecustomercontact", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo ceateCustomerContact(HttpServletRequest request,CustomerContact customerContact, GeneralCondition gcon){
		ReturnInfo rtnInfo = this.checkUser(request, gcon);
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
		if(rtnInfo != null && rtnInfo.getSuccess()){//通过身份和角色验证
			try {
				rtnInfo =customerContactService.ceateCustomerContact(customerContact);
			}catch (Exception e) {
				logger.error(e.getMessage());
				rtnInfo.setSuccess(false);
				rtnInfo.setMessageType(-2);
				rtnInfo.setMessage(e.getMessage());
				return rtnInfo;
			}
		}
		return rtnInfo ;
	}

	@RequestMapping(value = "/motifycustomercontact", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo motifyCustomerContact(HttpServletRequest request,CustomerContact customerContact, GeneralCondition gcon){
		ReturnInfo rtnInfo = this.checkUser(request, gcon);
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
		if(rtnInfo != null && rtnInfo.getSuccess()){//通过身份和角色验证
			try {
				rtnInfo =customerContactService.motifyCustomerContact(customerContact);
			}catch (Exception e) {
				logger.error(e.getMessage());
				rtnInfo.setSuccess(false);
				rtnInfo.setMessageType(-2);
				rtnInfo.setMessage(e.getMessage());
				return rtnInfo;
			}
		}
		return rtnInfo ;
	}

	@RequestMapping(value = "/deletecustomercontact", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo deleteCustomerContact(HttpServletRequest request,CustomerContact customerContact, GeneralCondition gcon){
		ReturnInfo rtnInfo = this.checkUser(request, gcon);
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
		if(rtnInfo != null && rtnInfo.getSuccess()){//通过身份和角色验证
			makeOffsetAndRows(gcon);
			try {

				rtnInfo =customerContactService.deleteCustomerContact(customerContact);


			}catch (Exception e) {
				logger.error(e.getMessage());
				rtnInfo.setSuccess(false);
				rtnInfo.setMessageType(-2);
				rtnInfo.setMessage(e.getMessage());
				return rtnInfo;
			}
		}
		return rtnInfo ;
	}
}
