package com.yootii.bdy.mailremind.serviceImpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yootii.bdy.common.ReturnInfo;
import com.yootii.bdy.mailremind.dao.MailRemindTypeMapper;
import com.yootii.bdy.mailremind.model.MailRemindType;
import com.yootii.bdy.mailremind.service.MailRemindTypeService;
@Service
public class MailRemindTypeServiceImpl implements MailRemindTypeService{
	
	@Resource
	private MailRemindTypeMapper mailRemindTypeMapper;
	
	@Override
	public ReturnInfo queryMailRemindType() {
		ReturnInfo rtnInfo = new ReturnInfo();
		List<MailRemindType> types = mailRemindTypeMapper.selectAllType();
		rtnInfo.setSuccess(true);
		rtnInfo.setMessage("查询成功");
		rtnInfo.setData(types);
		return rtnInfo;
	}
}