package com.yootii.bdy.user.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yootii.bdy.agency.model.AgencyCustomer;
import com.yootii.bdy.common.CommonController;
import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.common.Globals;
import com.yootii.bdy.common.ReturnInfo;
import com.yootii.bdy.customer.model.Customer;
import com.yootii.bdy.customer.service.CustomerService;
import com.yootii.bdy.department.model.Department;
import com.yootii.bdy.permission.model.Permission;
import com.yootii.bdy.role.model.Role;
import com.yootii.bdy.security.model.Token;
import com.yootii.bdy.security.service.SysService;
import com.yootii.bdy.user.model.User;
import com.yootii.bdy.user.service.UserResetPassWordService;
import com.yootii.bdy.user.service.UserService;

@Controller
@RequestMapping("/interface/user")
public class UserController extends CommonController{
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Resource
	protected UserService userService ;
	
	@Resource
	protected CustomerService customerService;
	
	@Resource
	protected SysService sysService;
	
	@Resource
	private UserResetPassWordService resetPassWordService;
	
	// 增加用户
	@RequestMapping(value = "/createuser", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo createUser(HttpServletRequest request,User user, GeneralCondition gcon){
//		ReturnInfo rtnInfo = this.checkUserAndRole(request, gcon, "代理机构管理员");//代理机构管理员
		ReturnInfo rtnInfo = this.checkUser(request, gcon);//2018-05-18 普通代理人可以创建其他代理人
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
		this.addURL(request.getRequestURI());
		if(rtnInfo != null && rtnInfo.getSuccess()){//通过身份和角色验证
			try {
				Integer userId=sysService.checkToken(gcon.getTokenID()).getUserID();
				User caller = userService.getUserById(userId);
				rtnInfo = userService.createUser(user, caller,gcon);
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
	// 增加代理机构管理员用户
	@RequestMapping(value = "/createagencyadmin", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo createAgencyAdmin(HttpServletRequest request,User user, GeneralCondition gcon, Integer agencyId){
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
		//不需要验证身份
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo = userService.createAgencyAdmin(user, agencyId);
		return returnInfo ;
	}
	//用户注册时，检查用户名是否唯一
	@RequestMapping(value = "/checkusername", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo queryUsername(String username){
		ReturnInfo returnInfo= new ReturnInfo();
		returnInfo = userService.checkUsername(username);
		return returnInfo;
	}
	
	//删除用户
	@RequestMapping(value = "/deleteuser", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo deleteUser(HttpServletRequest request,User user, GeneralCondition gcon){
		ReturnInfo rtnInfo = this.checkUser(request, gcon);//平台管理员
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
		if(rtnInfo != null && rtnInfo.getSuccess()){//通过身份和角色验证
			try {
				Integer userId=sysService.checkToken(gcon.getTokenID()).getUserID();
				User caller = userService.getUserById(userId);
				rtnInfo = userService.deleteUser(user, caller);
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
	//移除用户
	@RequestMapping(value = "/removeuser", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo removeUser(HttpServletRequest request,User user, GeneralCondition gcon){
		ReturnInfo rtnInfo = this.checkUserAndRole(request, gcon, "代理机构管理员");//代理机构管理员
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
		if(rtnInfo != null && rtnInfo.getSuccess()){//通过身份和角色验证
			try {
//				Integer userId=sysService.checkToken(gcon.getTokenID()).getUserID();
//				User caller = userService.getUserById(userId);
				rtnInfo = userService.removeUser(user);
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
	//修改用户
	@RequestMapping(value = "/modifyuser", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo modifyUser(HttpServletRequest request,User user, GeneralCondition gcon){
		ReturnInfo rtnInfo = this.checkUserAndRole(request, gcon, "代理机构管理员");//代理机构管理员
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
		if(rtnInfo != null && rtnInfo.getSuccess()){//通过身份和角色验证
			try {
//				Integer userId=sysService.checkToken(gcon.getTokenID()).getUserID();
//				User caller = userService.getUserById(userId);
				rtnInfo = userService.modifyUser(user);
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
	
	
	// 查询用户
	@RequestMapping(value = "/queryuserlist", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo queryUserList(HttpServletRequest request, User user, GeneralCondition gcon){
		ReturnInfo rtnInfo = this.checkUser(request, gcon);
		if(rtnInfo != null && rtnInfo.getSuccess()){//通过身份和角色验证
			makeOffsetAndRows(gcon);
			try {
				Integer userId=sysService.checkToken(gcon.getTokenID()).getUserID();
				User caller = userService.getUserById(userId);
//				if(userService.hasRole("代理机构管理员", caller)){
//					rtnInfo = userService.queryAgencyUserList(user, gcon, caller);
//				}else 
				if(caller.getUserType()==1){//平台管理员查询代理机构管理员
					rtnInfo = userService.queryUserList(user, gcon, caller);
				}else{//2018-05-18代理机构人员，查询本代理机构的用户
					rtnInfo = userService.queryAgencyUserList(user, gcon, caller);
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
	// 查询用户
		@RequestMapping(value = "/queryuserlistbypermission", produces = "application/json;charset=UTF-8")
		@ResponseBody
		public ReturnInfo queryUserListByPermission(HttpServletRequest request,Permission permission, GeneralCondition gcon){
			ReturnInfo rtnInfo = this.checkUser(request, gcon);
			if(rtnInfo != null && rtnInfo.getSuccess()){//通过身份和角色验证
				makeOffsetAndRows(gcon);
				try {
					
						rtnInfo = userService.queryAgencyUserListByPermission(permission, gcon);
					
					
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
// 查询用户
	@RequestMapping(value = "/queryuserlistbypermissionDepId", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo queryUserListByPermissionDepId(HttpServletRequest request,Permission permission, String depName, GeneralCondition gcon){
		ReturnInfo rtnInfo = this.checkUser(request, gcon);
		if(rtnInfo != null && rtnInfo.getSuccess()){//通过身份和角色验证
			makeOffsetAndRows(gcon);
			try {
				
					rtnInfo = userService.queryAgencyUserListByPermissionDepId(permission,depName, gcon);
				
				
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
	
	@RequestMapping(value = "/bindrole", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo bindRole(HttpServletRequest request, Integer userId,Integer roleId, GeneralCondition gcon) {
		ReturnInfo rtnInfo = this.checkUserAndRole(request, gcon, "代理机构管理员");//代理机构管理员
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
		if(rtnInfo != null && rtnInfo.getSuccess()){//通过身份和角色验证
			try {
//				Integer userId=sysService.checkToken(gcon.getTokenID()).getUserID();
//				User caller = userService.getUserById(userId);
				rtnInfo = userService.bindRole(userId,roleId); 
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
	public ReturnInfo unbindRole(HttpServletRequest request, Integer userId,Integer roleId, GeneralCondition gcon) {
		ReturnInfo rtnInfo = this.checkUserAndRole(request, gcon, "代理机构管理员");//代理机构管理员
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
		if(rtnInfo != null && rtnInfo.getSuccess()){//通过身份和角色验证
			try {
//				Integer userId=sysService.checkToken(gcon.getTokenID()).getUserID();
//				User caller = userService.getUserById(userId);
				rtnInfo = userService.unbindRole(userId,roleId); 
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
	@RequestMapping(value = "/binddept", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo bindDept(HttpServletRequest request,  Integer userId,Integer deptId, GeneralCondition gcon) {
		ReturnInfo rtnInfo = this.checkUserAndRole(request, gcon, "代理机构管理员");//代理机构管理员
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
		if(rtnInfo != null && rtnInfo.getSuccess()){//通过身份和角色验证
			try {
//				Integer userId=sysService.checkToken(gcon.getTokenID()).getUserID();
//				User caller = userService.getUserById(userId);
				rtnInfo = userService.bindDept(userId,deptId); 
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
	
	@RequestMapping(value = "/unbinddept", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo unbindDept(HttpServletRequest request,  Integer userId,Integer deptId, GeneralCondition gcon) {
		ReturnInfo rtnInfo = this.checkUserAndRole(request, gcon, "代理机构管理员");//代理机构管理员
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
		if(rtnInfo != null && rtnInfo.getSuccess()){//通过身份和角色验证
			try {
//				Integer userId=sysService.checkToken(gcon.getTokenID()).getUserID();
//				User caller = userService.getUserById(userId);
				rtnInfo = userService.unbindDept(userId,deptId); 
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
	@RequestMapping(value = "/bindcustomer", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo bindCustomer(HttpServletRequest request,  Integer userId,Integer custId, GeneralCondition gcon) {
		ReturnInfo rtnInfo = this.checkUserAndRole(request, gcon, "代理机构管理员");//代理机构管理员
		ReturnInfo rtnInfo1 = this.checkUserAndRole(request, gcon, "公司领导");
		
		String tokenID=gcon.getTokenID();
		this.addTokenId(tokenID);  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
		if((rtnInfo != null && rtnInfo.getSuccess())||(rtnInfo1 != null && rtnInfo1.getSuccess())){//通过身份和角色验证
			try {
				rtnInfo = userService.bindCustomer(userId,custId);
				if(!rtnInfo.getSuccess())throw new Exception(rtnInfo.getMessage());
				rtnInfo = customerService.addCustomerAgentUser(custId.toString(), userId.toString(), tokenID);
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
	
	@RequestMapping(value = "/unbindcustomer", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo unbindCustomer(HttpServletRequest request,  Integer userId,Integer custId, GeneralCondition gcon) {
		ReturnInfo rtnInfo = this.checkUserAndRole(request, gcon, "代理机构管理员");//代理机构管理员
		ReturnInfo rtnInfo1 = this.checkUserAndRole(request, gcon, "公司领导");
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
		if((rtnInfo != null && rtnInfo.getSuccess())||(rtnInfo1 != null && rtnInfo1.getSuccess())){//通过身份和角色验证
			try {
				rtnInfo = userService.unbindCustomer(userId,custId);
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
	
	@RequestMapping(value = "/modifyuserself", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo modifyUserself(HttpServletRequest request, User user, GeneralCondition gcon) {
    	ReturnInfo rtnInfo = this.checkUser(request,gcon);
    	this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
    	this.addURL(request.getRequestURI());
    	
		if (rtnInfo != null && rtnInfo.getSuccess()) { // 通过身份验证
			try{
				if(user!=null&&user.getUserId()==null){
					Integer userId=sysService.checkToken(gcon.getTokenID()).getUserID();
					user.setUserId(userId);
				}
//				User caller = userService.getUserById(userId);//当前用户自修改的所有信息
				rtnInfo = userService.modifyUserSelf(user,gcon);
				
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
	
	@RequestMapping(value = "/uploadusericon", produces = "application/json;charset=UTF-8")
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
				rtnInfo = userService.uploadUserIcon(request, username);
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
	public Object validUser(HttpServletRequest request, User userParam,GeneralCondition gcon) {
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
		return resetPassWordService.validUser(request, userParam,gcon);
	}
	
	@RequestMapping(value = "/tofindpassword", produces = "application/json;charset=UTF-8")		
	@ResponseBody
	public ReturnInfo toFindPassword(HttpServletRequest request,GeneralCondition gcon) {
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
			return resetPassWordService.toFindPassword(request);
	}
	
	
	@RequestMapping(value = "/resetpassword", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo resetPassword(HttpServletRequest request, GeneralCondition gcon) {
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
		return resetPassWordService.resetPassword(request);
	}
	@RequestMapping(value = "/querydeptuserlist", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo queryDeptDserList(HttpServletRequest request, Integer deptId, GeneralCondition gcon){
		ReturnInfo rtnInfo = this.checkUser(request, gcon);
		if(rtnInfo != null && rtnInfo.getSuccess()){//通过身份和角色验证
			makeOffsetAndRows(gcon);
			try {
				rtnInfo = userService.queryUserByDeptId(deptId, gcon);
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
	
	@RequestMapping(value = "/haspermission", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo hasPermission(HttpServletRequest request, String permission, User user, GeneralCondition gcon){
		
		ReturnInfo rtnInfo = this.checkUser(request, gcon);
		if(rtnInfo != null && rtnInfo.getSuccess()){//通过身份和角色验证		
			try {
				boolean hasPermission = userService.hasPermission(permission, user);
				String result=hasPermission+"";
				Map<String, String> data=new HashMap<String, String>();
				data.put("hasPermission", result);				
				rtnInfo.setData(data);
				rtnInfo.setSuccess(true);
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
	//根据用户userId查询user
	@RequestMapping(value = "/userdetail", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object userDetail(HttpServletRequest request, User user,GeneralCondition gcon) {
		ReturnInfo rtnInfo = this.checkUser(request,gcon);
		if (rtnInfo != null && rtnInfo.getSuccess()) { // 通过身份验证
			Integer	userId=null;
			if(user!=null&&user.getUserId()!=null){
				userId = user.getUserId();
			}else{
				userId=sysService.checkToken(gcon.getTokenID()).getUserID();
			}
			rtnInfo = userService.getUserByPrimaryKey(userId);
		}
		return rtnInfo;
	}
	
	// 查询用户
	@RequestMapping(value = "/queryUserByCustId", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo queryUserByCustId(HttpServletRequest request,String custId, String agencyId, GeneralCondition gcon){
		
		
		ReturnInfo rtnInfo = this.checkUser(request, gcon);
		if(rtnInfo != null && rtnInfo.getSuccess()){//通过身份和角色验证
			
			if (custId == null || custId.equals("")) {
				rtnInfo.setSuccess(false);
				rtnInfo.setMessage("custId不能为空");
				rtnInfo.setMessageType(-2);
				return rtnInfo;
			}
			if (agencyId == null || agencyId.equals("")) {
				rtnInfo.setSuccess(false);
				rtnInfo.setMessage("agencyId不能为空");
				rtnInfo.setMessageType(-2);
				return rtnInfo;
			}
			
			try {				
				Integer cId=new Integer(custId);
				Integer aId=new Integer(agencyId);
				AgencyCustomer agencyCustomer=new AgencyCustomer();
				agencyCustomer.setCustId(cId);
				agencyCustomer.setAgencyId(aId);
				rtnInfo = userService.queryUserByCustId(agencyCustomer);				
				
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

	// 查询对外账单联系人
	@RequestMapping(value = "/queryContactUser", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo queryContactUser(HttpServletRequest request,Integer agencyId, GeneralCondition gcon){
		ReturnInfo rtnInfo = this.checkUser(request, gcon);
		if(rtnInfo != null && rtnInfo.getSuccess()){//通过身份和角色验证
			try {				
				rtnInfo = userService.queryContactUserByAgencyId(agencyId);				
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
	
	
	// 查询用户
	@RequestMapping(value = "/queryUserByUserId", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo queryUserByUserId(HttpServletRequest request,String userId, GeneralCondition gcon){
		
		
		ReturnInfo rtnInfo = this.checkUser(request, gcon);
		if(rtnInfo != null && rtnInfo.getSuccess()){//通过身份和角色验证
			
			if (userId == null || userId.equals("")) {
				rtnInfo.setSuccess(false);
				rtnInfo.setMessage("userId不能为空");
				rtnInfo.setMessageType(-2);
				return rtnInfo;
			}
			
			try {				
				Integer id=new Integer(userId);
				rtnInfo = userService.getUserByPrimaryKey(id);
				
				
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
	
	
	// 查询用户
	@RequestMapping(value = "/queryUserByUserName", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo queryUserByUserName(HttpServletRequest request,String username, GeneralCondition gcon){
					
		ReturnInfo rtnInfo = this.checkUser(request, gcon);
		if(rtnInfo != null && rtnInfo.getSuccess()){//通过身份和角色验证
			
			if (username == null || username.equals("")) {
				rtnInfo.setSuccess(false);
				rtnInfo.setMessage("username不能为空");
				rtnInfo.setMessageType(-2);
				return rtnInfo;
			}
			
			try {						
				User user = userService.getUserByUsername(username);
				rtnInfo.setSuccess(true);
				rtnInfo.setData(user);
				rtnInfo.setMessage("查询成功");					
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
		
	

	// 查询用户
	@RequestMapping(value = "/queryAdminByAgencyId", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo queryAdminByAgencyId(HttpServletRequest request, String agencyId, GeneralCondition gcon){
					
		ReturnInfo rtnInfo = this.checkUser(request, gcon);
		if(rtnInfo != null && rtnInfo.getSuccess()){//通过身份和角色验证
			
			if (agencyId == null || agencyId.equals("")) {				
				rtnInfo.setSuccess(false);
				rtnInfo.setMessage("代理机构Id不能为空");
				rtnInfo.setMessageType(Globals.MESSAGE_TYPE_PARAM_INVALID);					
				return rtnInfo;
			}
			
			try {	
				Integer id=new Integer(agencyId);
				rtnInfo = userService.queryAdminByAgencyId(id);						
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
		
	
	
	// 查询用户
		@RequestMapping(value = "/queryAgencyUserByFullName", produces = "application/json;charset=UTF-8")
		@ResponseBody
		public ReturnInfo queryAgencyUserByFullName(HttpServletRequest request, String agencyId, User user, GeneralCondition gcon){
						
			ReturnInfo rtnInfo = this.checkUser(request, gcon);
			if(rtnInfo != null && rtnInfo.getSuccess()){//通过身份和角色验证
				rtnInfo = new ReturnInfo();
				String fullName=user.getFullname();
				if (fullName==null || fullName.equals("")){				
					rtnInfo.setSuccess(false);
					rtnInfo.setMessage("fullName is null");
					rtnInfo.setMessageType(Globals.MESSAGE_TYPE_PARAM_INVALID);
					return rtnInfo;
				}
				if (agencyId == null || agencyId.equals("")) {				
					rtnInfo.setSuccess(false);
					rtnInfo.setMessage("代理机构Id不能为空");
					rtnInfo.setMessageType(Globals.MESSAGE_TYPE_PARAM_INVALID);					
					return rtnInfo;
				}
								
				try {	
					Integer cId=Integer.parseInt(agencyId);
					rtnInfo = userService.queryAgencyUserByFullName(user, gcon, cId);			
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
