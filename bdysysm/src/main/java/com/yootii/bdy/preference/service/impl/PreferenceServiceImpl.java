package com.yootii.bdy.preference.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.common.ReturnInfo;
import com.yootii.bdy.permission.dao.PermissionMapper;
import com.yootii.bdy.permission.model.Permission;
import com.yootii.bdy.preference.dao.PreferenceFieldMapper;
import com.yootii.bdy.preference.dao.PreferenceValueMapper;
import com.yootii.bdy.preference.model.PreferenceField;
import com.yootii.bdy.preference.model.PreferenceValue;
import com.yootii.bdy.preference.service.PreferenceService;
@Service
public class PreferenceServiceImpl implements PreferenceService{
	@Resource
	private PreferenceFieldMapper preferenceFieldMapper;
	@Resource
	private PreferenceValueMapper preferenceValueMapper;
	@Override
	public ReturnInfo queryPreferenceField(PreferenceField preferenceField,GeneralCondition gcon) {
		ReturnInfo rtnInfo = new ReturnInfo();
		
		List<PreferenceField> preferenceFields = new ArrayList<PreferenceField>();
		preferenceFields=preferenceFieldMapper.selectByPreferenceField(preferenceField,gcon);
		Long total = preferenceFieldMapper.selectCountByPreferenceField(preferenceField);
		rtnInfo.setData(preferenceFields);
		rtnInfo.setTotal(total);
		rtnInfo.setSuccess(true);
		rtnInfo.setMessageType(4);
		rtnInfo.setMessage("查询配置项成功");
		return rtnInfo;
	}

	@Override
	public ReturnInfo createPreferenceField(PreferenceField preferenceField) {
		ReturnInfo rtnInfo = new ReturnInfo();
		
		preferenceFieldMapper.insertSelective(preferenceField);
		
		rtnInfo.setSuccess(true);
		rtnInfo.setMessageType(4);
		rtnInfo.setMessage("增加配置项成功");
		return rtnInfo;
	}

	@Override
	public ReturnInfo deletePreferenceField(PreferenceField preferenceField) {
		ReturnInfo rtnInfo = new ReturnInfo();
		
		preferenceFieldMapper.deleteByPrimaryKey(preferenceField.getPreferenceId());
		
		rtnInfo.setSuccess(true);
		rtnInfo.setMessageType(4);
		rtnInfo.setMessage("删除配置项成功");
		return rtnInfo;
	}

	@Override
	public ReturnInfo modifyPreferenceField(PreferenceField preferenceField) {
		ReturnInfo rtnInfo = new ReturnInfo();
		
		preferenceFieldMapper.updateByPrimaryKeySelective(preferenceField);
		
		rtnInfo.setSuccess(true);
		rtnInfo.setMessageType(4);
		rtnInfo.setMessage("修改配置项成功");
		return rtnInfo;
	}

	@Override
	public ReturnInfo queryPreferenceValue(PreferenceValue preferenceValue,GeneralCondition gcon) {
		ReturnInfo rtnInfo = new ReturnInfo();
		
		List<PreferenceValue> preferenceValues = new ArrayList<PreferenceValue>();
		preferenceValues=preferenceValueMapper.selectByPreferenceValue(preferenceValue,gcon);
		for(PreferenceValue p : preferenceValues ) {
			if(p.getStringValue() == null && p.getIntValue() == null) {
				PreferenceField preferenceField = preferenceFieldMapper.selectByPrimaryKey(p.getPreferenceId());
				String dataType = preferenceField.getDataType();
				if("int".equals(dataType)) {
					p.setIntValue(Integer.parseInt(preferenceField.getDefaultIntValue()));
				}
				if("string".equals(dataType)) {
					p.setStringValue(preferenceField.getDefaultStringValue());
				}
			}
		}
		Long total = preferenceValueMapper.selectCountByPreferenceValue(preferenceValue);
		rtnInfo.setData(preferenceValues);
		rtnInfo.setTotal(total);
		rtnInfo.setSuccess(true);
		rtnInfo.setMessageType(4);
		rtnInfo.setMessage("查询配置项参数成功");
		return rtnInfo;
	}

	@Override
	public ReturnInfo createPreferenceValue(PreferenceValue preferenceValue) {
		ReturnInfo rtnInfo = new ReturnInfo();
		
		preferenceValueMapper.insertSelective(preferenceValue);
		
		rtnInfo.setSuccess(true);
		rtnInfo.setMessageType(4);
		rtnInfo.setMessage("创建配置项参数成功");
		return rtnInfo;
	}

	@Override
	public ReturnInfo deletePreferenceValue(PreferenceValue preferenceValue) {
		ReturnInfo rtnInfo = new ReturnInfo();
		
		preferenceValueMapper.deleteByPrimaryKey(preferenceValue.getId());
		
		rtnInfo.setSuccess(true);
		rtnInfo.setMessageType(4);
		rtnInfo.setMessage("删除配置项参数成功");
		return rtnInfo;
	}

	@Override
	public ReturnInfo modifyPreferenceValue(List<PreferenceValue> preferenceValue) {
		ReturnInfo rtnInfo = new ReturnInfo();
		if(preferenceValue.size()>0) {
			for(PreferenceValue p : preferenceValue) {
				preferenceValueMapper.updateByPrimaryKeySelective(p);
			}
		}
		
		
		rtnInfo.setSuccess(true);
		rtnInfo.setMessageType(4);
		rtnInfo.setMessage("修改配置项参数成功");
		return rtnInfo;
	}

}
