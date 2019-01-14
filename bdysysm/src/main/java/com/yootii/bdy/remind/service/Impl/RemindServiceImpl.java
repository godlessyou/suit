package com.yootii.bdy.remind.service.Impl;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yootii.bdy.agency.model.Agency;
import com.yootii.bdy.agency.service.AgencyService;
import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.common.Globals;
import com.yootii.bdy.common.ReturnInfo;
import com.yootii.bdy.customer.model.Customer;
import com.yootii.bdy.customer.service.CustomerService;
import com.yootii.bdy.invite.model.AgencyInvite;
import com.yootii.bdy.remind.dao.RemindMapper;
import com.yootii.bdy.remind.model.Remind;
import com.yootii.bdy.remind.service.RemindService;
import com.yootii.bdy.security.model.Token;
import com.yootii.bdy.taskremind.dao.TaskRemindMapper;
import com.yootii.bdy.taskremind.model.TaskRemind;
import com.yootii.bdy.taskremind.service.TaskRemindService;
import com.yootii.bdy.user.model.User;
import com.yootii.bdy.user.service.UserService;


@Service
public class RemindServiceImpl implements RemindService {

	@Resource
	private RemindMapper remindMapper;
	
	@Resource
	private CustomerService customerService;
	
	@Resource
	private UserService userService;
	
	@Autowired
	private AgencyService agencyService;
	@Override
	public Boolean deleteRemind(Integer remindId) {
		Remind remind = new Remind();
		remind.setRid(remindId);
		remind.setIsclose(1);
		remindMapper.updateByPrimaryKeySelective(remind);
		return true;
	}

	@Override
	public Boolean insertRemind(Remind remind) {
		remind.setIsclose(0);
		remindMapper.insertSelective(remind);
		return true;
	}

	@Override
	public List<Remind> selectRemindLists(Remind remind, GeneralCondition gcon) {
		return remindMapper.selectByRemind(remind, gcon);
	}

	@Override
	public Long selectRemindListCount(Remind remind, GeneralCondition gcon) {
		
		return remindMapper.selectByRemindCount(remind, gcon);
	}
	
	@Override
	public ReturnInfo queryRemindList(Remind remind, GeneralCondition gcon) {
		ReturnInfo info = new ReturnInfo();
		List<Remind> lists = remindMapper.selectByRemind(remind, gcon);
		info.setData(lists);
		long count = remindMapper.selectByRemindCount(remind, gcon);
		info.setTotal(count);
		return info;
	}
	
	@Override
	public Boolean insertRemindByType(Integer type,Date date,Integer agencyId,Integer custId,Integer caseId) {
		Remind remind = new Remind();
		String title = "";
		String message = "";
		remind.setIsclose(0);
		remind.setCreatedate(new Date());
		Token token = Globals.getToken();
		
		if(token.isUser()) {
			User user = userService.getUserById(token.getUserID());
			remind.setCreateuser(user.getUsername());
			remind.setUserid(user.getUserId());
			Agency agency = agencyService.selectAgencyByUserId(user.getUserId());
			remind.setAgencyid(agency.getId());
			remind.setCustid(custId);
			remind.setCaseId(caseId);
		} else {
			Customer customer = customerService.getCustById(token.getCustomerID());
			remind.setCreateuser(customer.getUsername());
			remind.setCustid(customer.getId());
			remind.setAgencyid(agencyId);
			remind.setCaseId(caseId);
		}
		/*
（1）续展时限：对于已经立案，但尚未递交申请的续展案件，创建案件时限，绝限日期=【注册日期】+10年-1天。
		 （2）异议时限：对于已经立案，但尚未递交申请的异议类型案件，创建案件时限，绝限日期=【公告日期】+3个月-1天
（3）异议答辩时限：对于收到了异议答辩通知的案件，创建案件时限，绝限日期=【收文日期】+29天。
		 （4）异议复审时限：对于收到了异议复审通知的案件，创建案件时限，绝限日期=【收文日期】+29天。
（5）补正时限：对于收到了补正通知的案件，创建案件时限，绝限日期=【收文日期】+29天。
		 （6）部分驳回复审时限：对于收到了部分驳回通知的案件，创建案件时限，绝限日期=【收文日期】+14天。
（7）驳回复审时限：对于收到了部分驳回通知的案件，创建案件时限，绝限日期=【收文日期】+14天。
		 （8）分割申请提交时限：对于接收到部分驳回通知，并且拒绝驳回复审的案件，创建案件时限，绝限日期=【收文日期】+14天。
		 （9）不予注册复审时限：对于收到了不予注册通知的案件，创建案件时限，绝限日期=【收文日期】+14天。
		 （10）参加不予注册复审时限：对于收到了参加不予注册复审通知的案件，创建案件时限，绝限日期=【收文日期】+29天。
（11）优先权时限：对于要求优先权的商标注册案件，创建案件时限，绝限日期=【优先权日期】+6个月-1天。
		 以下没做呢
		 （12）证据交换时限：如果案件收到证据交换通知，那么创建案件时限，绝限日期=【收文日期】+29天。
		（13）补充材料时限：如果案件收到证据交换通知，那么创建案件时限，绝限日期=【递交日期】+3个月-1天。
		 （14）无效宣告答辩时限：对于收到了无效宣告答辩通知的案件，创建案件时限，绝限日期=【收文日期】+29天。
		 （15）撤销通用名称答辩时限：对于收到了撤销通用名称答辩通知的案件，创建案件时限，绝限日期=【收文日期】+29天。
		 （16）撤销复审答辩时限：对于收到了撤销复审答辩通知的案件，创建案件时限，绝限日期=【收文日期】+29天。
		 （17）撤销复审时限：查看案件是否是撤销类型的案件，并且受到了裁定书，并且裁定结果是术，绝限日期=【收文日期】+14天。
		 (18) 质证通知时限 ： 收到质证通知后 建立质证通知时限  绝限日期=收文日期+30天
		 */
		GregorianCalendar gc=new GregorianCalendar(); 
		gc.setTime(date); 
		switch(type) {
		case 1:
			gc.add(1, 10);
			gc.add(5, -1);
			title = "立案时限";
			message = "续展时限";
			break;
		case 2:
			gc.add(2, 3);
			gc.add(5, -1);
			title = "立案时限";
			message = "异议时限";
			break;
		case 3:
			gc.add(5, 29);
			title = "官文时限";
			message = "异议答辩时限";
			break;
		case 4:
			gc.add(5, 29);
			title = "官文时限";
			message = "异议复审时限";
			break;
		case 5:
			gc.add(5, 29);
			title = "官文时限";
			message = "补正时限";
			break;
		case 6:
			gc.add(5, 14);
			title = "官文时限";
			message = "部分驳回复审时限";
			break;
		case 7:
			gc.add(5, 14);
			title = "官文时限";
			message = "驳回复审时限";
			break;
		case 8:
			gc.add(5, 14);
			title = "官文时限";
			message = "分割申请提交时限";
			break;
		case 9:
			gc.add(5, 14);
			title = "官文时限";
			message = "不予注册复审时限";
			break;
		case 10:
			gc.add(5, 29);
			title = "官文时限";
			message = "参加不予注册复审时限";
			break;
		case 11:
			gc.add(2, 6);
			gc.add(5, -1);
			title = "立案时限";
			message = "优先权时限";
			break;
		case 12:
			gc.add(5, 30);
			title="官文时限";
			message = "质证时限";
			break;
		case 13:
			gc.add(2, 3);
			gc.add(5, -1);
			title = "官文时限";
			message = "补充材料时限";
			break;
		}
		remind.setLimitdate(gc.getTime());
		remind.setTitle(title);
		remind.setMessage(message);
		remindMapper.insertSelective(remind);
		return true;
	}
}