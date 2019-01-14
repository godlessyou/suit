package com.yootii.bdy.util;

import org.springframework.stereotype.Component;



@Component
public class ServiceUrlConfig {

	// 流程服务接口
	private String processEngineUrl="";	
	
	private String bdyautomailUrl="";
	
	private String processServiceUrl ="";
	
	
	public String getProcessEngineUrl() {
		return processEngineUrl;
	}

	public void setProcessEngineUrl(String processEngineUrl) {
		processEngineUrl=processEngineUrl.trim();
		this.processEngineUrl = processEngineUrl;
	}

	public String getBdyautomailUrl() {
		return bdyautomailUrl;
	}

	public void setBdyautomailUrl(String bdyautomailUrl) {
		this.bdyautomailUrl = bdyautomailUrl;
	}

	public String getProcessServiceUrl() {
		return processServiceUrl;
	}

	public void setProcessServiceUrl(String processServiceUrl) {
		this.processServiceUrl = processServiceUrl;
	}
	
}
