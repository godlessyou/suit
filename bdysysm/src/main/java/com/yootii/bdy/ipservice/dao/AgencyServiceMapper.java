package com.yootii.bdy.ipservice.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.ipservice.model.AgencyService;

public interface AgencyServiceMapper {
    int deleteByPrimaryKey(Integer agencyServiceId);
    
    int deleteByAgencyService(AgencyService record);

    int insert(AgencyService record);

    int insertSelective(AgencyService record);
    
//    int insertSelectiveWithUserId(@Param("record") AgencyService record,@Param("userId") Integer userId); //暂时未用

    AgencyService selectByPrimaryKey(Integer agencyServiceId);
    
    List<AgencyService> queryByAgencyId(Integer agencyId);

    int updateByPrimaryKeySelective(AgencyService record);
    
    int updateByPrimaryKeyAndAgencyId(AgencyService record);

    int updateByPrimaryKey(AgencyService record);
    
    List<AgencyService> selectByAgencyService(@Param("agencyService") AgencyService agencyService,
    		@Param("gcon") GeneralCondition gcon,@Param("userId") Integer userId,@Param("custId") Integer custId,@Param("serviceName") String serviceName);
    
    Long selectByAgencyServiceCount(@Param("agencyService") AgencyService agencyService,
    		@Param("gcon") GeneralCondition gcon,@Param("userId") Integer userId,@Param("custId") Integer custId,@Param("serviceName") String serviceName);
    
    List<Map<String, Object>> selectByCustId(@Param("agencyService") AgencyService agencyService,
    		@Param("gcon") GeneralCondition gcon,@Param("custId") Integer custId);
    
    Long selectByCustIdCount(@Param("agencyService") AgencyService agencyService,
    		@Param("gcon") GeneralCondition gcon,@Param("custId") Integer custId);
    
    List<Map<String, Object>> selectByAgencyId(@Param("agencyService") AgencyService agencyService, @Param("agencyId") Integer agencyId);
    
    AgencyService selectAgencyServiceId(@Param("agencyService") AgencyService agencyService);
	
    Map<String, Object> selectAgencyService(@Param("caseTypeId")Integer caseTypeId,@Param("agencyId")Integer agencyId,@Param("serviceType")Integer serviceType);
    
    
}