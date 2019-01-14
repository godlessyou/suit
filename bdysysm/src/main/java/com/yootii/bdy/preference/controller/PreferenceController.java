package com.yootii.bdy.preference.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yootii.bdy.common.CommonController;
import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.common.ReturnInfo;
import com.yootii.bdy.preference.model.PreferenceField;
import com.yootii.bdy.preference.model.PreferenceValue;
import com.yootii.bdy.preference.service.PreferenceService;
import com.yootii.bdy.user.model.User;
import com.yootii.bdy.user.service.UserService;

@Controller
@RequestMapping("/interface/preference")
public class PreferenceController extends CommonController{
	private final Logger logger = Logger.getLogger(this.getClass());
	@Resource
	private PreferenceService preferenceService;
	@Resource
	private UserService userService;
	@RequestMapping(value = "/querypreferencefield", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo queryPreferenceField(HttpServletRequest request,GeneralCondition gcon,PreferenceField preferenceField){
		ReturnInfo info = this.checkUser(request, gcon);
		makeOffsetAndRows(gcon);
		if (info != null && info.getSuccess()) { // 通过身份验证
			try{
				
				info = preferenceService.queryPreferenceField(preferenceField,gcon);
			}catch(Exception e){
				logger.error(e.getMessage());
				info.setSuccess(false);
				info.setMessage("创建失败");
				info.setMessageType(-2);
				return info;
			}
		}
		return info;
	}
	@RequestMapping(value = "/createpreferencefield", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo createPreferenceField(HttpServletRequest request,GeneralCondition gcon,PreferenceField preferenceField){
		ReturnInfo info = this.checkUser(request, gcon);
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
		if (info != null && info.getSuccess()) { // 通过身份验证
			try{
				Integer userId=sysService.checkToken(gcon.getTokenID()).getUserID();
				User user = userService.getUserById(userId);
				if(user.getUserType() == 1) {
					info = preferenceService.createPreferenceField(preferenceField);
				}else {
					
					info.setSuccess(false);
					info.setMessage("创建失败,只有平台管理员可以创建配置项");
					info.setMessageType(-2);
					return info;
				}
				
			}catch(Exception e){
				logger.error(e.getMessage());
				info.setSuccess(false);
				info.setMessage("创建失败");
				info.setMessageType(-2);
				return info;
			}
		}
		return info;
	}
	@RequestMapping(value = "/deletepreferencefield", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo deletePreferenceField(HttpServletRequest request,GeneralCondition gcon,PreferenceField preferenceField){
		ReturnInfo info = this.checkUser(request, gcon);
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
		if (info != null && info.getSuccess()) { // 通过身份验证
			try{
				Integer userId=sysService.checkToken(gcon.getTokenID()).getUserID();
				User user = userService.getUserById(userId);
				if(user.getUserType() == 1) {
					info = preferenceService.deletePreferenceField(preferenceField);
				}else {
					
					info.setSuccess(false);
					info.setMessage("创建失败,只有平台管理员可以创建配置项");
					info.setMessageType(-2);
					return info;
				}
				
			}catch(Exception e){
				logger.error(e.getMessage());
				info.setSuccess(false);
				info.setMessage("创建失败");
				info.setMessageType(-2);
				return info;
			}
		}
		return info;
	}
	@RequestMapping(value = "/modifypreferencefield", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo modifyPreferenceField(HttpServletRequest request,GeneralCondition gcon,PreferenceField preferenceField){
		ReturnInfo info = this.checkUser(request, gcon);
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
		if (info != null && info.getSuccess()) { // 通过身份验证
			try{
				Integer userId=sysService.checkToken(gcon.getTokenID()).getUserID();
				User user = userService.getUserById(userId);
				if(user.getUserType() == 1) {
					info = preferenceService.modifyPreferenceField(preferenceField);
				}else {
					
					info.setSuccess(false);
					info.setMessage("创建失败,只有平台管理员可以创建配置项");
					info.setMessageType(-2);
					return info;
				}
				
				
			}catch(Exception e){
				logger.error(e.getMessage());
				info.setSuccess(false);
				info.setMessage("创建失败");
				info.setMessageType(-2);
				return info;
			}
		}
		return info;
	}
	@RequestMapping(value = "/querypreferencevalue", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo queryPreferenceValue(HttpServletRequest request,GeneralCondition gcon,PreferenceValue preferenceValue){
		ReturnInfo info = this.checkUser(request, gcon);
		makeOffsetAndRows(gcon);
		if (info != null && info.getSuccess()) { // 通过身份验证
			try{
				Integer userId=sysService.checkToken(gcon.getTokenID()).getUserID();
				info = preferenceService.queryPreferenceValue(preferenceValue,gcon);
			}catch(Exception e){
				logger.error(e.getMessage());
				info.setSuccess(false);
				info.setMessage("创建失败");
				info.setMessageType(-2);
				return info;
			}
		}
		return info;
	}
	@RequestMapping(value = "/createpreferencevalue", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo createPreferenceValue(HttpServletRequest request,GeneralCondition gcon,PreferenceValue preferenceValue){
		ReturnInfo info = this.checkUser(request, gcon);
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
		if (info != null && info.getSuccess()) { // 通过身份验证
			try{
				Integer userId=sysService.checkToken(gcon.getTokenID()).getUserID();
				info = preferenceService.createPreferenceValue(preferenceValue);
			}catch(Exception e){
				logger.error(e.getMessage());
				info.setSuccess(false);
				info.setMessage("创建失败");
				info.setMessageType(-2);
				return info;
			}
		}
		return info;
	}
	@RequestMapping(value = "/deletepreferencevalue", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo deletePreferenceValue(HttpServletRequest request,GeneralCondition gcon,PreferenceValue preferenceValue){
		ReturnInfo info = this.checkUser(request, gcon);
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
		if (info != null && info.getSuccess()) { // 通过身份验证
			try{
				Integer userId=sysService.checkToken(gcon.getTokenID()).getUserID();
				info = preferenceService.deletePreferenceValue(preferenceValue);
			}catch(Exception e){
				logger.error(e.getMessage());
				info.setSuccess(false);
				info.setMessage("创建失败");
				info.setMessageType(-2);
				return info;
			}
		}
		return info;
	}
	@RequestMapping(value = "/modifypreferencevalue", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo modifyPreferenceValue(HttpServletRequest request,GeneralCondition gcon,List<PreferenceValue> preferenceValue){
		ReturnInfo info = this.checkUser(request, gcon);
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
		if (info != null && info.getSuccess()) { // 通过身份验证
			try{
				Integer userId=sysService.checkToken(gcon.getTokenID()).getUserID();
				info = preferenceService.modifyPreferenceValue(preferenceValue);
			}catch(Exception e){
				logger.error(e.getMessage());
				info.setSuccess(false);
				info.setMessage("创建失败");
				info.setMessageType(-2);
				return info;
			}
		}
		return info;
	}
}
