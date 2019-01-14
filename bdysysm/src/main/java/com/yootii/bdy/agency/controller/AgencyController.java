package com.yootii.bdy.agency.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

//import org.apache.shiro.authz.AuthorizationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yootii.bdy.agency.model.Agency;
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
@RequestMapping("interface/agency")
public class AgencyController extends CommonController{
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
	@RequestMapping(value="/queryagencysimplelist", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object queryAgencySimpleList(HttpServletRequest request,Agency agency,GeneralCondition gcon) {
		ReturnInfo rtnInfo = this.checkUser(request, gcon);		
		makeOffsetAndRows(gcon);		 
		if (rtnInfo != null && rtnInfo.getSuccess()) { // 通过身份验证
			//检查权限
			try {				
				/*Integer userId=sysService.checkToken(gcon.getTokenID()).getUserID();
				User user = userService.getUserById(userId);*/
				rtnInfo =agencyService.queryAgencySimpleList(agency, gcon);
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
	@RequestMapping(value="/queryagencylist", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object queryAgencyList(HttpServletRequest request,Agency agency,GeneralCondition gcon) {
		ReturnInfo rtnInfo = this.checkUserAndRole(request, gcon, "平台管理员");	
		makeOffsetAndRows(gcon);		 
		// rtnInfo = this.checkUser(request,gcon);
		if (rtnInfo != null && rtnInfo.getSuccess()) { // 通过身份验证
			//检查权限
			try {				
				/*Integer userId=sysService.checkToken(gcon.getTokenID()).getUserID();
				User user = userService.getUserById(userId);*/
				rtnInfo =agencyService.queryAgencyList(agency, gcon);
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
	@RequestMapping(value="/queryagencydetail", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object queryAgencyDetail(HttpServletRequest request,Agency agency,GeneralCondition gcon) {
		ReturnInfo rtnInfo = this.checkUser(request, gcon);		
		makeOffsetAndRows(gcon);		 
		// rtnInfo = this.checkUser(request,gcon);
		if (rtnInfo != null && rtnInfo.getSuccess()) { // 通过身份验证
			//检查权限
			try {				
				Token token = sysService.checkToken(gcon.getTokenID());
				if(token.isUser()) {
					Integer userId=sysService.checkToken(gcon.getTokenID()).getUserID();
					User user = userService.getUserById(userId);
					rtnInfo =agencyService.queryAgencyDetailByUser(agency, gcon, user);
				}else {
					String username=sysService.checkToken(gcon.getTokenID()).getUsername();
					Customer customer=customerService.selectByUsername(username);
					rtnInfo=agencyService.queryAgencyDetailByCustomer(agency, gcon, customer);
				}
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
		ReturnInfo rtnInfo = this.checkUser(request, gcon);
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
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
	@RequestMapping(value="/statscustandcpagency", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object statsCustAndCpAgency(HttpServletRequest request,Integer agencyId,Integer userId,GeneralCondition gcon) {
		//ReturnInfo rtnInfo = this.checkUserAndRole(request, gcon, "平台管理员");	
		ReturnInfo rtnInfo = this.checkUser(request, gcon);
		makeOffsetAndRows(gcon);		 
		if (rtnInfo != null && rtnInfo.getSuccess()) { // 通过身份验证
			//检查权限
			try {				
				rtnInfo =agencyService.statsCustAndCpAgency(request, agencyId,userId,gcon);
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
	//查询本代理机构下的custId和userId
	@RequestMapping(value="/querycustidanduseridbyagency", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object queryCustIdandUserIdByagency(HttpServletRequest request,Integer agencyId,GeneralCondition gcon) {
		//ReturnInfo rtnInfo = this.checkUserAndRole(request, gcon, "平台管理员");	
		ReturnInfo rtnInfo = this.checkUser(request, gcon);
				 
		if (rtnInfo != null && rtnInfo.getSuccess()) { // 通过身份验证
			//检查权限
			try {				
				rtnInfo =agencyService.queryCustIdandUserIdByagency(request, agencyId,gcon);
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
	
	
	@RequestMapping(value="/queryAgencyChannel", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object queryAgencyChannel(HttpServletRequest request,GeneralCondition gcon) {
		ReturnInfo rtnInfo = this.checkUser(request, gcon);		
		makeOffsetAndRows(gcon);		 
		// rtnInfo = this.checkUser(request,gcon);
		if (rtnInfo != null && rtnInfo.getSuccess()) { // 通过身份验证
			//检查权限
			try {				
				
				rtnInfo=agencyService.queryAgencyChannel( gcon);				
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
