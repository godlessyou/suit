package com.yootii.bdy.bill.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yootii.bdy.bill.dao.ChargeItemMapper;
import com.yootii.bdy.bill.model.ChargeItem;
import com.yootii.bdy.bill.service.ChargeItemService;
import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.common.Globals;
import com.yootii.bdy.common.ReturnInfo;

@Service("ChargeItemService")
public class ChargeItemServiceImpl implements ChargeItemService{
	
	@Resource
	private ChargeItemMapper chargeItemMapper;
	
	@Override
	public ReturnInfo createChargeItem(GeneralCondition gcon,
			ChargeItem chargeItem) {
			ReturnInfo rtnInfo = new ReturnInfo();
			if(chargeItem.getChargeType()!=null && chargeItem.getChargeType() !="") {
				
			
			Integer i= chargeItemMapper.checkName(chargeItem);
			
			if(i<1) {
				chargeItemMapper.insertSelective(chargeItem);
				rtnInfo.setData(chargeItem);
				rtnInfo.setSuccess(true);
				rtnInfo.setMessageType(4);
				rtnInfo.setMessage("创建价目记录成功");
				return rtnInfo;
			}else {
				rtnInfo.setSuccess(false);
				rtnInfo.setMessageType(-2);
				rtnInfo.setMessage("价目记录不能重名");
				return rtnInfo;
			}
		}else {
			rtnInfo.setSuccess(false);
			rtnInfo.setMessageType(-2);
			rtnInfo.setMessage("收费种类不能为空");
			return rtnInfo;
		}
	}

	@Override
	public ReturnInfo deleteChargeItem(GeneralCondition gcon,
			ChargeItem chargeItem) {
		ReturnInfo rtnInfo = new ReturnInfo();
		chargeItemMapper.deleteByPrimaryKey(chargeItem.getChargeItemId());
		rtnInfo.setSuccess(true);
		rtnInfo.setMessageType(4);
		rtnInfo.setMessage("删除价目记录成功");
		return rtnInfo;
	}

	@Override
	public ReturnInfo modifyChargeItem(GeneralCondition gcon,
			ChargeItem chargeItem) {
		ReturnInfo rtnInfo = new ReturnInfo();
		Integer i= chargeItemMapper.checkName(chargeItem);
		if(i<1) {
			chargeItemMapper.updateByPrimaryKeySelective(chargeItem);
			rtnInfo.setData(chargeItem);
			rtnInfo.setSuccess(true);
			rtnInfo.setMessageType(4);
			rtnInfo.setMessage("修改价目记录成功");
			return rtnInfo;
		}else {
			rtnInfo.setSuccess(false);
			rtnInfo.setMessageType(-2);
			rtnInfo.setMessage("价目记录不能重名");
			return rtnInfo;
		}
		
	}

	@Override
	public ReturnInfo queryChargeItemList(GeneralCondition gcon,
			ChargeItem chargeItem) {
		ReturnInfo rtnInfo = new ReturnInfo();
		if (gcon.getPageSize() <= 0) {
			gcon.setPageSize(10);
		}
		if (gcon.getPageNo() <= 0) {
			gcon.setPageNo(1);
		}
		List<ChargeItem> chargeItems = new ArrayList<ChargeItem>();
		chargeItems=chargeItemMapper.selectByChargeItem(chargeItem,gcon);
		Long total = chargeItemMapper.selectCountByChargeItem(chargeItem,gcon);
		rtnInfo.setData(chargeItems);
		rtnInfo.setTotal(total);
		rtnInfo.setCurrPage(gcon.getPageNo());
		rtnInfo.setSuccess(true);
		rtnInfo.setMessageType(4);
		rtnInfo.setMessage("查询价目记录列表成功");
		return rtnInfo;
	}

	@Override
	public ReturnInfo queryChargeItemDetail(GeneralCondition gcon,
			ChargeItem chargeItem) {
		ReturnInfo rtnInfo = new ReturnInfo();
		ChargeItem chargeItem2 = chargeItemMapper.selectByPrimaryKey(chargeItem.getChargeItemId());
		rtnInfo.setData(chargeItem2);
		rtnInfo.setSuccess(true);
		rtnInfo.setMessageType(4);
		rtnInfo.setMessage("查询价目记录成功");
		return rtnInfo;
	}

	
	@Override
	public ReturnInfo queryChargeItemListById(String agencyServiceId) {
		ReturnInfo rtnInfo = new ReturnInfo();
		
		List<ChargeItem> chargeItems = new ArrayList<ChargeItem>();
		
		if (agencyServiceId==null || agencyServiceId.equals("")){				
			rtnInfo.setSuccess(false);
			rtnInfo.setMessageType(Globals.MESSAGE_TYPE_PARAM_INVALID);
			rtnInfo.setMessage("agencyServiceId is null");
			return rtnInfo;
		}
		
		Integer id=new Integer(agencyServiceId);
		chargeItems=chargeItemMapper.selectChargeItemListById(id);
		
		rtnInfo.setData(chargeItems);		
		rtnInfo.setSuccess(true);
		
		return rtnInfo;
	}

}
