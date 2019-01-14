package com.yootii.bdy.agency.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yootii.bdy.agency.model.AgencyCustomer;

public interface AgencyCustomerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AgencyCustomer record);

    int insertSelective(AgencyCustomer record);

    AgencyCustomer selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AgencyCustomer record);

    int updateByPrimaryKey(AgencyCustomer record);

	Integer selectByAgencyAndCustId(@Param("agencyId")Integer agencyId,@Param("custId")Integer custId);

	List<Integer> selectCustIdByAgencyId(Integer agencyId);
}