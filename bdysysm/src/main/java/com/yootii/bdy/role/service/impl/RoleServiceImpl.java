package com.yootii.bdy.role.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yootii.bdy.role.dao.RoleMapper;
import com.yootii.bdy.role.dao.RolePermissionMapper;
import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.common.ReturnInfo;
import com.yootii.bdy.permission.dao.PermissionMapper;
import com.yootii.bdy.permission.model.Permission;
import com.yootii.bdy.role.model.RolePermission;
import com.yootii.bdy.role.model.Role;
import com.yootii.bdy.role.service.RoleService;

@Service("roleService")
public class RoleServiceImpl implements RoleService{
	@Resource
	private RoleMapper roleMapper;
	@Resource
	private RolePermissionMapper rolePermissionMapper;
	@Resource
	private PermissionMapper permissionMapper;

	@Override
	public ReturnInfo queryRoleList(Role role, GeneralCondition gcon) {
		ReturnInfo rtnInfo = new ReturnInfo();
		if (gcon.getPageSize() <= 0) {
			gcon.setPageSize(10);
		}
		if (gcon.getPageNo() <= 0) {
			gcon.setPageNo(1);
		}
		//少写了关于agencyId的处理
		
		
		List<Role> roles = new ArrayList<Role>();
		roles=roleMapper.selectByrole(role,gcon);
		Long total = roleMapper.selectCountByrole(role,gcon);
		rtnInfo.setData(roles);
		rtnInfo.setTotal(total);
		rtnInfo.setCurrPage(gcon.getPageNo());
		rtnInfo.setSuccess(true);
		rtnInfo.setMessageType(4);
		rtnInfo.setMessage("查询角色成功");
		return rtnInfo;
	}

	@Override
	@Transactional
	public ReturnInfo createRole(Role role) {
		ReturnInfo rtnInfo = new ReturnInfo();
		//少写了关于agencyId的处理
	//	if(user.getUserType()!=null && user.getUserType() <= 10) {
			Integer i=roleMapper.checkName(role);
			if(i<1) {
				  roleMapper.insertSelective(role);
				 int roleId=role.getId();
				List<Permission> permissions = role.getPermission();
				if(permissions !=null && permissions.size()>0) {
					for(Permission p : permissions) {
						if(p.getId() != null) {
							RolePermission rolePermission=new RolePermission();
							rolePermission.setRoleId(roleId);
							rolePermission.setPermissionId(p.getId());
							rolePermissionMapper.insertSelective(rolePermission);
						}else {
							if(p.getPermission() !=null ) {
								p.setAgencyId(role.getAgencyId());
								Integer count = permissionMapper.checkPermission(p);
								if(count<1) {
									permissionMapper.insertSelective(p);
									RolePermission rolePermission=new RolePermission();
									rolePermission.setRoleId(roleId);
									rolePermission.setPermissionId(p.getId());
									rolePermissionMapper.insertSelective(rolePermission);
								}
								 
							}
							
						}
					}
				}
				rtnInfo.setData(role);
				rtnInfo.setSuccess(true);
				rtnInfo.setMessageType(4);
				rtnInfo.setMessage("创建角色成功");
				return rtnInfo;
			}else {
				rtnInfo.setSuccess(false);
				rtnInfo.setMessageType(-2);
				rtnInfo.setMessage("角色不能重名");
				return rtnInfo;
			}
	//	}
		//rtnInfo.setSuccess(false);
		//rtnInfo.setMessageType(-4);
		//rtnInfo.setMessage("没有权限创建角色");
		//return rtnInfo;
	}

	@Override
	@Transactional
	public ReturnInfo modifyRole(Role role) {
		ReturnInfo rtnInfo = new ReturnInfo();
	//	if(user.getUserType()!=null && user.getUserType() <= 10) {
		Role role2 = roleMapper.selectByPrimaryKey(role.getId());
			if(role2.getNoedit() != null && role2.getNoedit()==1) {
				if(role.getDescription() != null && role.getDescription() !="") {
					if(role.getDescription().equals(role2.getDescription())) {
						
					}else {
						rtnInfo.setSuccess(false);
						rtnInfo.setMessageType(-2);
						rtnInfo.setMessage("该角色为内置角色,不能修改");
						return rtnInfo;
					}
					
				}else {
					if(role2.getDescription()!=null && role.getDescription() !="") {
						rtnInfo.setSuccess(false);
						rtnInfo.setMessageType(-2);
						rtnInfo.setMessage("该角色为内置角色,不能修改");
						return rtnInfo;
					}
				}
				if(role.getAgencyId() != null) {
					if(role.getAgencyId()==(role2.getAgencyId())) {
						
					}else {
						rtnInfo.setSuccess(false);
						rtnInfo.setMessageType(-2);
						rtnInfo.setMessage("该角色为内置角色,不能修改");
						return rtnInfo;
					}
					
				}
				else {
					if(role2.getAgencyId()!=null) {
						rtnInfo.setSuccess(false);
						rtnInfo.setMessageType(-2);
						rtnInfo.setMessage("该角色为内置角色,不能修改");
						return rtnInfo;
					}
				}
				if(role.getName() != null && role.getName() != "") {
					if(role.getName().equals(role2.getName())) {
						
					}else {
						rtnInfo.setSuccess(false);
						rtnInfo.setMessageType(-2);
						rtnInfo.setMessage("该角色为内置角色,不能修改");
						return rtnInfo;
					}
					
				}
				else {
					if(role2.getName()!=null && role.getName() != "") {
						rtnInfo.setSuccess(false);
						rtnInfo.setMessageType(-2);
						rtnInfo.setMessage("该角色为内置角色,不能修改");
						return rtnInfo;
					}
				}
		}
			Integer i=roleMapper.checkName(role);
			if(i<1) {
				roleMapper.updateByPrimaryKeySelective(role);
				List<Permission> permissions = role.getPermission();
				if(permissions !=null && permissions.size()>0) {
					for(Permission p : permissions) {
						if(p.getId() != null) {
							RolePermission rolePermission=new RolePermission();
							rolePermission.setRoleId(role.getId());
							int permissionId = p.getId();
							rolePermission.setPermissionId(permissionId);
							rolePermissionMapper.unbindRolePermission(rolePermission);
							rolePermissionMapper.insertSelective(rolePermission);
							}else if(p.getPermission()==null && p.getNote()==null) {
								permissionMapper.deleteByPrimaryKey(p.getId());
							}else {
								permissionMapper.insertSelective(p);
								RolePermission rolePermission=new RolePermission();
								rolePermission.setRoleId(role.getId());
								int permissionId = p.getId();
								rolePermission.setPermissionId(permissionId);
								rolePermissionMapper.insertSelective(rolePermission);
								
							}
						}
					rtnInfo.setSuccess(true);
					rtnInfo.setMessageType(4);
					rtnInfo.setMessage("修改角色成功");
					return rtnInfo;
				}
				
			}else {
				rtnInfo.setSuccess(false);
				rtnInfo.setMessageType(-2);
				rtnInfo.setMessage("角色不能重名");
				return rtnInfo;
			}
	//	}
		rtnInfo.setSuccess(true);
		rtnInfo.setMessageType(4);
		rtnInfo.setMessage("修改角色成功");

			return rtnInfo;
	}

	@Override
	@Transactional
	public ReturnInfo deleteRole(Role role) {
		ReturnInfo rtnInfo = new ReturnInfo();
	//	if(user.getUserType()!=null && user.getUserType() <= 10) {
		Role role2 = roleMapper.selectByPrimaryKey(role.getId());
		if(role2.getNoedit() != null && role2.getNoedit()==1) {
			rtnInfo.setSuccess(false);
			rtnInfo.setMessageType(-2);
			rtnInfo.setMessage("该角色为内置角色,不能删除");
			return rtnInfo;
		}
			roleMapper.deleteByPrimaryKey(role.getId());
			rolePermissionMapper.deleteByRoleId(role.getId());
			rtnInfo.setSuccess(true);
			rtnInfo.setMessageType(4);
			rtnInfo.setMessage("删除角色成功");
			return rtnInfo;
	//	}
		/*rtnInfo.setSuccess(false);
		rtnInfo.setMessageType(-4);
		rtnInfo.setMessage("没有权限删除角色");
		return rtnInfo;*/
		
	}

	@Override
	@Transactional
	public ReturnInfo bindrePermission(RolePermission rolePermission) {
		ReturnInfo rtnInfo = new ReturnInfo();
		//if(user.getUserType()!=null && user.getUserType() <= 10) {
			rolePermissionMapper.unbindRolePermission(rolePermission);
			rolePermissionMapper.bindRolePermission(rolePermission);
			rtnInfo.setSuccess(true);
			rtnInfo.setMessageType(4);
			rtnInfo.setMessage("绑定角色权限成功");
			return rtnInfo;
		/*}
		rtnInfo.setSuccess(false);
		rtnInfo.setMessageType(-4);
		rtnInfo.setMessage("没有权限绑定角色权限");
		return rtnInfo;*/
	}

	@Override
	@Transactional
	public ReturnInfo unbindPermission(RolePermission rolePermission) {
		ReturnInfo rtnInfo = new ReturnInfo();
		//if(user.getUserType()!=null && user.getUserType() <= 10) {
		RolePermission rolePermission2 = rolePermissionMapper.selectByRolePermission(rolePermission);
		if(rolePermission2.getNoedit() != null && rolePermission2.getNoedit()==1) {
			rtnInfo.setSuccess(false);
			rtnInfo.setMessageType(-2);
			rtnInfo.setMessage("该绑定关系为内置绑定关系,不能解除绑定");
			return rtnInfo;
		}
			rolePermissionMapper.unbindRolePermission(rolePermission);
			rtnInfo.setSuccess(true);
			rtnInfo.setMessageType(4);
			rtnInfo.setMessage("解绑角色权限成功");
			return rtnInfo;
		/*}
		rtnInfo.setSuccess(false);
		rtnInfo.setMessageType(-4);
		rtnInfo.setMessage("没有权限解绑角色权限");
		return rtnInfo;*/
	}

	@Override
	public Set<String> getRoleByUserName(String userName) {
		
		return roleMapper.selectRoleByUserName(userName);
	}

	@Override
	public Set<String> getRoleByUserId(Integer userId) {
		
		return roleMapper.selectRoleByUserId(userId);
	}

	@Override
	public List<Role> getRoleListByUserId(Integer userId) {
		
		return roleMapper.selectRoleListByUserId(userId);
	}

	@Override
	public ReturnInfo addRolePermissionByAgencyId(Integer agencyId) {
		ReturnInfo info =new ReturnInfo();
		Properties properties =  new  Properties();
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("role_permission.properties");  
		
			
		
		try {
			//properties.load(inputStream);
			properties.load(new InputStreamReader(inputStream, "utf-8"));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String roleList = properties.getProperty("roleList");
		String permissionList = properties.getProperty("permission");
		String role_permission = properties.getProperty("role_permission");
		//添加role
		try {
			String[] role = roleList.split(",");
			
			for(int i = 0; i<role.length ; i++) {
				Role r =new Role();
				r.setName(role[i]);
				r.setAgencyId(agencyId);
				r.setNoedit(1);
				System.out.println(r);
				//List<Role> selectByroleOwn = roleMapper.selectByroleOwn(r);
				Long byrole = roleMapper.selectCountByroleOwn(r);
				if(byrole<1) {
					roleMapper.insertSelective(r);
				}
					
			}
			//添加permission
			String[] permission = permissionList.split(",");
			for(int i = 0; i<permission.length ; i++) {
				Permission p =new Permission();
				p.setPermission(permission[i]);
				p.setAgencyId(agencyId);
				p.setNoedit(1);
				Long byPermission = permissionMapper.selectCountByPermissionOwn(p);
				if(byPermission<1) {
					permissionMapper.insertSelective(p);
				}
			}
			//添加role_permission
			String[] rps = role_permission.split(";");
			for(int i = 0; i<rps.length ; i++) {
				String[] rp = rps[i].split(",");
				for(int j=1;j<rp.length;j++) {
					RolePermission rolePermission =new RolePermission();
					Role r1 =new Role();
					Permission p1= new Permission();
					
					r1.setAgencyId(agencyId);
					r1.setNoedit(1);
					r1.setName(rp[0]);
					List<Role> byroleOwn = roleMapper.selectByroleOwn(r1);
					p1.setAgencyId(agencyId);
					p1.setNoedit(1);
					p1.setPermission(rp[j]);
					List<Permission> byPermissionOwn = permissionMapper.selectByPermissionOwn(p1);
					if(byroleOwn != null && byroleOwn.size()>0  && byPermissionOwn != null && byPermissionOwn.size()>0) {
						rolePermission.setRoleId(byroleOwn.get(0).getId());
						rolePermission.setPermissionId(byPermissionOwn.get(0).getId());
						RolePermission selectByRolePermission = rolePermissionMapper.selectByRolePermission(rolePermission);
						if(selectByRolePermission ==null) {
							rolePermissionMapper.insertSelective(rolePermission);
						} 
					}
				}
			}
			info.setSuccess(true);
			return info;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			info.setSuccess(false);
			info.setMessage(e.getMessage());
			return info;
		}
	}
	public static void main(String[] args) {
		RoleServiceImpl r =new RoleServiceImpl();
		ReturnInfo addRolePermissionByAgencyId = r.addRolePermissionByAgencyId(10);
		System.out.println(addRolePermissionByAgencyId);
	}
}
