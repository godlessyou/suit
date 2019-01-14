package com.yootii.bdy.user.service;



import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.yootii.bdy.agency.model.Agency;
import com.yootii.bdy.agency.model.AgencyCustomer;
import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.common.ReturnInfo;
import com.yootii.bdy.permission.model.Permission;
import com.yootii.bdy.role.model.Role;
import com.yootii.bdy.user.model.User;
public interface UserService {  

	//创建用户（供平台管理、代理机构管理员使用)
	public ReturnInfo createUser(User user,User caller,GeneralCondition gcon);
	
	//创建用户（供平台管理、代理机构管理员使用)
	public ReturnInfo createAgencyAdmin(User user,Integer agencyId);
	
	//根据username查询用户
	public ReturnInfo checkUsername(String username);

	//删除用户（超级管理员和平台管理员使用）
	public ReturnInfo deleteUser(User user,User caller) throws Exception;
	
	//移除用户（代理机构管理员使用）
	public ReturnInfo removeUser(User user) throws Exception;
	
	//修改用户
	public ReturnInfo modifyUser(User user);
	
	//查询用户列表（供平台管理、代理机构管理员、超级管理员使用）
	public ReturnInfo queryAgencyUserList(User user,GeneralCondition gcon,User caller);
	
	//查询用户列表（供平台管理、代理机构管理员、超级管理员使用）
	public ReturnInfo queryUserList(User user,GeneralCondition gcon,User caller);
		
    //绑定角色
    public ReturnInfo bindRole(Integer userId, Integer roleId);
    
    //解绑角色
    public ReturnInfo unbindRole(Integer userId,Integer roleId);
    
    //绑定部门
    public ReturnInfo bindDept(Integer userId, Integer departmentId);

    //解绑部门
    public ReturnInfo unbindDept(Integer userId,Integer departmentId);
    
    //绑定客户
    public ReturnInfo bindCustomer(Integer userId, Integer custId);

    //解绑客户
    public ReturnInfo unbindCustomer(Integer userId,Integer custId);
	
    //修改用户基本信息
    public ReturnInfo modifyUserSelf(User user,GeneralCondition gcon);
    
    //上传用户头像
    public ReturnInfo uploadUserIcon(HttpServletRequest request,String username);
    
    public User getUserById(Integer userId);
    
    public ReturnInfo getUserByPrimaryKey(Integer userId);
    
    public User getUserByUsername(String username);
    //判断用户角色
    public boolean hasRole(String roleName,User user);
    //判断用户角色
    public boolean hasPermission(String permission,User user);
    //用户绑定代理所
    public ReturnInfo bindAgency(Integer userId, Integer agencyId);
    //用户解绑代理所
    public ReturnInfo unbindAgency(Integer userId, Integer agencyId);
    
    //指定部门下的所有用户
    public ReturnInfo queryUserByDeptId(Integer deptId,GeneralCondition gcon);

	public ReturnInfo queryAgencyUserListByPermission(Permission permission, GeneralCondition gcon);

	public ReturnInfo queryAgencyUserListByPermissionDepId(Permission permission, String depName, GeneralCondition gcon);
	
	//查询该部门下是否存在该用户
	public boolean checkUserInDept(Integer userId, String depName);
	
	//查询该部门下是否存在该用户
	public ReturnInfo queryUserByCustId(AgencyCustomer agencyCustomer);
	
	public ReturnInfo queryContactUserByAgencyId(Integer agencyId);

	public List<User> selectAgentByAgencyId(Integer id) ;

	public ReturnInfo checkAgencyAdmin(User user);

	public ReturnInfo createUser(User user, User caller, Integer AgencyId, GeneralCondition gcon);

	public ReturnInfo createAgencyAdmin(User user, Agency agency);
		
	public ReturnInfo queryAdminByAgencyId(Integer agencyId) ;
	
	public ReturnInfo queryAgencyUserByFullName(User user, GeneralCondition gcon,Integer agencyId) ;
	
}  