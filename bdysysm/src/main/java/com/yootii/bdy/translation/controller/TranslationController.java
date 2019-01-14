package com.yootii.bdy.translation.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yootii.bdy.agency.model.Agency;
import com.yootii.bdy.common.CommonController;
import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.common.ReturnInfo;
import com.yootii.bdy.customer.model.Customer;
import com.yootii.bdy.invite.model.AgencyInvite;
import com.yootii.bdy.invite.model.CustomerInvite;
import com.yootii.bdy.task.service.AgentOpenTaskService;
import com.yootii.bdy.task.service.CoopeOpenTaskService;
import com.yootii.bdy.task.service.CustOpenTaskService;
import com.yootii.bdy.translation.service.TranslationService;
import com.yootii.bdy.user.model.User;

@Controller
@RequestMapping("interface/translation")
public class TranslationController extends CommonController {

	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Resource
	private TranslationService translationService;
	

	
	
	
	@RequestMapping(value="/translation", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo translation(HttpServletRequest request,String bflanguage,String aflanguage){
		ReturnInfo info = new ReturnInfo();
		try {
			
			Map<String, Object> runmap = new HashMap<String, Object>();
			Enumeration<String> em = request.getParameterNames();
			while (em.hasMoreElements()) {
				String name = (String) em.nextElement();
				if (name.startsWith("run_")) {
					String value = request.getParameter(name);
					String pName = name.substring(4);
					logger.info("doTaskByPro: property name:" + pName
							+ " value:" + value);
					runmap.put(pName, value);
				}
			}
			
			//验证登录身份
			info.setSuccess(true);
			info.setData(translationService.translationMap(runmap, bflanguage,aflanguage)); 				
		}catch (Exception e) {
			logger.error(e.getMessage());
			info.setSuccess(false);
			info.setMessageType(-2);
			info.setMessage(e.getMessage());
			return info;
		}
	
	
		return info;
	}
	
	
}