package com.yootii.bdy.department.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yootii.bdy.agency.model.Agency;
import com.yootii.bdy.agency.service.AgencyService;
import com.yootii.bdy.common.CommonController;
import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.common.ReturnInfo;
import com.yootii.bdy.department.model.Department;
import com.yootii.bdy.department.service.DepartmentService;
import com.yootii.bdy.user.model.User;

@Controller
@RequestMapping("/interface/department")
public class DepartmentController extends CommonController {
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Resource
	protected DepartmentService departmentService;
	
	
	@RequestMapping(value = "/createdept", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo createDepartment(HttpServletRequest request, Department department, GeneralCondition gcon){
		ReturnInfo rtnInfo = this.checkUserAndRole(request, gcon, "代理机构管理员");
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
		if (rtnInfo != null && rtnInfo.getSuccess()) { // 通过身份验证
			try {
				Integer userId=sysService.checkToken(gcon.getTokenID()).getUserID();
				User caller = userService.getUserById(userId);
				rtnInfo = departmentService.createDept(department, caller);
			}
			catch (Exception e) {
				logger.error(e.getMessage());
				rtnInfo.setSuccess(false);
				rtnInfo.setMessageType(-2);
				rtnInfo.setMessage(e.getMessage());
				return rtnInfo;
			}
		}	
		return rtnInfo;
	}
	@RequestMapping(value = "/deletedept", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo deleteDepartment(HttpServletRequest request, Department department, GeneralCondition gcon){
		ReturnInfo rtnInfo = this.checkUserAndRole(request, gcon, "代理机构管理员");
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
		if (rtnInfo != null && rtnInfo.getSuccess()) { // 通过身份验证
			try {
				Integer userId=sysService.checkToken(gcon.getTokenID()).getUserID();
				User caller = userService.getUserById(userId);
				rtnInfo = departmentService.deleteDept(department);
			}
			catch (Exception e) {
				logger.error(e.getMessage());
				rtnInfo.setSuccess(false);
				rtnInfo.setMessageType(-2);
				rtnInfo.setMessage(e.getMessage());
				return rtnInfo;
			}
		}	
		return rtnInfo;
	}
	@RequestMapping(value = "/modifydept", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo modifyDepartment(HttpServletRequest request, Department department, GeneralCondition gcon){
		ReturnInfo rtnInfo = this.checkUserAndRole(request, gcon, "代理机构管理员");
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
		if (rtnInfo != null && rtnInfo.getSuccess()) { // 通过身份验证
			try {
				Integer userId=sysService.checkToken(gcon.getTokenID()).getUserID();
				User caller = userService.getUserById(userId);
				rtnInfo = departmentService.modifyDept(department);
			}
			catch (Exception e) {
				logger.error(e.getMessage());
				rtnInfo.setSuccess(false);
				rtnInfo.setMessageType(-2);
				rtnInfo.setMessage(e.getMessage());
				return rtnInfo;
			}
		}	
		return rtnInfo;
	}
	@RequestMapping(value = "/querydept", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo queryDepartment(HttpServletRequest request, GeneralCondition gcon){
		ReturnInfo rtnInfo = this.checkUser(request,gcon);
		if (rtnInfo != null && rtnInfo.getSuccess()) { // 通过身份验证
			try {
				Integer userId=sysService.checkToken(gcon.getTokenID()).getUserID();
				User caller = userService.getUserById(userId);
				rtnInfo = departmentService.queryDepts(caller);
			}
			catch (Exception e) {
				logger.error(e.getMessage());
				rtnInfo.setSuccess(false);
				rtnInfo.setMessageType(-2);
				rtnInfo.setMessage(e.getMessage());
				return rtnInfo;
			}
		}	
		return rtnInfo;
	}
	
	@RequestMapping(value = "/querydepartuser", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo queryDepartmentUser(HttpServletRequest request, GeneralCondition gcon){
		ReturnInfo rtnInfo = this.checkUser(request,gcon);
		if (rtnInfo != null && rtnInfo.getSuccess()) { // 通过身份验证
			try {
				Integer userId=sysService.checkToken(gcon.getTokenID()).getUserID();
				Agency agency = agencyService.selectAgencyByUserId(userId);
				rtnInfo = departmentService.queryDepartmentUserByAgencId(agency.getId());
			}
			catch (Exception e) {
				logger.error(e.getMessage());
				rtnInfo.setSuccess(false);
				rtnInfo.setMessageType(-2);
				rtnInfo.setMessage(e.getMessage());
				return rtnInfo;
			}
		}	
		return rtnInfo;
	}	
	
	@RequestMapping(value = "/querysubdept", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo querySubDepartment(HttpServletRequest request, GeneralCondition gcon){
		ReturnInfo rtnInfo = this.checkUser(request,gcon);
		if (rtnInfo != null && rtnInfo.getSuccess()) { // 通过身份验证
			try {
				Integer userId=sysService.checkToken(gcon.getTokenID()).getUserID();
				User user = userService.getUserById(userId);
				rtnInfo = departmentService.queryDepartmentByDeptId(user.getAgency().getId());
			}
			catch (Exception e) {
				logger.error(e.getMessage());
				rtnInfo.setSuccess(false);
				rtnInfo.setMessageType(-2);
				rtnInfo.setMessage(e.getMessage());
				return rtnInfo;
			}
		}	
		return rtnInfo;
	}
	@RequestMapping(value = "/queryuserdept", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo queryUserDepartment(HttpServletRequest request, GeneralCondition gcon){
		ReturnInfo rtnInfo = this.checkUser(request,gcon);
		if (rtnInfo != null && rtnInfo.getSuccess()) { // 通过身份验证
			try {
				Integer userId=sysService.checkToken(gcon.getTokenID()).getUserID();
				rtnInfo = departmentService.queryDepartmentByUserId(userId);
			}
			catch (Exception e) {
				logger.error(e.getMessage());
				rtnInfo.setSuccess(false);
				rtnInfo.setMessageType(-2);
				rtnInfo.setMessage(e.getMessage());
				return rtnInfo;
			}
		}	
		return rtnInfo;
	}
}
