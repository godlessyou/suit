package com.yootii.bdy.role.service;

import java.util.List;
import java.util.Set;

import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.common.ReturnInfo;
import com.yootii.bdy.role.model.RolePermission;
import com.yootii.bdy.role.model.Role;
import com.yootii.bdy.user.model.User;

public interface RoleService {
	ReturnInfo queryRoleList(Role role,GeneralCondition gcon);
	
	ReturnInfo createRole(Role role);
	
	ReturnInfo modifyRole(Role role);
	
	ReturnInfo deleteRole(Role role);
	
	ReturnInfo bindrePermission(RolePermission rolePermission);
	
	ReturnInfo unbindPermission(RolePermission rolePermission);

	Set<String> getRoleByUserName(String userName);
	
	Set<String> getRoleByUserId(Integer userId);
	List<Role> getRoleListByUserId(Integer userId);
	//新加代理机构时增加内部角色权限
	ReturnInfo addRolePermissionByAgencyId(Integer agencyId);
}
