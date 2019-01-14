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
import com.yootii.bdy.invite.model.CustomerInvite;
import com.yootii.bdy.invite.service.CustomerInviteService;
import com.yootii.bdy.user.model.User;

@Controller
@RequestMapping("interface/customerinvite")
public class CustomerInviteController extends CommonController {
	@Resource
	private CustomerInviteService customerInviteService;
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@RequestMapping(value="/querycustomerinvitelist", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo queryCustomerInviteList(HttpServletRequest request,CustomerInvite customerInvite,GeneralCondition gcon){
		ReturnInfo info = this.checkUser(request, gcon);
		if (info != null && info.getSuccess()) { // 通过身份验证
			boolean isUser=sysService.checkToken(gcon.getTokenID()).isUser();
			if(!isUser){//是客户
				info.setSuccess(false);
				info.setMessage("权限不足");
				return info;
			}
			Integer userId = sysService.checkToken(gcon.getTokenID()).getUserID();
			makeOffsetAndRows(gcon);
			//验证登录身份
			try{
				info = customerInviteService.queryCustomerInviteList(customerInvite, gcon,userId);
			}catch(Exception e){
				logger.error(e.getMessage());
				info.setSuccess(false);
				info.setMessage("查询列表失败");
				info.setMessageType(-2);
				return info;
			}
		}
		return info;
	}
	@RequestMapping(value="/addcustomerinvite", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo addCustomerInvite(HttpServletRequest request, CustomerInvite customerInvite,GeneralCondition gcon){
		ReturnInfo info = new ReturnInfo();
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
		//验证登录身份
//		info = customerInviteService.addCustomerInvite(customerInvite);
		return info;
	}
	
	@RequestMapping(value="/modifycustomerinvite", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo modifyCustomerInvite(HttpServletRequest request, CustomerInvite customerInvite,GeneralCondition gcon){
		ReturnInfo info = new ReturnInfo();
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
		//验证登录身份
//		info = customerInviteService.modifyCustomerInviteStatus(customerInvite);
		return info;
	}
}
