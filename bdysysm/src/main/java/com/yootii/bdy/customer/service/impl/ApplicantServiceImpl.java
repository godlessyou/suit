package com.yootii.bdy.customer.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.common.ReturnInfo;
import com.yootii.bdy.customer.dao.ApplicantMapper;
import com.yootii.bdy.customer.dao.CustomerApplicantMapper;
import com.yootii.bdy.customer.model.Applicant;
import com.yootii.bdy.customer.model.CustomerApplicant;
import com.yootii.bdy.customer.service.ApplicantService;
import com.yootii.bdy.customer.service.CustomerService;
@Service("ApplicantService")
public class ApplicantServiceImpl implements ApplicantService{
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Resource
	private ApplicantMapper applicantmapper;	
	@Resource
	private CustomerApplicantMapper customerApplicantMapper;
	@Resource
	private CustomerService customerService;
	@Override
	public ReturnInfo queryApplicant(Applicant applicant, GeneralCondition gcon, Integer customerId) {
		ReturnInfo info = new ReturnInfo();
		List<Applicant> applicants = new ArrayList<>();
		Long total = (long) 0;
		if(customerId != null) {
			 applicants = applicantmapper.selectByApplicantAndCustId(applicant, gcon,customerId);
			 total = applicantmapper.selectByApplicantCountAndCustId(applicant, gcon,customerId);
		}else {
			 applicants = applicantmapper.selectByApplicant(applicant, gcon);
			 total = applicantmapper.selectByApplicantCount(applicant, gcon);

		}
		
		info.setData(applicants);
		info.setTotal(total);
		info.setCurrPage(gcon.getPageNo());
		info.setSuccess(true);
		info.setMessage("查询申请人成功");
		return info ;
	}
	@Override
	public ReturnInfo queryApplicantDetail(Applicant applicant, GeneralCondition gcon) {
		ReturnInfo info = new ReturnInfo();
		List<Applicant> applicants = applicantmapper.selectByApplicantAndTm(applicant, gcon);
		Long total = applicantmapper.selectByApplicantAndTmCount(applicant, gcon);
		info.setData(applicants);
		info.setTotal(total);
		info.setCurrPage(gcon.getPageNo());
		info.setSuccess(true);
		info.setMessage("查询申请人成功");
		return info ;
		
	}
	
	@Override
	public ReturnInfo ceateApplicant(Applicant applicant, GeneralCondition gcon,Integer customerId) {
		ReturnInfo rtnInfo = new ReturnInfo();
//		Applicant applicant1 = applicantmapper.selectByApplicantCNName(applicant);
		
		Applicant applicant1 = getApplicantByName(applicant);
		
		if(applicant1 !=null) {
			Integer mainAppId=applicant1.getMainAppId();			
			if (mainAppId==null){
				Integer appId=applicant1.getId();
				mainAppId=appId;
				applicant1.setMainAppId(mainAppId);
				applicantmapper.updateByPrimaryKeySelective(applicant1);
			}				
			
			boolean hasBindCust=customerService.checkBindCust(customerId, mainAppId);
			if(!hasBindCust){
				rtnInfo = customerService.bindApplicant(customerId, mainAppId);
				if(rtnInfo.getSuccess()){			    
	//					rtnInfo.setData(applicant1);
					rtnInfo.setSuccess(true);
					rtnInfo.setMessageType(4);
					rtnInfo.setMessage("添加申请人成功");
				}
			}else{
				rtnInfo.setSuccess(true);
				rtnInfo.setMessageType(4);
				rtnInfo.setMessage("添加申请人成功");
			}
			
			return rtnInfo;				
			
		}else {
			//Modification start, by yang guang, 
			//2018-10-25
			//设置默认值
			String appType=applicant.getAppType();
			if (appType==null || appType.equals("")){
				applicant.setAppType("法人或其他组织");
			}
			String sendType=applicant.getSendType();
			if (sendType==null || sendType.equals("")){
				applicant.setSendType("国外");
			}
			//Modification end
			
		    applicantmapper.insertSelective(applicant);
		    
		    Integer mainAppId=applicant.getMainAppId();
			if (mainAppId==null){
				Integer appId=applicant.getId();
				mainAppId=appId;
				applicant.setMainAppId(mainAppId);
				applicantmapper.updateByPrimaryKeySelective(applicant);
			}	
		    rtnInfo = customerService.bindApplicant(customerId, mainAppId);
		    if(rtnInfo.getSuccess()){			    
//					rtnInfo.setData(applicant);
				rtnInfo.setSuccess(true);
				rtnInfo.setMessageType(4);
				rtnInfo.setMessage("创建申请人成功");
		    }
			return rtnInfo;
		}
	}
	
	
	@Override
	public ReturnInfo motifyApplicant(Applicant applicant, GeneralCondition gcon, Integer customerId) {
		ReturnInfo rtnInfo = new ReturnInfo();
		
		Integer appId=applicant.getId();		
		
		if(appId==null){
			rtnInfo.setSuccess(false);
			rtnInfo.setMessageType(-1);
			rtnInfo.setMessage("缺少必要的参数appId");
			return rtnInfo;
		}
		
		Applicant applicantold = applicantmapper.selectByPrimaryKey(appId);
		Integer mainAppIdOld=applicantold.getMainAppId();
		
		//申请人的主申请人是否为其他申请人
		boolean isMainAppSelf=false;
		if (mainAppIdOld.intValue()==appId.intValue()){
			isMainAppSelf=true;
		}
		
		boolean needBindCust=false;//是否需要绑定其它客户
		if (customerId!=null){
			needBindCust=true;
			List<CustomerApplicant> list =customerApplicantMapper.selectbyAppId(mainAppIdOld);		
			if(list !=null) {				
				for(CustomerApplicant ca:list) {
					Integer custId=ca.getCustId();
					if (custId!=null && custId.intValue()==customerId.intValue()){
						needBindCust=false;
						break;
					}						
				}				
			}
			if (needBindCust){
				if (!isMainAppSelf){
					applicant.setMainAppId(appId); //解除与主申请人的绑定关系
				}
			}
		}
			
		Integer hasTm=applicantold.getHasTm();
		
		String oldApplicantName=applicantold.getApplicantName();
		String applicantName=applicant.getApplicantName();
		
		//Modification start, by yang guang
		//2018-12-18	
		//to resolve BDY-750
		if(hasTm==null || hasTm!= 1) {
			applicantmapper.updateByPrimaryKeySelective(applicant);		
		} else {
			if (oldApplicantName==null && applicantName==null){
				applicantmapper.updateByPrimaryKeySelective(applicant);
			}
			if (oldApplicantName!=null && applicantName!=null && oldApplicantName.equals(applicantName)){
				applicantmapper.updateByPrimaryKeySelective(applicant);
			}else{
				logger.info("商标信息中的申请人名称不允许修改");
			}
		}		
		//Modification end
		
		if (needBindCust){	
			//如果申请人的主申请人是自己，那么解除与原有客户的绑定关系
			if (isMainAppSelf){
				customerApplicantMapper.deleteByAppId(appId); //解除与原有客户的绑定关系
			}	
			
			CustomerApplicant ca=new CustomerApplicant();
			ca.setAppId(appId);
			ca.setCustId(customerId);
			customerApplicantMapper.insertSelective(ca);
		}
		
		rtnInfo.setSuccess(true);
		rtnInfo.setMessageType(-3);
		rtnInfo.setMessage("修改申请人成功");
		return rtnInfo;
	}

	@Override
	public ReturnInfo bindApplicant(String idlist,Integer mainId) {
		ReturnInfo rtnInfo	 = new ReturnInfo();
		String[] idl = idlist.split(",");
		for(String id:idl) {
			Applicant applicant = new Applicant();
			applicant.setId(Integer.valueOf(id));
			applicant.setMainAppId(mainId);
			applicantmapper.updateByPrimaryKeySelective(applicant);

			List<CustomerApplicant> list = customerApplicantMapper.selectbyAppIdAndMainId(Integer.valueOf(id),mainId);
			if(list !=null) {
				for(CustomerApplicant ca:list) {
					ca.setAppId(mainId);
					//Modification start, 2018-08-15
//					customerApplicantMapper.insert(ca);
					customerApplicantMapper.updateByPrimaryKeySelective(ca);
					//Modification end
					
				}
			}
//			list =customerApplicantMapper.selectbyAppId(Integer.valueOf(id));
//			if(list !=null) {
//				for(CustomerApplicant ca:list) {
//					customerApplicantMapper.deleteByPrimaryKey(ca.getId());
//				}
//			}
		}
		rtnInfo.setSuccess(true);
		rtnInfo.setMessageType(-3);
		rtnInfo.setMessage("修改申请人成功");
		return rtnInfo;
	}

	@Override
	public ReturnInfo unBindApplicant(Integer id) {
		ReturnInfo rtnInfo = new ReturnInfo();
		Applicant applicant = new Applicant();
		applicant=applicantmapper.selectByPrimaryKey(id);
		List<CustomerApplicant> list =customerApplicantMapper.selectbyAppId(applicant.getMainAppId());
		applicant.setId(id);
		applicant.setMainAppId(id);
		applicantmapper.updateByPrimaryKeySelective(applicant);
		if(list !=null) {
			for(CustomerApplicant ca:list) {
				ca.setAppId(id);
				ca.setId(null);
				customerApplicantMapper.insertSelective(ca);
			}
		}
		rtnInfo.setSuccess(true);
		rtnInfo.setMessageType(-3);
		rtnInfo.setMessage("修改申请人成功");
		return rtnInfo;
	}
	@Override
	public ReturnInfo deleteapplicant(Applicant applicant, GeneralCondition gcon,Integer customerId) {
		ReturnInfo rtnInfo = new ReturnInfo();
		
		applicantmapper.deleteByPrimaryKey(applicant.getId());
		customerService.unBindApplicant(customerId, applicant.getId());
		rtnInfo.setSuccess(true);
		rtnInfo.setMessageType(-3);
		rtnInfo.setMessage("删除申请人成功");
		return rtnInfo;
	}
	
	
	
	@Override
	public ReturnInfo queryApplicantNameByCustId(GeneralCondition gcon, Integer customerId) {
		ReturnInfo info = new ReturnInfo();
		List<Applicant> applicants = applicantmapper.selectApplicantNameByCustId(customerId, gcon);
		Long total = (long)0;
		if(applicants!=null){
			total=(long)applicants.size();
		}
		info.setData(applicants);
		info.setTotal(total);
		info.setCurrPage(gcon.getPageNo());
		info.setSuccess(true);
		info.setMessage("查询申请人成功");
		return info ;
	}
	
	
	
	@Override
	public ReturnInfo queryApplicantByCustId(GeneralCondition gcon, Integer customerId) {
		ReturnInfo info = new ReturnInfo();
		//Modification start, 2018-11-23
		//to reduce time of search
		
		//Modification start, by yang guang, 2018-09-03
		//in order to return all data
//		gcon.setRows(10000);
		
		//Modification end
		
		List<Applicant> applicants = applicantmapper.selectByApplicantByCustId(customerId, gcon);
		Long total = applicantmapper.selectByApplicantByCustIdCount(customerId, gcon);
		info.setData(applicants);
		info.setTotal(total);
		info.setCurrPage(gcon.getPageNo());
		info.setSuccess(true);
		info.setMessage("查询申请人成功");
		return info ;
	}
	@Override
	public ReturnInfo queryAllApplicantByCustId(GeneralCondition gcon, Integer customerId) {
		ReturnInfo info = new ReturnInfo();
		String orderCol=gcon.getOrderCol();
		if (orderCol==null){
			gcon.setOrderCol("id");
		}
		String orderAsc=gcon.getOrderAsc();
		if (orderAsc==null){
			gcon.setOrderAsc("asc");
		}
		List<Applicant> applicants = new ArrayList<>();
		if(customerId == null){
			applicants = applicantmapper.AllApplicantByCustId(customerId, gcon);
		}else{
			applicants = applicantmapper.selectAllApplicantByCustId(customerId,gcon);
			Long total = applicantmapper.selectByApplicantByCustIdCount(customerId, gcon);
			info.setTotal(total);
		}
		
		
		info.setData(applicants);
		info.setCurrPage(gcon.getPageNo());
		info.setSuccess(true);
		info.setMessage("查询申请人成功");
		return info ;
	}
	@Override
	public ReturnInfo queryAllApplicantByAppId(GeneralCondition gcon, Integer AppId) {
		ReturnInfo info = new ReturnInfo();
		List<Applicant> applicants = applicantmapper.selectAllApplicantById(AppId);
		//Long total = applicantmapper.selectByApplicantByCustIdCount(customerId, gcon);
		info.setData(applicants);
		//info.setTotal(total);
		info.setCurrPage(gcon.getPageNo());
		info.setSuccess(true);
		info.setMessage("查询申请人成功");
		return info ;
	}
	@Override
	public Applicant selectAppAndMaterialByPrimaryKey(Integer id) {
		
		return applicantmapper.selectAppAndMaterialByPrimaryKey(id);
	}
	@Override
	public ReturnInfo queryAndCreateAapplicant(GeneralCondition gcon, Applicant applicant, Integer custId) {
		ReturnInfo rtnInfo = new ReturnInfo();
		Applicant app = applicantmapper.selectByName(applicant);
		
	/*	if(app == null) {
			int i = applicantmapper.insertSelective(applicant);
			customerService.bindApplicant(custId, applicant.getId());
			rtnInfo.setData(applicant);
		}else {
			customerService.bindApplicant(custId, app.getId());
			rtnInfo.setData(app);
		}	*/
		rtnInfo.setData(app);
		rtnInfo.setCurrPage(gcon.getPageNo());
		rtnInfo.setSuccess(true);
		rtnInfo.setMessage("查询申请人成功");
		return rtnInfo ;
	}
	@Override
	public ReturnInfo queryApplicantbyappcnname(String applicantCnName, GeneralCondition gcon) {
		ReturnInfo rtnInfo = new ReturnInfo();
		Applicant app =new Applicant();
		
		app.setApplicantName(applicantCnName);
		Applicant applicant1 = applicantmapper.selectByApplicantCNName(app);
		rtnInfo.setData(applicant1);
		rtnInfo.setSuccess(true);
		rtnInfo.setMessage("查询申请人成功");
		return rtnInfo ;
	}
	
	
	private void updateApplicantProperties(Applicant  applicant1, Applicant  applicant){

//		applicant.setId(applicant1.getId());
		
		//从数据库中获取的申请人信息				
		String applicantEnName1=applicant1.getApplicantEnName();
		String applicantAddress1=applicant1.getApplicantAddress();
		String applicantEnAddress1=applicant1.getApplicantEnAddress();
		String appType1=applicant1.getAppType();
		String sendType1=applicant1.getSendType();
		String country1=applicant1.getCountry();
		
		//从案件中提取的申请人信息				
		String applicantEnName=applicant.getApplicantEnName();
		String applicantAddress=applicant.getApplicantAddress();
		String applicantEnAddress=applicant.getApplicantEnAddress();
		String appType=applicant.getAppType();
		String sendType=applicant.getSendType();
		String country=applicant.getCountry();									

		//如果数据库中的申请人信息与案件信息中提取的申请人信息不同，那么，执行更新操作。				
		boolean needUpdate=false;
					
		if  (applicantEnName!=null && !applicantEnName.equals("")){
			if (applicantEnName1==null  || !applicantEnName.equals(applicantEnName1)){
				applicant1.setApplicantEnName(applicantEnName);
				needUpdate=true;
			}
		}
		if  (applicantAddress!=null && !applicantAddress.equals("") ){						
			if (applicantAddress1==null  || !applicantAddress.equals(applicantAddress1)){
				applicant1.setApplicantAddress(applicantAddress);
				needUpdate=true;						
			}
		}	
		if  (applicantEnAddress!=null && !applicantEnAddress.equals("")){
			if (applicantEnAddress1==null || !applicantEnAddress.equals(applicantEnAddress1) ){
				applicant1.setApplicantEnAddress(applicantEnAddress);
				needUpdate=true;
			}
		}					
		if  (appType!=null && !appType.equals("")){
			if (appType1==null || !appType.equals(appType1) ){
				applicant1.setAppType(appType);
				needUpdate=true;							
			}
		}		
		if  (sendType!=null && !sendType.equals("")){
			if (sendType1==null || !sendType.equals(sendType) ){
				applicant1.setSendType(sendType);
				needUpdate=true;							
			}
		}
		if  (country!=null && !country.equals("")){
			if (country1==null || !country.equals(country1) ){
				applicant1.setCountry(country);
				needUpdate=true;							
			}
		}		
		
		if (needUpdate){
			applicantmapper.updateByPrimaryKeySelective(applicant1);
		}
		
	}
	
	@Override
	public ReturnInfo checkAndSaveApplicant(Applicant  applicant,Integer customerId) {
		ReturnInfo rtnInfo = new ReturnInfo();
//		Applicant applicant1 = applicantmapper.selectByApplicantCNName(applicant);
		
		Applicant applicant1 = getApplicantByName(applicant);
		
		if(applicant1 !=null) {			
			Integer mainAppId=applicant1.getMainAppId();
			if (mainAppId==null){
				Integer appId=applicant1.getId();
				mainAppId=appId;
				applicant1.setMainAppId(mainAppId);
				applicantmapper.updateByPrimaryKeySelective(applicant1);
			}
					
			updateApplicantProperties(applicant1, applicant);
			
//			customerService.unBindApplicant(customerId, applicant1.getId());
//			customerService.bindApplicant(customerId, applicant1.getId());
			
			//Modification start, by yang guang
			//2018-10-25
			//do not bind customer at here
			/*
			boolean hasBindCust=customerService.checkBindCust(customerId, mainAppId);
			if(!hasBindCust){
				rtnInfo = customerService.bindApplicant(customerId, mainAppId);
				if(!rtnInfo.getSuccess()){			    
					return rtnInfo;
				}
			}			
			
			*/
		

			rtnInfo.setData(applicant);
			rtnInfo.setSuccess(true);
			rtnInfo.setMessageType(4);
			rtnInfo.setMessage("保存申请人成功");
			return rtnInfo;
		}else {
			
			//Modification start, by yang guang, 
			//2018-10-25
			//设置默认值
			String appType=applicant.getAppType();
			if (appType==null || appType.equals("")){
				applicant.setAppType("法人或其他组织");
			}
			String sendType=applicant.getSendType();
			if (sendType==null || sendType.equals("")){
				applicant.setSendType("国外");
			}
			//Modification end
			
			
			applicantmapper.insertSelective(applicant);
							
		    Integer mainAppId=applicant.getMainAppId();
			if (mainAppId==null){
				Integer appId=applicant.getId();
				mainAppId=appId;
				applicant.setMainAppId(mainAppId);
				applicantmapper.updateByPrimaryKeySelective(applicant);
			}	
		    rtnInfo = customerService.bindApplicant(customerId, mainAppId);
			
		    if(rtnInfo.getSuccess()){			    
				rtnInfo.setData(applicant);
				rtnInfo.setSuccess(true);
				rtnInfo.setMessageType(4);
				rtnInfo.setMessage("创建申请人成功");
		    }
			
			return rtnInfo;
		}
	}
	
	@Override
	public ReturnInfo queryApplicantbyappname(String appCnName, String appEnName, GeneralCondition gcon) {
		ReturnInfo rtnInfo = new ReturnInfo();
		if(appCnName ==null && appEnName ==null) {
			rtnInfo.setSuccess(false);
			rtnInfo.setMessage("查询申请人失败");
			return rtnInfo ;
		}
		if(appCnName !=null) {
			Applicant app =new Applicant();
			
			app.setApplicantName(appCnName);
			Applicant applicant = applicantmapper.selectByApplicantCNName(app);
			rtnInfo.setData(applicant);
			rtnInfo.setSuccess(true);
			rtnInfo.setMessage("查询申请人成功");
			return rtnInfo ;
		}
		if(appEnName !=null) {
			Applicant app =new Applicant();
			
			app.setApplicantEnName(appEnName);
			List<Applicant> applicants = applicantmapper.selectByApplicant(app, gcon);
			Applicant applicant =applicants.get(0);
			rtnInfo.setData(applicant);
			rtnInfo.setSuccess(true);
			rtnInfo.setMessage("查询申请人成功");
			return rtnInfo ;
		}
		rtnInfo.setSuccess(false);
		rtnInfo.setMessage("查询申请人失败");
		return rtnInfo ;
		
	}
	
	
	
	
	  public Applicant getApplicantByName(Applicant applicant){
		
		String appEnName=applicant.getApplicantEnName();		
		List<Applicant> applicantList=applicantmapper.selectByApplicantName(applicant);
		
		Applicant app=null;
		
		if (applicantList!=null && applicantList.size()>0){
			for(Applicant applicant1:applicantList){				
				String applicantEnName=applicant1.getApplicantEnName();
				//精确匹配：申请人中文名字相同，并且英文名字也相同的
				if (applicantEnName!=null && appEnName!=null && applicantEnName.equals(appEnName)){
					app=applicant1;
					break;
				}					
				if (app==null){
					Integer id=applicant1.getId();
					Integer mainAppId=applicant1.getMainAppId();
					//主申请人
					if (id!=null && mainAppId!=null && id.intValue()==mainAppId.intValue()){
						app=applicant1;					
					}
				}
			}
			if (app==null){
				app=applicantList.get(0);
			}
		}
		
		return app;
	}
	  
	  
	  
	  
	@Override
	public ReturnInfo queryTmCountByAppIdList(GeneralCondition gcon, String appIds) {
		ReturnInfo info = new ReturnInfo();
		Map<String, Object>map=new HashMap<String, Object>();
		
		List<String> list = Arrays.asList(appIds.split(","));
		List<Integer> appIdList =null;
		if (list!=null && list.size()>0){
			appIdList=new ArrayList<Integer>();
			for(String id: list){				
				Integer appId=Integer.parseInt(id);
				appIdList.add(appId);
			}
		}
		
		String orderCol=gcon.getOrderCol();
		if (orderCol==null){
			gcon.setOrderCol("id");
		}
		String orderAsc=gcon.getOrderAsc();
		if (orderAsc==null){
			gcon.setOrderAsc("asc");
		}
		
		List<Applicant> applicants = applicantmapper.selectTmCountByAppIdList(appIdList, gcon);
		
		info.setData(applicants);			
		info.setCurrPage(gcon.getPageNo());
		info.setSuccess(true);
		info.setMessage("查询申请人的商标数量成功");
		return info ;
	}
	

}
