package com.yootii.bdy.agency.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.yootii.bdy.agency.dao.AgencyCustomerMapper;
import com.yootii.bdy.agency.dao.AgencyMapper;
import com.yootii.bdy.agency.dao.AgencyUserMapper;
import com.yootii.bdy.agency.dao.CooperationAgencyMapper;
import com.yootii.bdy.agency.model.Agency;
import com.yootii.bdy.agency.model.AgencyCustomer;
import com.yootii.bdy.agency.model.CooperationAgency;
import com.yootii.bdy.agency.model.RetuenAmount;
import com.yootii.bdy.agency.service.AgencyService;
import com.yootii.bdy.common.Constant;
import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.user.model.User;
import com.yootii.bdy.user.service.UserService;
import com.yootii.bdy.common.ReturnInfo;
import com.yootii.bdy.customer.dao.CustomerMapper;
import com.yootii.bdy.customer.model.Customer;
import com.yootii.bdy.department.dao.DepartmentMapper;
import com.yootii.bdy.ipservice.service.AgencyServiceService;
import com.yootii.bdy.role.service.RoleService;
@Service("agencyService")
public class AgencyServiceImpl implements AgencyService{
	@Resource
	private UserService userService;
	@Resource
	private AgencyMapper agencyMapper;
	@Resource
	private AgencyUserMapper agencyUserMapper;	
	@Resource
	private AgencyCustomerMapper agencyCustomerMapper;
	@Resource
	private CustomerMapper customerMapper;
	@Resource
	private DepartmentMapper departmentMapper;
	@Resource
	private CooperationAgencyMapper cooperationAgencyMapper;
	@Resource
	private RoleService roleService;
	
	@Resource
	private AgencyServiceService agencyServiceService;
	@Value("${fileUrl}")  
	private String fileUrl; 
	@Override
	public ReturnInfo queryAgencySimpleList(Agency agency, GeneralCondition gcon) {
		ReturnInfo rtnInfo = new ReturnInfo();
		if (gcon.getPageSize() <= 0) {
			gcon.setPageSize(10);
		}
		if (gcon.getPageNo() <= 0) {
			gcon.setPageNo(1);
		}
		List<Agency> agencys = new ArrayList<Agency>();
		agencys=agencyMapper.selectByAgencySimple(agency,gcon);
		Long total = agencyMapper.selectCountByAgencySimple(agency,gcon);
		rtnInfo.setData(agencys);
		rtnInfo.setTotal(total);
		rtnInfo.setCurrPage(gcon.getPageNo());
		rtnInfo.setSuccess(true);
		rtnInfo.setMessageType(4);
		rtnInfo.setMessage("查询代理机构成功");
		return rtnInfo;
		
	}
	@Override
	public ReturnInfo queryAgencyDetailByUser(Agency agency, GeneralCondition gcon, User user) {
		ReturnInfo rtnInfo = new ReturnInfo();
		
		// 查看该用户是不是该代理机构的成员
		Integer i = agencyUserMapper.selectByAgencyAndUserId(agency.getId(),user.getUserId());
		if(i>0) {
			Agency agency1=agencyMapper.selectAgencyDetail(agency.getId());
			rtnInfo.setData(agency1);
			rtnInfo.setCurrPage(gcon.getPageNo());
			rtnInfo.setSuccess(true);
			rtnInfo.setMessageType(4);
			rtnInfo.setMessage("查询代理机构成功");
			return rtnInfo;
		}else {
			rtnInfo.setSuccess(false);
			rtnInfo.setMessageType(-3);
			rtnInfo.setMessage("用户没有权限查看代理机构详细信息");
			return rtnInfo;
		}
		
		
	}
	@Override
	public ReturnInfo queryAgencyDetailByCustomer(Agency agency, GeneralCondition gcon, Customer customer) {
		ReturnInfo rtnInfo = new ReturnInfo();
		// 查看该用户是不是该代理机构的成员
				Integer i = agencyCustomerMapper.selectByAgencyAndCustId(agency.getId(),customer.getId());
				if(i>0) {
					Agency agency1=agencyMapper.selectAgencyDetail(agency.getId());
					rtnInfo.setData(agency1);
					rtnInfo.setCurrPage(gcon.getPageNo());
					rtnInfo.setSuccess(true);
					rtnInfo.setMessageType(4);
					rtnInfo.setMessage("查询代理机构成功");
					return rtnInfo;
				}else {
					rtnInfo.setSuccess(false);
					rtnInfo.setMessageType(-3);
					rtnInfo.setMessage("用户没有权限查看代理机构详细信息");
					return rtnInfo;
				}
	}
	@Override
	public ReturnInfo queryAgencyList(Agency agency, GeneralCondition gcon) {
		ReturnInfo rtnInfo = new ReturnInfo();
		//if(user.getUserType()!=null && user.getUserType() <= 20) {
			List<Agency> agencys=agencyMapper.selectAgencyList(agency,gcon);
			Long total = agencyMapper.selectCountByAgency(agency,gcon);
			rtnInfo.setData(agencys);
			rtnInfo.setTotal(total);
			rtnInfo.setCurrPage(gcon.getPageNo());
			rtnInfo.setSuccess(true);
			rtnInfo.setMessageType(4);
			rtnInfo.setMessage("查询代理机构成功");
			return rtnInfo;
		/*}
		rtnInfo.setSuccess(false);
		rtnInfo.setMessageType(-3);
		rtnInfo.setMessage("用户没有权限查看代理机构详细信息");
		return rtnInfo;*/
	}
	@Override
	@Transactional
	public ReturnInfo createAgency(Agency agency) {
		ReturnInfo rtnInfo = new ReturnInfo();
		//if(user.getUserType()!=null && user.getUserType() <= 10) {
			Integer i= agencyMapper.checkName(agency);
			if(i<1) {
				agencyMapper.insertSelective(agency);
				//创建机构内部角色权限
				roleService.addRolePermissionByAgencyId(agency.getId());
				//添加合作所
		   		CooperationAgency cooperationAgency =new CooperationAgency();
		   		cooperationAgency.setAgencyId(agency.getId());
		   		cooperationAgency.setCooperationAgencyId(1);
		   		bindcooperationagency(cooperationAgency, null);
		   		
		   		cooperationAgency.setCooperationAgencyId(2);
		   		cooperationAgency.setId(null);
		   		bindcooperationagency(cooperationAgency, null);
				//添加所以服务9999
		   		agencyServiceService.createAgencyAllService(agency.getId());
		   		
				
				rtnInfo.setData(agency);
				rtnInfo.setSuccess(true);
				rtnInfo.setMessageType(4);
				rtnInfo.setMessage("创建代理机构成功");
				return rtnInfo;
			}else {
				rtnInfo.setSuccess(false);
				rtnInfo.setMessageType(-2);
				rtnInfo.setMessage("代理机构不能重名");
				return rtnInfo;
			}
			
			
		/*}
		rtnInfo.setSuccess(false);
		rtnInfo.setMessageType(-3);
		rtnInfo.setMessage("用户没有权限创建代理机构");
		return rtnInfo;*/
		
	}
	@Override
	public ReturnInfo checkName(Agency agency) {
		ReturnInfo rtnInfo = new ReturnInfo();
		Integer i= agencyMapper.checkName(agency);
		if(i<1) {
			rtnInfo.setSuccess(true);
			rtnInfo.setMessageType(4);
			rtnInfo.setMessage("当前代理机构名字可用");
			return rtnInfo;
		}
		rtnInfo.setSuccess(false);
		rtnInfo.setMessageType(-3);
		rtnInfo.setMessage("重复的代理机构名字");
		return rtnInfo;
	}
	@Override
	@Transactional
	public ReturnInfo modifyAgency(Agency agency, GeneralCondition gcon, User user) {
		ReturnInfo rtnInfo = new ReturnInfo();
		//代理机构管理员可以修改本机构属性信息，代理机构管理员可以将客户用户从客户名单中删除。
		if(userService.hasRole("代理机构管理员", user)) {
			if(agency.getName()!=null) {
				Integer checkName = agencyMapper.checkName(agency);
				if(checkName >0) {
					rtnInfo.setSuccess(false);
					rtnInfo.setMessageType(-3);
					rtnInfo.setMessage("代理机构不能重名");
					return rtnInfo;
				}
			}
			
			Integer i = agencyUserMapper.selectByAgencyAndUserId(agency.getId(), user.getUserId());
			
			
			if(i>0) {
				
				//Modification start, by yang guang, 2018-12-12
				//set default value
				Integer appOnline=agency.getAppOnline();
				if (appOnline!=null && appOnline==1){
					Integer appChannel=agency.getAppChannel();
					if (appChannel==null){
						appChannel=agency.getId();
						agency.setAppChannel(appChannel);
					}					
				}
				//Modification end
				
				//修改
				 modifyAgency1(agency);
				 rtnInfo.setSuccess(true);
				 rtnInfo.setMessageType(4);
				 rtnInfo.setMessage("修改代理机构成功");
				 return rtnInfo;
			}else {
				rtnInfo.setSuccess(false);
				rtnInfo.setMessageType(-3);
				rtnInfo.setMessage("代理机构管理员只能修改本代理机构");
				return rtnInfo;
			}
		}
		//平台管理员可以修改机构某些属性信息
		if(user.getUserType() ==1) {
			//修改
			modifyAgency1(agency);
			rtnInfo.setSuccess(true);
			rtnInfo.setMessageType(4);
			rtnInfo.setMessage("修改代理机构成功");
			return rtnInfo;
		}
		
		rtnInfo.setSuccess(false);
		rtnInfo.setMessageType(-3);
		rtnInfo.setMessage("修改失败");
		return rtnInfo;
	}
	
	@Override
	@Transactional
	public ReturnInfo deleteAgency(Agency agency) {
		ReturnInfo rtnInfo = new ReturnInfo();
		//删除代理机构时要保证数据一致性，要保证该代理机构没有合作代理机构、没有下属部门、没有所属代理人员
		/*if(user.getUserType()>10) {
			rtnInfo.setSuccess(false);
			rtnInfo.setMessageType(-3);
			rtnInfo.setMessage("该用户没有权限删除代理机构");
			return rtnInfo;
		}*/
		Integer i =0;
		i=cooperationAgencyMapper.selectCooperationAgencyCountByAgencyId(agency.getId());
		if(i>0) {
			rtnInfo.setSuccess(false);
			rtnInfo.setMessageType(-3);
			rtnInfo.setMessage("该代理机构有合作代理机构,禁止删除");
			return rtnInfo;
		}
		i=departmentMapper.selectDepartmentCountByAgencId(agency.getId());
		if(i>0) {
			rtnInfo.setSuccess(false);
			rtnInfo.setMessageType(-3);
			rtnInfo.setMessage("该代理机构有下属部门,禁止删除");
			return rtnInfo;
		}
		i=agencyUserMapper.selectUserCountByAgencId(agency.getId());
		if(i>0) {
			rtnInfo.setSuccess(false);
			rtnInfo.setMessageType(-3);
			rtnInfo.setMessage("该代理机构有所属代理人员,禁止删除");
			return rtnInfo;
		}
		agencyMapper.deleteByPrimaryKey(agency.getId());
		rtnInfo.setSuccess(true);
		rtnInfo.setMessageType(-3);
		rtnInfo.setMessage("删除代理机构成功");
		return rtnInfo;
	}
	@Override
	public ReturnInfo uploadLogo(HttpServletRequest request, Agency agency) {
		ReturnInfo rtnInfo = new ReturnInfo();
		try {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile file = multipartRequest.getFiles("file").get(0);
			String filePath = null;
			if (file.getSize() != 0) {
				filePath = uploadFile(agency, file, rtnInfo);
				agency.setLogo(filePath + file.getOriginalFilename());
				// 将logo路径数据保存到表中
				if (rtnInfo.getSuccess()) {
					agencyMapper.updateByPrimaryKeySelective(agency);
					Agency agency2 = agencyMapper.selectByPrimaryKey(agency.getId());
					rtnInfo.setData(agency2);
					rtnInfo.setSuccess(true);
					rtnInfo.setMessage("上传logo成功");
				}
			}

		} catch (Exception e) {
			rtnInfo.setSuccess(false);
			rtnInfo.setMessage("上传失败：" + e.getMessage());
		}

		return rtnInfo;
	}
	@Override
	public ReturnInfo bindcooperationagency( CooperationAgency cooperationAgency, User user) {
		ReturnInfo rtnInfo = new ReturnInfo();
		//代理机构管理员可以修改本机构属性信息，代理机构管理员可以将客户用户从客户名单中删除。
//		if(user.getUserType()<=20) {
//			Integer i = agencyUserMapper.selectByAgencyAndUserId(cooperationAgency.getAgencyId(), user.getUserId());
//			if(i>0) {
				cooperationAgencyMapper.deleteByCooperationAgency(cooperationAgency);
				cooperationAgencyMapper.insertSelective(cooperationAgency);
				rtnInfo.setSuccess(true);
				rtnInfo.setMessageType(2);
				rtnInfo.setMessage("绑定合作机构成功");
				return rtnInfo;
//			}
//		}
//		rtnInfo.setSuccess(false);
//		rtnInfo.setMessageType(-4);
//		rtnInfo.setMessage("绑定合作机构失败");
//		return rtnInfo;
	}
	@Override
	public ReturnInfo unbindcooperationagency( CooperationAgency cooperationAgency,User user) {
		ReturnInfo rtnInfo = new ReturnInfo();
		//代理机构管理员可以修改本机构属性信息，代理机构管理员可以将客户用户从客户名单中删除。
		
			Integer i = agencyUserMapper.selectByAgencyAndUserId(cooperationAgency.getAgencyId(), user.getUserId());
			if(i>0) {
				cooperationAgencyMapper.deleteByCooperationAgency(cooperationAgency);
				rtnInfo.setSuccess(true);
				rtnInfo.setMessageType(2);
				rtnInfo.setMessage("解绑合作机构成功");
				return rtnInfo;
			}else {
				rtnInfo.setSuccess(false);
				rtnInfo.setMessageType(-4);
				rtnInfo.setMessage("解绑合作机构失败");
				return rtnInfo;
			}
		
	}
	@Override
	public ReturnInfo querycooperationagency( Agency agency, GeneralCondition gcon,Integer userId) {
		ReturnInfo rtnInfo = new ReturnInfo();
		//Integer i = agencyUserMapper.selectByAgencyAndUserId(agency.getId(), user.getUserId());
		//if(i>0) {
			
			List<Agency> agencys=agencyMapper.selectCooperationAgencyList(agency,gcon);
			Long total = cooperationAgencyMapper.selectCountByCooperationAgency(agency,gcon);
			rtnInfo.setData(agencys);
			rtnInfo.setTotal(total);
			rtnInfo.setCurrPage(gcon.getPageNo());
			rtnInfo.setSuccess(true);
			rtnInfo.setMessageType(4);
			rtnInfo.setMessage("查询合作机构成功");
			return rtnInfo;
		//}
		/*rtnInfo.setSuccess(false);
		rtnInfo.setMessageType(-3);
		rtnInfo.setMessage("用户没有权限查看合作机构");
		return rtnInfo;*/
	}
	
	
	
	
	private String uploadFile(Agency agency, MultipartFile uploadFile, ReturnInfo rtnInfo) {
		String filePath = null;
		if (uploadFile != null) {
			// 文件目录
			try {

				String fileName = uploadFile.getOriginalFilename();
				//String savePath = Constant.app_dir + Constant.agencyLogo;
				//将上传路径改为D:/apache-tomcat-8.5.9-1/biaodeyi/..
				String savePath =fileUrl+ Constant.agencyLogo;
				
				filePath = Constant.agencyLogo;
				File file = new File(savePath);
				if (!file.exists())
					file.mkdirs();
				uploadFile.transferTo(new File(savePath + fileName));
				rtnInfo.setSuccess(true);
				rtnInfo.setMessage("文件上传成功");
			} catch (Exception e) {
				rtnInfo.setSuccess(false);
				rtnInfo.setMessage("文件上传失败,失败原因：" + e.getMessage());
			}

		}
		return filePath;
	}
	@Transactional
	private void modifyAgency1(Agency agency) {
		
		agencyMapper.updateByPrimaryKeySelective(agency);
		List<Customer> customerList = agency.getAgencyCustomer();
		if(customerList != null) {
				for(Customer customer : customerList) {
					if(customer.getId() == null || customer.getId().intValue()==0) {
						int id = customerMapper.insertSelective(customer);
						//Integer id= customerMapper.selectMaxId();
						AgencyCustomer agencyCustomer=new AgencyCustomer();
						agencyCustomer.setAgencyId(agency.getId());
						agencyCustomer.setCustId(id);
						agencyCustomerMapper.insertSelective(agencyCustomer);
					}else {
						customerMapper.updateByPrimaryKeySelective(customer);
					}
				}
			}		
		}
	@Override
	public Agency selectAgencyByUserId(Integer userId) {
			//ReturnInfo rtnInfo = new ReturnInfo();
			Agency agency=agencyMapper.selectAgencyByUserId(userId);
			return agency;					
	}
	@Override
	public List<Agency> selectCooperationAgencyListByUserId(Integer userId) {
		List<Agency> agency=agencyMapper.selectCooperationAgencyByUserId(userId);
		return agency;		
	}
	@Override
	public List<Agency> selectAgencyByCustId(Integer custId) {
		
		return agencyMapper.selectAgencyByCustId(custId);
	}
	@Override
	public ReturnInfo statsCustAndCpAgency(HttpServletRequest request, Integer agencyId, Integer userId, GeneralCondition gcon) {
		ReturnInfo returnInfo = new ReturnInfo();
		
		List<RetuenAmount> r=agencyMapper.selectCustAndCpAgencyAmount(agencyId,gcon,userId);
		returnInfo.setData(r);
		
		
		returnInfo.setSuccess(true);
		
		returnInfo.setMessage("查询成功");
		return returnInfo;
	}
	@Override
	public ReturnInfo queryCustIdandUserIdByagency(HttpServletRequest request, Integer agencyId,
			GeneralCondition gcon) {
		ReturnInfo returnInfo = new ReturnInfo();
		RetuenAmount r = new RetuenAmount();
		List<Integer> custId = agencyCustomerMapper.selectCustIdByAgencyId(agencyId);
		List<Integer> userId = agencyUserMapper.selectUserIdByAgencyId(agencyId);
		r.setCustId(custId);
		r.setUserId(userId);
		returnInfo.setData(r);		
		returnInfo.setSuccess(true);
		
		returnInfo.setMessage("查询成功");
		return returnInfo;
	}
	@Override
	public ReturnInfo queryuncooperationagency(Agency agency, GeneralCondition gcon) {
		ReturnInfo returnInfo = new ReturnInfo();
		/*List<Agency> cpAgencys=agencyMapper.selectCooperationAgencyList(agency,gcon);
		List<Agency> agencys = agencyMapper.selectByAgencySimple(agency, gcon);
		Agency agency2 = agencyMapper.selectByPrimaryKey(agency.getId());
		cpAgencys.add(agency2);*/
		/*if(cpAgencys !=null && agencys!=null) {			
			for(int i=0;i<agencys.size();i++) {
				for(int j=0;j<cpAgencys.size();j++) {
					if(agencys.get(i).getId() == cpAgencys.get(j).getId()) {
						agencys.remove(i);
					}
				}
			}
		}*/
		List<Agency> agencys  =new ArrayList<>();
		agencys =agencyMapper.selectUnCooperationAgencyList(agency,gcon);
		List<Map<String,Object>> l = new ArrayList<>();
		if(agencys!=null) {
			for(Agency a: agencys ) {
				Map<String,Object> map =new HashMap<String, Object>();
				map.put("id", a.getId());
				map.put("name", a.getName());
				/*a.setAddress(null);
				a.setLogo(null);
				a.setTel(null);*/
				l.add(map);
			}
		}
		
		returnInfo.setData(l);		
		returnInfo.setSuccess(true);
		returnInfo.setTotal(new Long(agencys.size()));
		returnInfo.setMessage("查询成功");
		return returnInfo;
	}
	
	
	@Override
	public ReturnInfo queryAgencyChannel(GeneralCondition gcon) {
		ReturnInfo rtnInfo = new ReturnInfo();	
		List<Agency> list=agencyMapper.selectAppChannel();			
		rtnInfo.setSuccess(true);
		rtnInfo.setData(list);					
		rtnInfo.setMessage("查询成功");
		return rtnInfo;		
	}
		
}
