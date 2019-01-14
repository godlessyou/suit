package com.yootii.bdy.ipservice.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yootii.bdy.common.CommonController;
import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.common.ReturnInfo;
import com.yootii.bdy.ipservice.model.AgencyService;
import com.yootii.bdy.ipservice.model.PlatformService;
import com.yootii.bdy.ipservice.service.AgencyServiceService;
import com.yootii.bdy.security.model.Token;
import com.yootii.bdy.user.model.User;
import com.yootii.bdy.user.service.UserService;

@Controller
@RequestMapping("/interface/agencyservice")
public class AgencyServiceController extends CommonController{
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Resource
	private AgencyServiceService agencyServiceService;
	
	@RequestMapping(value = "/createagencyservice", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo createAgencyService(HttpServletRequest request,GeneralCondition gcon,
			AgencyService agencyService,Integer caseTypeId,Integer serviceType){
		ReturnInfo info = this.checkUserAndRole(request, gcon, "代理机构管理员");
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
		if (info != null && info.getSuccess()) { // 通过身份验证
			try{
				Integer userId=sysService.checkToken(gcon.getTokenID()).getUserID();
				info = agencyServiceService.createAgencyService(agencyService,userId,caseTypeId,serviceType);
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
	@RequestMapping(value = "/deleteagencyservice", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo deleteAgencyService(HttpServletRequest request,GeneralCondition gcon,AgencyService agencyService){
		ReturnInfo info = this.checkUserAndRole(request, gcon, "代理机构管理员");
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
		if (info != null && info.getSuccess()) { // 通过身份验证
			try{
				Integer userId=sysService.checkToken(gcon.getTokenID()).getUserID();
				info = agencyServiceService.deleteAgencyService(agencyService,userId);
			}catch(Exception e){
				logger.error(e.getMessage());
				info.setSuccess(false);
				info.setMessage("删除失败");
				info.setMessageType(-2);
				return info;
			}
		}
		return info;
	}
	@RequestMapping(value = "/modifyagencyservice", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo modifyAgencyService(HttpServletRequest request,GeneralCondition gcon,AgencyService agencyService){
		ReturnInfo info = this.checkUserAndRole(request, gcon, "代理机构管理员");
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
		if (info != null && info.getSuccess()) { // 通过身份验证
			try{
				Integer userId=sysService.checkToken(gcon.getTokenID()).getUserID();
				info = agencyServiceService.modifyAgencyService(agencyService,userId);
			}catch(Exception e){
				logger.error(e.getMessage());
				info.setSuccess(false);
				info.setMessage("修改失败");
				info.setMessageType(-2);
				return info;
			}
		}
		return info;
	}
	@RequestMapping(value = "/queryagencyservicelist", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo queryAgencyServiceList(HttpServletRequest request,GeneralCondition gcon,AgencyService agencyService,String serviceName){
		ReturnInfo info = this.checkUser(request, gcon);
		makeOffsetAndRows(gcon);
		if (info != null && info.getSuccess()) { // 通过身份验证
			Token token=sysService.checkToken(gcon.getTokenID());
			try{
				info = agencyServiceService.queryAgencyServiceList(agencyService,gcon,token,serviceName);
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
	@RequestMapping(value = "/queryagencyservicedetail", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo queryAgencyServiceDetail(HttpServletRequest request,GeneralCondition gcon,AgencyService agencyService){
		ReturnInfo info = this.checkUser(request, gcon);
		if (info != null && info.getSuccess()) { // 通过身份验证
			try{
				info = agencyServiceService.queryAgencyServiceDetail(agencyService);
			}catch(Exception e){
				logger.error(e.getMessage());
				info.setSuccess(false);
				info.setMessage("查询详情失败");
				info.setMessageType(-2);
				return info;
			}
		}
		return info;
	}
	@RequestMapping(value = "/queryagencyservicesold", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo queryAgencyServicesOld(HttpServletRequest request,GeneralCondition gcon,AgencyService agencyService){
		ReturnInfo info = this.checkUser(request, gcon);
		if (info != null && info.getSuccess()) { // 通过身份验证
			Token token=sysService.checkToken(gcon.getTokenID());
			try{
				info = agencyServiceService.custQueryAgencyServiceList(agencyService, gcon, token);
			}catch(Exception e){
				logger.error(e.getMessage());
				info.setSuccess(false);
				info.setMessage("查询失败");
				info.setMessageType(-2);
				return info;
			}
		}
		return info;
	}
	@RequestMapping(value = "/queryagencyservices", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo queryAgencyServices(HttpServletRequest request,GeneralCondition gcon,Integer caseTypeId){
		ReturnInfo info = this.checkUser(request, gcon);
		if (info != null && info.getSuccess()) { // 通过身份验证
			Token token=sysService.checkToken(gcon.getTokenID());
			try{
				info = agencyServiceService.custQueryAgencyServiceListByCaseTypeId(caseTypeId, gcon, token);
			}catch(Exception e){
				logger.error(e.getMessage());
				info.setSuccess(false);
				info.setMessage("查询失败");
				info.setMessageType(-2);
				return info;
			}
		}
		return info;
	}
	@RequestMapping(value = "/getagencyservice", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo getAgencyService(HttpServletRequest request,GeneralCondition gcon,AgencyService agencyService){
		ReturnInfo info = this.checkUser(request, gcon);
		if (info != null && info.getSuccess()) { // 通过身份验证
			Token token=sysService.checkToken(gcon.getTokenID());
			try{
				info = agencyServiceService.getAgencyService(agencyService);
			}catch(Exception e){
				logger.error(e.getMessage());
				info.setSuccess(false);
				info.setMessage("查询失败");
				info.setMessageType(-2);
				return info;
			}
		}
		return info;
	}
	@RequestMapping(value = "/queryagencyservice", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo queryAgencyService(HttpServletRequest request,GeneralCondition gcon,Integer caseTypeId,Integer agencyId,Integer serviceType){
		ReturnInfo info = this.checkUser(request, gcon);
		if (info != null && info.getSuccess()) { // 通过身份验证
			try{
				info = agencyServiceService.queryAgencyService(caseTypeId, agencyId, serviceType);
			}catch(Exception e){
				logger.error(e.getMessage());
				info.setSuccess(false);
				info.setMessage("查询失败");
				info.setMessageType(-2);
				return info;
			}
		}
		return info;
	}
}