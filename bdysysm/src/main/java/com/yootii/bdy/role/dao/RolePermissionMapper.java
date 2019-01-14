package com.yootii.bdy.role.dao;

import org.apache.ibatis.annotations.Param;

import com.yootii.bdy.role.model.RolePermission;

public interface RolePermissionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RolePermission record);

    int insertSelective(RolePermission record);

    RolePermission selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RolePermission record);

    int updateByPrimaryKey(RolePermission record);

	void deleteByRoleId(@Param("roleId")Integer id);

	int bindRolePermission(RolePermission rolePermission);

	int unbindRolePermission(RolePermission rolePermission);

	void deleteByPermissionId(@Param("permissionId")Integer id);

	RolePermission selectByRolePermission(@Param("rolePermission")RolePermission rolePermission);
}