package com.yootii.bdy.util;

import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;


@Component
public class ProcessEnginUrlConfig {

	// 流程服务接口
	private String processEngineUrl="";

	public String getProcessEngineUrl() {
		return processEngineUrl;
	}

	public void setProcessEngineUrl(String processEngineUrl) {
		this.processEngineUrl = processEngineUrl;
	}

	

}
