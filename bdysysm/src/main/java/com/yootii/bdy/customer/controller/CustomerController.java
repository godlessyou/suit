package com.yootii.bdy.customer.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yootii.bdy.common.CommonController;
import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.common.Globals;
import com.yootii.bdy.common.ReturnInfo;
import com.yootii.bdy.customer.model.Customer;
import com.yootii.bdy.customer.service.CustomerResetPassWordService;
import com.yootii.bdy.customer.service.CustomerService;
import com.yootii.bdy.permission.model.Permission;
import com.yootii.bdy.security.model.Token;
import com.yootii.bdy.security.service.SysService;
import com.yootii.bdy.task.service.CustOpenTaskService;
import com.yootii.bdy.user.model.User;
import com.yootii.bdy.user.service.UserService;

@Controller
@RequestMapping("/interface/customer")
public class CustomerController extends CommonController{
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Resource
	protected CustomerService customerService;
	
	@Resource
	protected SysService sysService;
	
	@Resource
	protected UserService userService;
	
	@Resource
	private CustomerResetPassWordService resetPassWordService;
	@Resource
	private CustOpenTaskService custOpenTaskService;
	
	
	@RequestMapping(value = "/createcustomer", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo createCustomer(HttpServletRequest request,Integer agencyId, Integer agencyContactId, Customer customer,GeneralCondition gcon){
		ReturnInfo info = this.checkUser(request, gcon);//当前登录的客户用户
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
		if(info != null && info.getSuccess()){
			try{
				info = customerService.createCustomer(customer, gcon, agencyId, agencyContactId);
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
	@RequestMapping(value="addCustomer",produces="application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo addCustomer(HttpServletRequest request,String username,String email,
			String password,String phone,String caller,String callerPassword){
		ReturnInfo returnInfo = new ReturnInfo();
		if(caller==null || caller ==""){
			returnInfo.setSuccess(false);
			returnInfo.setMessage("调用者用户名不能为空！");
			returnInfo.setMessageType(Globals.MESSAGE_TYPE_PARAM_INVALID);
			return returnInfo;
		}
		if(callerPassword==null || callerPassword ==""){
			returnInfo.setSuccess(false);
			returnInfo.setMessage("调用者密码不能为空！");
			returnInfo.setMessageType(Globals.MESSAGE_TYPE_PARAM_INVALID);
			return returnInfo;
		}
		if(username ==null || username ==""){
			returnInfo.setSuccess(false);
			returnInfo.setMessage("账户名不能为空！");
			returnInfo.setMessageType(Globals.MESSAGE_TYPE_PARAM_INVALID);
			return returnInfo;
		}
		if(email == null ||email ==""){
			returnInfo.setSuccess(false);
			returnInfo.setMessage("邮箱不能为空！");
			returnInfo.setMessageType(Globals.MESSAGE_TYPE_PARAM_INVALID);
			return returnInfo;
		}
		if(password ==null || password ==""){
			returnInfo.setSuccess(false);
			returnInfo.setMessage("密码不能为空！");
			returnInfo.setMessageType(Globals.MESSAGE_TYPE_PARAM_INVALID);
			return returnInfo;
		}
		//验证   调用者用户名，密码
		returnInfo = custOpenTaskService.validCaller(caller,callerPassword);
		if(!returnInfo.getSuccess()){
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
	}
	/**
	 * 更新完善 通过接口添加的用户信息  
	 * @param request
	 * @return
	 */
	@RequestMapping(value="updateCustomer",produces="application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo updateCustomer(HttpServletRequest request,Customer customer,GeneralCondition gcon){
		
		ReturnInfo returnInfo = this.checkUser(request, gcon);//当前登录的客户用户
		if(returnInfo!=null && returnInfo.getSuccess()){
			try{
				returnInfo =custOpenTaskService.updateCustomer(customer);
			}catch (Exception e) {
				e.printStackTrace();
				returnInfo.setSuccess(false);
				returnInfo.setMessage("更新失败");
			}
		}
		return returnInfo;
	}
	
	
	@RequestMapping(value = "/queryCustomerUser", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo queryCustomerUser(HttpServletRequest request,GeneralCondition gcon){
		ReturnInfo info = this.checkUser(request, gcon);//当前登录的客户用户
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
		if(info != null && info.getSuccess()){
			try{
				Token token=sysService.checkToken(gcon.getTokenID());
				if(!token.isUser()){
					info.setSuccess(false);
					info.setMessage("没有权限");
					return info;
				}
				Integer userID = token.getUserID();
				User user = userService.getUserById(userID);
				info = customerService.queryCustomerUser(user.getAgency().getId(), gcon);
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
	@RequestMapping(value = "/deletecustomer", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo deleteCustomer(HttpServletRequest request,Integer custId,GeneralCondition gcon){
		ReturnInfo info = this.checkUser(request, gcon);//当前登录的客户用户
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
		if(info != null && info.getSuccess()){
			try{
			Integer userId=sysService.checkToken(gcon.getTokenID()).getUserID();
			User caller = userService.getUserById(userId);
			if(caller.getUserType()!=1){
				info.setSuccess(false);
				info.setMessage("权限不足");
				return info;
			}
			info = customerService.deleteCustomer(custId);
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
	@RequestMapping(value = "/checkusername", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo checkUsername(String username){
		ReturnInfo info = new ReturnInfo();
		info = customerService.checkUsername(username);
		return info;
	}
	@RequestMapping(value = "/checkcompanyname", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo checkCompanyName(String name){
		ReturnInfo info = new ReturnInfo();
		info = customerService.checkCompanyName(name);
		return info;
	}
	@RequestMapping(value = "/bindagency", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo bindAgency(Integer custId,Integer agencyId, Integer agencyContactId, GeneralCondition gcon, HttpServletRequest request){
		ReturnInfo info = new ReturnInfo();
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
		info = customerService.bindAgency(custId, agencyId, agencyContactId);
		return info;
	}
	@RequestMapping(value = "/unbindagency", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo unbindAgency(HttpServletRequest request,Integer custId,Integer agencyId,GeneralCondition gcon){
		ReturnInfo info = this.checkUser(request, gcon);//当前登录的客户用户
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
		if(info != null && info.getSuccess()){
			info = customerService.unbindAgency(custId, agencyId);
		}
		return info;
	}
	@RequestMapping(value = "/querycustomer", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo queryCustomer(HttpServletRequest request,Customer customer,GeneralCondition gcon){
		ReturnInfo rtnInfo = this.checkUser(request, gcon);
		makeOffsetAndRows(gcon);
		if(rtnInfo != null && rtnInfo.getSuccess()){
			try{
				Token token=sysService.checkToken(gcon.getTokenID());
				if(!token.isUser()){
					rtnInfo.setSuccess(false);
					rtnInfo.setMessage("没有权限");
					return rtnInfo;
				}
				Integer userID = token.getUserID();
				User caller = userService.getUserById(userID);
				Integer level = null;
				if(userService.hasRole("代理机构管理员", caller)
						||userService.hasRole("公司领导", caller)
						||(caller.getUserType()==1)){
					//代理机构管理员、公司领导和平台管理员
					rtnInfo.setSuccess(true);
					rtnInfo = customerService.queryCustomer(customer, caller,gcon);
				}
				else if(userService.hasRole("一级部门负责人", caller)){
					level =0;
					rtnInfo = customerService.queryOwnCustomer(customer,caller,gcon,level);
				}else if(userService.hasRole("二级部门负责人", caller)){
					level =1;
					rtnInfo = customerService.queryOwnCustomer(customer,caller,gcon,level);
				}else if(userService.hasRole("代理人", caller)){//通过代理人身份和角色验证
					rtnInfo = customerService.queryOwnCustomer(customer, caller,gcon,level);
				}
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
	@RequestMapping(value = "/agentquerycustomer", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo agentQueryCustomer(HttpServletRequest request,Customer customer,GeneralCondition gcon){
		ReturnInfo rtnInfo = this.checkUser(request, gcon);
		makeOffsetAndRows(gcon);
		if(rtnInfo != null && rtnInfo.getSuccess()){
			try{
				Token token=sysService.checkToken(gcon.getTokenID());
				if(!token.isUser()){
					rtnInfo.setSuccess(false);
					rtnInfo.setMessage("没有权限");
					return rtnInfo;
				}
				Integer userID = token.getUserID();
				User caller = userService.getUserById(userID);
				rtnInfo = customerService.queryCustomer(customer, caller,gcon);
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
	@RequestMapping(value = "/customerdetail", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo queryCustomerDetail(HttpServletRequest request,Integer custId,GeneralCondition gcon){
		ReturnInfo rtnInfo = this.checkUser(request, gcon);//代理机构管理员
		if(rtnInfo != null && rtnInfo.getSuccess()){//通过身份和角色验证
			try {
				if(custId==null){
					custId=sysService.checkToken(gcon.getTokenID()).getCustomerID();
				}
				rtnInfo = customerService.getCustDetailById(custId);
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
	@RequestMapping(value = "/modifycustomerself", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo modifyUserself(HttpServletRequest request, Customer customer, GeneralCondition gcon) {
    	ReturnInfo rtnInfo = this.checkUser(request,gcon);
    	this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
    	this.addURL(request.getRequestURI());
		if (rtnInfo != null && rtnInfo.getSuccess()) { // 通过身份验证
			try{
				Integer custId=sysService.checkToken(gcon.getTokenID()).getCustomerID();
				Customer caller = customerService.getCustById(custId);
				if(customer.getId()==null){
					customer.setId(caller.getId());
				}
				rtnInfo = customerService.modifyCustomerSelf(customer);
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
	@RequestMapping(value = "/modifycustomerlocked", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo modifyCustomerLock(HttpServletRequest request, Integer custId,Integer locked, GeneralCondition gcon) {
    	ReturnInfo rtnInfo = this.checkUser(request,gcon);
    	this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
    	this.addURL(request.getRequestURI());
		if (rtnInfo != null && rtnInfo.getSuccess()) { // 通过身份验证
			try{
				Token token = sysService.checkToken(gcon.getTokenID());
				Integer userId = token.getUserID();
				User caller = userService.getUserById(userId);
				if(caller.getUserType()!=1){//平台管理员修改客户的状态
					rtnInfo.setSuccess(false);
					rtnInfo.setMessage("权限不足");
					return rtnInfo;
				}
				rtnInfo = customerService.modifyCustomerLocked(custId,locked);
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
	
	@RequestMapping(value = "/uploadcustomericon", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo uploadUserIcon(HttpServletRequest request, GeneralCondition gcon) {
		ReturnInfo rtnInfo = this.checkUser(request,gcon);
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
		if (rtnInfo != null && rtnInfo.getSuccess()) { // 通过身份验证
			try{
				String username=request.getParameter("username");
				if(username==null||"".equals(username)){
					username=sysService.checkToken(gcon.getTokenID()).getUsername();
				}
				rtnInfo = customerService.uploadUserIcon(request, username);
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
	
	@RequestMapping(value = "/validuser", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object validUser(HttpServletRequest request, Customer customer,GeneralCondition gcon) {
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
		return resetPassWordService.validUser(request, customer,gcon);
	}
	
	@RequestMapping(value = "/tofindpassword", produces = "application/json;charset=UTF-8")		
	@ResponseBody
	public Object toFindPassword(HttpServletRequest request,GeneralCondition gcon) {
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
			return resetPassWordService.toFindPassword(request);
	}
	
	
	@RequestMapping(value = "/resetpassword", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo resetPassword(HttpServletRequest request, GeneralCondition gcon) {
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
		return resetPassWordService.resetPassword(request, gcon);
	}
	
	@RequestMapping(value = "/bindrole", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo bindRole(HttpServletRequest request, Integer customerId,Integer roleId, GeneralCondition gcon) {
		ReturnInfo rtnInfo = this.checkUserAndRole(request, gcon, "代理机构管理员");//代理机构管理员
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
		if(rtnInfo != null && rtnInfo.getSuccess()){//通过身份和角色验证
			try {

				rtnInfo = customerService.bindRole(customerId,roleId); 
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
	
	@RequestMapping(value = "/unbindrole", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo unbindRole(HttpServletRequest request, Integer customerId,Integer roleId, GeneralCondition gcon) {
		ReturnInfo rtnInfo = this.checkUserAndRole(request, gcon, "代理机构管理员");//代理机构管理员
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
		if(rtnInfo != null && rtnInfo.getSuccess()){//通过身份和角色验证
			try {
				rtnInfo = customerService.unbindRole(customerId,roleId); 
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
	
	@RequestMapping(value = "/bindapplicant", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo bindapplicant(HttpServletRequest request, Integer custId,Integer appId, GeneralCondition gcon) {
		ReturnInfo rtnInfo = this.checkUser(request,gcon);
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
		if(rtnInfo != null && rtnInfo.getSuccess()){//通过身份和角色验证
			try {

				rtnInfo = customerService.bindApplicant(custId,appId); 
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
	
	@RequestMapping(value = "/unbindapplicant", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo unbindapplicant(HttpServletRequest request, Integer custId,Integer appId, GeneralCondition gcon) {
		ReturnInfo rtnInfo = this.checkUser(request,gcon);
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
		if(rtnInfo != null && rtnInfo.getSuccess()){//通过身份和角色验证
			try {
				rtnInfo = customerService.unBindApplicant(custId,appId); 
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
	
	@RequestMapping(value = "/querycustomerlistbypermission", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo queryCustomerListByPermission(HttpServletRequest request,Permission permission, GeneralCondition gcon){
		ReturnInfo rtnInfo = this.checkUser(request, gcon);
		if(rtnInfo != null && rtnInfo.getSuccess()){//通过身份和角色验证
			makeOffsetAndRows(gcon);
			try {
				rtnInfo = customerService.queryAgencyCustomerListByPermission(permission, gcon);
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
	@RequestMapping(value = "/sendaccountmail", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo sendAccountMail(HttpServletRequest request,Integer custId, GeneralCondition gcon){
		ReturnInfo rtnInfo = this.checkUser(request, gcon);
		if(rtnInfo != null && rtnInfo.getSuccess()){//通过身份和角色验证
			try {
				rtnInfo = customerService.sendAccountEmail(custId);
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
	
	
	@RequestMapping(value = "/queryCustomerRegion", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo queryCustomerRegion(HttpServletRequest request,Customer customer,GeneralCondition gcon){
		ReturnInfo rtnInfo = this.checkUser(request, gcon);
		makeOffsetAndRows(gcon);
		if(rtnInfo != null && rtnInfo.getSuccess()){
			try{
				Token token=sysService.checkToken(gcon.getTokenID());
				if(!token.isUser()){
					rtnInfo.setSuccess(false);
					rtnInfo.setMessage("没有权限");
					return rtnInfo;
				}
				Integer userID = token.getUserID();
				User caller = userService.getUserById(userID);
				Integer level = null;
				if(userService.hasRole("代理机构管理员", caller)
						||userService.hasRole("公司领导", caller)
						||(caller.getUserType()==1)){
					//代理机构管理员、公司领导和平台管理员
					rtnInfo.setSuccess(true);
					rtnInfo = customerService.queryCustomerRegion(caller);
				}
				else if(userService.hasRole("一级部门负责人", caller)){
					level =0;
					rtnInfo = customerService.queryCustomerOwnRegion(caller,level);
				}else if(userService.hasRole("二级部门负责人", caller)){
					level =1;
					rtnInfo = customerService.queryCustomerOwnRegion(caller,level);
				}else if(userService.hasRole("代理人", caller)){//通过代理人身份和角色验证
					rtnInfo = customerService.queryCustomerOwnRegion(caller,level);
				}
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
