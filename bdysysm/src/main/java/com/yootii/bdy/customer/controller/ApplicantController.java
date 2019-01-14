package com.yootii.bdy.customer.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yootii.bdy.agency.model.Agency;
import com.yootii.bdy.agency.service.AgencyService;
import com.yootii.bdy.common.CommonController;
import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.common.ReturnInfo;
import com.yootii.bdy.customer.model.Applicant;
import com.yootii.bdy.customer.service.ApplicantService;

@Controller
@RequestMapping("interface/applicant")
public class ApplicantController extends CommonController{
	
	@Resource
	private ApplicantService applicantService;
	
	@RequestMapping(value="/queryapplicant", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object queryApplicant(HttpServletRequest request,Applicant applicant,GeneralCondition gcon,Integer customerId) {
		ReturnInfo rtnInfo = this.checkUser(request, gcon);		
		makeOffsetAndRows(gcon);		 
		if (rtnInfo != null && rtnInfo.getSuccess()) { // 通过身份验证
			//检查权限
			try {				
				Integer customerId1=sysService.checkToken(gcon.getTokenID()).getCustomerID();
				if(customerId1 != null) {
					rtnInfo =applicantService.queryApplicant(applicant,gcon,customerId1);
				}else {
					rtnInfo =applicantService.queryApplicant(applicant,gcon,customerId);
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
	@RequestMapping(value="/queryapplicantdetail", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object queryApplicantDetail(HttpServletRequest request,Applicant applicant,GeneralCondition gcon) {
		ReturnInfo rtnInfo = this.checkUser(request, gcon);		
		makeOffsetAndRows(gcon);		 
		if (rtnInfo != null && rtnInfo.getSuccess()) { // 通过身份验证
			//检查权限
			try {				
				/*Integer userId=sysService.checkToken(gcon.getTokenID()).getUserID();
				User user = userService.getUserById(userId);*/
				rtnInfo =applicantService.queryApplicantDetail(applicant,gcon);
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
	@RequestMapping(value="/queryapplicantbyappcnname", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object queryApplicantbyappcnname(HttpServletRequest request,String appCnName,GeneralCondition gcon) {
		ReturnInfo rtnInfo = this.checkUser(request, gcon);		
		makeOffsetAndRows(gcon);		 
		if (rtnInfo != null && rtnInfo.getSuccess()) { // 通过身份验证
			//检查权限
			try {				
				/*Integer userId=sysService.checkToken(gcon.getTokenID()).getUserID();
				User user = userService.getUserById(userId);*/
				rtnInfo =applicantService.queryApplicantbyappcnname(appCnName,gcon);
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
	@RequestMapping(value="/queryallapplicantbyappId", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object queryAllApplicantByAppId(HttpServletRequest request,Integer appId,GeneralCondition gcon) {
		ReturnInfo rtnInfo = this.checkUser(request, gcon);		
		makeOffsetAndRows(gcon);		 
		if (rtnInfo != null && rtnInfo.getSuccess()) { // 通过身份验证
			//检查权限
			try {				
				/*Integer userId=sysService.checkToken(gcon.getTokenID()).getUserID();
				User user = userService.getUserById(userId);*/
				rtnInfo =applicantService.queryAllApplicantByAppId(gcon, appId);
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
	@RequestMapping(value="/queryallapplicantbycustId", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object queryAllApplicantByCustId(HttpServletRequest request,Integer customerId,GeneralCondition gcon) {
		ReturnInfo rtnInfo = this.checkUser(request, gcon);		
		makeOffsetAndRows(gcon);		 
		if (rtnInfo != null && rtnInfo.getSuccess()) { // 通过身份验证
			//检查权限
			try {				
				/*Integer userId=sysService.checkToken(gcon.getTokenID()).getUserID();
				User user = userService.getUserById(userId);*/
				rtnInfo =applicantService.queryAllApplicantByCustId(gcon, customerId);
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
	@RequestMapping(value="/bindapplicant", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object bindApplicant(HttpServletRequest request,String idlist,Integer mainId, GeneralCondition gcon) {
		ReturnInfo rtnInfo = this.checkUser(request, gcon);		
		makeOffsetAndRows(gcon);		 
		if (rtnInfo != null && rtnInfo.getSuccess()) { // 通过身份验证
			//检查权限
			try {				
				rtnInfo =applicantService.bindApplicant(idlist, mainId);
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
	@RequestMapping(value="/unbindapplicant", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object unBindApplicant(HttpServletRequest request,Integer id,GeneralCondition gcon) {
		ReturnInfo rtnInfo = this.checkUser(request, gcon);		
		makeOffsetAndRows(gcon);		 
		if (rtnInfo != null && rtnInfo.getSuccess()) { // 通过身份验证
			//检查权限
			try {				
				rtnInfo =applicantService.unBindApplicant(id);
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
	@RequestMapping(value="/checkAndSaveApplicant", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object checkAndSaveApplicant(HttpServletRequest request,Applicant  applicant,Integer customerId,GeneralCondition gcon) {
		ReturnInfo rtnInfo = this.checkUser(request, gcon);		
		makeOffsetAndRows(gcon);		 
		if (rtnInfo != null && rtnInfo.getSuccess()) { // 通过身份验证
			//检查权限
			try {				
				/*Integer userId=sysService.checkToken(gcon.getTokenID()).getUserID();
				User user = userService.getUserById(userId);*/
				rtnInfo =applicantService.checkAndSaveApplicant(applicant,customerId);
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
	@RequestMapping(value="/queryapplicantbyappname", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object queryApplicantbyappname(HttpServletRequest request,String appCnName,String appEnName,GeneralCondition gcon) {
		ReturnInfo rtnInfo = this.checkUser(request, gcon);		
		makeOffsetAndRows(gcon);		 
		if (rtnInfo != null && rtnInfo.getSuccess()) { // 通过身份验证
			//检查权限
			try {				
				/*Integer userId=sysService.checkToken(gcon.getTokenID()).getUserID();
				User user = userService.getUserById(userId);*/
				rtnInfo =applicantService.queryApplicantbyappname(appCnName,appEnName,gcon);
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
	
	@RequestMapping(value="/ceateapplicant", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object ceateApplicant(HttpServletRequest request,Applicant applicant,GeneralCondition gcon, Integer customerId) {
		ReturnInfo rtnInfo = this.checkUser(request, gcon);		
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
		makeOffsetAndRows(gcon);		 
		if (rtnInfo != null && rtnInfo.getSuccess()) { // 通过身份验证
			//检查权限
			try {				
//				Integer customerId=sysService.checkToken(gcon.getTokenID()).getCustomerID();
				rtnInfo =applicantService.ceateApplicant(applicant,gcon,customerId);
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
	
	@RequestMapping(value="/motifyapplicant", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object motifyApplicant(HttpServletRequest request,Applicant applicant,GeneralCondition gcon,Integer customerId) {
		ReturnInfo rtnInfo = this.checkUser(request, gcon);		
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
		makeOffsetAndRows(gcon);		 
		if (rtnInfo != null && rtnInfo.getSuccess()) { // 通过身份验证
			//检查权限
			try {				
				/*Integer userId=sysService.checkToken(gcon.getTokenID()).getUserID();
				User user = userService.getUserById(userId);*/
				rtnInfo =applicantService.motifyApplicant(applicant,gcon,customerId);
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
	
	@RequestMapping(value="/deleteapplicant", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object deleteApplicant(HttpServletRequest request,Applicant applicant,GeneralCondition gcon,Integer customerId) {
		ReturnInfo rtnInfo = this.checkUser(request, gcon);		
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
		makeOffsetAndRows(gcon);		 
		if (rtnInfo != null && rtnInfo.getSuccess()) { // 通过身份验证
			//检查权限
			try {				
//				Integer customerId=sysService.checkToken(gcon.getTokenID()).getCustomerID();
				rtnInfo =applicantService.deleteapplicant(applicant,gcon,customerId);
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
	
	@RequestMapping(value="/queryapplicantbycustomerId", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object queryApplicantByCustomerId(HttpServletRequest request,GeneralCondition gcon,Integer customerId) {
		ReturnInfo rtnInfo = this.checkUser(request, gcon);		
		makeOffsetAndRows(gcon);		 
		if (rtnInfo != null && rtnInfo.getSuccess()) { // 通过身份验证
			//检查权限
			try {				
				/*Integer userId=sysService.checkToken(gcon.getTokenID()).getUserID();
				User user = userService.getUserById(userId);*/
				rtnInfo =applicantService.queryApplicantByCustId(gcon,customerId);
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
	
	
	
	@RequestMapping(value="/queryApplicantByCustId", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object queryApplicantByCustId(HttpServletRequest request,GeneralCondition gcon,Integer customerId) {
		ReturnInfo rtnInfo = this.checkUser(request, gcon);		
		makeOffsetAndRows(gcon);		 
		if (rtnInfo != null && rtnInfo.getSuccess()) { // 通过身份验证
			//检查权限
			try {				
				/*Integer userId=sysService.checkToken(gcon.getTokenID()).getUserID();
				User user = userService.getUserById(userId);*/
				rtnInfo =applicantService.queryApplicantNameByCustId(gcon, customerId);
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
	
	@RequestMapping(value="/queryandcreateapplicant", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object queryAndCreateAapplicant(HttpServletRequest request,GeneralCondition gcon,Applicant applicant,Integer custId) {
		ReturnInfo rtnInfo = this.checkUser(request, gcon);		
		makeOffsetAndRows(gcon);	
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
		if (rtnInfo != null && rtnInfo.getSuccess()) { // 通过身份验证
			//检查权限
			try {				
				/*Integer userId=sysService.checkToken(gcon.getTokenID()).getUserID();
				User user = userService.getUserById(userId);*/
				rtnInfo =applicantService.queryAndCreateAapplicant(gcon,applicant,custId);
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
	
	
	
	@RequestMapping(value="/queryTmCountByAppIdList", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object queryTmCountByAppIdList(HttpServletRequest request,GeneralCondition gcon,String appIdList) {
		ReturnInfo rtnInfo = this.checkUser(request, gcon);		
		makeOffsetAndRows(gcon);		 
		if (rtnInfo != null && rtnInfo.getSuccess()) { // 通过身份验证
			//检查权限
			try {				
				/*Integer userId=sysService.checkToken(gcon.getTokenID()).getUserID();
				User user = userService.getUserById(userId);*/
				rtnInfo =applicantService.queryTmCountByAppIdList(gcon,appIdList);
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
