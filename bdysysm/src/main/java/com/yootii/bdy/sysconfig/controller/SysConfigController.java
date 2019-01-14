package com.yootii.bdy.sysconfig.controller;



import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yootii.bdy.common.CommonController;
import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.common.Globals;
import com.yootii.bdy.common.IpList;
import com.yootii.bdy.common.ReturnInfo;
import com.yootii.bdy.security.model.Token;


@Controller
@RequestMapping("/interface/sysconfig")
public class SysConfigController extends CommonController{
	
	private final Logger logger = Logger.getLogger(this.getClass());
		
	
	//设置用户的语言环境
	@RequestMapping(value = "/setLanguage", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo setLanguage(HttpServletRequest request, String language, GeneralCondition gcon){
		
		ReturnInfo rtnInfo = new ReturnInfo();
		if (language==null || language.equals("")){
			rtnInfo.setSuccess(false);		
			rtnInfo.setMessage("缺少参数language");
			rtnInfo.setMessageType(Globals.MESSAGE_TYPE_PARAM_INVALID);
			return rtnInfo;
		}
		
		rtnInfo = this.checkUser(request, gcon);		
		if(rtnInfo != null && rtnInfo.getSuccess()){//通过身份和角色验证
			Token token =(Token)rtnInfo.getData();				
			token.setLanguage(language);	
			Globals.setLanguage(language);
			rtnInfo.setSuccess(true);		
			rtnInfo.setMessage("语言设置成功");
			logger.info("language is set to " + language);
		}
		return rtnInfo;
	}
	
	public static String getIp2(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if((!(ip==null || ip.equals(""))) && !"unKnown".equalsIgnoreCase(ip)){
			//多次反向代理后会有多个ip值，第一个ip才是真实ip
			int index = ip.indexOf(",");
			if(index != -1){
				return ip.substring(0,index);
			}else{
				return ip;
			}
		}
		ip = request.getHeader("X-Real-IP");
		if((!(ip==null || ip.equals(""))) && !"unKnown".equalsIgnoreCase(ip)){
			return ip;
		}
		return request.getRemoteAddr();
	}

	//设置用户的语言环境
	@RequestMapping(value = "/getRegion", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo getRegion(HttpServletRequest request,GeneralCondition gcon){

		ReturnInfo rtnInfo = new ReturnInfo();
		String ip = getIp2(request);


		String[] strs = ip.split("\\.");
		if (strs.length != 4) {
			rtnInfo.setSuccess(false);		
			rtnInfo.setMessage("参数ip错误");
			rtnInfo.setMessageType(Globals.MESSAGE_TYPE_PARAM_INVALID);
			return rtnInfo;
		}
		int key = Integer.valueOf(strs[0]) * 256 + Integer.valueOf(strs[1]);
		List<Object[]> list = IpList.IpList.get(key);
		if (list == null) {
			rtnInfo.setSuccess(true);		
			rtnInfo.setMessage("查找成功");
			rtnInfo.setData("en");
			return rtnInfo;
		}
		if (list.size() == 1 && list.get(0).length==1) {
			rtnInfo.setSuccess(true);		
			rtnInfo.setMessage("查找成功");
			rtnInfo.setData(list.get(0)[0]);
			return rtnInfo;
		}
		int ipValue = Integer.valueOf(strs[2]) * 256 + Integer.valueOf(strs[3]);
		for (Object[] ipRange : list) {
			if (ipValue >=  Integer.valueOf(ipRange[1].toString()).intValue() && ipValue <=  Integer.valueOf(ipRange[2].toString()).intValue()) {
				rtnInfo.setSuccess(true);		
				rtnInfo.setMessage("查找成功");
				rtnInfo.setData(list.get(0)[0]);
				return rtnInfo;
			}
		}
		rtnInfo.setSuccess(true);		
		rtnInfo.setMessage("查找成功");
		rtnInfo.setData("en");
		return rtnInfo;
	}

}
