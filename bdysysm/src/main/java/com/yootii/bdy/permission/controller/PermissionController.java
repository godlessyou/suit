package com.yootii.bdy.permission.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

//import org.apache.shiro.authz.AuthorizationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yootii.bdy.common.CommonController;
import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.common.ReturnInfo;
import com.yootii.bdy.permission.model.Permission;
import com.yootii.bdy.permission.service.PermissionService;
import com.yootii.bdy.role.model.Role;
import com.yootii.bdy.security.service.SysService;
import com.yootii.bdy.user.model.User;
import com.yootii.bdy.user.service.UserService;

@Controller
@RequestMapping("/interface/permission")
public class PermissionController extends CommonController{
	@Resource
	protected UserService userService;
	@Resource
	protected PermissionService permissionService;
	@Resource
	protected SysService sysService;
	
	@RequestMapping(value="/querypermission", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object queryPermissionList(HttpServletRequest request,Permission permission,GeneralCondition gcon,String group) {
		ReturnInfo rtnInfo = this.checkUser(request, gcon);		
		makeOffsetAndRows(gcon);		 
		
		if (rtnInfo != null && rtnInfo.getSuccess()) { // 通过身份验证
			//检查权限
			try {
				

				rtnInfo = permissionService.queryPermissionList(permission, gcon,group);
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
	
	@RequestMapping(value="/ceatepermission", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object ceatePermission(HttpServletRequest request,Permission permission,GeneralCondition gcon) {
		ReturnInfo rtnInfo = this.checkUserAndRole(request, gcon, "代理机构管理员");	
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
		makeOffsetAndRows(gcon);		 

		if (rtnInfo != null && rtnInfo.getSuccess()) { // 通过身份验证
			//检查权限
			try {

				rtnInfo = permissionService.createPermission(permission);
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
	
	@RequestMapping(value="/motifypermission", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object motifyPermission(HttpServletRequest request,Permission permission,GeneralCondition gcon) {
		ReturnInfo rtnInfo = this.checkUser(request, gcon);		
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
		makeOffsetAndRows(gcon);		 

		if (rtnInfo != null && rtnInfo.getSuccess()) { // 通过身份验证
			//检查权限
			try {

				rtnInfo = permissionService.modifyPermission(permission);
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
	
	@RequestMapping(value="/deletepermission", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object deletePermission(HttpServletRequest request,Permission permission,GeneralCondition gcon) {
		ReturnInfo rtnInfo = this.checkUser(request, gcon);	
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
		makeOffsetAndRows(gcon);		 

		if (rtnInfo != null && rtnInfo.getSuccess()) { // 通过身份验证
			//检查权限
			try {				

				rtnInfo = permissionService.deletePermission(permission);
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
	
	@RequestMapping(value="/querypermissionnoown", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object queryPermissionListNoOwn(HttpServletRequest request,Permission permission,GeneralCondition gcon,Integer roleId) {
		ReturnInfo rtnInfo = this.checkUser(request, gcon);		
		makeOffsetAndRows(gcon);		 
		
		if (rtnInfo != null && rtnInfo.getSuccess()) { // 通过身份验证
			//检查权限
			try {
				

				rtnInfo = permissionService.queryPermissionListNoOwn(permission, gcon,roleId);
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
