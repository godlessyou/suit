package com.yootii.bdy.security.service;

import com.yootii.bdy.security.model.Token;

public interface SysService {

    
	/**
	 * 检查检查会话token
	 * @param tokenID
	 * @return
	 */
	Token checkToken(String tokenID);
	
	/**
	 * 增加token
	 * @param tokenID
	 * @return Boolean
	 */
	void addToken(Token token);
	
	/**
	 * 移除token
	 * @param String tokenID
	 * @return Token
	 */
	Token removeToken(String tokenID);
	
	/**
	 * 移除过期token
	 * @param
	 */
	void removeExpiredTokens();
	

	/**
	 * 设置超时
	 * @param seconds
	 */
	void setExpiredSeconds(int seconds);

	/**
	 * 
	 * @return int 超时间隔
	 */
	int getExpiredSeconds();
}
