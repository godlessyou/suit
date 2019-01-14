package com.yootii.bdy.user.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.permission.model.Permission;
import com.yootii.bdy.user.model.User;

public interface UserMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKeyWithBLOBs(User record);

    int updateByPrimaryKey(User record);
    
    User selectByUsername(String username);
    
    User selectByEmail(String email);
    
    List<User> selectByUser(@Param("user") User user,@Param("gcon") GeneralCondition gcon,@Param("caller") User caller);
    
    List<User> selectAgencyUser(@Param("user") User user,@Param("gcon") GeneralCondition gcon,@Param("agencyId") Integer agencyId);
    
    
    List<User> selectAgencyUserByFullName(Map<String, Object>map);
    
    
    
    List<Map<String,Long>> getUserCount(@Param("user") User user,@Param("gcon") GeneralCondition gcon,@Param("caller") User caller );
    
    List<Map<String,Long>> selectAgencyUserCount(@Param("user") User user,@Param("gcon") GeneralCondition gcon,@Param("agencyId") Integer agencyId);
    
    int bindRole(@Param("userId") Integer userId,@Param("roleId") Integer roleId);

    int unbindRole(@Param("userId") Integer userId,@Param("roleId") Integer roleId);
    
    int bindDepartment(@Param("userId") Integer userId,@Param("departmentId") Integer departmentId);

    int unbindDepartment(@Param("userId") Integer userId,@Param("departmentId") Integer departmentId);
    
    int bindCustomer(@Param("userId") Integer userId,@Param("custId") Integer custId);

    int unbindCustomer(@Param("userId") Integer userId,@Param("custId") Integer custId);
    
    int bindAgency(@Param("userId") Integer userId,@Param("agencyId") Integer agencyId);
    
    int unbindAgency(@Param("userId") Integer userId,@Param("agencyId") Integer agencyId);
    //指定部门下的所有用户
  	List<User> selectUserByDeptId(Integer deptId);
  	
  	List<Map<String,Long>> selectUserByDeptIdCount(Integer deptId);

	List<User> selectUserByPermission(@Param("permission")Permission permission, @Param("gcon")GeneralCondition gcon);

	Long selectUserByPermissionCount(@Param("permission")Permission permission, @Param("gcon")GeneralCondition gcon);
	
	List<User> queryContactUserByAgencyId(Integer agencyId);

	List<User> selectAgentByAgencyId(Integer agencyId);
	
	long getUserCaseCount(@Param("userId")Integer userId);
	
	List<User> selectAdminByAgencyId(Integer agencyId);
}