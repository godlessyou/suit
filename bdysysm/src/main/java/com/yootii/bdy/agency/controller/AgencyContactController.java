package com.yootii.bdy.agency.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yootii.bdy.agency.model.AgencyContact;
import com.yootii.bdy.agency.service.AgencyContactService;
import com.yootii.bdy.common.CommonController;
import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.common.ReturnInfo;

@Controller
@RequestMapping("interface/agencycontact")
public class AgencyContactController extends CommonController {
	@Resource
	private AgencyContactService agencyContactService;
	@RequestMapping(value="/createagencycontact", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo createAgencyContact(HttpServletRequest request,AgencyContact agencyContact,GeneralCondition gcon){
		ReturnInfo rtnInfo = this.checkUserAndRole(request, gcon, "代理机构管理员");
		ReturnInfo rtnInfo2 = this.checkUserAndRole(request, gcon, "公司领导");
		makeOffsetAndRows(gcon);		 
		if ((rtnInfo != null && rtnInfo.getSuccess())||(rtnInfo2 != null && rtnInfo2.getSuccess())) { // 通过身份验证
			//检查权限
			try {				
				rtnInfo =agencyContactService.createAgencyContact(agencyContact);
				return rtnInfo;
			} catch (Exception e) {
				rtnInfo.setSuccess(false);
				rtnInfo.setMessage(e.getMessage());
				e.printStackTrace();
				return rtnInfo;
			}
		}
		return rtnInfo;
	}
	@RequestMapping(value="/deleteagencycontact", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo deleteAgencyContact(HttpServletRequest request,Integer  agencyContactId,GeneralCondition gcon){
		ReturnInfo rtnInfo = this.checkUserAndRole(request, gcon, "代理机构管理员");
		ReturnInfo rtnInfo2 = this.checkUserAndRole(request, gcon, "公司领导");
		makeOffsetAndRows(gcon);		 
		if ((rtnInfo != null && rtnInfo.getSuccess())||(rtnInfo2 != null && rtnInfo2.getSuccess())) { // 通过身份验证
			//检查权限
			try {				
				rtnInfo =agencyContactService.deleteAgencyContact(agencyContactId);
				return rtnInfo;
			} catch (Exception e) {
				rtnInfo.setSuccess(false);
				rtnInfo.setMessage(e.getMessage());
				e.printStackTrace();
				return rtnInfo;
			}
		}
		return rtnInfo;
	}
	@RequestMapping(value="/queryagencycontactbycust", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo queryAgencyContact(HttpServletRequest request,Integer  agencyId,Integer custId,GeneralCondition gcon){
		ReturnInfo rtnInfo = this.checkUser(request, gcon);
		makeOffsetAndRows(gcon);		 
		if ((rtnInfo != null && rtnInfo.getSuccess())) { // 通过身份验证
			//检查权限
			try {				
				rtnInfo =agencyContactService.queryAgencyContactById(agencyId, custId);
				return rtnInfo;
			} catch (Exception e) {
				rtnInfo.setSuccess(false);
				rtnInfo.setMessage(e.getMessage());
				e.printStackTrace();
				return rtnInfo;
			}
		}
		return rtnInfo;
	}
	@RequestMapping(value="/modifyagencycontact", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo modifyAgencyContact(HttpServletRequest request,Integer agencyContactId,Integer userId,GeneralCondition gcon){
		ReturnInfo rtnInfo = this.checkUserAndRole(request, gcon, "代理机构管理员");
		ReturnInfo rtnInfo2 = this.checkUserAndRole(request, gcon, "公司领导");
		makeOffsetAndRows(gcon);		 
		if ((rtnInfo != null && rtnInfo.getSuccess())||(rtnInfo2 != null && rtnInfo2.getSuccess())) { // 通过身份验证
			//检查权限
			try {				
				rtnInfo =agencyContactService.modifyAgencyContact(agencyContactId,userId);
				return rtnInfo;
			} catch (Exception e) {
				rtnInfo.setSuccess(false);
				rtnInfo.setMessage(e.getMessage());
				e.printStackTrace();
				return rtnInfo;
			}
		}
		return rtnInfo;
	}
	@RequestMapping(value="/queryagencycontact", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo queryAgencyContactByAgencyId(HttpServletRequest request,AgencyContact agencyContact,String name,String fullname,GeneralCondition gcon){
		ReturnInfo rtnInfo = this.checkUser(request, gcon);
		makeOffsetAndRows(gcon);		 
		if ((rtnInfo != null && rtnInfo.getSuccess())) { // 通过身份验证
			//检查权限
			try {				
				rtnInfo =agencyContactService.queryAgencyContactByAgencyId(agencyContact, gcon, name, fullname);
				return rtnInfo;
			} catch (Exception e) {
				rtnInfo.setSuccess(false);
				rtnInfo.setMessage(e.getMessage());
				e.printStackTrace();
				return rtnInfo;
			}
		}
		return rtnInfo;
	}
}
