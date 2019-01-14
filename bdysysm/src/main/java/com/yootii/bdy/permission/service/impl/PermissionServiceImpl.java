package com.yootii.bdy.permission.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.common.ReturnInfo;
import com.yootii.bdy.permission.dao.PermissionMapper;
import com.yootii.bdy.permission.model.Permission;
import com.yootii.bdy.permission.service.PermissionService;
import com.yootii.bdy.role.dao.RolePermissionMapper;
import com.yootii.bdy.role.model.Role;
import com.yootii.bdy.user.model.User;
import com.yootii.bdy.user.service.UserService;
@Service("permissionService")
public class PermissionServiceImpl implements PermissionService{
	@Resource
	private PermissionMapper permissionMapper;
	@Resource
	private RolePermissionMapper rolePermissionMapper;
	@Resource
	private UserService userService;
	@Override
	public ReturnInfo queryPermissionList(Permission permission, GeneralCondition gcon,String group) {
		ReturnInfo rtnInfo = new ReturnInfo();
		if (gcon.getPageSize() <= 0) {
			gcon.setPageSize(10);
		}
		if (gcon.getPageNo() <= 0) {
			gcon.setPageNo(1);
		}
		List<Permission> permissions = new ArrayList<Permission>();
		permissions=permissionMapper.selectByPermission(permission,gcon,group);
		Long total = permissionMapper.selectCountByPermission(permission,gcon);
		rtnInfo.setData(permissions);
		rtnInfo.setTotal(total);
		rtnInfo.setCurrPage(gcon.getPageNo());
		rtnInfo.setSuccess(true);
		rtnInfo.setMessageType(4);
		rtnInfo.setMessage("查询权限成功");
		return rtnInfo;
	}

	@Override
	@Transactional
	public ReturnInfo createPermission(Permission permission) {
		ReturnInfo rtnInfo = new ReturnInfo();
		//if(userService.hasPermission("", user)) {
			//Integer i=permissionMapper.chenkPermission(permission.getPermission());
		
		Integer i=permissionMapper.checkPermission(permission);
			if(i<1) {
				permissionMapper.insertSelective(permission);
				rtnInfo.setData(permission);
				rtnInfo.setSuccess(true);
				rtnInfo.setMessageType(4);
				rtnInfo.setMessage("创建权限成功");
				return rtnInfo;
			}else {
				rtnInfo.setSuccess(false);
				rtnInfo.setMessageType(-2);
				rtnInfo.setMessage("权限不能重名");
				return rtnInfo;
			}
		/*}
		rtnInfo.setSuccess(false);
		rtnInfo.setMessageType(-4);
		rtnInfo.setMessage("没有权限创建权限");
		return rtnInfo;*/
	}

	@Override
	@Transactional
	public ReturnInfo modifyPermission(Permission permission) {
		ReturnInfo rtnInfo = new ReturnInfo();
		//if(user.getUserType()!=null && user.getUserType() <= 10) {
			//Integer i=permissionMapper.chenkPermission(permission.getPermission());
		
		Permission permission2 = permissionMapper.selectByPrimaryKey(permission.getId());
		
		//Modification start, by yang guang, 2018-03-11, to resolve NullPointerException
		if (permission2==null){
			rtnInfo.setSuccess(false);
			rtnInfo.setMessageType(-2);
			rtnInfo.setMessage("参数不正确，系统不存在指定id的权限");
			return rtnInfo;
		}
		//Modification end
		
		if(permission2.getNoedit() != null && permission2.getNoedit()==1) {
			rtnInfo.setSuccess(false);
			rtnInfo.setMessageType(-2);
			rtnInfo.setMessage("该权限为内置权限,不能修改");
			return rtnInfo;
		}
			Integer i=permissionMapper.checkPermission(permission);
			if(i<1) {
				permissionMapper.updateByPrimaryKeySelective(permission);
				rtnInfo.setSuccess(true);
				rtnInfo.setMessageType(4);
				rtnInfo.setMessage("修改权限成功");
				return rtnInfo;
			}else {
				rtnInfo.setSuccess(false);
				rtnInfo.setMessageType(-2);
				rtnInfo.setMessage("权限不能重名");
				return rtnInfo;
			}
		/*}
		rtnInfo.setSuccess(false);
		rtnInfo.setMessageType(-4);
		rtnInfo.setMessage("没有权限修改权限");
		return rtnInfo;*/
	}

	@Override
	@Transactional
	public ReturnInfo deletePermission(Permission permission) {
		ReturnInfo rtnInfo = new ReturnInfo();
		//if(user.getUserType()!=null && user.getUserType() <= 10) {
		Permission permission2 = permissionMapper.selectByPrimaryKey(permission.getId());
		if(permission2.getNoedit() != null && permission2.getNoedit()==1) {
			rtnInfo.setSuccess(false);
			rtnInfo.setMessageType(-2);
			rtnInfo.setMessage("该权限为内置权限,不能删除");
			return rtnInfo;
		}
			permissionMapper.deleteByPrimaryKey(permission.getId());
			rolePermissionMapper.deleteByPermissionId(permission.getId());
			rtnInfo.setSuccess(true);
			rtnInfo.setMessageType(4);
			rtnInfo.setMessage("删除权限成功");
			return rtnInfo;
		/*}
		rtnInfo.setSuccess(false);
		rtnInfo.setMessageType(-4);
		rtnInfo.setMessage("没有权限删除权限");
		return rtnInfo;*/
	}

	@Override
	public Set<String> getPermissionByUserName(String userName) {
		
		return permissionMapper.selectPermissionByUserName(userName);
	}

	@Override
	public List<Permission> getPermissionByUserId(Integer userId) {
		return permissionMapper.selectPermissionListByUserId(userId);
	}

	@Override
	public ReturnInfo queryPermissionListNoOwn(Permission permission, GeneralCondition gcon,Integer roleId) {
		ReturnInfo rtnInfo = new ReturnInfo();
		List<Permission> permissions = new ArrayList<Permission>();
		permissions=permissionMapper.selectByPermissionNoOwn(permission,gcon,roleId); 
		rtnInfo.setData(permissions);
		rtnInfo.setCurrPage(gcon.getPageNo());
		rtnInfo.setSuccess(true);
		rtnInfo.setMessageType(4);
		rtnInfo.setMessage("查询权限成功");
		return rtnInfo;
	}

}
