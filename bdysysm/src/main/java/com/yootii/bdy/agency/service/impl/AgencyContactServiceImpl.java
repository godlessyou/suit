package com.yootii.bdy.agency.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yootii.bdy.agency.dao.AgencyContactMapper;
import com.yootii.bdy.agency.model.AgencyContact;
import com.yootii.bdy.agency.service.AgencyContactService;
import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.common.ReturnInfo;
import com.yootii.bdy.user.model.User;

@Service
public class AgencyContactServiceImpl implements AgencyContactService{
	@Resource
	private AgencyContactMapper agencyContactMapper;
	
	@Override
	public ReturnInfo createAgencyContact(AgencyContact agencyContact) {
		ReturnInfo info = new ReturnInfo();
		Integer agencyId = agencyContact.getAgencyId();
		Integer custId = agencyContact.getCustId();
		Integer userId = agencyContact.getUserId();
		if(agencyId==null){
			info.setSuccess(false);
			info.setMessage("agencyID不能为空");
			return info;
		}
		if(custId==null){
			info.setSuccess(false);
			info.setMessage("custID不能为空");
			return info;
		}
		if(userId==null){
			info.setSuccess(false);
			info.setMessage("userID不能为空");
			return info;
		}
		//检查该客户是否已经设置该联系人
		AgencyContact checkResult = agencyContactMapper.checkAgencyContact(agencyContact);
		if(checkResult==null){
			agencyContactMapper.insertSelective(agencyContact);
		}
		info.setSuccess(true);
		info.setMessage("创建成功");
		return info;
	}

	@Override
	public ReturnInfo deleteAgencyContact(Integer agencyContactId) {
		ReturnInfo info = new ReturnInfo();
		if(agencyContactId==null){
			info.setSuccess(false);
			info.setMessage("删除失败");
			return info;
		}
		agencyContactMapper.deleteByPrimaryKey(agencyContactId);
		info.setSuccess(true);
		info.setMessage("删除成功");
		return info;
	}

	@Override
	public List<User> queryAgencyContact(AgencyContact agencyContact) {
		Integer agencyId = agencyContact.getAgencyId();
		Integer custId = agencyContact.getCustId();
		if(agencyId==null){
			return null;
		}
		if(custId==null){
			return null;
		}
		List<User> users = agencyContactMapper.selectUsers(agencyContact);
		return users;
	}

	@Override
	public ReturnInfo queryAgencyContactById(Integer agencyId, Integer custId) {
		ReturnInfo info = new ReturnInfo();
		AgencyContact agencyContact = new AgencyContact();
		agencyContact.setAgencyId(agencyId);
		agencyContact.setCustId(custId);
		if(agencyId==null){
			info.setSuccess(false);
			info.setMessage("代理机构ID不能为空");
			return info;
		}
		if(custId==null){
			info.setSuccess(false);
			info.setMessage("客户ID不能为空");
			return info;
		}
		List<Map<String, Object>> users = agencyContactMapper.selectAgencyContact(agencyContact);
		info.setSuccess(true);
		info.setData(users);
		info.setMessage("查询成功");
		return info;
	}

	@Override
	public ReturnInfo modifyAgencyContact(Integer agencyContactId,Integer userId) {
		ReturnInfo info = new ReturnInfo();
		if(agencyContactId==null){
			info.setSuccess(false);
			info.setMessage("代理机构联系人主键不能为空");
			return info;
		}
		if(userId==null){
			info.setSuccess(false);
			info.setMessage("请选择联系人");
			return info;
		}
		AgencyContact agencyContact = new AgencyContact();
		agencyContact.setId(agencyContactId);//只能修改该客户的联系人，不用修改custId和agencyId
		agencyContact.setUserId(userId);
		agencyContactMapper.updateByPrimaryKeySelective(agencyContact);
		info.setSuccess(true);
		info.setMessage("修改成功");
		return info;
	}

	@Override
	public ReturnInfo queryAgencyContactByAgencyId(AgencyContact agencyContact,GeneralCondition gcon,String name,String fullname) {
		ReturnInfo info = new ReturnInfo();
		Integer agencyId = agencyContact.getAgencyId();
		if(agencyId==null){
			info.setSuccess(false);
			info.setMessage("代理机构ID不能为空");
			return info;
		}
		List<Map<String, Object>> data = agencyContactMapper.selectByAgencyId(agencyContact, gcon, name, fullname);
		Long count = agencyContactMapper.selectByAgencyIdCount(agencyContact, gcon, name, fullname);
		info.setSuccess(true);
		info.setData(data);
		info.setTotal(count);
		info.setCurrPage(gcon.getPageNo());
		info.setMessage("查询成功");
		return info;
	}
	
}
