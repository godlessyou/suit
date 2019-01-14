package com.yootii.bdy.mailbox.service;

import com.yootii.bdy.common.ReturnInfo;
public interface MailBoxService {
	public ReturnInfo createMailbox(String email,String password,Integer userId,String tokenID);
}
