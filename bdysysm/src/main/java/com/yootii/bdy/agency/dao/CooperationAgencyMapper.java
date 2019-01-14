package com.yootii.bdy.agency.dao;

import org.apache.ibatis.annotations.Param;

import com.yootii.bdy.agency.model.Agency;
import com.yootii.bdy.agency.model.CooperationAgency;
import com.yootii.bdy.common.GeneralCondition;

public interface CooperationAgencyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CooperationAgency record);

    int insertSelective(CooperationAgency record);

    CooperationAgency selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CooperationAgency record);

    int updateByPrimaryKey(CooperationAgency record);

	Integer selectCooperationAgencyCountByAgencyId(@Param("agencyId")Integer id);

	void deleteByCooperationAgency(CooperationAgency cooperationAgency);

	Long selectCountByCooperationAgency(@Param("agency")Agency agency, @Param("gcon")GeneralCondition gcon);
}