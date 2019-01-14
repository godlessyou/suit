package com.yootii.bdy.customer.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.yootii.bdy.agency.dao.AgencyContactMapper;
import com.yootii.bdy.agency.dao.AgencyUserMapper;
import com.yootii.bdy.agency.model.AgencyContact;
import com.yootii.bdy.agency.service.AgencyContactService;
import com.yootii.bdy.bill.model.Discount;
import com.yootii.bdy.bill.service.DiscountService;
import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.common.ReturnInfo;
import com.yootii.bdy.customer.dao.CustomerApplicantMapper;
import com.yootii.bdy.customer.dao.CustomerMapper;
import com.yootii.bdy.customer.dao.RegionMapper;
import com.yootii.bdy.customer.model.Customer;
import com.yootii.bdy.customer.model.CustomerApplicant;
import com.yootii.bdy.customer.model.CustomerContact;
import com.yootii.bdy.customer.model.Region;
import com.yootii.bdy.customer.model.Regions;
import com.yootii.bdy.customer.service.CustomerContactService;
import com.yootii.bdy.customer.service.CustomerService;
import com.yootii.bdy.mail.MailSenderInfo;
import com.yootii.bdy.mail.SendMailUtil;
import com.yootii.bdy.mailremind.model.MailRemindSettings;
import com.yootii.bdy.mailremind.service.MailRemindSettingsService;
import com.yootii.bdy.permission.dao.PermissionMapper;
import com.yootii.bdy.permission.model.Permission;
import com.yootii.bdy.role.dao.RoleMapper;
import com.yootii.bdy.security.model.Token;
import com.yootii.bdy.security.service.AuthenticationService;
import com.yootii.bdy.security.service.SysService;
import com.yootii.bdy.user.dao.UserMapper;
import com.yootii.bdy.user.model.Mail;
import com.yootii.bdy.user.model.User;
import com.yootii.bdy.user.service.UserService;
import com.yootii.bdy.util.GraspUtil;
import com.yootii.bdy.util.JsonUtil;
import com.yootii.bdy.util.ListUtils;
import com.yootii.bdy.util.ServiceUrlConfig;
import com.yootii.bdy.util.UploadUtil;

@Service
public class CustomerServiceImpl implements CustomerService{
	
	@Resource
	public CustomerMapper customerMapper;
	@Resource
	public AgencyUserMapper agencyUserMapper;
	@Resource
	protected AuthenticationService authenticationService;
	@Resource
	public PermissionMapper permissionMapper;
	@Resource
	public DiscountService discountService;
	@Resource
	public RoleMapper roleMapper;
	@Resource
	public CustomerApplicantMapper customerApplicantMapper;
	@Resource
	protected MailSenderInfo mailSenderInfo;
	@Resource
	protected SysService sysService;
	@Resource
	protected UserService userService;
	@Resource	
	private ServiceUrlConfig serviceUrlConfig;
	@Resource
	private CustomerContactService customerContactService;
	@Resource
	private AgencyContactService agencyContactService;
	@Resource
	private MailRemindSettingsService mailRemindSettingsService;
	
	@Resource
	private RegionMapper regionMapper;
	
	//行政区划
	private String [] quhua={"族","县","市","州","省"};	
	
	
	@Override
	public ReturnInfo createCustomer(Customer customer, GeneralCondition gcon, Integer agencyId,Integer agencyContactId) {
		return createCustomer(customer,gcon,agencyId,agencyContactId,1.0);
	}
	
	@Override
	public ReturnInfo createCustomer(Customer customer, GeneralCondition gcon, Integer agencyId, Integer agencyContactId, Double value) {
		ReturnInfo info = new ReturnInfo();
		Token token = sysService.checkToken(gcon.getTokenID());
		Integer userId = null;
		if(token.isUser()){
			userId=token.getUserID();//代理人创建客户，userId为当前登录的代理人的ID
		}
		String email = customer.getEmail();
		if(customer.getEmail()==null||agencyId==null
				||customer.getName()==null
				||customer.getAddress()==null||customer.getCountry()==null){
			info.setSuccess(false);
			info.setMessage("企业名称、邮箱等必填信息不能为空");
			return info;
		}
		if("".equals(customer.getEmail())
				||"".equals(customer.getName())
				||"".equals(customer.getAddress())||"".equals(customer.getCountry())){
			info.setSuccess(false);
			info.setMessage("企业地址、国家等必填信息不能为空");
			return info;
		}
		Customer customer2 = customerMapper.selectByEmail(customer.getEmail());
		if(customer2!=null){
			info.setSuccess(false);
			info.setMessage("该邮箱已注册");
			return info;
		}
		String username = customer.getUsername();
		if(username==null||"".equals(username)){
			username = email.replaceAll("[@.]", "_");
			customer.setUsername(username);
		}
		Customer customer3 = customerMapper.selectByUsername(customer.getUsername());
		if(customer3!=null){
			info.setSuccess(false);
			info.setMessage("用户名已存在");
			return info;
		}
		
		List<Region> regionList=regionMapper.selectAllRegion();
		
		
		String country=customer.getCountry();
		if (country!=null && country.equals("中国")){											
			//获取客户所在的地区
			processRegion(customer, regionList);
			Integer rId=customer.getRegionId();
			if (rId==null){
				info.setSuccess(false);
				info.setMessage("请填写客户冠有省、市、县/区三级区划的详细地址");
				return info;
			}
		}
		
		
		// 生成密码
		String password = customer.getPassword();
		if(password==null){//如果密码为空，直接以账号名称作为密码，以后给客户发账号密码邮件时，系统要知道密码
			customer.setPassword(username);
		}
		Map<String, String> passwordPair = authenticationService
				.generatePassowrdEncoded(customer.getPassword());//如果getPassword为空，则随机生成
		customer.setPassword(passwordPair.get("passwordEncoded"));
		customer.setPasswordSrc(passwordPair.get("password"));
		customer.setUserType(20);//平台普通用户
		customerMapper.insertSelective(customer);
		//绑定客户和代理所
//		Customer customer4 = customerMapper.selectByEmail(customer.getEmail());
		
		Integer custId= customer.getId();
		customerMapper.bindAgency(agencyId, custId);
		if(userId!=null){
			userService.bindCustomer(userId, custId);//用户绑定客户
		}
		//	创建基础折扣	   	
		Discount discount= new Discount();
	   	discount.setAgencyId(agencyId.toString());
	   	discount.setCustId(custId);
	   	discount.setValue(BigDecimal.valueOf(value));
	   	info = discountService.createDiscountByValue(discount, null);
	   	
	   	//创建客户的默认联系人
	   	CustomerContact customerContact = new CustomerContact();
	   	customerContact.setCustId(custId);
	   	customerContact.setEmail(email);
	   	customerContact.setName(customer.getFullname());
	   	customerContact.setAddress(customer.getAddress());
	   	customerContact.setTel(customer.getPhone());
	   	info=customerContactService.ceateCustomerContact(customerContact);
	   	
	   	//创建代理所的针对该客户的联系人
		AgencyContact agencyContact=new AgencyContact();
		agencyContact.setAgencyId(agencyId);
		agencyContact.setCustId(custId);
		Integer uId=new Integer(agencyContactId);
		agencyContact.setUserId(uId);
		info=agencyContactService.createAgencyContact(agencyContact);
	   	
		//创建邮件提醒默认设置
		info=mailRemindSettingsService.createExistCustDefaultSettings(custId);
		
	   	if(!info.getSuccess()) return info;
	   	
		info.setSuccess(true);
		info.setData(customer);
		info.setMessage("添加客户成功");
		return info ;
	}

	@Override
	public ReturnInfo checkUsername(String username) {
		ReturnInfo info = new ReturnInfo();
		Map<String, Boolean> data = new HashMap<String, Boolean>();
		if(username==null||"".equals(username)){
			info.setSuccess(false);
			info.setMessage("用户名不能为空");
			return info;
		}
		Customer customer = customerMapper.selectByUsername(username);
		if(customer!=null){
			info.setSuccess(true);
			info.setMessage("该用户名已存在");
			data.put("isExists", true);
			info.setData(data);
			return info;
		}
		info.setSuccess(true);
		info.setMessage("该用户名不存在");
		data.put("isExists", false);
		info.setData(data);
		return info ;
	}

	@Override
	public ReturnInfo checkCompanyName(String name) {
		ReturnInfo info = new ReturnInfo();
		Map<String, Boolean> data = new HashMap<String, Boolean>();
		if(name==null||"".equals(name)){
			info.setSuccess(false);
			info.setMessage("客户名不能为空");
			return info;
		}
		Customer customer = customerMapper.selectByCompanyName(name);
		if(customer!=null){
			info.setSuccess(true);
			info.setMessage("该客户已存在");
			data.put("isExists", true);
			info.setData(data);
			return info;
		}
		info.setSuccess(true);
		info.setMessage("该客户不存在");
		data.put("isExists", false);
		info.setData(data);
		return info ;
	}

	@Override
	public ReturnInfo bindAgency( Integer agencyId,Integer custId, Integer agencyContactId) {
		ReturnInfo info = new ReturnInfo();
		if(custId==null || agencyId==null || agencyContactId==null){
			info.setMessageType(-4);
			info.setMessage("参数为空");
			info.setSuccess(false);
			return info;
		}
		customerMapper.unbindAgency(agencyId, custId);
		customerMapper.bindAgency(agencyId, custId);
		
	 	//创建代理所的针对该客户的联系人
		AgencyContact agencyContact=new AgencyContact();
		agencyContact.setAgencyId(agencyId);
		agencyContact.setCustId(custId);
		Integer uId=new Integer(agencyContactId);
		agencyContact.setUserId(uId);
		info=agencyContactService.createAgencyContact(agencyContact);
		
		info.setMessage("绑定成功");
		info.setSuccess(true);
		return info;
	}

	@Override
	public ReturnInfo unbindAgency(Integer custId, Integer agencyId) {
		ReturnInfo info = new ReturnInfo();
		if(custId==null||agencyId==null){
			info.setMessageType(-4);
			info.setMessage("参数为空");
			info.setSuccess(false);
			return info;
		}
		customerMapper.unbindAgency(agencyId, custId);
		info.setMessage("解绑成功");
		info.setSuccess(true);
		return info;
	}

	@Override
	public ReturnInfo queryCustomer(Customer customer,User caller,GeneralCondition gcon) {
		ReturnInfo info = new ReturnInfo();
		Integer agencyId = null;
		if(caller==null||caller.getUserType()==null){
			info.setSuccess(false);
			info.setMessage("查询失败");
			return info;
		}
		if(caller.getUserType()!=1){//不是平台管理员
			agencyId= agencyUserMapper.selectAgencyIdByUserId(caller.getUserId());
			if(agencyId==null){
				info.setSuccess(false);
				info.setMessage("查询失败");
				return info;
			}
		}
		List<Customer> customers = customerMapper.selectByCustomer(customer, gcon,agencyId);
		//页数
		List<Map<String,Long>> counts = customerMapper.selectByCustomerCount(customer, gcon,agencyId);
		Long total = 0L;
		for (Map<String,Long> onecount:counts) {
			total+= onecount.get("count");
		} 
		info.setData(customers);
		info.setTotal(total);
		info.setCurrPage(gcon.getPageNo());
		info.setSuccess(true);
		info.setMessage("查询客户成功");
		return info ;
	}
	@Override
	public ReturnInfo queryCustomerUser(Integer agencyId,GeneralCondition gcon) {
		ReturnInfo info = new ReturnInfo();
		
		List<Customer> customers = customerMapper.selectCustUserByAgencyId(agencyId, gcon);	
		//页数
		Long counts = customerMapper.selectCustUserCountByAgencyId(agencyId, gcon);
		info.setData(customers);
		info.setTotal(counts);
		info.setCurrPage(gcon.getPageNo());
		info.setSuccess(true);
		info.setMessage("查询用户成功");
		return info ;
	}
	@Override
	public ReturnInfo queryOwnCustomer(Customer customer, User caller,
			GeneralCondition gcon,Integer level) {
		ReturnInfo info = new ReturnInfo();
//		Integer agencyId = agencyUserMapper.selectAgencyIdByUserId(caller.getUserId());
		List<Customer> customers = customerMapper.selectByOwnCustomer(customer, gcon,caller.getUserId(),level);
		//页数
		List<Map<String,Long>> counts = customerMapper.selectByOwnCustomerCount(customer, gcon,caller.getUserId(),level);
		Long total = 0L;
		for (Map<String,Long> onecount:counts) {
			total+= onecount.get("count");
		} 
		info.setData(customers);
		info.setTotal(total);
		info.setCurrPage(gcon.getPageNo());
		info.setSuccess(true);
		info.setMessage("查询用户成功");
		return info ;
	}
	@Override
	public ReturnInfo modifyCustomerSelf(Customer customer) {
		ReturnInfo info = new ReturnInfo();
		Customer customer2 = customerMapper.selectByPrimaryKey(customer.getId());
		if(customer2==null){
			info.setSuccess(false);
			info.setMessageType(-4);
			info.setMessage("客户Id不正确");
			return info;
		}
		if (customer2.getUsername()!=null && customer.getUsername()!=null
				&& !customer2.getUsername().equals(customer.getUsername())) { 
			info.setSuccess(false);
			info.setMessageType(-4);
			info.setMessage("不能更改账户名");
			return info;
		}
		//修改密码
		if(customer.getPassword()!=null&& !"".equals(customer.getPassword())){
			Map<String, String> passwordPair = authenticationService.generatePassowrdEncoded(customer.getPassword());
			customer.setPassword(passwordPair.get("passwordEncoded"));
		}
		customerMapper.updateByPrimaryKeySelective(customer);
		
		info.setMessage("修改成功");
		info.setSuccess(true);
		return info;
	}
	@Override
	public ReturnInfo modifyCustomerLocked(Integer custId, Integer locked) {
		ReturnInfo info = new ReturnInfo();
		if(custId==null||locked==null){
			info.setSuccess(false);
			info.setMessageType(-4);
			info.setMessage("参数不正确");
			return info;
		}
		Customer record = new Customer();
		record.setId(custId);
		record.setLocked(locked);
		customerMapper.updateByPrimaryKeySelective(record);
		info.setMessage("修改成功");
		info.setSuccess(true);
		return info;
	}
	@Override
	public ReturnInfo uploadUserIcon(HttpServletRequest request, String username) {
		ReturnInfo info=new ReturnInfo();
		String message = null;
		Map<String, String> customerIcons= new HashMap<String, String>();
		try {
			customerIcons = UploadUtil.uploadUserIcon(request,
					username);
		} catch (Exception e) {
			info.setSuccess(false);
			info.setMessageType(-4);
			info.setMessage("修改用户异常：" + e.getMessage());
			return info;
		}
		if (customerIcons==null || customerIcons.size()==0){
			message ="图片文件没有上传成功";
			info.setSuccess(false);
			info.setMessageType(-4);
			info.setMessage(message);
			return info;
		}
		Iterator<Entry<String, String>> iter = customerIcons.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>) iter
					.next();

			username = entry.getKey();
			String usericon = entry.getValue();

			Customer customer = customerMapper.selectByUsername(username);
			customer.setUserIcon(usericon);
			customerMapper.updateByPrimaryKeySelective(customer);
			break;
		}
		info.setSuccess(true);
		info.setMessage("上传成功");
		return info;
	}

	@Override
	public Customer selectByUsername(String username) {
		if(username==null||"".equals(username)){
			return null;
		}
		return customerMapper.selectByUsername(username);
	}
	@Override
	public Customer getCustById(Integer id) {
		if(id==null){
			return null;
		}
		return customerMapper.selectByPrimaryKey(id);
	}
	@Override
	public ReturnInfo getCustDetailById(Integer id) {
		ReturnInfo info = new ReturnInfo();
		if(id==null){
			info.setSuccess(false);
			info.setMessage("查询失败");
			return info;
		}
		Customer customer = customerMapper.selectByPrimaryKey(id);
		info.setSuccess(true);
		info.setMessage("查询成功");
		info.setData(customer);
		return info;
	}
	@Override
	public ReturnInfo bindRole(Integer customerId, Integer roleId) {
		ReturnInfo info = new ReturnInfo();
		if(customerId==null||roleId==null){
			info.setMessageType(-4);
			info.setMessage("参数为空");
			info.setSuccess(false);
			return info;
		}
		customerMapper.unbindRole(customerId, roleId);
		customerMapper.bindRole(customerId, roleId);
		info.setMessage("绑定成功");
		info.setSuccess(true);
		return info;
	}

	@Override
	public ReturnInfo unbindRole(Integer customerId, Integer roleId) {
		ReturnInfo info = new ReturnInfo();
		if(customerId==null||roleId==null){
			info.setMessageType(-4);
			info.setMessage("参数为空");
			info.setSuccess(false);
			return info;
		}
		customerMapper.unbindRole(customerId, roleId);
		
		info.setMessage("绑定成功");
		info.setSuccess(true);
		return info;
	}
	
	@Override
	public ReturnInfo bindApplicant(Integer custId, Integer appId) {
		ReturnInfo info = new ReturnInfo();
		try{
			if(custId==null||appId==null){
				info.setMessageType(-4);
				info.setMessage("参数为空");
				info.setSuccess(false);
				return info;
			}
			
			CustomerApplicant c =new CustomerApplicant();
			c.setAppId(appId);
			c.setCustId(custId);			
//			customerApplicantMapper.unbindApplicant(c);
			customerApplicantMapper.bindApplicant(c);
			
			info.setMessage("客户与申请人绑定成功");
			info.setSuccess(true);
		}catch(Exception e){			
			info.setSuccess(false);
			info.setMessage("客户与申请人绑定失败");
		}
		
		return info;
	}

	@Override
	public ReturnInfo unBindApplicant(Integer custId, Integer appId) {
		ReturnInfo info = new ReturnInfo();
		if(custId==null||appId==null){
			info.setMessageType(-4);
			info.setMessage("参数为空");
			info.setSuccess(false);
			return info;
		}
		CustomerApplicant c =new CustomerApplicant();
		c.setAppId(appId);
		c.setCustId(custId);
		customerApplicantMapper.unbindApplicant(c);
		info.setMessage("解绑成功");
		info.setSuccess(true);
		return info;
	}

	@Override
	public boolean hasRoleByCustomer(String roleName, Customer customer) {
		Set<String> roles = roleMapper.selectRoleByCustomerId(customer.getId());
		boolean b = roles.contains(roleName);
		return b;
	}

	@Override
	public boolean hasPermissionByCustomer(String permission1, Customer customer) {
		/*
		 * 例如 ： 查看用户是否有  role:create  这个权限
		 * 		用户有 role 或者  role:create 都返回 true  
		 */
			List<String> permissions = permissionMapper.selectPermissionByCustomerId(customer.getId());
			
			String[] split = permission1.split(":");
			String p=split[0];
			if(split.length == 1) {
				return permissions.contains(p);
			}
			boolean hasPermission = false;
			for(int i=0; i < split.length; i++) {
				
				if(i==0) {
					if( permissions.contains(p)) {
						hasPermission=true;
						return hasPermission;
					}else {
						i++;
					}			
				}
				
				 p = p+":"+split[i];
				 hasPermission = permissions.contains(p);
				if(hasPermission) {
					return hasPermission;
				}else {
					i++;
				}
						
			}
			return hasPermission;
	}

	@Override
	public ReturnInfo queryAgencyCustomerListByPermission(Permission permission, GeneralCondition gcon) {
		ReturnInfo info = new ReturnInfo();
		List<Customer> users = customerMapper.selectCustomerByPermission(permission,gcon);
		//页数
		Long counts = customerMapper.selectCustomerByPermissionCount(permission, gcon);
		info.setTotal(counts);
		info.setCurrPage(gcon.getPageNo());
		info.setData(users);
		info.setMessage("查询成功");
		info.setSuccess(true);
		return info;
	}

	@Override
	public ReturnInfo deleteCustomer(Integer custId) {
		ReturnInfo info = new ReturnInfo();
		if(custId==null){
			info.setMessage("客户Id不能为空");
			info.setSuccess(false);
			return info;
		}
		customerMapper.deleteByPrimaryKey(custId);
		customerMapper.unbindAllAgency(custId);
		customerMapper.unbindAllRole(custId);
		customerMapper.unbindAllUser(custId);
		info.setSuccess(true);
		info.setMessage("删除成功");
		return info;
	}

	@Override
	public List<Customer> queryAllCustomer() {
		List<Customer> customers = customerMapper.selectAllCustomer();
		return customers;
	}

	@Override
	public ReturnInfo sendAccountEmail(Integer custId) {
		ReturnInfo info = new ReturnInfo();
		if(custId==null){
			info.setSuccess(false);
			info.setMessage("customer id can not be null");
			return info;
		}
		Customer customer = customerMapper.selectByPrimaryKey(custId);
		// 设置邮件参数
		String mailSubject = "BDY Registration Notice";
		String mailContent = "<p>Your account information is as follows:</p>"
				+ "<p>Username: "+customer.getUsername()+"</p>"
				+ "<p>Password: "+customer.getUsername()+"</p>"
				+ "<p>Access address: http://47.105.166.130</p>";
		String mailTo = customer.getEmail();
		boolean validate = true;
		mailSenderInfo.setSubject(mailSubject);
		mailSenderInfo.setContent(mailContent);
		mailSenderInfo.setToAddress(mailTo);
		mailSenderInfo.setValidate(validate);

		// 发送邮件
		SendMailUtil sendMailUtil=new SendMailUtil();
		try {
			sendMailUtil.sendmail(mailSenderInfo);
		} catch (Exception e) {
			info.setSuccess(false);
			info.setMessage("Sending Mail Failure:"+e.getMessage());
			return info;
		}	
		info.setSuccess(true);
		info.setMessage("Send Mail Success");
		return info;
	}

	@Override
	public ReturnInfo changeCustomerAgentUser(String customerId, String beUserId, String afUserId) {
		ReturnInfo rtnInfo = new ReturnInfo();
		try {

			String url=serviceUrlConfig.getProcessServiceUrl()+"/task/changecustomeragentuser?customerId="+ customerId+"&beUserId="+ beUserId+"&afUserId="+ afUserId;
			String jsonString;

			jsonString = GraspUtil.getText(url);
			rtnInfo=JsonUtil.toObject(jsonString, ReturnInfo.class);
			if(!rtnInfo.getSuccess()) {
				throw new Exception(rtnInfo.getMessage());
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rtnInfo.setSuccess(false);
			rtnInfo.setMessage(e.getMessage());
			rtnInfo.setMessageType(-1);
		} 
		return rtnInfo;
	}

	@Override
	public ReturnInfo addCustomerAgentUser(String customerId, String userIds, String tokenID) {
		ReturnInfo rtnInfo = new ReturnInfo();
		try {

			String url=serviceUrlConfig.getProcessServiceUrl()+"/task/addcustomeragentuser?customerId="+ customerId+"&userIds="+ userIds +"&tokenID="+tokenID;
			String jsonString;

			jsonString = GraspUtil.getText(url);
			rtnInfo=JsonUtil.toObject(jsonString, ReturnInfo.class);
			if(!rtnInfo.getSuccess()) {
				throw new Exception(rtnInfo.getMessage());
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rtnInfo.setSuccess(false);
			rtnInfo.setMessage(e.getMessage());
			rtnInfo.setMessageType(-1);
		} 
		return rtnInfo;
	}
	
	
	
	public boolean checkBindCust(Integer custId, Integer appId) {		
		boolean hasSameOne=false;
		List<CustomerApplicant> list = customerApplicantMapper.selectbyAppId(appId);			
		if(list !=null) {
			for(CustomerApplicant ca:list) {
				Integer cId=ca.getCustId();
				if (cId!=null && cId.intValue()==custId.intValue()){
					hasSameOne=true;
					break;
				}
			}
		}
		return hasSameOne;
	}
	
	
	@Override
	public ReturnInfo queryCustomerRegion(User caller) {
		ReturnInfo info = new ReturnInfo();
		Integer agencyId = null;
		if(caller==null||caller.getUserType()==null){
			info.setSuccess(false);
			info.setMessage("查询失败");
			return info;
		}
		if(caller.getUserType()!=1){//不是平台管理员
			agencyId= agencyUserMapper.selectAgencyIdByUserId(caller.getUserId());
			if(agencyId==null){
				info.setSuccess(false);
				info.setMessage("查询失败");
				return info;
			}
		}
		List<Customer> customers = customerMapper.selectCustomerRegion(agencyId);
		List<Integer> ids=new ArrayList<Integer>();
		boolean hasGuoWai=false;
		for(Customer customer: customers){
			Integer regionId=customer.getRegionId();
			String country=customer.getCountry();
			if (country!=null && !country.equals("中国")){
				hasGuoWai=true;
				continue;
			}
			if (regionId!=null){
				if (!ids.contains(regionId)){
					ids.add(regionId);
				}
			}			
		}
		
		List<Regions> regionList=regionMapper.selectRegions();
		

		findSameOne(regionList, ids);
		
		if (hasGuoWai){
			Regions regions=new Regions();
			regions.setRegionName("国外");
			regions.setRegionId(987654321);
			regionList.add(0, regions);
		}
		
		info.setData(regionList);
		
		info.setSuccess(true);
		info.setMessage("查询客户所在地区成功");
		return info ;
	}
	
	
	
	@Override
	public ReturnInfo queryCustomerOwnRegion( User caller,Integer level) {
		ReturnInfo info = new ReturnInfo();
//		Integer agencyId = agencyUserMapper.selectAgencyIdByUserId(caller.getUserId());
		List<Customer> customers = customerMapper.selectCustomerOwnRegion(caller.getUserId(),level);
		List<Integer> ids=new ArrayList<Integer>();
		boolean hasGuoWai=false;
		for(Customer customer: customers){
			Integer regionId=customer.getRegionId();
			String country=customer.getCountry();
			if (country!=null && !country.equals("中国")){
				hasGuoWai=true;
				continue;
			}
			if (regionId!=null){
				if (!ids.contains(regionId)){
					ids.add(regionId);
				}
			}			
		}
		
		List<Regions> regionList=regionMapper.selectRegions();
		

		findSameOne(regionList, ids);
		
		if (hasGuoWai){
			Regions regions=new Regions();
			regions.setRegionName("国外");
			regions.setRegionId(987654321);
			regionList.add(0, regions);
		}
		
		info.setData(regionList);
		
		info.setSuccess(true);
		info.setMessage("查询客户所在地区成功");
		return info ;
	}
	
	
	private boolean findSameOne(List<Regions>regionList, List<Integer> ids){
		boolean result=false;
		Iterator<Regions> it = regionList.iterator();
		while (it.hasNext()) {
			Regions regions=it.next();	
			Integer regionId=regions.getRegionId();			
			if (regionId==null){
				it.remove();				
			}	
			boolean hasSameOne=false;
			for(Integer id: ids){
				if (regionId.intValue()==id.intValue()){
					hasSameOne=true;
					result=true;
					break;
				}
			}
			List<Regions>regionList2=regions.getCitys();
			if (regionList2!=null && regionList2.size()>0){					
				boolean find=findSameOne(regionList2, ids);
				//如果下一级的地区与客户集合中的regionId不同，那么删除当前区域
				if (!find){
					it.remove();
				}
			}else{
				if (!hasSameOne){
					//如果下一级地区为空，并且当前级别地区与客户集合中的regionId不同，那么删除当前区域
					it.remove();
				}
			}
		}
		return result;
	}
	
	
	protected void processRegion(Customer customer,List<Region> regionList){
		
		if (regionList==null || regionList.size()==0){
			return ;
		}
				
		String address=customer.getAddress();
		
		String custName=customer.getName();
		
		if (address==null || address.equals("")){
			if (custName==null || custName.equals("")){
				return ;
			}else{
				//如果客户的地址为空，那么尝试用客户名称作为地址去匹配相应的行政区划
				address=custName;
			}
			
		}
		
		int maxLevel=-1;
		for(Region region:regionList){
			String regionName=region.getRegionName();
			if (regionName==null || regionName.equals("")){
				continue;
			}
			
			Integer regionId=region.getRegionId();
			Integer level=region.getLevel();
			
			int currentLevel=0;
			boolean find=false;
			if (level!=null){
				currentLevel=level.intValue();
			}
			
			String shortName=null;
				
			for(String key: quhua){
				int pos=regionName.indexOf(key);
				if(pos>-1){
					if (regionName.length()>2){
						if (regionName.indexOf("西双版纳")>-1){
							shortName="西双版纳";
						}else{
							shortName=regionName.substring(0, 2);
						}
						break;
					}
				}
			}
			
			
			if (shortName!=null && address.indexOf(shortName)>-1){				
				find=true;		
			}
			else if (address.indexOf(regionName)>-1){
				find=true;
			}
			
			if(find){
				if (currentLevel>maxLevel){
					maxLevel=currentLevel;
					customer.setRegionId(regionId);
					if (maxLevel>1){
						break;
					}
				}
			}
		}		
		
	}
}
