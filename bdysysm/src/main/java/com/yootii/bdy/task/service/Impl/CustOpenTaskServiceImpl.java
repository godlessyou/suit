package com.yootii.bdy.task.service.Impl;

import java.io.InputStream;
import java.math.BigDecimal;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;

import com.yootii.bdy.agency.dao.AgencyContactMapper;
import com.yootii.bdy.agency.dao.AgencyUserMapper;
import com.yootii.bdy.agency.model.Agency;
import com.yootii.bdy.agency.model.AgencyContact;
import com.yootii.bdy.agency.model.AgencyUser;
import com.yootii.bdy.agency.model.CooperationAgency;
import com.yootii.bdy.agency.service.AgencyService;
import com.yootii.bdy.bill.model.Discount;
import com.yootii.bdy.bill.service.DiscountService;
import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.common.Globals;
import com.yootii.bdy.common.LoginReturnInfo;
import com.yootii.bdy.common.ReturnInfo;
import com.yootii.bdy.customer.dao.CustomerMapper;
import com.yootii.bdy.customer.model.Customer;
import com.yootii.bdy.customer.service.CustomerService;
import com.yootii.bdy.invite.model.AgencyInvite;
import com.yootii.bdy.invite.model.CustomerInvite;
import com.yootii.bdy.invite.service.AgencyInviteService;
import com.yootii.bdy.invite.service.CustomerInviteService;
import com.yootii.bdy.mailremind.service.MailRemindSettingsService;
import com.yootii.bdy.process.service.ProcessService;
import com.yootii.bdy.security.service.AuthenticationService;
import com.yootii.bdy.task.service.CustOpenTaskService;
import com.yootii.bdy.taskremind.model.TaskRemind;
import com.yootii.bdy.taskremind.service.TaskRemindService;
import com.yootii.bdy.user.model.User;
import com.yootii.bdy.user.service.UserService;
import com.yootii.bdy.util.GraspUtil;
import com.yootii.bdy.util.JsonUtil;

@Service
public class CustOpenTaskServiceImpl implements CustOpenTaskService {
	
	private final Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private ProcessService processService;
	
	@Autowired
	private CustomerInviteService customerInviteService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private DiscountService discountService;
	
	@Autowired
	private UserService userService;
	@Resource
	public CustomerMapper customerMapper;
	@Resource
	private AuthenticationService authenticationService;

	@Autowired
	private TaskRemindService taskRemindService;
	
	@Resource
	private AgencyContactMapper agencyContactMapper;
	@Resource
	private AgencyUserMapper agencyUserMapper;
	@Resource
	private MailRemindSettingsService mailRemindSettingsService;
	@Resource
	private StandardPasswordEncoder passwordEncoder;
	
	public StandardPasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
	}

	public void setPasswordEncoder(StandardPasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}
	
	@Override
	public ReturnInfo startProCustOpen(HttpServletRequest request, User user, CustomerInvite customerInvite, Customer customer, String agencyContactId) {
		ReturnInfo res = new ReturnInfo();
		String email = customerInvite.getEmail();
		if(agencyContactId==null||agencyContactId.equals("")){
			res.setSuccess(false);
			res.setMessage("代理所联系人Id不能为空");
			res.setMessageType(Globals.MESSAGE_TYPE_PARAM_INVALID);
			return res;
		}
		if(email==null||"".equals(email)){
			res.setSuccess(false);
			res.setMessage("邮箱不能为空");
			return res;
		}
		String name = customerInvite.getName();
		if(name==null||"".equals(name)){
			res.setSuccess(false);
			res.setMessage("企业名称不能为空");
			return res;
		}
		Integer agencyId = customerInvite.getAgencyId();
		if(agencyId==null||"".equals(agencyId)){
			res.setSuccess(false);
			res.setMessage("代理所Id不能为空");
			return res;
		}
		String address = customer.getAddress();
		if(address==null||"".equals(address)){
			res.setSuccess(false);
			res.setMessage("地址不能为空");
			return res;
		}
		String fullname = customer.getFullname();
		if(fullname==null||"".equals(fullname)){
			res.setSuccess(false);
			res.setMessage("全称不能为空");
			return res;
		}
		String country = customer.getCountry();
		if(country==null||"".equals(country)){
			res.setSuccess(false);
			res.setMessage("国家不能为空");
			return res;
		}
		String sex = customer.getSex();
		if(sex==null||"".equals(sex)){
			res.setSuccess(false);
			res.setMessage("性别不能为空");
			return res;
		}
		String phone = customer.getPhone();
		if(phone==null||"".equals(phone)){
			res.setSuccess(false);
			res.setMessage("电话不能为空");
			return res;
		}
		String proKey = "cust_open";
		String permission = "客户开通:启动";
		String nextpermission = "客户开通:资质审核";
		try {
			Map<String, Object> runMap = new HashMap<String, Object>();

			
			ReturnInfo checkret = processService.checkuserstart(permission, user.getUserId().toString());

			if(!checkret.getSuccess()) throw new Exception(checkret.getMessage());
			//添加流程参数			
			runMap.put("contactId", agencyContactId);
			runMap.put("CustName", name);
			runMap.put("Email", email);
			runMap.put("agencyId", agencyId);
			runMap.put("UserId", user.getUserId());
			runMap.put("Address", address);
			runMap.put("Fullname", fullname);
			runMap.put("Country", country);
			runMap.put("Sex", sex);
			runMap.put("Phone", phone);
			runMap.put("permission", nextpermission);
			//添加邀请列表
			ReturnInfo r2 = customerInviteService.addCustomerInvite(customerInvite);
			if(r2.getSuccess()!=true) {
				throw new Exception("数据表添加失败|"+r2.getMessage());
			}
			customerInvite.setId(Integer.valueOf(((Map<String, Object>)r2.getData()).get("id").toString()));
			Integer customerInviteId = customerInvite.getId();
			runMap.put("CustomerInviteId", customerInviteId);
			//启动流程
			res = processService.startProcessByKey(proKey, runMap);
			if(!res.getSuccess()) {
				throw new Exception("流程启动失败|"+res.getMessage());
			}
			Map<String, Object> resData = (Map<String, Object>) res.getData();
			List<Map<String,Object>> tasklistret = (List<Map<String, Object>>) resData.get("taskList");
			String taskid = tasklistret.get(0).get("taskId").toString();
			
			
			customerInvite.setProcessInstanceId(Integer.valueOf(resData.get("ProcessInstanceId").toString()));
			customerInvite.setStatus("审核中");
			customerInviteService.modifyCustomerInviteStatus(customerInvite);
//			

			TaskRemind taskRemind = new TaskRemind(); 
			taskRemind.setAgencyid(customerInvite.getAgencyId());
			taskRemind.setType(2);
			taskRemind.setMessage("请审核关于新客户\""+ customerInvite.getName()+"\"的资质");
			taskRemind.setTitle("资质审核");
			taskRemind.setTaskid(taskid);
			taskRemindService.insertTaskRemind(taskRemind);
			
		} catch (Exception e) {
			e.printStackTrace();
			res.setSuccess(false);
			res.setMessage(e.getMessage());
			return res;
		}

		return res;
	}

	//校验调用者是否合法
	@Override
	public ReturnInfo validCaller(String username, String password) {
        ReturnInfo returnInfo = new ReturnInfo();
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		GeneralCondition gcon = new GeneralCondition();
		//校验密码
        user = authenticationService.authentication(user, gcon, true);
        if (user == null) {
			returnInfo.setSuccess(false);
			returnInfo.setMessage("调用者的用户名或者密码不正确！");
			returnInfo.setMessageType(Globals.MESSAGE_TYPE_AUTHORTY_INVALID);
			return returnInfo;
		} 
        returnInfo.setSuccess(true);
      
		return returnInfo;
	}

	
	@Override
	public ReturnInfo addCustomer(String username, String email,
			String password, String phone)throws Exception{
		ReturnInfo returnInfo = new ReturnInfo();
		Map<String, Object> map = new HashMap<>();
		//判断customer表中是否username 重复，如果有，返回用户名已存在
		int exist = agencyContactMapper.isExist(username);
		if(exist !=0){
			returnInfo.setMessage("同名账户已存在！");
			returnInfo.setSuccess(false);
			return returnInfo;
		}
		//对password  进行加密
		Map<String, String> passwordPair = authenticationService.generatePassowrdEncoded(password);
		password = passwordPair.get("passwordEncoded");
		map.put("username", username);
		map.put("email", email);
		map.put("password", password);
		map.put("phone", phone);
		agencyContactMapper.addCustomer(map);
		returnInfo.setSuccess(true);
		returnInfo.setMessage("添加成功");
		return returnInfo;
	}
	
	@Override
	public ReturnInfo updateCustomer(Customer customer)throws Exception{
		ReturnInfo returnInfo = new ReturnInfo();
		Map<String, Object> map = new HashMap<>();
		//custId
		Integer id = customer.getId();
		if(id==null){
			returnInfo.setSuccess(false);
			returnInfo.setMessage("id 不能为空");
			return returnInfo;
		}
		String name= customer.getName();
		if(name ==""|| name==null){
			returnInfo.setSuccess(false);
			returnInfo.setMessage("企业名称不能为空");
			return returnInfo;
		}
		String address = customer.getAddress();
		if(address=="" || address==null){
			returnInfo.setSuccess(false);
			returnInfo.setMessage("地址不能为空");
			return returnInfo;
		}
		String country = customer.getAddress();
		if(country == "" || country==null){
			returnInfo.setSuccess(false);
			returnInfo.setMessage("国家不能为空");
			return returnInfo;
		}
		String fullname = customer.getFullname();
		if(fullname =="" || fullname == null){
			
		}
		//完善该客户信息
		agencyContactMapper.updateCustomer(customer);
		//绑定客户和代理所（默认选择万慧达这个代理所，对应的agencyId为1）   agency_customer 表
		customerMapper.bindAgency(1,id);
		
		//创建客户的基础折扣	 
		Discount discount = new Discount();
		discount.setAgencyId("1");
		discount.setCustId(id);
		discount.setValue(BigDecimal.valueOf(1));
		GeneralCondition gcon = new GeneralCondition();
		discountService.createDiscountByValue(discount, gcon);
		
		//创建客户的默认联系人（就是该客户自己）对应  customer_contact
		Map<String, Object> custom = agencyContactMapper.queryCustomerById(id);
		if(custom !=null){
			// 同步  customer_contact表
			int count = agencyContactMapper.checkExist(id);
			if(count == 0){
				agencyContactMapper.insertCustomerContact(custom);
			}
		}
		
		
		//创建代理所的针对该客户的联系人,查询出默认的代理所下的员工,挑选一位 成为联系人  对应agency_contact 表
		int agencyId = 1;
		List<Map<String, Object>>proxy = agencyContactMapper.queryProxy(agencyId);	
		
		if(proxy.size()!=0){
			Map<String, Object> contact = proxy.get(1);
			Map<String,Object> agencyContact = new HashMap<>();
			Integer userId = (Integer)contact.get("userId");
			Integer agenId = 1;
			Integer custId = id;
			agencyContact.put("agencyId", agenId);
			agencyContact.put("userId",userId);
			agencyContact.put("custId",custId);
			AgencyContact agencyContact2 = new AgencyContact();
			agencyContact2.setAgencyId(agenId);
			agencyContact2.setUserId(userId);
			agencyContact2.setCustId(custId);
			AgencyContact checkResult = agencyContactMapper.checkAgencyContact(agencyContact2);
			//检查该客户是否已经设置该联系人  插入agency_contact表
			if(checkResult==null){
				agencyContactMapper.insertAgencyContact(agencyContact);
			}
		}
		 
		//创建邮件提醒默认设置
		mailRemindSettingsService.createExistCustDefaultSettings(id);
		returnInfo.setSuccess(true);
		returnInfo.setMessage("更新成功");
		return returnInfo;
	}
	
	
	
	
//	@Override
	public ReturnInfo deleteProCustOpen(HttpServletRequest request,CustomerInvite customerInvite) {
		ReturnInfo res = new ReturnInfo();
		try {
			
			if(customerInvite.getId() == null) throw new Exception("参数错误");
			
			String processInstanceId = customerInviteService.queryCustomerInviteDetail(customerInvite.getId()).getProcessInstanceId().toString();
			customerInviteService.deleteCustomerInvite(customerInvite);
			//删除流程
			res = processService.deleteProcess(processInstanceId);
			if(!res.getSuccess()) {
				throw new Exception("流程启动失败|"+res.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			res.setSuccess(false);
			res.setMessage(e.getMessage());
			return res;
		}

		return res;
	}
	
	public ReturnInfo saveAndSendEmail(HttpServletRequest request,CustomerInvite customerInvite) {
		ReturnInfo res = new ReturnInfo();
		try {
			
			customerInviteService.modifyCustomerInviteEMail(customerInvite);
			
			CustomerInvite customerInvitenew = customerInviteService.queryCustomerInviteDetail(customerInvite.getId());
			SendEmail(request, customerInvite.getId(), customerInvitenew.getProcessInstanceId().toString(),null,null);
			res.setMessage("发送成功");
			res.setSuccess(true);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			res.setSuccess(false);
			res.setMessage(e.getMessage());
			return res;
		}

		return res;
	}
	
	@Override
	public ReturnInfo doTaskCOReview(HttpServletRequest request,Integer trId,User user,Boolean review) {
		ReturnInfo res =new ReturnInfo();
		String permission = "客户开通:资质审核";
		String nextpermission = "客户开通:折扣填写";
		try {
			
			TaskRemind taskremind = taskRemindService.selectTaskRemindById(trId);

			String taskId = taskremind.getTaskid(); 
			ReturnInfo checkret = processService.checkuserstart(permission, user.getUserId().toString());
			if(!checkret.getSuccess()) throw new Exception(checkret.getMessage());
			//获取参数
			Map<String, Object> runMap = new HashMap<String, Object>();
			runMap.put("audited", review);
			ReturnInfo r1 = processService.showtaskvariables(taskId);
		   	if(r1.getSuccess()!=true) {
		   		throw new Exception("参数获取失败|"+r1.getMessage());
		   	}
			Map<String, Object> proMap = (Map<String, Object>) r1.getData();

			Integer agencyId =Integer.valueOf(proMap.get("agencyId").toString());
			String name =proMap.get("CustName").toString();
			
			//做任务
			runMap.put("permission", nextpermission);
			res = processService.doTask(taskId, runMap);
			if(!res.getSuccess()) {
				throw new Exception("流程做任务失败|"+res.getMessage());
			}

		   	taskRemindService.deleteTaskRemind(trId);
			String processInstanceId = proMap.get("processInstanceId").toString();
//			
		   	String nexttaskid = (String) processService.checkNextTask(processInstanceId).getData();
			TaskRemind taskRemind = new TaskRemind(); 
			taskRemind.setAgencyid(agencyId);
			taskRemind.setType(3);
			taskRemind.setMessage("请填写关于新客户\""+ name+"\"的折扣");
			taskRemind.setTitle("折扣填写");
			taskRemind.setTaskid(nexttaskid);
			taskRemindService.insertTaskRemind(taskRemind);
			
			
		}
		catch(Exception e) {
			//关闭流程
			e.printStackTrace();
			res.setSuccess(false);
			res.setMessage(e.getMessage());
			return res;
		}
		return res;
	}
	@Override
	public ReturnInfo doTaskCOFinancial(HttpServletRequest request,Integer trId,User user,Double discount) {
		ReturnInfo res =new ReturnInfo();
		String permission = "客户开通:折扣填写";
		String nextpermission = "客户开通:创建客户";
		try {
			
			TaskRemind taskremind = taskRemindService.selectTaskRemindById(trId);
			String taskId = taskremind.getTaskid();
			ReturnInfo checkret = processService.checkuserstart(permission, user.getUserId().toString());
			if(!checkret.getSuccess()) throw new Exception(checkret.getMessage());
			//获取参数
			Map<String, Object> runMap = new HashMap<String, Object>();
			runMap.put("discount",discount);
			ReturnInfo r1 = processService.showtaskvariables(taskId);
		   	if(r1.getSuccess()!=true) {
		   		throw new Exception("参数获取失败|"+r1.getMessage());
		   	}
			Map<String, Object> proMap = (Map<String, Object>) r1.getData();//流程中的参数集合

//			//判断是否需要删除
//			if(customerInvite.getId()!=null) {
//				ReturnInfo r1_5 = CustomerInviteService.deleteCustomerInvite(customerInvite);
//				if(r1_5.getSuccess()!=true) {
//					throw new Exception("数据表删除失败|"+r1_5.getMessage());
//				}
//			}

//			Integer agencyId =Integer.valueOf(proMap.get("agencyId").toString());
//			String email =proMap.get("Email").toString();
//			String name =proMap.get("CustName").toString();
			String processInstanceId = proMap.get("processInstanceId").toString();

//			CustomerInvite customerInvite = new CustomerInvite();
//			customerInvite.setAgencyId(agencyId);
//			customerInvite.setEmail(email);
//			customerInvite.setName(name);
			//customerInvite.setProcessInstanceId(Integer.valueOf(processInstanceId));
			Customer customer = new Customer();
			Integer customerInviteId =Integer.valueOf(proMap.get("CustomerInviteId").toString());
			String email = proMap.get("Email").toString();
			Integer agencyId = Integer.valueOf(proMap.get("agencyId").toString());
			String address = proMap.get("Address").toString();
			String fullname = proMap.get("Fullname").toString();
			String country = proMap.get("Country").toString();
			String sex = proMap.get("Sex").toString();
			String phone = proMap.get("Phone").toString();
			String name = proMap.get("CustName").toString();
			String agencyContactId=proMap.get("contactId").toString();
			
			Integer agencyContactUserId=null;
	   		if (agencyContactId!=null && !agencyContactId.equals("")){
	   			agencyContactUserId=new Integer(agencyContactId);
	   		}
			
			
			customer.setEmail(email);
			customer.setAddress(address);
			customer.setFullname(fullname);
			customer.setCountry(country);
			customer.setSex(sex);
			customer.setPhone(phone);
			customer.setName(name);
			//与数据库中的数据进行对比
			Boolean flag = false;
			List<Customer> customers = customerService.queryAllCustomer();
			Customer tempCust = null;
			for(Customer customer2 : customers){
				//客户已创建账户
				String custName=customer2.getName();
				if(name.equals(custName)){
					tempCust = customer2;
					flag=true;
				}
			}
			//创建客户
		   	if (!flag) {
		   		String username = email.replaceAll("[@.]", "_");
				customer.setUsername(username);
		   		//新	
				logger.info("agencyId: " + agencyId);
				logger.info("CustName: " + name);
				CustomerInvite customerInvite=customerInviteService.queryCustomerInviteByAgencyIdAndName(agencyId, name);
				if (customerInvite!=null){
					customer.setEmail(customerInvite.getEmail());
				}else{
					logger.info("customerInvite is null");
				}
		   		GeneralCondition gcon = new GeneralCondition();		   		
		   		if (request!=null){
		   			String tokenID=request.getParameter("tokenID");
		   			gcon.setTokenID(tokenID);		
		   		}else{
		   			gcon =getGcon();
		   		}
		   		   				
		   		ReturnInfo r3_5 = customerService.createCustomer(customer,gcon, agencyId, agencyContactUserId, Double.valueOf(discount));
		   		if(r3_5.getSuccess()!=true) {
		   			throw new Exception("创建客户失败|"+r3_5.getMessage());
		   		}		   		
		   		
//		   		2018-5-28自动通知客户取消，代理人手动通知客户
//		   		SendEmail(request,customerInviteId,processInstanceId,username,((Customer)r3_5.getData()).getPasswordSrc());
		   	} else {
		   		//已有
		   		customerService.bindAgency(agencyId,tempCust.getId(),agencyContactUserId);
			   	Discount dis= new Discount();
			   	dis.setAgencyId(agencyId.toString());
			   	dis.setCustId(tempCust.getId());
			   	dis.setValue(BigDecimal.valueOf(discount));
			   	res =discountService.createDiscountByValue(dis, null);
			   	if(!res.getSuccess()) {
					throw new Exception("添加折扣失败|"+res.getMessage());
				}	
			   	
			   	//2018-08-16， 自动通知客户取消，修改为代理人手动通知客户
//			   	SendEmail(request,customerInviteId,processInstanceId,null,null);
		   	}
			
		   	taskRemindService.deleteTaskRemind(trId);
		   	
		   	//修改邀请列表
		   	CustomerInvite customerInvite = new CustomerInvite();
		   	customerInvite.setStatus("已开通");
		   	customerInvite.setId(Integer.valueOf(proMap.get("CustomerInviteId").toString()));
		   	ReturnInfo r4 = customerInviteService.modifyCustomerInviteStatus(customerInvite);
		   	if(r4.getSuccess()!=true) {
		   		throw new Exception("数据表修改失败|"+r4.getMessage());
		   	}
			//做任务
			runMap.put("permission", nextpermission);
			res = processService.doTask(taskId, runMap);
			if(!res.getSuccess()) {
				throw new Exception("流程做任务失败|"+res.getMessage());
			}
		}
		catch(Exception e) {
			//关闭流程
			e.printStackTrace();
			res.setSuccess(false);
			res.setMessage(e.getMessage());
			return res;
		}
		return res;
	}



	private void SendEmail(HttpServletRequest request, Integer customerInviteId, String processInstanceId,String username,String password) throws Exception {
		//发送邮件
		CustomerInvite customerInvite = customerInviteService.queryCustomerInviteDetail(customerInviteId);

		
		//发送邮件
		ReturnInfo r1 = customerInviteService.invateCustomer(request, customerInvite, processInstanceId,username,password);
		if(r1.getSuccess()!=true) {
			throw new Exception("邮件发送失败|"+r1.getMessage());
		}
		
		//修改邀请列表
		customerInvite.setStatus("已发送");
		ReturnInfo r2 = customerInviteService.modifyCustomerInviteStatus(customerInvite);
		if(r2.getSuccess()!=true) {
			throw new Exception("数据表修改失败|"+r2.getMessage());
		}
	}

	@Override
	public ReturnInfo proCustOpencheck(HttpServletRequest request, String proId, String sid) {
		ReturnInfo res =new ReturnInfo();
		Map<String, Object> runMap = new HashMap<String, Object>();
		try {
			//获取参数   
			ReturnInfo r1 = processService.showtaskvariablesbypro(proId);
		   	if(r1.getSuccess()!=true) {
		   		throw new Exception("参数获取失败|"+r1.getMessage());
		   	}
		   	
		   	Map<String, Object> proMap = (Map<String, Object>) r1.getData();
		   	//测试权限
		   	ReturnInfo r2 = customerInviteService.checkLinkOutdate(request, sid, proMap.get("CustName").toString(), Integer.valueOf(proMap.get("agencyId").toString()));
		   	if(r2.getSuccess()!=true) {
		   		throw new Exception("无效链接|"+r2.getMessage());
		   	}

		   	//
		   	Map<String, Object> resMap = new HashMap<String, Object>();
		   	res.setSuccess(true);
		   	resMap.put("CustName",proMap.get("CustName"));
		   	res.setData(resMap);
		}
		catch(Exception e) {
			e.printStackTrace();
			res.setSuccess(false);
			res.setMessage(e.getMessage());
			return res;
		}
		return res;
	}


	@Override
	public ReturnInfo doTaskCOWrite(HttpServletRequest request, String proId,Customer customer, Customer customer2, String sid) {
		ReturnInfo res =new ReturnInfo();
		try {
			//获取参数
			Map<String, Object> runMap = new HashMap<String, Object>();
			ReturnInfo r1 = processService.showtaskvariablesbypro(proId);
		   	if(r1.getSuccess()!=true) {
		   		throw new Exception("参数获取失败|"+r1.getMessage());
		   	}
			Map<String, Object> proMap = (Map<String, Object>) r1.getData();
			//测试权限
		   	ReturnInfo r2 = customerInviteService.checkLinkOutdate(request, sid, proMap.get("CustName").toString(), Integer.valueOf(proMap.get("agencyId").toString()));
		   	if(r2.getSuccess()!=true) {
		   		throw new Exception("无效链接|"+r2.getMessage());
		   	}

		   	String agencyContactId=proMap.get("contactId").toString();
			
			Integer agencyContactUserId=null;
	   		if (agencyContactId!=null && !agencyContactId.equals("")){
	   			agencyContactUserId=new Integer(agencyContactId);
	   		}
	   		
		   	//创建客户
		   	Integer agencyId = Integer.valueOf(proMap.get("agencyId").toString());
		   	if (customer2==null) {
		   		//新
		   		customer.setEmail(customerInviteService.queryCustomerInviteByAgencyIdAndName(Integer.valueOf(proMap.get("agencyId").toString()), proMap.get("CustName").toString()).getEmail());
		   		ReturnInfo r3_5 = customerService.createCustomer(customer,null, agencyId,agencyContactUserId,Double.valueOf(proMap.get("discount").toString()));
		   		if(r3_5.getSuccess()!=true) {
		   			throw new Exception("创建客户失败|"+r3_5.getMessage());
		   		}
		   	} else {
		   		//已有
		   		customerService.bindAgency(agencyId,customer2.getId(),agencyContactUserId);
			   	Discount discount= new Discount();
			   	discount.setAgencyId(agencyId.toString());
			   	discount.setCustId(customer2.getId());
			   	discount.setValue(BigDecimal.valueOf(Double.valueOf(proMap.get("discount").toString())));
			   	res =discountService.createDiscountByValue(discount, null);
			   	if(!res.getSuccess()) {
					throw new Exception("添加折扣失败失败|"+res.getMessage());
				}	
		   	}

			//修改邀请列表
			CustomerInvite customerInvite = new CustomerInvite();
			customerInvite.setStatus("已开通");
			customerInvite.setId(Integer.valueOf(proMap.get("CustomerInviteId").toString()));
			ReturnInfo r4 = customerInviteService.modifyCustomerInviteStatus(customerInvite);
			if(r4.getSuccess()!=true) {
				throw new Exception("数据表修改失败|"+r4.getMessage());
			}
			//做任务
			res = processService.doTaskByPro(proId, runMap);
			if(!res.getSuccess()) {
				throw new Exception("流程做任务失败|"+res.getMessage());
			}		
		}
		catch(Exception e) {
			//关闭流程
			e.printStackTrace();
			res.setSuccess(false);
			res.setMessage(e.getMessage());
			return res;
		}
		return res;
	}
	
	
	private String login(User user) {
		GeneralCondition gcon=new GeneralCondition();
		Object obj = authenticationService.login(user, gcon);	
		LoginReturnInfo rtnInfo = (LoginReturnInfo)obj;		
		String tokenID = rtnInfo.getTokenID();		
		return tokenID;
		
	}
	
	private  GeneralCondition getGcon(){
		GeneralCondition gcon=new GeneralCondition();
		
		gcon.setOffset(0);
		gcon.setRows(10);
		
		User user=new User();
		
		user.setUsername("whd_wangfang");
		user.setPassword("123456");		
		
	    String tokenID=login(user);	   
	    gcon.setTokenID(tokenID);			
	    return gcon;	
	}


	

	

	
}