package com.yootii.bdy.mailremind.serviceImpl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yootii.bdy.common.ReturnInfo;
import com.yootii.bdy.customer.model.Customer;
import com.yootii.bdy.customer.service.CustomerService;
import com.yootii.bdy.mailremind.dao.MailRemindSettingsMapper;
import com.yootii.bdy.mailremind.dao.MailRemindTypeMapper;
import com.yootii.bdy.mailremind.model.MailRemindSettings;
import com.yootii.bdy.mailremind.model.MailRemindType;
import com.yootii.bdy.mailremind.service.MailRemindSettingsService;

@Service
public class MailRemindSettingsServiceImpl implements MailRemindSettingsService{
	
	@Resource
	private MailRemindSettingsMapper mailRemindSettingsMapper;
	@Resource
	private CustomerService customerService;
	@Resource
	private MailRemindTypeMapper mailRemindTypeMapper;
	@Override
	public ReturnInfo createMailRemindSettings(MailRemindSettings settings) {
		ReturnInfo rtnInfo = new ReturnInfo();
		Integer custId = settings.getCustId();
		Integer remindType = settings.getRemindType();
		if(custId==null){
			rtnInfo.setSuccess(false);
			rtnInfo.setMessage("客户Id不能为空");
			return rtnInfo;
		}
		if(remindType==null){
			rtnInfo.setSuccess(false);
			rtnInfo.setMessage("提醒类型不能为空");
			return rtnInfo;
		}
		MailRemindSettings settings2 = mailRemindSettingsMapper.selectByCustIdAndRemindType(custId,remindType);
		if(settings2==null){
			mailRemindSettingsMapper.insertSelective(settings);
		}
		rtnInfo.setSuccess(true);
		rtnInfo.setMessage("设置成功");
		return rtnInfo;
	}

	@Override
	public ReturnInfo deleteMailRemindSettings(Integer setId) {
		ReturnInfo rtnInfo = new ReturnInfo();
		if(setId==null){
			rtnInfo.setSuccess(false);
			rtnInfo.setMessage("设置Id不能为空");
			return rtnInfo;
		}
		mailRemindSettingsMapper.deleteByPrimaryKey(setId);
		rtnInfo.setSuccess(true);
		rtnInfo.setMessage("删除成功");
		return rtnInfo;
	}

	@Override
	public ReturnInfo createExistCustDefaultSettings(Integer custId) {
		List<MailRemindType> types = mailRemindTypeMapper.selectAllType();
		if(custId==null){//如果不传custId则针对所有的客户，增加默认的提醒配置数据
			List<Customer> customers = customerService.queryAllCustomer();
			if(customers!=null&&customers.size()>0&&types!=null&&types.size()>0){
				for(Customer customer : customers){
					for(MailRemindType type :types){
						Integer customerId = customer.getId();
						MailRemindSettings settings = new MailRemindSettings();
						settings.setCustId(customerId);
						settings.setRemindType(type.getRemindType());
						createMailRemindSettings(settings);
					}
				}
			}
		}else{
			if(types!=null&&types.size()>0){
				for(MailRemindType type :types){
					MailRemindSettings settings = new MailRemindSettings();
					settings.setCustId(custId);
					settings.setRemindType(type.getRemindType());
					createMailRemindSettings(settings);
				}
			}
		}
		ReturnInfo rtnInfo = new ReturnInfo();
		rtnInfo.setSuccess(true);
		rtnInfo.setMessage("创建成功");
		return rtnInfo;
	}

	@Override
	public ReturnInfo queryMailRemindSettings(Integer custId) {
		ReturnInfo rtnInfo = new ReturnInfo(); 
		if(custId==null){
			rtnInfo.setSuccess(false);
			rtnInfo.setMessage("客户ID不能为空");
			return rtnInfo;
		}
		List<Map<String, Object>> settings = mailRemindSettingsMapper.selectByCustId(custId);
		rtnInfo.setSuccess(true);
		rtnInfo.setMessage("查询成功");
		rtnInfo.setData(settings);
		return rtnInfo;
	}
}
