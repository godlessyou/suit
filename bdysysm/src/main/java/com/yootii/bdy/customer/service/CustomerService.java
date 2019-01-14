package com.yootii.bdy.customer.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.common.ReturnInfo;
import com.yootii.bdy.customer.model.Customer;
import com.yootii.bdy.permission.model.Permission;
import com.yootii.bdy.user.model.User;

public interface CustomerService {
	//创建客户用户
	public ReturnInfo createCustomer(Customer customer, GeneralCondition gcon, Integer agencyId, Integer agencyContactId);
	
	public ReturnInfo deleteCustomer(Integer custId);
	
	public ReturnInfo checkUsername(String username);
	
	public ReturnInfo checkCompanyName(String name);
	
	public ReturnInfo bindAgency(Integer custId,Integer agencyId, Integer agencyContactId);
	
	public ReturnInfo unbindAgency(Integer custId,Integer agencyId);
	//查询当前代理机构的客户
	public ReturnInfo queryCustomer(Customer customer,User caller,GeneralCondition gcon);
	//查询当前代理人负责客户
	public ReturnInfo queryOwnCustomer(Customer customer,User caller,GeneralCondition gcon,Integer level);
	
	public List<Customer> queryAllCustomer();

	public ReturnInfo modifyCustomerSelf(Customer customer);
	
	public ReturnInfo modifyCustomerLocked(Integer custId, Integer locked);
	
	public ReturnInfo uploadUserIcon(HttpServletRequest request, String username) ;
	
	public Customer selectByUsername(String username);
	
	public Customer getCustById(Integer id) ;
	
	public ReturnInfo getCustDetailById(Integer id) ;

	public ReturnInfo bindRole(Integer customerId, Integer roleId);

	public ReturnInfo unbindRole(Integer customerId, Integer roleId);
	
	 //判断客户是否拥有角色
    public boolean hasRoleByCustomer(String roleName,Customer customer);
    //判断客户是否拥有权限
    public boolean hasPermissionByCustomer(String permission,Customer customer);

	public ReturnInfo bindApplicant(Integer custId, Integer appId);

	public ReturnInfo unBindApplicant(Integer custId, Integer appId);

	public ReturnInfo queryAgencyCustomerListByPermission(Permission permission, GeneralCondition gcon);

	public ReturnInfo createCustomer(Customer customer, GeneralCondition gcon, Integer agencyId, Integer agencyContactId, Double value);
	
	public ReturnInfo sendAccountEmail(Integer custId);

	public ReturnInfo queryCustomerUser(Integer agencyId, GeneralCondition gcon);
	
	//变更案件代理人接口
	public ReturnInfo changeCustomerAgentUser(String customerId,String beUserId, String afUserId);
	
	//添加案件代理人接口	
	public ReturnInfo addCustomerAgentUser(String customerId, String userIds, String tokenID);
	
	
	public boolean checkBindCust(Integer custId, Integer appId);
			
	
	public ReturnInfo queryCustomerRegion(User caller);
	
	public ReturnInfo queryCustomerOwnRegion( User caller,Integer level);
}


