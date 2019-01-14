package com.yootii.bdy.agency.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

//import org.apache.shiro.authz.AuthorizationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yootii.bdy.agency.model.Agency;
import com.yootii.bdy.agency.model.CooperationAgency;
import com.yootii.bdy.agency.service.AgencyService;
import com.yootii.bdy.common.CommonController;
import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.common.ReturnInfo;
import com.yootii.bdy.customer.model.Customer;
import com.yootii.bdy.customer.service.CustomerService;
import com.yootii.bdy.permission.service.PermissionService;
import com.yootii.bdy.security.model.Token;
import com.yootii.bdy.security.service.SysService;
import com.yootii.bdy.user.model.User;
import com.yootii.bdy.user.service.UserService;

@Controller
@RequestMapping("interface/cooperationagency")
public class CooperationAgencyController extends CommonController{
	@Resource
	private AgencyService agencyService;
	@Resource
	protected UserService userService;
	@Resource
	protected PermissionService permissionService;
	@Resource
	protected SysService sysService;
	@Resource
	protected CustomerService customerService;
	@RequestMapping(value="/querycooperationagency", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object querycooperationagency(HttpServletRequest request,Agency agency,GeneralCondition gcon,Integer userId) {
		ReturnInfo rtnInfo = this.checkUser(request, gcon);		
		makeOffsetAndRows(gcon);		 
		if (rtnInfo != null && rtnInfo.getSuccess()) { // 通过身份验证
			//检查权限
			try {				
				/*Integer userId=sysService.checkToken(gcon.getTokenID()).getUserID();
				User user = userService.getUserById(userId);*/
				rtnInfo =agencyService.querycooperationagency(agency, gcon,userId);
				return rtnInfo;
			} catch (Exception e) {
				rtnInfo.setSuccess(false);
				rtnInfo.setMessage(e.getMessage());
				e.printStackTrace();
				return rtnInfo;
			}
		} else {
			return rtnInfo;
		}						
	}
	@RequestMapping(value="/queryuncooperationagency", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object queryuncooperationagency(HttpServletRequest request,Agency agency,GeneralCondition gcon) {
		ReturnInfo rtnInfo = this.checkUser(request, gcon);		
		makeOffsetAndRows(gcon);		 
		if (rtnInfo != null && rtnInfo.getSuccess()) { // 通过身份验证
			//检查权限
			try {				
				/*Integer userId=sysService.checkToken(gcon.getTokenID()).getUserID();
				User user = userService.getUserById(userId);*/
				rtnInfo =agencyService.queryuncooperationagency(agency, gcon);
				return rtnInfo;
			} catch (Exception e) {
				rtnInfo.setSuccess(false);
				rtnInfo.setMessage(e.getMessage());
				e.printStackTrace();
				return rtnInfo;
			}
		} else {
			return rtnInfo;
		}						
	}
	
	@RequestMapping(value="/bindcooperationagency", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object bindcooperationagency(HttpServletRequest request,CooperationAgency cooperationAgency,GeneralCondition gcon,User user) {
		ReturnInfo rtnInfo = this.checkUserAndRole(request, gcon, "代理机构管理员");
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
		makeOffsetAndRows(gcon);		 
		// rtnInfo = this.checkUser(request,gcon);
		if (rtnInfo != null && rtnInfo.getSuccess()) { // 通过身份验证
			//检查权限
			try {				
				/*Integer userId=sysService.checkToken(gcon.getTokenID()).getUserID();
				User user = userService.getUserById(userId);*/
				rtnInfo =agencyService.bindcooperationagency(cooperationAgency, user);
				return rtnInfo;
			} catch (Exception e) {
				rtnInfo.setSuccess(false);
				rtnInfo.setMessage(e.getMessage());
				e.printStackTrace();
				return rtnInfo;
			}
		} else {
			return rtnInfo;
		}						
	}
	@RequestMapping(value="/unbindcooperationagency", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object unbindcooperationagency(HttpServletRequest request,CooperationAgency cooperationAgency,GeneralCondition gcon,User user) {
		ReturnInfo rtnInfo = this.checkUserAndRole(request, gcon, "代理机构管理员");	
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
		makeOffsetAndRows(gcon);		 
		// rtnInfo = this.checkUser(request,gcon);
				if (rtnInfo != null && rtnInfo.getSuccess()) { // 通过身份验证
					//检查权限
					try {				
						Integer userId=sysService.checkToken(gcon.getTokenID()).getUserID();
						User user1 = userService.getUserById(userId);
						rtnInfo =agencyService.unbindcooperationagency(cooperationAgency, user1);
						return rtnInfo;
					} catch (Exception e) {
						rtnInfo.setSuccess(false);
						rtnInfo.setMessage(e.getMessage());
						e.printStackTrace();
						return rtnInfo;
					}
				} else {
					return rtnInfo;
				}						
						
	}
	@RequestMapping(value="/createagency", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object createAgency(HttpServletRequest request,Agency agency,GeneralCondition gcon) {
		ReturnInfo rtnInfo = this.checkUserAndRole(request, gcon, "平台管理员");	
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
		makeOffsetAndRows(gcon);		 
		if (rtnInfo != null && rtnInfo.getSuccess()) { // 通过身份验证
			//检查权限
			try {				
				rtnInfo =agencyService.createAgency(agency);
				return rtnInfo;
			} catch (Exception e) {
				rtnInfo.setSuccess(false);
				rtnInfo.setMessage(e.getMessage());
				e.printStackTrace();
				return rtnInfo;
			}
		} else {
			return rtnInfo;
		}						
	}
	@RequestMapping(value="/modifyagency", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object modifyAgency(HttpServletRequest request,Agency agency,GeneralCondition gcon) {
		ReturnInfo rtnInfo = this.checkUser(request, gcon);		
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
		makeOffsetAndRows(gcon);		 
		// rtnInfo = this.checkUser(request,gcon);
		if (rtnInfo != null && rtnInfo.getSuccess()) { // 通过身份验证
			//检查权限
			try {				
				Integer userId=sysService.checkToken(gcon.getTokenID()).getUserID();
				User user = userService.getUserById(userId);
				rtnInfo =agencyService.modifyAgency(agency, gcon, user);
				return rtnInfo;
			} catch (Exception e) {
				rtnInfo.setSuccess(false);
				rtnInfo.setMessage(e.getMessage());
				e.printStackTrace();
				return rtnInfo;
			}
		} else {
			return rtnInfo;
		}						
	}
	@RequestMapping(value="/checkname", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object checkName(HttpServletRequest request,Agency agency,GeneralCondition gcon) {
		ReturnInfo rtnInfo = this.checkUser(request, gcon);		
		makeOffsetAndRows(gcon);		 
		if (rtnInfo != null && rtnInfo.getSuccess()) { // 通过身份验证
			//检查权限
			try {				
				rtnInfo =agencyService.checkName(agency);
				return rtnInfo;
			} catch (Exception e) {
				rtnInfo.setSuccess(false);
				rtnInfo.setMessage(e.getMessage());
				e.printStackTrace();
				return rtnInfo;
			}
		} else {
			return rtnInfo;
		}						
	}
	@RequestMapping(value="/deleteagency", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object deleteAgency(HttpServletRequest request,Agency agency,GeneralCondition gcon) {
		ReturnInfo rtnInfo = this.checkUserAndRole(request, gcon, "平台管理员");	
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
		makeOffsetAndRows(gcon);		 
		if (rtnInfo != null && rtnInfo.getSuccess()) { // 通过身份验证
			//检查权限
			try {				
				rtnInfo =agencyService.deleteAgency(agency);
				return rtnInfo;
			} catch (Exception e) {
				rtnInfo.setSuccess(false);
				rtnInfo.setMessage(e.getMessage());
				e.printStackTrace();
				return rtnInfo;
			}
		} else {
			return rtnInfo;
		}						
	}
	@RequestMapping(value="/uploadlogo", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object uploadLogo(HttpServletRequest request,Agency agency,GeneralCondition gcon) {
		//ReturnInfo rtnInfo = this.checkUserAndRole(request, gcon, "平台管理员");	
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
		ReturnInfo rtnInfo = this.checkUser(request, gcon);
		makeOffsetAndRows(gcon);		 
		if (rtnInfo != null && rtnInfo.getSuccess()) { // 通过身份验证
			//检查权限
			try {				
				rtnInfo =agencyService.uploadLogo(request, agency);
				return rtnInfo;
			} catch (Exception e) {
				rtnInfo.setSuccess(false);
				rtnInfo.setMessage(e.getMessage());
				e.printStackTrace();
				return rtnInfo;
			}
		} else {
			return rtnInfo;
		}						
	}
}
