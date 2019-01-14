package com.yootii.bdy.invite.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yootii.bdy.common.CommonController;
import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.common.ReturnInfo;
import com.yootii.bdy.invite.model.AgencyInvite;
import com.yootii.bdy.invite.service.AgencyInviteService;
import com.yootii.bdy.user.model.User;

@Controller
@RequestMapping("interface/agencyinvite")
public class AgencyInviteController extends CommonController {
	
	private final Logger logger = Logger.getLogger(this.getClass());
	@Resource
	private AgencyInviteService agencyInviteService;
	
	@RequestMapping(value="/queryagencyinvitelist", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo queryAgencyInviteList(HttpServletRequest request,AgencyInvite agencyInvite,GeneralCondition gcon){
		ReturnInfo info = this.checkUser(request, gcon);
		
		if (info != null && info.getSuccess()) { // 通过身份验证
			try{
				Integer userId=sysService.checkToken(gcon.getTokenID()).getUserID();
				User caller = userService.getUserById(userId);
				if(caller==null||caller.getUserType()!=1){//只有平台管理员查询
					info.setSuccess(false);
					info.setMessage("权限不足");
					return info;
				}
				makeOffsetAndRows(gcon);
				//验证登录身份
				info = agencyInviteService.queryAgencyInviteList(agencyInvite, gcon);
			}catch(Exception e){
				logger.error(e.getMessage());
				info.setSuccess(false);
				info.setMessage("创建失败");
				return info;
			}
		}
		return info;
	}
	@RequestMapping(value="/addagencyinvite", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo addAgencyInvite(HttpServletRequest request,AgencyInvite agencyInvite,GeneralCondition gcon){
		ReturnInfo info = new ReturnInfo();
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
		//验证登录身份
//		info = agencyInviteService.addAgencyInvite(agencyInvite);
		return info;
	}
	
	@RequestMapping(value="/modifyagencyinvite", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo modifyAgencyInvite(HttpServletRequest request,AgencyInvite agencyInvite,GeneralCondition gcon){
		ReturnInfo info = new ReturnInfo();
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
		//验证登录身份
//		info = agencyInviteService.modifyAgencyInviteStatus(agencyInvite);
		return info;
	}
}
