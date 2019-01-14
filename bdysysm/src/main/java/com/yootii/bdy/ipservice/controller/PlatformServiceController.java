package com.yootii.bdy.ipservice.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yootii.bdy.common.CommonController;
import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.common.ReturnInfo;
import com.yootii.bdy.ipservice.model.PlatformService;
import com.yootii.bdy.ipservice.service.PlatformServiceService;
import com.yootii.bdy.user.model.User;
import com.yootii.bdy.user.service.UserService;

@Controller
@RequestMapping("/interface/platformservice")
public class PlatformServiceController extends CommonController{
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Resource
	private PlatformServiceService platformServiceService;
	
	@Resource
	protected UserService userService;
	
	@RequestMapping(value = "/createservice", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo createPlatformService(HttpServletRequest request,GeneralCondition gcon,PlatformService platformService){
		ReturnInfo info = this.checkUser(request, gcon);
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
		if (info != null && info.getSuccess()) { // 通过身份验证
			try{
				Integer userId=sysService.checkToken(gcon.getTokenID()).getUserID();
				User caller = userService.getUserById(userId);
				if(caller==null||caller.getUserType()!=1){
					info.setSuccess(false);
					info.setMessage("权限不足");
					return info;
				}
				info = platformServiceService.createPlatformService(request,platformService);
			}catch(Exception e){
				logger.error(e.getMessage());
				info.setSuccess(false);
				info.setMessage("创建失败");
				return info;
			}
		}
		return info;
	}
	
	@RequestMapping(value = "/deleteservice", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo deletePlatformService(HttpServletRequest request,GeneralCondition gcon,PlatformService platformService){
		ReturnInfo info = this.checkUser(request, gcon);
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
		if (info != null && info.getSuccess()) { // 通过身份验证
			try{
				Integer userId=sysService.checkToken(gcon.getTokenID()).getUserID();
				User caller = userService.getUserById(userId);
				if(caller==null||caller.getUserType()!=1){
					info.setSuccess(false);
					info.setMessage("权限不足");
					return info;
				}
				info = platformServiceService.deletePlatformService(platformService);
			}catch(Exception e){
				logger.error(e.getMessage());
				info.setSuccess(false);
				info.setMessage("删除失败");
				info.setMessageType(-2);
				return info;
			}
		}
		return info;
	}
	
	@RequestMapping(value = "/modifyservice", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo modifyPlatformService(HttpServletRequest request,GeneralCondition gcon,PlatformService platformService){
		ReturnInfo info = this.checkUser(request, gcon);
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
		if (info != null && info.getSuccess()) { // 通过身份验证
			try{
				Integer userId=sysService.checkToken(gcon.getTokenID()).getUserID();
				User caller = userService.getUserById(userId);
				if(caller==null||caller.getUserType()!=1){
					info.setSuccess(false);
					info.setMessage("权限不足");
					return info;
				}
				info = platformServiceService.modifyPlatformService(request,platformService);
			}catch(Exception e){
				logger.error(e.getMessage());
				info.setSuccess(false);
				info.setMessage("修改失败");
				info.setMessageType(-2);
				return info;
			}
		}
		return info;
	}
	
	@RequestMapping(value = "/queryservicelist", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo queryPlatformServiceList(HttpServletRequest request,GeneralCondition gcon,PlatformService platformService){
		ReturnInfo info = this.checkUser(request, gcon);
		if (info != null && info.getSuccess()) { // 通过身份验证
			makeOffsetAndRows(gcon);
			try{
				info = platformServiceService.queryPlatformServiceList(platformService, gcon);
			}catch(Exception e){
				logger.error(e.getMessage());
				info.setSuccess(false);
				info.setMessage("查询列表失败");
				info.setMessageType(-2);
				return info;
			}
		}
		return info;
	}
	
	@RequestMapping(value = "/queryservicedetail", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo queryPlatformServiceDetail(HttpServletRequest request,GeneralCondition gcon,PlatformService platformService){
		ReturnInfo info = this.checkUser(request, gcon);
		if (info != null && info.getSuccess()) { // 通过身份验证
			makeOffsetAndRows(gcon);
			try{
				info = platformServiceService.queryPlatformServiceDetail(platformService);
			}catch(Exception e){
				e.printStackTrace();
				logger.error(e.getMessage());
				info.setSuccess(false);
				info.setMessage("查询详情失败");
				info.setMessageType(-2);
				return info;
			}
		}
		return info;
	}
	@RequestMapping(value = "/queryservices", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo queryPlatformServices(HttpServletRequest request,GeneralCondition gcon,PlatformService platformService){
		ReturnInfo info = this.checkUser(request, gcon);
		if (info != null && info.getSuccess()) { // 通过身份验证
			makeOffsetAndRows(gcon);
			try{
				info = platformServiceService.queryPlatformServices();
			}catch(Exception e){
				e.printStackTrace();
				logger.error(e.getMessage());
				info.setSuccess(false);
				info.setMessage("查询详情失败");
				info.setMessageType(-2);
				return info;
			}
		}
		return info;
	}
	
	/**
	 * 修改logo
	 * @param request
	 * @param gCondition
	 * @param serviceId
	 * @return
	 */
	@RequestMapping(value="/uploadlogo",produces="application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo upLoad(HttpServletRequest request,GeneralCondition gCondition,Integer serviceId){
		ReturnInfo returnInfo = this.checkUser(request, gCondition);
		if(returnInfo !=null && returnInfo.getSuccess()){
			try{
				returnInfo = platformServiceService.upLoad(request, serviceId);
				returnInfo.setSuccess(true);
				returnInfo.setMessage("上传成功");
			}catch (Exception e) {
				e.printStackTrace();
				returnInfo.setSuccess(false);
				returnInfo.setMessage("上传失败"+e.getMessage());
			}
		}
		return returnInfo;
	}
	
	
	
}