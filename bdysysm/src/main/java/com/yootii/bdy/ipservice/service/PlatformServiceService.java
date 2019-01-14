package com.yootii.bdy.ipservice.service;

import javax.servlet.http.HttpServletRequest;

import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.common.ReturnInfo;
import com.yootii.bdy.ipservice.model.PlatformService;

public interface PlatformServiceService {
	public ReturnInfo createPlatformService(HttpServletRequest request,PlatformService platformService);
	public ReturnInfo deletePlatformService(PlatformService platformService);
	public ReturnInfo modifyPlatformService(HttpServletRequest request,PlatformService platformService);
	public ReturnInfo queryPlatformServiceList(PlatformService platformService,GeneralCondition gcon);
	public ReturnInfo queryPlatformServiceDetail(PlatformService platformService);
	public ReturnInfo queryPlatformServices();
	public PlatformService uploadLogo(HttpServletRequest request,Integer serviceId)throws Exception;
	public ReturnInfo upLoad(HttpServletRequest request,Integer serviceId) throws Exception;
}