package com.yootii.bdy.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yootii.bdy.agency.model.AgencyCustomer;
import com.yootii.bdy.user.model.User;
import com.yootii.bdy.user.model.UserCustomer;

public interface UserCustomerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserCustomer record);

    int insertSelective(UserCustomer record);

    UserCustomer selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserCustomer record);

    int updateByPrimaryKey(UserCustomer record);
    
    List<UserCustomer>  getUserByCustId(@Param("agencyCustomer")AgencyCustomer agencyCustomer);
}