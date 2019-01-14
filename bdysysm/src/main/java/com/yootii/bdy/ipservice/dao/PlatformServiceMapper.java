package com.yootii.bdy.ipservice.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.ipservice.model.PlatformService;

public interface PlatformServiceMapper {
    int deleteByPrimaryKey(Integer serviceId);

    int insert(PlatformService record);

    int insertSelective(PlatformService record);

    PlatformService selectByPrimaryKey(Integer serviceId);

    int updateByPrimaryKeySelective(PlatformService record);

    int updateByPrimaryKey(PlatformService record);
    
    List<PlatformService> selectByPlatformService(@Param("platformService") PlatformService platformService,
    		@Param("gcon")GeneralCondition gcon);
    
    Long selectByPlatformServiceCount(@Param("platformService") PlatformService platformService,
    		@Param("gcon")GeneralCondition gcon);
    
    List<PlatformService> queryPlatformService(@Param("parent") Integer parent);
    
    PlatformService queryPlatformServiceByCaseTypeIdAndServiceType(@Param("caseTypeId")Integer caseTypeId,@Param("serviceType")Integer serviceType);
    
    Integer selectByCaseTypeIdAndParent(Integer caseTypeId);
    
    void upLoad(PlatformService platformService);
    
    PlatformService selectById(@Param("id")Integer serviceId);
}