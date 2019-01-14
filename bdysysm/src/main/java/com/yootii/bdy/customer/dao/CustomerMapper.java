package com.yootii.bdy.customer.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.customer.model.Customer;
import com.yootii.bdy.permission.model.Permission;
import com.yootii.bdy.user.model.User;

public interface CustomerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Customer record);

    int insertSelective(Customer record);

    Customer selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Customer record);

    int updateByPrimaryKeyWithBLOBs(Customer record);

    int updateByPrimaryKey(Customer record);
    
    Customer selectByUsername(String username);
    
    Customer selectByCompanyName(String name);
    
    Customer selectByEmail(String email);
    
    List<Customer> selectAllCustomer();
    
    List<Customer> selectCustUserByAgencyId(@Param("agencyId") Integer agencyId,@Param("gcon") GeneralCondition gcon);
    
    Long selectCustUserCountByAgencyId(@Param("agencyId") Integer agencyId,@Param("gcon") GeneralCondition gcon);

    List<Customer> selectByCustomer(@Param("customer") Customer customer,@Param("gcon") GeneralCondition gcon,@Param("agencyId") Integer agencyId);

    List<Map<String,Long>> selectByCustomerCount(@Param("customer") Customer customer,@Param("gcon") GeneralCondition gcon,@Param("agencyId") Integer agencyId);
    
    List<Customer> selectByOwnCustomer(@Param("customer") Customer customer,@Param("gcon") GeneralCondition gcon,@Param("userId") Integer userId,@Param("level") Integer level);

    List<Map<String,Long>> selectByOwnCustomerCount(@Param("customer") Customer customer,@Param("gcon") GeneralCondition gcon,@Param("userId") Integer userId,@Param("level") Integer level);
    
    int bindAgency(@Param("agencyId") Integer agencyId,@Param("custId") Integer custId);
    
    int unbindAgency(@Param("agencyId") Integer agencyId,@Param("custId") Integer custId);

	void unbindRole(@Param("customerId")Integer customerId, @Param("roleId")Integer roleId);

	void bindRole(@Param("customerId")Integer customerId, @Param("roleId")Integer roleId);	
    
	void unbindAllAgency(@Param("custId") Integer custId);

	void unbindAllRole(@Param("custId") Integer custId);
	
	void unbindAllUser(@Param("custId") Integer custId);

	List<Customer> selectCustomerByPermission(@Param("permission")Permission permission, @Param("gcon")GeneralCondition gcon);

	Long selectCustomerByPermissionCount(@Param("permission")Permission permission, @Param("gcon")GeneralCondition gcon);

    
	List<Customer> selectCustomerRegion(@Param("agencyId") Integer agencyId);
	
	List<Customer> selectCustomerOwnRegion(@Param("userId") Integer userId,@Param("level") Integer level);
    
	
    
}