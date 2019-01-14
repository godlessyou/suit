package com.yootii.bdy.ipservice.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yootii.bdy.agency.dao.AgencyUserMapper;
import com.yootii.bdy.bill.dao.ChargeItemMapper;
import com.yootii.bdy.bill.model.ChargeItem;

import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.common.ReturnInfo;
import com.yootii.bdy.common.casetype;
import com.yootii.bdy.ipservice.dao.AgencyServiceMapper;
import com.yootii.bdy.ipservice.dao.PlatformServiceMapper;
import com.yootii.bdy.ipservice.model.AgencyService;
import com.yootii.bdy.ipservice.model.PlatformService;
import com.yootii.bdy.ipservice.service.AgencyServiceService;
import com.yootii.bdy.security.model.Token;


@Service
public class AgencyServiceServiceImpl implements AgencyServiceService{
	
	@Resource
	private AgencyServiceMapper agencyServiceMapper;
	
	@Resource
	public AgencyUserMapper agencyUserMapper;
	
	@Resource
	private ChargeItemMapper chargeItemMapper;
	
	@Resource
	private PlatformServiceMapper platformServiceMapper;
	

	
	@Override
	public ReturnInfo createAgencyService(AgencyService agencyService,Integer userId,Integer caseTypeId,Integer serviceType) {
		//根据caseTypeId和serviceType获取serviceId后,创建agencyService
		ReturnInfo info = new ReturnInfo();
		if(caseTypeId==null||serviceType==null){
			info.setSuccess(false);
			info.setMessage("平台业务类型不能为空");
			return info;
		}
		PlatformService ps = platformServiceMapper.queryPlatformServiceByCaseTypeIdAndServiceType(caseTypeId, serviceType);
		if(ps==null){
			info.setSuccess(false);
			info.setMessage("标得宜平台暂未提供该业务");
			return info;
		}
		Integer serviceId = ps.getServiceId();
		agencyService.setServiceId(serviceId);
		Integer agencyId = 0;
		if(userId==null) {
			agencyId=agencyService.getAgencyId();
		}else {
			agencyId = agencyUserMapper.selectAgencyIdByUserId(userId);
			if(agencyId==null){
				info.setSuccess(false);
				info.setMessage("数据有误");
				return info;
			}
			agencyService.setAgencyId(agencyId);
		}
		
		//查询是否该代理机构的该服务已开通
		Map<String, Object> agencyServiceMap = agencyServiceMapper.selectAgencyService(caseTypeId, agencyId, serviceType);
		if(agencyServiceMap!=null&&agencyServiceMap.get("agencyServiceId")!=null){
			info.setSuccess(false);
			info.setMessage("该服务已经开通过了");
			return info;
		}
		//先插入charge_item表中
		ChargeItem item = new ChargeItem();
		item.setAgencyId(agencyId);
//		PlatformService ps = platformServiceMapper.selectByPrimaryKey(agencyService.getServiceId());
//		if(ps==null){
//			info.setSuccess(false);
//			info.setMessage("平台服务不存在");
//			return info;
//		}
		String serviceName = ps.getServiceName();
		item.setChargeType("服务费");
		item.setChnName(serviceName+"费");
		if(agencyService.getPrice()==null) {
			agencyService.setPrice(BigDecimal.valueOf(9999.99));
		}
		item.setPrice(agencyService.getPrice());
		item.setCurrency(agencyService.getCurrency());
//		PlatformService p = platformServiceMapper.selectByPrimaryKey(agencyService.getServiceId());
		item.setCaseType(ps.getCaseType());
		chargeItemMapper.insertSelective(item);
		agencyService.setChargeItemId(item.getChargeItemId());
		if(ps.getServiceName().equals("高级服务")) {

			ChargeItem itemg = new ChargeItem();

			itemg.setAgencyId(agencyId);
			itemg.setChargeType("官费");			

			//Modification start, 2018-12-29
			//to fix bug BDY2-82
//			itemg.setChnName(casetype.casetypeList.get(caseTypeId)+"官费");
			itemg.setChnName("官费");	
			Double fee=casetype.MoneyList.get(caseTypeId);
			if (fee==null){
				fee=500.00;
			}
			itemg.setPrice(BigDecimal.valueOf(fee));
			
			//Modification end
			
			itemg.setCurrency(agencyService.getCurrency());
			itemg.setCaseType(ps.getCaseType());

			chargeItemMapper.insertSelective(itemg);
		}
		
		
		
		agencyServiceMapper.insertSelective(agencyService);
//		agencyServiceMapper.insertSelectiveWithUserId(agencyService,userId);//暂时未用，不能确定该userId是否一定有agencyId
		info.setSuccess(true);
		info.setMessage("服务开启成功");
		return info;
	}
	
	@Override
	public ReturnInfo createAgencyAllService(Integer agencyId) {
		ReturnInfo info = new ReturnInfo();
		List<AgencyService> agencyServicelist = agencyServiceMapper.queryByAgencyId(1);
		for(AgencyService agencyService:agencyServicelist) {
			Integer caseTypeId = casetype.casetypeIdList.get(agencyService.getPlatformService().getCaseType());
			agencyService.setAgencyServiceId(null);
			agencyService.setAgencyId(agencyId);
			agencyService.setPrice(null);
			createAgencyService(agencyService,null,caseTypeId,agencyService.getPlatformService().getServiceType());
		}
		info.setSuccess(true);
		info.setMessage("服务开启成功");
		return info;
	}

	@Override
	public ReturnInfo deleteAgencyService(AgencyService agencyService,Integer userId) {
		ReturnInfo info = new ReturnInfo();
		Integer agencyServiceId = agencyService.getAgencyServiceId();
		Integer agencyId = agencyUserMapper.selectAgencyIdByUserId(userId);
		if(agencyId==null){
			info.setSuccess(false);
			info.setMessage("数据有误");
			return info;
		}
		if(agencyServiceId==null){
			info.setSuccess(false);
			info.setMessage("代理机构服务ID不能为空");
			return info;
		}
		agencyService.setAgencyId(agencyId);
		agencyServiceMapper.deleteByAgencyService(agencyService);
		info.setSuccess(true);
		info.setMessage("服务已关闭");
		return info;
	}

	@Override
	public ReturnInfo modifyAgencyService(AgencyService agencyService,Integer userId) {
		ReturnInfo info = new ReturnInfo();
		Integer agencyServiceId = agencyService.getAgencyServiceId();
		
		
		if(agencyServiceId==null){
			info.setSuccess(false);
			info.setMessage("代理机构服务ID不能为空");
			return info;
		}
		Integer agencyId = agencyUserMapper.selectAgencyIdByUserId(userId);
		if(agencyId==null){
			info.setSuccess(false);
			info.setMessage("数据有误");
			return info;
		}
		agencyService.setAgencyId(agencyId);
		AgencyService agencyService2 = agencyServiceMapper.selectByPrimaryKey(agencyServiceId);
		if(agencyService2==null){
			info.setSuccess(false);
			info.setMessage("代理机构服务ID有误");
			return info;
		}
		Integer chargeItemId = agencyService2.getChargeItemId();
		if(chargeItemId==null){
			info.setSuccess(false);
			info.setMessage("数据有误");
			return info;
		}
		ChargeItem chargeItem = new ChargeItem();
		chargeItem.setChargeItemId(chargeItemId);
		chargeItem.setPrice(agencyService.getPrice());
		chargeItem.setCurrency(agencyService.getCurrency());
		chargeItemMapper.updateByPrimaryKeySelective(chargeItem);
		agencyService.setChargeItemId(null);//chargeItem不能修改
		agencyService.setServiceId(null);//平台服务Id不能修改
		agencyServiceMapper.updateByPrimaryKeyAndAgencyId(agencyService);
		info.setSuccess(true);
		info.setMessage("修改成功");
		return info;
	}

	@Override
	public ReturnInfo queryAgencyServiceList(AgencyService agencyService,
			GeneralCondition gcon,Token token,String serviceName) {
		ReturnInfo info = new ReturnInfo();
		if(token==null){
			info.setSuccess(false);
			info.setMessage("数据有误");
			return info;
		}
		Integer userId =  token.getUserID();
		Integer custId = token.getCustomerID();
		if(userId==null&&custId==null){
			info.setSuccess(false);
			info.setMessage("数据有误");
			return info;
		}
		List<AgencyService> agencyServices = agencyServiceMapper.selectByAgencyService(agencyService, gcon, userId, custId,serviceName);
		Long count = agencyServiceMapper.selectByAgencyServiceCount(agencyService, gcon, userId, custId,serviceName);
		info.setSuccess(true);
		info.setData(agencyServices);
		
		//Modification start, 2018-12-29
		//fix bug BDY2-81
		info.setCurrPage(gcon.getPageNo());
		//Modification end
		
		info.setTotal(count);
		info.setMessage("查询成功");
		return info;
	}

	@Override
	public ReturnInfo queryAgencyServiceDetail(AgencyService agencyService) {
		ReturnInfo info = new ReturnInfo();
		Integer agencyServiceId = agencyService.getAgencyServiceId();
		if(agencyServiceId==null){
			info.setSuccess(false);
			info.setMessage("代理机构服务ID不能为空");
			return info;
		}
		AgencyService agencyService2 = agencyServiceMapper.selectByPrimaryKey(agencyServiceId);
		info.setSuccess(true);
		info.setData(agencyService2);
		info.setMessage("查询成功");
		return info;
	}
	@Override
	public ReturnInfo custQueryAgencyServiceList(AgencyService agencyService,
			GeneralCondition gcon,Token token) {
		ReturnInfo info = new ReturnInfo();
		if(token==null){
			info.setSuccess(false);
			info.setMessage("数据有误");
			return info;
		}
		Integer custId = token.getCustomerID();
		if(custId==null){
			info.setSuccess(false);
			info.setMessage("无权限");
			return info;
		}
		
		List<Map<String, Object>> agencys = agencyServiceMapper.selectByCustId(agencyService, gcon, custId);
		for(Map<String, Object> map:agencys){
			Integer agencyId = (Integer)map.get("agencyId");
			List<Map<String, Object>> agencyServices = agencyServiceMapper.selectByAgencyId(agencyService, agencyId);
			map.put("data", agencyServices);
		}
		Long count = agencyServiceMapper.selectByCustIdCount(agencyService, gcon, custId);
		info.setSuccess(true);
		info.setData(agencys);
		info.setTotal(count);
		info.setMessage("查询成功");
		return info;
	}
	@Override
	public ReturnInfo custQueryAgencyServiceListByCaseTypeId(Integer caseTypeId,
			GeneralCondition gcon,Token token) {
		ReturnInfo info = new ReturnInfo();
		if(token==null){
			info.setSuccess(false);
			info.setMessage("数据有误");
			return info;
		}
		Integer custId = token.getCustomerID();
		if(custId==null){
			info.setSuccess(false);
			info.setMessage("无权限");
			return info;
		}
		if(caseTypeId==null){
			info.setSuccess(false);
			info.setMessage("案件类型不能为空");
			return info;
		}
		Integer serviceId = platformServiceMapper.selectByCaseTypeIdAndParent(caseTypeId);//根据案件类型查询一级平台服务
		AgencyService agencyService = new AgencyService();
		agencyService.setServiceId(serviceId);
		List<Map<String, Object>> agencys = agencyServiceMapper.selectByCustId(agencyService, gcon, custId);
		for(Map<String, Object> map:agencys){
			Integer agencyId = (Integer)map.get("agencyId");
			List<Map<String, Object>> agencyServices = agencyServiceMapper.selectByAgencyId(agencyService, agencyId);
			map.put("data", agencyServices);
		}
		Long count = agencyServiceMapper.selectByCustIdCount(agencyService, gcon, custId);
		info.setSuccess(true);
		info.setData(agencys);
		info.setTotal(count);
		info.setMessage("查询成功");
		return info;
	}
	@Override
	public ReturnInfo getAgencyService(AgencyService agencyService) {
		ReturnInfo info = new ReturnInfo();
		AgencyService result = agencyServiceMapper.selectAgencyServiceId(agencyService);
		info.setSuccess(true);
		info.setData(result);
		return info;
	}

	@Override
	public ReturnInfo queryAgencyService(Integer caseTypeId, Integer agencyId,
			Integer serviceType) {
		ReturnInfo info = new ReturnInfo();
		if(caseTypeId==null||agencyId==null||serviceType==null){
			info.setSuccess(false);
			info.setMessage("ID不能为空 ");
			return info;
		}
		Map<String, Object> result = agencyServiceMapper.selectAgencyService(caseTypeId,agencyId,serviceType);
		info.setSuccess(true);
		info.setData(result);
		info.setMessage("查询成功");
		return info;
	}
}