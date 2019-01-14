package com.yootii.bdy.role.dao;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.role.model.Role;

public interface RoleMapper {
	
    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

	List<Role> selectByrole(@Param("role")Role role, @Param("gcon")GeneralCondition gcon);

	Long selectCountByrole(@Param("role")Role role, @Param("gcon")GeneralCondition gcon);

	Integer chenkName(@Param("name")String name);

	Set<String> selectRoleByUserName(@Param("userName")String userName);

	Integer checkName(@Param("role")Role role);

	Set<String> selectRoleByUserId(@Param("userId")Integer userId);

	Set<String> selectRoleByCustomerId(@Param("customerId")Integer id);

	List<Role> selectRoleListByUserId(@Param("userId")Integer userId);

	Long selectCountByroleOwn(@Param("role")Role role);
	List<Role> selectByroleOwn(@Param("role")Role role);
}