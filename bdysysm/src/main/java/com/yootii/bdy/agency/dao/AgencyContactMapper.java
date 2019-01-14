package com.yootii.bdy.agency.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yootii.bdy.agency.model.AgencyContact;
import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.customer.model.Customer;
import com.yootii.bdy.user.model.User;

public interface AgencyContactMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AgencyContact record);

    int insertSelective(AgencyContact record);

    AgencyContact selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AgencyContact record);

    int updateByPrimaryKey(AgencyContact record);
    
    List<User> selectUsers(AgencyContact agencyContact);
    
    List<Map<String, Object>> selectAgencyContact(AgencyContact agencyContact);
    
    List<Map<String, Object>> selectByAgencyId(@Param("agencyContact")AgencyContact agencyContact,
    		@Param("gcon")GeneralCondition gcon,@Param("name")String name,@Param("fullname")String fullname);
    
    Long selectByAgencyIdCount(@Param("agencyContact")AgencyContact agencyContact,
    		@Param("gcon")GeneralCondition gcon,@Param("name")String name,@Param("fullname")String fullname);
    
    AgencyContact checkAgencyContact(AgencyContact agencyContact);
    
    int isExist(@Param("username")String username);
    
    void addCustomer(Map<String, Object> map);
    void updateCustomer(Customer customer);
    List<Map<String, Object>> queryProxy(@Param("id")Integer id);
    void insertAgencyContact(Map<String, Object> map);
    Map<String, Object> queryCustomerById(@Param("id")Integer id);
    void insertCustomerContact(Map<String, Object> map);
    
    int checkExist(@Param("custId")Integer custId);
    
    int validCaller(@Param("username")String username,@Param("password")String password);
}