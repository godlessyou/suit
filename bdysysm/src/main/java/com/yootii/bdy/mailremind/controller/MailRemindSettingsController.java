package com.yootii.bdy.mailremind.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yootii.bdy.common.CommonController;
import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.common.ReturnInfo;
import com.yootii.bdy.mailremind.model.MailRemindSettings;
import com.yootii.bdy.mailremind.service.MailRemindSettingsService;
import com.yootii.bdy.mailremind.service.MailRemindTypeService;
import com.yootii.bdy.security.service.AuthenticationService;

@Controller
@RequestMapping("/interface/mailSettings")
public class MailRemindSettingsController extends CommonController{
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Resource
	private MailRemindSettingsService mailRemindSettingsService;
	
	@Resource
	private AuthenticationService authenticationService;
	@Resource
	private MailRemindTypeService mailRemindTypeService;
	
	@RequestMapping(value = "/createRemind", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo createMailRemindSettings(HttpServletRequest request,MailRemindSettings settings, GeneralCondition  gcon){
		ReturnInfo rtnInfo = this.checkUser(request, gcon);
		if (rtnInfo != null && rtnInfo.getSuccess()) { // 通过身份验证
			try {
				rtnInfo = mailRemindSettingsService.createMailRemindSettings(settings);
			}catch (Exception e) {
				logger.error(e.getMessage());
				rtnInfo.setSuccess(false);
				rtnInfo.setMessageType(-2);
				rtnInfo.setMessage(e.getMessage());
				return rtnInfo;
			}
		}	
		return rtnInfo;
	}
	@RequestMapping(value = "/deleteRemind", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo deleteMailRemindSettings(HttpServletRequest request,Integer setId, GeneralCondition  gcon){
		ReturnInfo rtnInfo = this.checkUser(request, gcon);
		if (rtnInfo != null && rtnInfo.getSuccess()) { // 通过身份验证
			try {
				rtnInfo = mailRemindSettingsService.deleteMailRemindSettings(setId);
			}catch (Exception e) {
				logger.error(e.getMessage());
				rtnInfo.setSuccess(false);
				rtnInfo.setMessageType(-2);
				rtnInfo.setMessage(e.getMessage());
				return rtnInfo;
			}
		}	
		return rtnInfo;
	}
	@RequestMapping(value = "/createCustRemind", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo createExistCustDefaultMailRemindSettings(HttpServletRequest request,Integer custId, GeneralCondition  gcon){
		ReturnInfo rtnInfo = this.checkUser(request, gcon);
		if (rtnInfo != null && rtnInfo.getSuccess()) { // 通过身份验证
			try {
				rtnInfo = mailRemindSettingsService.createExistCustDefaultSettings(custId);
			}catch (Exception e) {
				logger.error(e.getMessage());
				rtnInfo.setSuccess(false);
				rtnInfo.setMessageType(-2);
				rtnInfo.setMessage(e.getMessage());
				return rtnInfo;
			}
		}	
		return rtnInfo;
	}
	@RequestMapping(value = "/queryRemind", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo queryMailRemindSettings(HttpServletRequest request,Integer custId, GeneralCondition  gcon){
		ReturnInfo rtnInfo = this.checkUser(request, gcon);
		if (rtnInfo != null && rtnInfo.getSuccess()) {// 通过身份验证
			try {
				rtnInfo = mailRemindSettingsService.queryMailRemindSettings(custId);
			}catch (Exception e) {
				logger.error(e.getMessage());
				rtnInfo.setSuccess(false);
				rtnInfo.setMessageType(-2);
				rtnInfo.setMessage(e.getMessage());
				return rtnInfo;
			}
		}	
		return rtnInfo;
	}
	@RequestMapping(value = "/queryRemindType", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo queryMailRemindType(HttpServletRequest request,GeneralCondition  gcon){
		ReturnInfo rtnInfo = this.checkUser(request, gcon);
		if (rtnInfo != null && rtnInfo.getSuccess()) {// 通过身份验证
			try {
				rtnInfo = mailRemindTypeService.queryMailRemindType();
			}catch (Exception e) {
				logger.error(e.getMessage());
				rtnInfo.setSuccess(false);
				rtnInfo.setMessageType(-2);
				rtnInfo.setMessage(e.getMessage());
				return rtnInfo;
			}
		}	
		return rtnInfo;
	}
}
