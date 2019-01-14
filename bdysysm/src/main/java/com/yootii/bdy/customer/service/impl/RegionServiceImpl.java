package com.yootii.bdy.customer.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.common.ReturnInfo;
import com.yootii.bdy.customer.dao.RegionMapper;
import com.yootii.bdy.customer.model.Region;
import com.yootii.bdy.customer.service.RegionService;

@Service("RegionService")
public class RegionServiceImpl implements RegionService{
	
	@Resource
	protected RegionMapper regionMapper;

	@Override
	public ReturnInfo queryRegionList(Region region, GeneralCondition gcon) {
		ReturnInfo info = new ReturnInfo();
		List<Region>	customerContacts = regionMapper.selectRegionList(region, gcon);
		Long total =  regionMapper.selectRegionCount(region, gcon);
		info.setData(customerContacts);
		info.setTotal(total);
		info.setCurrPage(gcon.getPageNo());
		info.setSuccess(true);
		info.setMessage("查询地区列表成功");
		return info ;
	}

	
	
}
