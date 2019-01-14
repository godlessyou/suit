package com.yootii.bdy.permission.dao;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.permission.model.Permission;

public interface PermissionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Permission record);

    int insertSelective(Permission record);

    Permission selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Permission record);

    int updateByPrimaryKey(Permission record);

	List<Permission> selectByPermission(@Param("permission")Permission permission, @Param("gcon")GeneralCondition gcon, @Param("group")String group);

	Long selectCountByPermission(@Param("permission")Permission permission, @Param("gcon")GeneralCondition gcon);

	Integer chenkPermission(String permission);

	Set<String> selectPermissionByUserName(String userName);

	List<String> selectPermissionByUserId(@Param("userId")Integer userId);

	List<Permission> selectPermissionListByUserId(Integer userId);

	Integer checkPermission(@Param("permission")Permission permission);

	List<String> selectPermissionByCustomerId(@Param("customerId")Integer id);

	List<Permission> selectByPermissionNoOwn(@Param("permission")Permission permission, @Param("gcon")GeneralCondition gcon, @Param("roleId")Integer roleId);

	Long selectCountByPermissionOwn(@Param("permission")Permission p);
	List<Permission> selectByPermissionOwn(@Param("permission")Permission p);
}