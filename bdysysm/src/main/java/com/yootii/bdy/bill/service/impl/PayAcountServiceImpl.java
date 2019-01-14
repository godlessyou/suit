package com.yootii.bdy.bill.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yootii.bdy.bill.dao.PayAcountMapper;
import com.yootii.bdy.bill.model.PayAcount;
import com.yootii.bdy.bill.service.PayAcountService;
import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.common.ReturnInfo;
@Service("PayAcountService")
public class PayAcountServiceImpl implements PayAcountService{
	
	@Resource
	private PayAcountMapper payAcountMapper;

	@Override
	public ReturnInfo queryPayAcountList(PayAcount payAcount, GeneralCondition gcon) {
		ReturnInfo rtnInfo = new ReturnInfo();
		if (gcon.getPageSize() <= 0) {
			gcon.setPageSize(10);
		}
		if (gcon.getPageNo() <= 0) {
			gcon.setPageNo(1);
		}
		List<PayAcount> payAcounts = new ArrayList<PayAcount>();
		payAcounts=payAcountMapper.selectByPayAcount(payAcount,gcon);
		Long total = payAcountMapper.selectCountByPayAcount(payAcount,gcon);
		rtnInfo.setData(payAcounts);
		rtnInfo.setTotal(total);
		rtnInfo.setCurrPage(gcon.getPageNo());
		rtnInfo.setSuccess(true);
		rtnInfo.setMessageType(4);
		rtnInfo.setMessage("查询收款账户列表成功");
		return rtnInfo;
	}

	@Override
	public ReturnInfo queryPayAcountDetail(PayAcount payAcount, GeneralCondition gcon) {
		ReturnInfo rtnInfo = new ReturnInfo();
		PayAcount payAcount2 = payAcountMapper.selectByPrimaryKey(payAcount.getPayAcountId());
		rtnInfo.setData(payAcount2);
		rtnInfo.setSuccess(true);
		rtnInfo.setMessageType(4);
		rtnInfo.setMessage("查询收款账户成功");
		return rtnInfo;
	}

	@Override
	public ReturnInfo createPayAcount(PayAcount payAcount, GeneralCondition gcon) {
		ReturnInfo rtnInfo = new ReturnInfo();
		int i = 0;
		if(payAcount.getBankAcount()!= null && payAcount.getBankAcount()!= "") {
			i= payAcountMapper.checkPayAcountByBankAcount(payAcount);
			if(i>0) {
				
				rtnInfo.setSuccess(false);
				rtnInfo.setMessageType(4);
				rtnInfo.setMessage("该银行账号已被创建为收款账户");
				return rtnInfo;
							
			}
		}else {
			rtnInfo.setSuccess(false);
			rtnInfo.setMessageType(4);
			rtnInfo.setMessage("银行账号不能为空");
			return rtnInfo;
		}
		
		if(payAcount.getPayAcountName()!= null && payAcount.getPayAcountName()!= "") {
			i= payAcountMapper.checkPayAcountByPayAcountName(payAcount);
			
			if(i>0) {
				
				rtnInfo.setSuccess(false);
				rtnInfo.setMessageType(4);
				rtnInfo.setMessage("账户名不能重复");
				return rtnInfo;
							
			}
		}	
		
		
		payAcountMapper.insertSelective(payAcount);
		rtnInfo.setSuccess(true);
		rtnInfo.setMessageType(4);
		rtnInfo.setMessage("创建收款账户成功");
		return rtnInfo;
		
		
	}

	@Override
	public ReturnInfo modifyPayAcount(PayAcount payAcount, GeneralCondition gcon) {
		ReturnInfo rtnInfo = new ReturnInfo();
		int i = 0;
		if(payAcount.getBankAcount()!= null && payAcount.getBankAcount()!= "") {
			i= payAcountMapper.checkPayAcountByBankAcount(payAcount);
			if(i>0) {
				
				rtnInfo.setSuccess(false);
				rtnInfo.setMessageType(4);
				rtnInfo.setMessage("该银行账号已被创建为收款账户");
				return rtnInfo;
							
			}
		}
		
		if(payAcount.getPayAcountName()!= null && payAcount.getPayAcountName()!= "") {
			i= payAcountMapper.checkPayAcountByPayAcountName(payAcount);
			
			if(i>0) {
				
				rtnInfo.setSuccess(false);
				rtnInfo.setMessageType(4);
				rtnInfo.setMessage("账户名不能重复");
				return rtnInfo;
							
			}
		}	
		payAcountMapper.updateByPrimaryKeySelective(payAcount);
		rtnInfo.setSuccess(true);
		rtnInfo.setMessageType(4);
		rtnInfo.setMessage("修改收款账户成功");
		return rtnInfo;
	}

	@Override
	public ReturnInfo deletePayAcount(PayAcount payAcount, GeneralCondition gcon) {
		ReturnInfo rtnInfo = new ReturnInfo();
		payAcountMapper.deleteByPrimaryKey(payAcount.getPayAcountId());
		rtnInfo.setSuccess(true);
		rtnInfo.setMessageType(4);
		rtnInfo.setMessage("删除收款账户成功");
		return rtnInfo;
	}

}
