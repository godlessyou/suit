package com.yootii.bdy.customer.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.common.ReturnInfo;
import com.yootii.bdy.customer.dao.CustomerContactMapper;
import com.yootii.bdy.customer.model.Customer;
import com.yootii.bdy.customer.model.CustomerContact;
import com.yootii.bdy.customer.service.CustomerContactService;

@Service("CustomerContactService")
public class CustomerContactServiceImpl implements CustomerContactService{
	
	@Resource
	protected CustomerContactMapper customerContactMapper;

	@Override
	public ReturnInfo queryCustomerContact(CustomerContact customerContact, GeneralCondition gcon) {
		ReturnInfo info = new ReturnInfo();
		List<CustomerContact>	customerContacts = customerContactMapper.selectByCustomerContact(customerContact,gcon);
		Long total =  customerContactMapper.selectCountByCustomerContact(customerContact,gcon);
		info.setData(customerContacts);
		info.setTotal(total);
		info.setCurrPage(gcon.getPageNo());
		info.setSuccess(true);
		info.setMessage("查询联系人成功");
		return info ;
	}

	@Override
	public ReturnInfo ceateCustomerContact(CustomerContact customerContact) {
		ReturnInfo info = new ReturnInfo();
		customerContactMapper.insertSelective(customerContact);
		info.setSuccess(true);
		info.setMessage("创建联系人成功");
		return info ;
	}

	@Override
	public ReturnInfo motifyCustomerContact(CustomerContact customerContact) {
		ReturnInfo info = new ReturnInfo();
		Integer id = customerContact.getId();
		if(id ==null){
			info.setSuccess(false);
			info.setMessage("联系人编号不能为空");
			return info ;
		}
		customerContactMapper.updateByPrimaryKeySelective(customerContact);
		info.setSuccess(true);
		info.setMessage("修改联系人成功");
		return info ;
	}

	@Override
	public ReturnInfo deleteCustomerContact(CustomerContact customerContact) {
		ReturnInfo info = new ReturnInfo();
		customerContactMapper.deleteByPrimaryKey(customerContact.getId());
		info.setData(customerContact);
		info.setSuccess(true);
		info.setMessage("删除联系人成功");
		return info ;
	}
	
	
}
