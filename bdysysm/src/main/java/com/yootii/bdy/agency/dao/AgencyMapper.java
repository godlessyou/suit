package com.yootii.bdy.agency.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yootii.bdy.agency.model.Agency;
import com.yootii.bdy.agency.model.RetuenAmount;
import com.yootii.bdy.common.GeneralCondition;

public interface AgencyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Agency record);

    int insertSelective(Agency record);

    Agency selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Agency record);

    int updateByPrimaryKey(Agency record);

	List<Agency> selectByAgencySimple(@Param("agency")Agency agency, @Param("gcon")GeneralCondition gcon);

	Long selectCountByAgencySimple(@Param("agency")Agency agency, @Param("gcon")GeneralCondition gcon);

	Agency selectAgencyDetail(Integer id);

	List<Agency> selectAgencyList(@Param("agency")Agency agency, @Param("gcon")GeneralCondition gcon);

	Long selectCountByAgency(@Param("agency")Agency agency, @Param("gcon")GeneralCondition gcon);

	Integer checkName(@Param("agency")Agency agency);

	List<Agency> selectCooperationAgencyList(@Param("agency")Agency agency, @Param("gcon")GeneralCondition gcon);

	Agency selectAgencyByUserId(Integer userId);

	List<Agency> selectCooperationAgencyByUserId(Integer userId);

	List<Agency> selectAgencyByCustId(Integer custId);

	List<RetuenAmount> selectCustAndCpAgencyAmount(@Param("agencyId")Integer agencyId, @Param("gcon")GeneralCondition gcon, @Param("userId")Integer userId);

	List<Agency> selectUnCooperationAgencyList(@Param("agency")Agency agency, @Param("gcon")GeneralCondition gcon);
	
	List<Agency> selectAppChannel();
	
}