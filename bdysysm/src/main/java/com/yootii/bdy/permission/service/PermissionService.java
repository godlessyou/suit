package com.yootii.bdy.permission.service;

import java.util.List;
import java.util.Set;

import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.common.ReturnInfo;
import com.yootii.bdy.permission.model.Permission;
import com.yootii.bdy.user.model.User;

public interface PermissionService {
	ReturnInfo queryPermissionList(Permission permission,GeneralCondition gcon, String group);
	
	ReturnInfo createPermission(Permission permission);
	
	ReturnInfo modifyPermission(Permission permission);
	
	ReturnInfo deletePermission(Permission permission);

	Set<String> getPermissionByUserName(String userName);

	List<Permission> getPermissionByUserId(Integer userId);

	ReturnInfo queryPermissionListNoOwn(Permission permission, GeneralCondition gcon, Integer roleId);
}
