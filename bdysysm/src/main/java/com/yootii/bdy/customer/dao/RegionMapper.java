package com.yootii.bdy.customer.dao;

import java.util.List;

import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.customer.model.Region;
import com.yootii.bdy.customer.model.Regions;

import org.apache.ibatis.annotations.Param;

public interface RegionMapper {
    int deleteByPrimaryKey(Integer regionId);

    int insert(Region record);

    int insertSelective(Region record);

    Region selectByPrimaryKey(Integer regionId);

    int updateByPrimaryKeySelective(Region record);

    int updateByPrimaryKey(Region record);
      
    
	List<Region> selectRegionList(@Param("region")Region region, @Param("gcon")GeneralCondition gcon);

	Long selectRegionCount(@Param("region")Region region, @Param("gcon")GeneralCondition gcon);
	
	List<Regions> selectRegions();
	
	List<Region> selectAllRegion();
}