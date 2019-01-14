package com.yootii.bdy.bill.service.impl;



import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yootii.bdy.bill.dao.DiscountMapper;
import com.yootii.bdy.bill.model.Discount;
import com.yootii.bdy.bill.model.ExchangeRate;
import com.yootii.bdy.bill.service.DiscountService;
import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.common.ReturnInfo;


@Service("DiscountService")
public class DiscountServiceImpl implements DiscountService{
	
	@Resource
	private DiscountMapper discountMapper;

	@Override
	public ReturnInfo createDiscount(Discount discount, GeneralCondition gcon) {
		ReturnInfo rtnInfo = new ReturnInfo();
		discountMapper.insertSelective(discount);
		//rtnInfo.setData(discount2);
		rtnInfo.setSuccess(true);
		rtnInfo.setMessageType(4);
		rtnInfo.setMessage("创建折扣成功");
		return rtnInfo;
	}

	@Override
	public ReturnInfo deleteDiscount(Discount discount, GeneralCondition gcon) {
		ReturnInfo rtnInfo = new ReturnInfo();
		discountMapper.deleteByPrimaryKey(discount.getDiscountId());
		//rtnInfo.setData(discount2);
		rtnInfo.setSuccess(true);
		rtnInfo.setMessageType(4);
		rtnInfo.setMessage("删除折扣成功");
		return rtnInfo;
	}

	@Override
	public ReturnInfo modifyDiscount(Discount discount, GeneralCondition gcon) {
		ReturnInfo rtnInfo = new ReturnInfo();
		discountMapper.updateByPrimaryKeySelective(discount);
		//rtnInfo.setData(discount2);
		rtnInfo.setSuccess(true);
		rtnInfo.setMessageType(4);
		rtnInfo.setMessage("修改折扣成功");
		return rtnInfo;
	}

	@Override
	public ReturnInfo queryDiscountList(Discount discount, GeneralCondition gcon, Integer userId,boolean isAgency) {
		ReturnInfo rtnInfo = new ReturnInfo();
		if (gcon.getPageSize() <= 0) {
			gcon.setPageSize(10);
		}
		if (gcon.getPageNo() <= 0) {
			gcon.setPageNo(1);
		}
		List<Discount> discounts = new ArrayList<Discount>();
		Long total=0L;
		if(isAgency) {
			if(discount.getCustId() != null && discount.getCoagencyId() == null) {
				discounts=discountMapper.selectByDiscountCoAgency(discount,gcon);
				Long total1 = discountMapper.selectCountByDiscountCoAgency(discount,gcon);
				total = total1;
			}
		}else {
			if(discount.getCustId() != null && discount.getCoagencyId() == null) {
				discounts=discountMapper.selectByDiscount(discount,gcon,userId);
				 total = discountMapper.selectCountByDiscount(discount,gcon,userId);
			}
			if(discount.getCoagencyId() != null && discount.getCustId() == null) {
				discounts=discountMapper.selectByDiscountCoAgency(discount,gcon);
				Long total1 = discountMapper.selectCountByDiscountCoAgency(discount,gcon);
				total = total1;
			}
		}
		
		
		rtnInfo.setData(discounts);
		rtnInfo.setTotal(total);
		rtnInfo.setCurrPage(gcon.getPageNo());
		rtnInfo.setSuccess(true);
		rtnInfo.setMessageType(4);
		rtnInfo.setMessage("查询折扣成功");
		return rtnInfo;
	}

	@Override
	public ReturnInfo queryDiscountDetail(Discount discount, GeneralCondition gcon) {
		ReturnInfo rtnInfo = new ReturnInfo();
		Discount discount2 = discountMapper.selectByPrimaryKey(discount.getDiscountId());
		rtnInfo.setData(discount2);
		rtnInfo.setSuccess(true);
		rtnInfo.setMessageType(4);
		rtnInfo.setMessage("查询折扣成功");
		return rtnInfo;
	}

	@Override
	public ReturnInfo createDiscountByValue(Discount discount, GeneralCondition gcon) {
		ReturnInfo rtnInfo = new ReturnInfo();
		if(discount.getValue() == null) {
			rtnInfo.setSuccess(false);
			rtnInfo.setMessage("创建折扣失败,折扣不能为空");
			return rtnInfo;
		}
		if(discount.getAgencyId() == null) {
			rtnInfo.setSuccess(false);
			rtnInfo.setMessage("创建折扣失败,agencyId不能为空");
			return rtnInfo;
		}
		if(discount.getCustId()== null) {
			rtnInfo.setSuccess(false);
			rtnInfo.setMessage("创建折扣失败,custId不能为空");
			return rtnInfo;
		}
		
		try {
			Date d =new Date();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
			Calendar c = Calendar.getInstance();
			c.setTime(new Date());
			c.add(Calendar.YEAR, +1);
			Date end = c.getTime();
			String startDate = df.format(d);
			String endDate = df.format(end);
			discount.setStartDate(startDate);
			discount.setEndDate(endDate);
			discount.setStatus(1);
			discount.setCaseType("商标注册");
			discountMapper.insertSelective(discount);
			
			discount.setCaseType("商标续展");
			discount.setDiscountId(null);
			discountMapper.insertSelective(discount);
			
			discount.setCaseType("商标转让");
			discount.setDiscountId(null);
			discountMapper.insertSelective(discount);
			
			
			discount.setCaseType("变更代理人/文件接收人");
			discount.setDiscountId(null);
			discountMapper.insertSelective(discount);
			
			
			discount.setCaseType("变更名义地址集体管理规则成员名单");
			discount.setDiscountId(null);
			discountMapper.insertSelective(discount);
			
			
			discount.setCaseType("商标更正");
			discount.setDiscountId(null);
			discountMapper.insertSelective(discount);
			
		} catch (Exception e) {
			e.printStackTrace();
			rtnInfo.setSuccess(false);
			rtnInfo.setMessage("创建折扣失败" + e.getMessage());
			return rtnInfo;
		}
		rtnInfo.setSuccess(true);
		rtnInfo.setMessage("创建折扣成功");
		return rtnInfo;
	}
}
