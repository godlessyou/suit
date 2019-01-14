package com.yootii.bdy.mailbox.service.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.yootii.bdy.common.ReturnInfo;
import com.yootii.bdy.mailbox.service.MailBoxService;
import com.yootii.bdy.util.GraspUtil;
import com.yootii.bdy.util.JsonUtil;
import com.yootii.bdy.util.ServiceUrlConfig;

@Service
public class MailBoxServiceImpl implements MailBoxService{
	@Resource
	private ServiceUrlConfig serviceUrlConfig;
	private final Logger logger = Logger.getLogger(this.getClass());
	@Override
	public ReturnInfo createMailbox(String email, String password,Integer userId,String tokenID) {
		ReturnInfo rtnInfo = new ReturnInfo();
		String url=serviceUrlConfig.getBdyautomailUrl()+"/mailbox/create?"+"email="+email+"&password="+ password+"&userId="+userId+"&tokenID="+tokenID;
		String jsonString = null;
		try {
			jsonString = GraspUtil.getText(url);
			rtnInfo=JsonUtil.toObject(jsonString, ReturnInfo.class);
		} catch (Exception e) {
			logger.info("create mailbox return object: "+ jsonString);
			e.printStackTrace();
			rtnInfo.setSuccess(false);
			rtnInfo.setMessage(e.getMessage());
		} 
		return rtnInfo;
	}
}
