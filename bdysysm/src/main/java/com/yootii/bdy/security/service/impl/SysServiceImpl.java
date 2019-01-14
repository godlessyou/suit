package com.yootii.bdy.security.service.impl;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.yootii.bdy.security.model.Token;
import com.yootii.bdy.security.service.SysService;

@Service
public class SysServiceImpl implements SysService{

	private static Map<String, Token> tokens = new ConcurrentHashMap<String, Token>();

	private int expiredSeconds;

	private static Logger logger = Logger.getLogger(SysServiceImpl.class);


	@Override
	public Token checkToken(String tokenID) {
		if (tokenID==null){
			logger.debug("tokeID is null");
			return null;			
		}
			
		if (!tokens.containsKey(tokenID)){
			logger.debug("tokens can not cantains this tokenID: "+ tokenID);
			return null;
		}
		Token token = tokens.get(tokenID);
		if(token==null){
			logger.debug("token is null, because of tokens can not cantains this tokenID: "+ tokenID);
			return null;
		}
		Date currenttime = new Date();
		long gapseconds = (currenttime.getTime() - token.getCheckTime().getTime()) / 1000;
		if (gapseconds > expiredSeconds * 1000L){
			logger.debug("return null, because expired Seconds, gapseconds="+ gapseconds+", expiredSeconds="+ expiredSeconds);
			return null;
		}
		token.setCheckTime(currenttime);
//		this.removeToken(tokenID);
//		this.addToken(token);
		return token;
	}

	@Override
	public void addToken(Token token) {
		String key = token.getTokenID();
		tokens.put(key, token);
		logger.debug("addToken: key="+  key+" , token="+ token);
		
		return;

	}

	@Override
	public Token removeToken(String tokenID) {
		Token token = tokens.get(tokenID);
		if (token != null){
			tokens.remove(tokenID);
			logger.debug("removeToken: tokenID="+  tokenID);
		}
		return token;

	}

	@Override
	public void removeExpiredTokens() {
		logger.debug("quart定时清除过期token,当前token总个数：" + tokens.size());
		
		long current = (new Date()).getTime();
		Collection<Token> tokenValues = tokens.values();
		Iterator<Token> iter = tokenValues.iterator();
		while (iter.hasNext()) {
			Token token = iter.next();

//			System.out.println("token值："+token.getUsername()+"="+token.getTokenID());
			
			if (current - token.getCheckTime().getTime() > expiredSeconds * 1000L) {
				logger.debug("removeExpiredTokens：TokenID="+token.getTokenID());
				tokens.remove(token.getTokenID());
				
			}
		}
		return;
	}

	@Override
	public void setExpiredSeconds(int seconds) {
		this.expiredSeconds = seconds;
	}

	@Override
	public int getExpiredSeconds() {
		return this.expiredSeconds;
	}
	
	public static void main(String[] args) {
//		SysServiceImpl sysServiceImpl=new SysServiceImpl();
//		Token token=new Token();
//		String tokenID="1111111111111";
//		token.setTokenID(tokenID);
//		Date currenttime = new Date();
//		System.out.println("currenttime="+ currenttime);
//		token.setCheckTime(currenttime);
//		sysServiceImpl.addToken(token);
//		try {
//			Thread.sleep(10000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		sysServiceImpl.checkToken(tokenID);
//		try {
//			Thread.sleep(10000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		sysServiceImpl.checkToken(tokenID);
	}
}
