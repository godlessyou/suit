package com.yootii.bdy.role.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

//import org.apache.shiro.SecurityUtils;
//import org.apache.shiro.authz.AuthorizationException;
//import org.apache.shiro.authz.annotation.RequiresPermissions;
//import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yootii.bdy.common.CommonController;
import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.common.ReturnInfo;
import com.yootii.bdy.role.model.Role;
import com.yootii.bdy.role.model.RolePermission;
import com.yootii.bdy.role.service.RoleService;
import com.yootii.bdy.security.service.SysService;
import com.yootii.bdy.user.model.User;
import com.yootii.bdy.user.service.UserService;



@Controller
@RequestMapping("/interface/role")
public class RoleController extends CommonController{
	@Resource
	private RoleService roleService;
	@Resource
	protected SysService sysService;
	
	@Resource
	protected UserService userService;
	@RequestMapping(value="/queryrolelist", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object queryRoleList(HttpServletRequest request,Role role,GeneralCondition gcon) {
		ReturnInfo rtnInfo = this.checkUser(request, gcon);		
		makeOffsetAndRows(gcon);		 
		// rtnInfo = this.checkUser(request,gcon);
		if (rtnInfo != null && rtnInfo.getSuccess()) { // 通过身份验证
			//检查权限
			try {
				/*Subject subject = SecurityUtils.getSubject();
				subject.checkPermission("123");*/

				rtnInfo = roleService.queryRoleList(role,gcon);
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
	@RequestMapping(value="/createrole", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object createRole(HttpServletRequest request,Role role,GeneralCondition gcon) {
		ReturnInfo rtnInfo = this.checkUser(request, gcon);	
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
		makeOffsetAndRows(gcon);		 
		// rtnInfo = this.checkUser(request,gcon);
		if (rtnInfo != null && rtnInfo.getSuccess()) { // 通过身份验证
			//检查权限
			try {

				rtnInfo = roleService.createRole(role);
			} catch (Exception e) {
				rtnInfo.setSuccess(false);
				rtnInfo.setMessage(e.getMessage());
				e.printStackTrace();
				return rtnInfo;
			}
			return rtnInfo;
		} else {
			return rtnInfo;
		}						
	}
	@RequestMapping(value="/modifyrole", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object modifyRole(HttpServletRequest request,Role role,GeneralCondition gcon) {
		ReturnInfo rtnInfo = this.checkUser(request, gcon);		
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
		makeOffsetAndRows(gcon);		 
		// rtnInfo = this.checkUser(request,gcon);
		if (rtnInfo != null && rtnInfo.getSuccess()) { // 通过身份验证
			//检查权限
			try {

				rtnInfo = roleService.modifyRole(role);
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
	
	@RequestMapping(value="/deleterole", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object deleteRole(HttpServletRequest request,Role role,GeneralCondition gcon) {
		ReturnInfo rtnInfo = this.checkUser(request, gcon);	
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
		makeOffsetAndRows(gcon);		 
		// rtnInfo = this.checkUser(request,gcon);
		if (rtnInfo != null && rtnInfo.getSuccess()) { // 通过身份验证
			//检查权限
			try {
				

				rtnInfo = roleService.deleteRole(role);
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
	@RequestMapping(value="/bindresource", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object bindreSource(HttpServletRequest request,RolePermission rolePermission,GeneralCondition gcon) {
		ReturnInfo rtnInfo = this.checkUser(request, gcon);		
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
		makeOffsetAndRows(gcon);		 
		// rtnInfo = this.checkUser(request,gcon);
		if (rtnInfo != null && rtnInfo.getSuccess()) { // 通过身份验证
			//检查权限
			try {
				

				
				rtnInfo = roleService.bindrePermission(rolePermission);
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
	@RequestMapping(value="/unbindresource", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object unbindResource(HttpServletRequest request,RolePermission rolePermission,GeneralCondition gcon) {
		ReturnInfo rtnInfo = this.checkUser(request, gcon);	
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
		makeOffsetAndRows(gcon);		 
		// rtnInfo = this.checkUser(request,gcon);
		if (rtnInfo != null && rtnInfo.getSuccess()) { // 通过身份验证
			//检查权限
			try {

				rtnInfo = roleService.unbindPermission(rolePermission);
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
