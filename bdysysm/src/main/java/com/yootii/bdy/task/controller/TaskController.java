package com.yootii.bdy.task.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yootii.bdy.agency.model.Agency;
import com.yootii.bdy.common.CommonController;
import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.common.ReturnInfo;
import com.yootii.bdy.customer.model.Customer;
import com.yootii.bdy.invite.model.AgencyInvite;
import com.yootii.bdy.invite.model.CustomerInvite;
import com.yootii.bdy.task.service.AgentOpenTaskService;
import com.yootii.bdy.task.service.CoopeOpenTaskService;
import com.yootii.bdy.task.service.CustOpenTaskService;
import com.yootii.bdy.user.model.User;

@Controller
@RequestMapping("interface/task")
public class TaskController extends CommonController {

	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Resource
	private AgentOpenTaskService agentOpentaskService;
	
	@Resource
	private CustOpenTaskService custOpenTaskService;
	
	@Resource
	private CoopeOpenTaskService coopeOpenTaskService;
	
	
	
	@RequestMapping(value="/invitationagent", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo invitationagent(HttpServletRequest request,User user,AgencyInvite agencyInvite,Agency agency,GeneralCondition gcon){
		ReturnInfo info = this.checkUser(request, gcon);
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
		if(info != null && info.getSuccess()){//通过身份和角色验证
			makeOffsetAndRows(gcon);
			try {
				Integer userId=sysService.checkToken(gcon.getTokenID()).getUserID();
				//验证登录身份
				info = agentOpentaskService.startProAgentOpen(request, user, agency, agencyInvite, null, userId.toString());				
			}catch (Exception e) {
				logger.error(e.getMessage());
				info.setSuccess(false);
				info.setMessageType(-2);
				info.setMessage(e.getMessage());
				return info;
			}
		}
		
		return info;
	}
	
	@RequestMapping(value="/checkAOlink", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo checkAOlink(HttpServletRequest request,String proId,String sid){
		ReturnInfo info = new ReturnInfo();
		
		//验证登录身份
		info = agentOpentaskService.proAgentOpencheck(request, proId, sid);
		
		return info;
	}
	
	@RequestMapping(value="/saveagent", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo saveagent(HttpServletRequest request,User user,String proId,Agency agency,GeneralCondition gcon,String sid){
		ReturnInfo info = new ReturnInfo();
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
		//验证登录身份
		info = agentOpentaskService.doTaskAOEmail(request, proId, sid, user, agency);
		
		return info;
	}
	
	@RequestMapping(value="/invitationcust", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo invitationcust(HttpServletRequest request,CustomerInvite customerInvite,Customer customer,GeneralCondition gcon, String agencyContactId){
		ReturnInfo info = this.checkUser(request, gcon);
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
		if(info != null && info.getSuccess()){//通过身份和角色验证
			makeOffsetAndRows(gcon);
			try {
				Integer userId=sysService.checkToken(gcon.getTokenID()).getUserID();
				User user = userService.getUserById(userId);
				//验证登录身份
				info = custOpenTaskService.startProCustOpen(request, user, customerInvite,customer, agencyContactId);
			}catch (Exception e) {
				logger.error(e.getMessage());
				info.setSuccess(false);
				info.setMessageType(-2);
				info.setMessage(e.getMessage());
				return info;
			}
		}
		
		return info;
	}
	
	/**
	 * 通过接口添加新用户
	 * @param request
	 * @param username
	 * @param email
	 * @param password
	 * @param phone
	 * @return
	 */
	/*@RequestMapping(value="addCustomer",produces="application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo addCustomer(HttpServletRequest request,String username,String email,String password,String phone){
		ReturnInfo returnInfo = new ReturnInfo();
		if(username ==null || username ==""){
			returnInfo.setSuccess(false);
			returnInfo.setMessage("username 不能为空！");
			return returnInfo;
		}
		if(email == null ||email ==""){
			returnInfo.setSuccess(false);
			returnInfo.setMessage("email 不能为空！");
			return returnInfo;
		}
		if(password ==null || password ==""){
			returnInfo.setSuccess(false);
			returnInfo.setMessage("password 不能为空！");
			return returnInfo;
		}
		try{
			returnInfo = custOpenTaskService.addCustomer(username, email, password, phone);
		}catch (Exception e) {
			returnInfo.setSuccess(false);
			returnInfo.setMessage("添加失败");
			e.printStackTrace();
		}
		return returnInfo;
	}*/
	/**
	 * 更新完善 通过接口添加的用户信息  
	 * @param request
	 * @return
	 */
	/*public ReturnInfo updateCustomer(HttpServletRequest request,Customer customer){
		ReturnInfo returnInfo = new ReturnInfo();
		
		try{
		   returnInfo =custOpenTaskService.updateCustomer(customer);
		}catch (Exception e) {
			e.printStackTrace();
			returnInfo.setSuccess(false);
			returnInfo.setMessage("更新失败");
		}
		return returnInfo;
	}*/
	
	

	@RequestMapping(value="/checkCOlink", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo checkCOlink(HttpServletRequest request,String proId,String sid){
		ReturnInfo info = new ReturnInfo();
		
		//验证登录身份
		info = custOpenTaskService.proCustOpencheck(request, proId, sid);
		
		return info;
	}

	@RequestMapping(value="/savecust", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo savecust(HttpServletRequest request,User user,String proId,Customer customer,GeneralCondition gcon,String sid){
		ReturnInfo info = new ReturnInfo();
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
		Customer customer2 = null;
		//验证登录身份
		if(gcon.getTokenID()!=null) {
			Integer custID=sysService.checkToken(gcon.getTokenID()).getCustomerID();
			customer2 = customerService.getCustById(custID);
		}
		
		info = custOpenTaskService.doTaskCOWrite(request, proId, customer, customer2, sid);
		
		return info;
	}
	

	@RequestMapping(value="/financialcust", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo financialcust(HttpServletRequest request,Double discount,GeneralCondition gcon,Integer trId){
		ReturnInfo info = new ReturnInfo();
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
		Customer customer2 = null;
		//验证登录身份
		if(gcon.getTokenID()!=null) {
			Integer userId=sysService.checkToken(gcon.getTokenID()).getUserID();
			User user = userService.getUserById(userId);	
			info = custOpenTaskService.doTaskCOFinancial(request, trId, user, discount);
		}	
		return info;
	}
	
	@RequestMapping(value="/reviewcust", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo reviewcust(HttpServletRequest request,Boolean review,GeneralCondition gcon,Integer trId){
		ReturnInfo info = new ReturnInfo();
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
		Customer customer2 = null;
		//验证登录身份
		if(gcon.getTokenID()!=null) {
			Integer userId=sysService.checkToken(gcon.getTokenID()).getUserID();
			User user = userService.getUserById(userId);	
			info = custOpenTaskService.doTaskCOReview(request, trId, user, review);
		}	
		return info;
	}
	
	@RequestMapping(value="/invitationcoope", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo invitationcoope(HttpServletRequest request,String toagencyid,String agencyid,GeneralCondition gcon){
		ReturnInfo info = this.checkUser(request, gcon);
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
		if(info != null && info.getSuccess()){//通过身份和角色验证
			makeOffsetAndRows(gcon);
			try {
				Integer userId=sysService.checkToken(gcon.getTokenID()).getUserID();
				User user = userService.getUserById(userId);
				//验证登录身份
				info = coopeOpenTaskService.startProCoopeOpen(request, user, toagencyid, agencyid);
			}catch (Exception e) {
				logger.error(e.getMessage());
				info.setSuccess(false);
				info.setMessageType(-2);
				info.setMessage(e.getMessage());
				return info;
			}
		}
		
		return info;
	}
	
	@RequestMapping(value="/replycoope", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo replycoope(HttpServletRequest request,Integer reply,Integer trId,GeneralCondition gcon){
		ReturnInfo info = this.checkUser(request, gcon);
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
		if(info != null && info.getSuccess()){//通过身份和角色验证
			makeOffsetAndRows(gcon);
			try {
				Integer userId=sysService.checkToken(gcon.getTokenID()).getUserID();
				User user = userService.getUserById(userId);
				//验证登录身份
				info = coopeOpenTaskService.doTaskCOConfirm(request, trId, user, reply);
			}catch (Exception e) {
				logger.error(e.getMessage());
				info.setSuccess(false);
				info.setMessageType(-2);
				info.setMessage(e.getMessage());
				return info;
			}
		}
		
		return info;
	}
	
}