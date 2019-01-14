package com.yootii.bdy.ipservice.service;

import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.common.ReturnInfo;
import com.yootii.bdy.ipservice.model.AgencyService;
import com.yootii.bdy.security.model.Token;


public interface AgencyServiceService {
   public ReturnInfo createAgencyService(AgencyService agencyService,Integer userId,Integer caseTypeId,Integer serviceType);
   public ReturnInfo deleteAgencyService(AgencyService agencyService,Integer userId);
   public ReturnInfo modifyAgencyService(AgencyService agencyService,Integer userId);
   public ReturnInfo queryAgencyServiceList(AgencyService agencyService,GeneralCondition gcon,Token token,String serviceName);
   public ReturnInfo queryAgencyServiceDetail(AgencyService agencyService);
   public ReturnInfo custQueryAgencyServiceList(AgencyService agencyService,GeneralCondition gcon,Token token);
   public ReturnInfo custQueryAgencyServiceListByCaseTypeId(Integer caseTypeId,GeneralCondition gcon,Token token);
   
   public ReturnInfo getAgencyService(AgencyService agencyService);
   
   public ReturnInfo queryAgencyService(Integer caseTypeId,Integer agencyId,Integer serviceType);
   public ReturnInfo createAgencyAllService(Integer agencyId);
}