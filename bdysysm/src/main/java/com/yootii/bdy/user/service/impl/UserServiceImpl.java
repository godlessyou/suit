package com.yootii.bdy.user.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.yootii.bdy.agency.dao.AgencyUserMapper;
import com.yootii.bdy.agency.model.Agency;
import com.yootii.bdy.agency.model.AgencyCustomer;
import com.yootii.bdy.agency.model.AgencyUser;
import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.common.Globals;
import com.yootii.bdy.common.ReturnInfo;
import com.yootii.bdy.customer.model.Customer;
import com.yootii.bdy.department.model.Department;
import com.yootii.bdy.mail.MailSenderInfo;
import com.yootii.bdy.mail.SendMailUtil;
import com.yootii.bdy.mailbox.service.MailBoxService;
import com.yootii.bdy.permission.dao.PermissionMapper;
import com.yootii.bdy.permission.model.Permission;
import com.yootii.bdy.role.dao.RoleMapper;
import com.yootii.bdy.role.model.Role;
import com.yootii.bdy.security.service.AuthenticationService;
import com.yootii.bdy.user.dao.UserCustomerMapper;
import com.yootii.bdy.user.dao.UserDepartmentMapper;
import com.yootii.bdy.user.dao.UserMapper;
import com.yootii.bdy.user.dao.UserRoleMapper;
import com.yootii.bdy.user.model.User;
import com.yootii.bdy.user.model.UserCustomer;
import com.yootii.bdy.user.model.UserDepartment;
import com.yootii.bdy.user.model.UserRole;
import com.yootii.bdy.user.service.UserService;
import com.yootii.bdy.util.AESUtil;
import com.yootii.bdy.util.GraspUtil;
import com.yootii.bdy.util.JsonUtil;
import com.yootii.bdy.util.ServiceUrlConfig;
import com.yootii.bdy.util.UploadUtil;



@Service
public class UserServiceImpl implements UserService {
	
	private final Logger logger = Logger.getLogger(this.getClass());
	@Resource
	private UserMapper userMapper;
	@Resource
	private RoleMapper roleMapper;
	@Resource
	private PermissionMapper permissionMapper;
	
	@Resource
	private AgencyUserMapper agencyUserMapper;
	
	@Resource
	private UserDepartmentMapper userDepartmentMapper;
	
	@Resource
	protected AuthenticationService authenticationService;
	
	@Resource
	protected MailSenderInfo mailSenderInfo;
	
	@Resource
	private UserCustomerMapper userCustomerMapper;
	
	@Resource
	private UserRoleMapper userRoleMapper;
	
	@Resource
	private MailBoxService mailBoxService;
	
	@Resource	
	private ServiceUrlConfig serviceUrlConfig;
	
	@Override
	public ReturnInfo createUser(User user, User caller,GeneralCondition gcon) {
		return createUser(user, caller, null, gcon);
	}
	
	
	@Override
	public ReturnInfo createUser(User user, User caller,Integer AgencyId,GeneralCondition gcon) {
		ReturnInfo returnInfo = checkAgencyAdmin(user);
		if(!returnInfo.getSuccess()){
			return returnInfo;
		}
		Integer agencyId;
		if(AgencyId==null) {
		
		//根据userId，查出agencyId,,用caller用户的agencyId绑定用户
			agencyId = agencyUserMapper.selectAgencyIdByUserId(caller.getUserId());
		}else {
			agencyId= AgencyId;
		}
		// 生成密码
		Map<String, String> passwordPair = authenticationService
				.generatePassowrdEncoded(user.getPassword());//如果user.getPassword为空，则随机生成
		user.setPassword(passwordPair.get("passwordEncoded"));
		if(user.getUserType()==null){
			user.setUserType(10);//默认是代理机构用户
		}
		if(user.getLocked()==null){
			user.setLocked(0);//默认添加是不锁定的
		}
		String username = user.getUsername();
		String email = user.getEmail();
		if(username==null||"".equals(username)){
			username = email.replaceAll("[@.]", "_");
			user.setUsername(username);
		}
		String emailPass = user.getEmailPass();
		if(emailPass!=null&&!"".equals(emailPass)){
			String encPassword = AESUtil.encrypt(emailPass, "12345678");
			user.setEmailPass(encPassword);
		}
		userMapper.insertSelective(user);
		Integer userId = user.getUserId();
		//如果emailPass不为空，则配置邮件信息
		if(emailPass!=null){
			returnInfo = mailBoxService.createMailbox(user.getEmail(), emailPass, userId,gcon.getTokenID());
			if(!returnInfo.getSuccess()){
				logger.info("Failure to create mailbox configuration information: "+ returnInfo.getMessage());
			}
		}
//		User user2 = userMapper.selectByUsername(user.getUsername());
		//插入代理机构和用户关联表，userId 和 agencyId
		AgencyUser agencyUser = new AgencyUser();
		agencyUser.setAgencyId(agencyId);
		agencyUser.setUserId(userId);
		agencyUserMapper.insertSelective(agencyUser);
		
		//Modification start, by yang guang, 2018-10-19
		//由前端控制，创建用户时，必须指定至少一个角色，因此，去掉默认绑定代理人角色
		/*
		//关联角色 部门 客户
		//默认绑定“代理人”角色
		List<Role> role1 = null;
		if(agencyId!=null){
			Role tmp = new Role();
			tmp.setAgencyId(agencyId);
			tmp.setName("代理人");
			role1 = roleMapper.selectByroleOwn(tmp);
		}
		List<Role> roles= user.getRoles();//从页面获取到的多个roleId
		if(roles!=null){
			//Modification start, by yang guang, 2018-10-19
			//do not to add duplicate role
			if (role1!=null && role1.size()>0){
				boolean hasAgentRole=false;
				Role role2=role1.get(0);
				Integer agentId=role2.getId();
				for(Role role : roles){
					Integer id=role.getId();						
					if (agentId!=null && id!=null && agentId.intValue()==id.intValue()){
						hasAgentRole=true;
						break;
					}					
				}
				if (!hasAgentRole){
					roles.add(role2);
				}
			}
			//Modification end
		}else{
			roles = role1;
		}
		
		*/
		//Modification end
		
		List<Role> roles= user.getRoles();//从页面获取到的多个roleId

		if(roles!=null&&roles.size()>0){
			for(Role role : roles){
				bindRole(userId,role.getId());
			}
		}
		List<Department> departments = user.getDepartments();
		if(departments!=null&&departments.size()>0){
			for(Department department : departments){
				bindDept(userId, department.getId());
			}
		}
		List<Customer> customers = user.getCustomers();
		if(customers!=null&&customers.size()>0){
			for(Customer customer : customers){
				bindCustomer(userId, customer.getId());
			}
		}
		returnInfo.setSuccess(true);
		if (annonceUser(user, passwordPair.get("password"))) {
			returnInfo.setMessage("用户创建成功并成功发送邮件通知！");
		} else {
			returnInfo.setMessage("用户创建成功但发送邮件通知失败。");
		}
		returnInfo.setData(passwordPair.get("password"));
		
		return returnInfo;
	}
	//以邮件的方式通知用户账号已开通
	private boolean annonceUser(User user, String password) {
		boolean validate = true;
		String mailSubject = "标得宜用户开通通知";
		String mailContent = "" + "<td>" + "您好！" + "<td>" + "用户:"
				+ user.getUsername() + " 已开通，初始密码:" + password
				+ "。 可通过以下网址登录标得宜。" + "</td>" + "<p></p>" + "<td>"
				+ "<a href='http://47.105.166.130'>yootii.com.cn/bdy</a>"
				+ "</td>";
		String mailTo = user.getEmail();
		mailSenderInfo.setSubject(mailSubject);
		mailSenderInfo.setContent(mailContent);
		mailSenderInfo.setToAddress(mailTo);
		mailSenderInfo.setValidate(validate);

		// 发送邮件
		SendMailUtil sendMailUtil = new SendMailUtil();
		try {
			sendMailUtil.sendmail(mailSenderInfo);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	private boolean annonceAdminUser(User user, String password, String agencyName) {
		boolean validate = true;
		String mailSubject = "标得宜管理员开通通知";
		String mailContent = "" + "<td>" + "您好！" + "<td>" + "代理所"+
				agencyName+"的管理员用户 已开通，初始密码:" + password
				+ "。 可通过以下网址登录标得宜。" + "</td>" + "<p></p>" + "<td>"
				+ "<a href='http://47.105.166.130'>www.yootii.com.cn</a>"
				+ "</td>";
		String mailTo = user.getEmail();
		mailSenderInfo.setSubject(mailSubject);
		mailSenderInfo.setContent(mailContent);
		mailSenderInfo.setToAddress(mailTo);
		mailSenderInfo.setValidate(validate);

		// 发送邮件
		SendMailUtil sendMailUtil = new SendMailUtil();
		try {
			sendMailUtil.sendmail(mailSenderInfo);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	@Override
	public ReturnInfo checkAgencyAdmin(User user) {
		ReturnInfo returnInfo = new ReturnInfo();
		String username = user.getUsername();
		/*
		 * 2018-05-18账户名为空则用默认账户名
		 * if(username==null||"".equals(username)){
			returnInfo.setSuccess(false);
			returnInfo.setMessage("账户名不能为空");
			return returnInfo;
		}else*/ 
		if(userMapper.selectByUsername(username)!=null){
			returnInfo.setSuccess(false);
			returnInfo.setMessage("该账户名已存在");
			return returnInfo;
		}
		if(user.getEmail()==null||"".equals(user.getEmail())){
			returnInfo.setSuccess(false);
			returnInfo.setMessage("邮箱不能为空");
			return returnInfo;
		}
//		else if((userMapper.selectByEmail(user.getEmail()))!=null){
//			returnInfo.setSuccess(false);
//			returnInfo.setMessage("邮箱已存在");
//			return returnInfo;
//		}
//		if(user.getPassword()==null||"".equals(user.getPassword())){
//			returnInfo.setSuccess(false);
//			returnInfo.setMessage("密码不能为空");
//			return returnInfo;
//		}
		returnInfo.setSuccess(true);
		return returnInfo;
	}

	@Override
	public ReturnInfo createAgencyAdmin(User user, Agency agency) {
		ReturnInfo returnInfo = createAgencyAdmin(user,agency.getId());
		if(!returnInfo.getSuccess()) return returnInfo;
		
		
		if (annonceAdminUser(user, (String) returnInfo.getData(),agency.getName())) {
			returnInfo.setMessage("管理员用户创建成功并成功发送邮件通知！");
		} else {
			returnInfo.setMessage("管理员用户创建成功但发送邮件通知失败。");
		}
		
		return returnInfo;
	}
	
	@Override
	public ReturnInfo createAgencyAdmin(User user, Integer agencyId) {
		ReturnInfo returnInfo = checkAgencyAdmin(user);
		if(!returnInfo.getSuccess()){
			return returnInfo;
		}
		user.setUserType(10);//代理机构管理员
		// 生成密码
		Map<String, String> passwordPair = authenticationService
				.generatePassowrdEncoded(user.getPassword());//如果user.getPassword为空，则随机生成
		user.setPassword(passwordPair.get("passwordEncoded"));
		userMapper.insertSelective(user);
		user = userMapper.selectByUsername(user.getUsername());
		Integer userId = user.getUserId();
		//用户和代理机构表绑定
		AgencyUser agencyUser = new AgencyUser();
		agencyUser.setAgencyId(agencyId);
		agencyUser.setUserId(userId);
		
		agencyUserMapper.insertSelective(agencyUser);
		//绑定角色
		Role role =new Role();
		role.setAgencyId(agencyId);
		role.setNoedit(1);
		role.setName("代理机构管理员");
		List<Role> byroleOwn = roleMapper.selectByroleOwn(role);
		if(byroleOwn !=null && byroleOwn.size()>0) {
			Integer roleId = byroleOwn.get(0).getId();
			UserRole ur =new UserRole();
			ur.setUserId(userId);
			ur.setRoleId(roleId);
			userRoleMapper.insertSelective(ur);
		}

		
		returnInfo.setData(passwordPair.get("password"));
		returnInfo.setSuccess(true);
		returnInfo.setMessage("创建成功");

		
		return returnInfo;
	}
	
	

	@Override
	public ReturnInfo checkUsername(String username) {
		ReturnInfo returnInfo = new ReturnInfo();
		User user = userMapper.selectByUsername(username);
		Map<String, Boolean> data = new HashMap<String, Boolean>();
		if(user!=null){
			returnInfo.setSuccess(true);
			returnInfo.setMessage("该用户名已存在");
			data.put("isExists", true);
			returnInfo.setData(data);
			return returnInfo;
		}
		returnInfo.setSuccess(true);
		returnInfo.setMessage("该用户名不存在");
		data.put("isExists", false);
		returnInfo.setData(data);
		return returnInfo;
	}

	@Override
	public ReturnInfo deleteUser(User user, User caller) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		//平台管理员删除代理机构用户
		//caller是管理员
		Integer userType = caller.getUserType();
		if(userType==null||userType!=1){
			returnInfo.setSuccess(false);
			returnInfo.setMessage("没有权限删除用户");
			return returnInfo;
		}
		Integer userId = user.getUserId();
		if(userId==null){
			returnInfo.setSuccess(false);
			returnInfo.setMessage("被删除用户Id不能为空");
			return returnInfo;
		}
		//用户解绑 代理机构 角色 部门 客户 
		User user2 = userMapper.selectByPrimaryKey(userId);
		if(user2==null){
			returnInfo.setSuccess(false);
			returnInfo.setMessage("该用户Id不存在");
			return returnInfo;
		}
		
		//Modification start,  2018-06-06
		//判断是否该用户是否正在处理案件
		//对于正在办理案件的用户，不允许删除
		boolean processCase=false;
		//1、首先，通过案件处理过程记录表，查看该用户是否正在处理未完成的案件
		Long count=userMapper.getUserCaseCount(userId);
		if (count==null || count.longValue()==0){	
			//2、如果上述结果为0，那么，查看待办的案件的处理人中是否有该用户
			List<Map<String, Object>> taskList=findUserTaskUrl(userId.toString());			
			if(taskList.size()>0){
				processCase=true;
			}
		}else{
			processCase=true;
		}		
		if (processCase){
			returnInfo.setSuccess(false);
			returnInfo.setMessage("该用户正在处理案件，不能删除");
			return returnInfo;
		}		
		//Modification end
		
		agencyUserMapper.deleteByUserId(userId);
		List<Role> roles = user2.getRoles();
		if(roles!=null&&roles.size()>0){
			for(Role role : roles){
				userMapper.unbindRole(userId, role.getId());
			}
		}

		List<Department> departments = user2.getDepartments();
		if(departments!=null&&departments.size()>0){
			for(Department department : departments){
				userMapper.unbindDepartment(userId, department.getId());
			}
		}
		List<Customer> customers = user2.getCustomers();
		if(customers!=null&&customers.size()>0){
			for(Customer customer : customers){
				userMapper.unbindCustomer(userId, customer.getId());
			}
		}
		userMapper.deleteByPrimaryKey(userId);
		
		returnInfo.setSuccess(true);
		returnInfo.setMessage("用户删除成功");
		return returnInfo;
	}

	@Override
	public ReturnInfo removeUser(User user) throws Exception {
		ReturnInfo returnInfo = new ReturnInfo();
		//代理机构管理员移除代理机构用户，只解绑，解绑代理机构，删除用户和角色的关联，删除用户和部门的关联
		//caller是管理员
		Integer userId = user.getUserId();
		if(userId==null){
			returnInfo.setSuccess(false);
			returnInfo.setMessage("被移除用户Id不能为空");
			return returnInfo;
		}
		
		//Modification start,  2018-06-06
		//判断是否该用户是否正在处理案件
		//对于正在办理案件的用户，不允许删除
		boolean processCase=false;
		//1、首先，通过案件处理过程记录表，查看该用户是否正在处理未完成的案件
		Long count=userMapper.getUserCaseCount(userId);
		if (count==null || count.longValue()==0){	
			//2、如果上述结果为0，那么，查看待办的案件的处理人中是否有该用户
			List<Map<String, Object>> taskList=findUserTaskUrl(userId.toString());			
			if(taskList.size()>0){
				processCase=true;
			}
		}else{
			processCase=true;
		}		
		if (processCase){
			returnInfo.setSuccess(false);
			returnInfo.setMessage("该用户正在处理案件，不能删除");
			return returnInfo;
		}		
		//Modification end
		
		//解绑代理机构
		agencyUserMapper.deleteByUserId(userId);
		//用户解绑 代理机构 角色 部门 客户 
		User user2 = userMapper.selectByPrimaryKey(userId);
		if(user2==null){
			returnInfo.setSuccess(false);
			returnInfo.setMessage("被移除用户Id不存在");
			return returnInfo;
		}
		List<Role> roles = user2.getRoles();
		if(roles!=null&&roles.size()>0){
			for(Role role : roles){
				userMapper.unbindRole(userId, role.getId());
			}
		}
		
		List<Department> departments = user2.getDepartments();
		if(departments!=null&&departments.size()>0){
			for(Department department : departments){
				userMapper.unbindDepartment(userId, department.getId());
			}
		}
		List<Customer> customers = user2.getCustomers();
		if(customers!=null&&customers.size()>0){
			for(Customer customer : customers){
				userMapper.unbindCustomer(userId, customer.getId());
			}
		}
		//2018-2-22，目前修改移除用户和删除用户功能相同
		userMapper.deleteByPrimaryKey(userId);
		
		returnInfo.setSuccess(true);
		returnInfo.setMessage("用户移除成功");
		return returnInfo;
	}

	@Override
	public ReturnInfo modifyUser(User user) {
		ReturnInfo returnInfo = new ReturnInfo();
		if(user.getUserId()==null){
			returnInfo.setSuccess(false);
			returnInfo.setMessageType(-4);
			returnInfo.setMessage("用户ID不能为空");
			return returnInfo;
		}
		User user2 = userMapper.selectByPrimaryKey(user.getUserId());
		if(user2==null){
			returnInfo.setSuccess(false);
			returnInfo.setMessage("被修改用户Id不存在");
			return returnInfo;
		}
		if (user.getUsername()!=null && !(user2.getUsername()).equals(user.getUsername())){ 
			returnInfo.setSuccess(false);
			returnInfo.setMessageType(-4);
			returnInfo.setMessage("不能更改账户名");
			return returnInfo;
		}
		//不能修改用户密码
		if(user.getPassword()!=null&& !"".equals(user.getPassword())){
			returnInfo.setSuccess(false);
			returnInfo.setMessageType(-3);
			returnInfo.setMessage("不能修改用户密码");
			return returnInfo;
		}
		List<Role> roleOld= user2.getRoles();//从以前roleId
		if(roleOld!=null&&roleOld.size()>0){
			for(Role role : roleOld){
				unbindRole(user2.getUserId(),role.getId());
			}
		}
		List<Role> roles= user.getRoles();//从页面获取到的多个roleId
		if(roles!=null&&roles.size()>0){
			for(Role role : roles){
				bindRole(user.getUserId(),role.getId());
			}
		}
		
		List<Department> departmentsOld = user2.getDepartments();
		if(departmentsOld!=null&&departmentsOld.size()>0){
			for(Department department : departmentsOld){
				userMapper.unbindDepartment(user2.getUserId(), department.getId());
			}
		}
		
		List<Department> departments = user.getDepartments();
		if(departments!=null&&departments.size()>0){
			for(Department department : departments){
				userMapper.bindDepartment(user.getUserId(), department.getId());
			}
		}
		
		//修改员工信息时不需要进行客户的修改
		/*List<Customer> customersOld = user2.getCustomers();
		if(customersOld!=null&&customersOld.size()>0){
			for(Customer customer : customersOld){
				userMapper.unbindCustomer(user2.getUserId(), customer.getId());
			}
		}

		List<Customer> customers = user.getCustomers();
		if(customers!=null&&customers.size()>0){
			for(Customer customer : customers){
				userMapper.bindCustomer(user.getUserId(), customer.getId());
			}
		}*/
		
		//平台管理员修改代理机构管理员，代理机构管理员修改员工信息
		userMapper.updateByPrimaryKeySelective(user);
		returnInfo.setSuccess(true);
		returnInfo.setMessage("修改成功");
		return returnInfo;
	}

	@Override
	public ReturnInfo queryAgencyUserList(User user, GeneralCondition gcon,
			User caller) {
		ReturnInfo returnInfo = new ReturnInfo();
		//代理机构管理员查询   //2018-05-18普通代理人员可以查询本代理机构的员工
		//代理机构管理员可以查询 userType = 20 的本代理机构的员工
		//查询caller的agencyId
		Integer agencyId = agencyUserMapper.selectAgencyIdByUserId(caller.getUserId());
		if(agencyId==null){
			returnInfo.setSuccess(false);
			returnInfo.setMessage("登录用户没有代理所");
			return returnInfo;
		}
		List<User> users = userMapper.selectAgencyUser(user, gcon, agencyId);//所有该代理所下的用户
		returnInfo.setData(users);
		//页数
		List<Map<String,Long>> counts = userMapper.selectAgencyUserCount(user, gcon, agencyId);
		Long total = 0L;
		for (Map<String,Long> onecount:counts) {
			total+= onecount.get("count");
		} 
		returnInfo.setTotal(total);
		returnInfo.setCurrPage(gcon.getPageNo());
		returnInfo.setSuccess(true);
		returnInfo.setMessage("查询用户成功");
		return returnInfo;
	}
	
	
	@Override
	public ReturnInfo queryAgencyUserByFullName(User user, GeneralCondition gcon, Integer agencyId) {
		ReturnInfo returnInfo = new ReturnInfo();
		
		String fullName=user.getFullname();
		if (fullName==null || fullName.equals("")){				
			returnInfo.setSuccess(false);
			returnInfo.setMessage("fullName is null");
			returnInfo.setMessageType(Globals.MESSAGE_TYPE_PARAM_INVALID);
			return returnInfo;
		}
		if (agencyId==null ){				
			returnInfo.setSuccess(false);
			returnInfo.setMessage("agencyId is null");
			returnInfo.setMessageType(Globals.MESSAGE_TYPE_PARAM_INVALID);
			return returnInfo;
		}
		
		List<String> fullNameList=new ArrayList<String>();
		
		String key=";";
		int pos=fullName.indexOf(key);
		if (pos<0){
			key="；";
		}	
		
		StringTokenizer idtok = new StringTokenizer(fullName, key);					
		while (idtok.hasMoreTokens()) {
			String name= idtok.nextToken();
			fullNameList.add(name);
		}		
				
		Map<String, Object>map=new HashMap<String, Object>();
		map.put("agencyId", agencyId);
		map.put("fullNameList", fullNameList);
		
		Long total=(long)0;
		List<User> users = userMapper.selectAgencyUserByFullName(map);
		returnInfo.setData(users);
		if (users!=null && users.size()>0){
			total=(long)users.size();
		}		
		returnInfo.setTotal(total);		
		returnInfo.setSuccess(true);
		returnInfo.setMessage("查询用户成功");
		return returnInfo;
	}
	
	
	@Override
	public ReturnInfo queryUserList(User user, GeneralCondition gcon,
			User caller) {
		//平台管理员查询
		ReturnInfo returnInfo = new ReturnInfo();
		//查询caller的agencyId
		List<User> users = userMapper.selectByUser(user, gcon,caller);
		returnInfo.setData(users);
		//页数
		List<Map<String,Long>> counts = userMapper.getUserCount(user, gcon, caller);
		Long total = 0L;
		for (Map<String,Long> onecount:counts) {
			total+= onecount.get("count");
		} 
		returnInfo.setTotal(total);
		returnInfo.setCurrPage(gcon.getPageNo());
		returnInfo.setSuccess(true);
		returnInfo.setMessage("查询用户成功");
		return returnInfo;
	}

	@Override
	public ReturnInfo bindRole(Integer userId, Integer roleId) {
		ReturnInfo info = new ReturnInfo();
		if(userId==null||roleId==null){
			info.setMessageType(-4);
			info.setMessage("参数为空");
			info.setSuccess(false);
			return info;
		}
		userMapper.unbindRole(userId, roleId);
		userMapper.bindRole(userId, roleId);
		info.setMessage("绑定成功");
		info.setSuccess(true);
		return info;
	}

	@Override
	public ReturnInfo unbindRole(Integer userId, Integer roleId) {
		ReturnInfo info = new ReturnInfo();
		//只有代理机构管理员
		
		if(userId==null||roleId==null){
			info.setMessageType(-4);
			info.setMessage("参数为空");
			info.setSuccess(false);
			return info;
		}
		userMapper.unbindRole(userId, roleId);
		info.setMessage("解除绑定成功");
		info.setSuccess(true);
		return info;
	}

	@Override
	public ReturnInfo bindDept(Integer userId, Integer departmentId) {
		ReturnInfo info = new ReturnInfo();
		if(userId==null||departmentId==null){
			info.setMessageType(-4);
			info.setMessage("参数为空");
			info.setSuccess(false);
			return info;
		}
		userMapper.unbindDepartment(userId, departmentId);
		userMapper.bindDepartment(userId, departmentId);
		info.setMessage("绑定成功");
		info.setSuccess(true);
		return info;
	}

	@Override
	public ReturnInfo unbindDept(Integer userId, Integer departmentId) {
		ReturnInfo info = new ReturnInfo();
		if(userId==null||departmentId==null){
			info.setMessageType(-4);
			info.setMessage("参数为空");
			info.setSuccess(false);
			return info;
		}
		userMapper.unbindDepartment(userId, departmentId);
		info.setMessage("解绑成功");
		info.setSuccess(true);
		return info;
	}

	@Override
	public ReturnInfo bindCustomer(Integer userId, Integer custId) {
		ReturnInfo info = new ReturnInfo();
		if(userId==null||custId==null){
			info.setMessageType(-4);
			info.setMessage("参数为空");
			info.setSuccess(false);
			return info;
		}
		userMapper.unbindCustomer(userId, custId);
		userMapper.bindCustomer(userId, custId);
		info.setMessage("绑定成功");
		info.setSuccess(true);
		return info;
	}

	@Override
	public ReturnInfo unbindCustomer(Integer userId, Integer custId) {
		ReturnInfo info = new ReturnInfo();
		if(userId==null||custId==null){
			info.setMessageType(-4);
			info.setMessage("参数为空");
			info.setSuccess(false);
			return info;
		}
		userMapper.unbindCustomer(userId, custId);
		info.setMessage("解绑成功");
		info.setSuccess(true);
		return info;
	}

	@Override
	public ReturnInfo modifyUserSelf(User user,GeneralCondition gcon) {
		ReturnInfo info = new ReturnInfo();
		User user2 = userMapper.selectByPrimaryKey(user.getUserId());
		if(user2==null){
			info.setSuccess(false);
			info.setMessageType(-4);
			info.setMessage("该用户不存在");
			return info;
		}
		if (user.getUsername()!=null && !user2.getUsername().equals(user.getUsername())) { 
			info.setSuccess(false);
			info.setMessageType(-4);
			info.setMessage("不能更改账户名");
			return info;
		}
		String emailPass = user.getEmailPass();
		if(emailPass!=null&&!"".equals(emailPass)){
			String encPassword = AESUtil.encrypt(emailPass, "12345678");
			user.setEmailPass(encPassword);
		}
		//如果emailPass不为空，则配置邮件信息
		if(emailPass!=null&&gcon!=null){
			info = mailBoxService.createMailbox(user.getEmail()==null?user2.getEmail():user.getEmail(), emailPass, user.getUserId(),gcon.getTokenID());
			if(!info.getSuccess()){
				logger.info("Failure to create mailbox configuration information: "+ info.getMessage());
			}
		}
		//修改密码
		if(user.getPassword()!=null&& !"".equals(user.getPassword())){
			Map<String, String> passwordPair = authenticationService.generatePassowrdEncoded(user.getPassword());
			user.setPassword(passwordPair.get("passwordEncoded"));
		}
		userMapper.updateByPrimaryKeySelective(user);
		
		info.setMessage("修改成功");
		info.setSuccess(true);
		return info;
	}

	@Override
	public ReturnInfo uploadUserIcon(HttpServletRequest request, String username) {
		ReturnInfo info=new ReturnInfo();
		String message = null;
		Map<String, String> userIcons= new HashMap<String, String>();
		try {
			userIcons = UploadUtil.uploadUserIcon(request,
					username);
		} catch (Exception e) {
			info.setSuccess(false);
			info.setMessageType(-4);
			info.setMessage("修改用户异常：" + e.getMessage());
			return info;
		}
		if (userIcons==null || userIcons.size()==0){
			message ="图片文件没有上传成功";
			info.setSuccess(false);
			info.setMessageType(-4);
			info.setMessage(message);
			return info;
		}
		Iterator<Entry<String, String>> iter = userIcons.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>) iter
					.next();

			username = entry.getKey();
			String usericon = entry.getValue();

			User user = getUserByUsername(username);
			user.setUserIcon(usericon);
			userMapper.updateByPrimaryKeySelective(user);
			break;
		}
		info.setSuccess(true);
		info.setMessage("上传成功");
		return info;
	}

	@Override
	public User getUserById(Integer userId) {
		User user = userMapper.selectByPrimaryKey(userId);
		return user;
	}
	@Override
	public ReturnInfo getUserByPrimaryKey(Integer userId) {
		ReturnInfo info = new ReturnInfo();
		User user = userMapper.selectByPrimaryKey(userId);
		info.setSuccess(true);
		info.setData(user);
		info.setMessage("查询成功");
		return info;
	}
		
	@Override
	public User getUserByUsername(String  username) {
		User user = userMapper.selectByUsername(username);
		return user;
	}

	@Override
	public boolean hasRole(String name,User user) {
		Set<String> roles = roleMapper.selectRoleByUserId(user.getUserId());
		boolean b = roles.contains(name);
		return b;
	}
	
	@Override
	public boolean hasPermission(String permission1,User user) {
	/*
	 * 例如 ： 查看用户是否有  role:create  这个权限
	 * 		用户有 role 或者  role:create 都返回 true  
	 */
		List<String> permissions = permissionMapper.selectPermissionByUserId(user.getUserId());
				
		int size=permissions.size();		
		if (size==0){
			return false;
		}
		
		
		String[] split = permission1.split(":");

		for(String permission: permissions) {
			String[] pm = permission.split(":");			
			if(split.length == 1) {
				if (split[0].equals(pm[0])){
					return true;
				}
			}else{
				int len=pm.length;
				if(len==1){
					continue;
				}				
				if (split[0].equals(pm[0]) && split[1].equals(pm[1])){
					return true;
				}
			}
		}			
		return false;			
	
		
		
//		boolean hasPermission = false;
//		
//		
//		for(int i=0; i < split.length; i++) {			
//			if(i==0) {
//				if( permissions.contains(p)) {
//					hasPermission=true;
//					return hasPermission;
//				}else {
//					i++;
//				}			
//			}
//			
//			 p = p+":"+split[i];
//			 hasPermission = permissions.contains(p);
//			if(hasPermission) {
//				return hasPermission;
//			}else {
//				i++;
//			}
//					
//		}
//		return hasPermission;
	}

	@Override
	public ReturnInfo bindAgency(Integer userId, Integer agencyId) {
		ReturnInfo info = new ReturnInfo();
		if(userId==null||agencyId==null){
			info.setMessageType(-4);
			info.setMessage("参数为空");
			info.setSuccess(false);
			return info;
		}
		userMapper.unbindAgency(userId, agencyId);
		userMapper.bindAgency(userId, agencyId);
		info.setMessage("绑定成功");
		info.setSuccess(true);
		return info;
	}

	@Override
	public ReturnInfo unbindAgency(Integer userId, Integer agencyId) {
		ReturnInfo info = new ReturnInfo();
		if(userId==null||agencyId==null){
			info.setMessageType(-4);
			info.setMessage("参数为空");
			info.setSuccess(false);
			return info;
		}
		userMapper.unbindAgency(userId, agencyId);
		info.setMessage("解绑成功");
		info.setSuccess(true);
		return info;
	}

	@Override
	public ReturnInfo queryUserByDeptId(Integer deptId,GeneralCondition gcon) {
		//要分页
		ReturnInfo info = new ReturnInfo();
		if(deptId==null||deptId==null){
			info.setMessageType(-4);
			info.setMessage("参数为空");
			info.setSuccess(false);
			return info;
		}
		List<User> users = userMapper.selectUserByDeptId(deptId);
		//页数
		List<Map<String,Long>> counts = userMapper.selectUserByDeptIdCount(deptId);
		Long total = 0L;
		for (Map<String,Long> onecount:counts) {
			total+= onecount.get("count");
		} 
		info.setTotal(total);
		info.setCurrPage(gcon.getPageNo());
		info.setData(users);
		info.setMessage("查询成功");
		info.setSuccess(true);
		return info;
	}

	@Override
	public ReturnInfo queryAgencyUserListByPermission(Permission permission, GeneralCondition gcon) {
		ReturnInfo info = new ReturnInfo();
		String[] split = permission.getPermission().split(":");
		String p=split[0];
		Set set = new  HashSet();
		List<User> users = new ArrayList<User>();
		Long counts = 0L;
		Integer checkPermission = permissionMapper.checkPermission(permission);
		if(checkPermission<1) {
			info.setMessage("查询失败,该代理机构下没有这个权限");
			info.setSuccess	(false);
			return info;
		}
		if(split.length == 1) {
			users = userMapper.selectUserByPermission(permission,gcon);
			
			counts = userMapper.selectUserByPermissionCount(permission,gcon);
			info.setTotal(Long.parseLong(String.valueOf(users.size())));
			info.setData(users);
		}else {
			for(int i=0; i < split.length; i++) {
				if(i==0) {
					permission.setPermission(p);
					List<User>	user = userMapper.selectUserByPermission(permission,gcon);
					
					Long count= userMapper.selectUserByPermissionCount(permission,gcon);
					set.addAll(user);
				}else {
					p = p+":"+split[i];
					permission.setPermission(p);
					List<User>	user = userMapper.selectUserByPermission(permission,gcon);
					
					Long count= userMapper.selectUserByPermissionCount(permission,gcon);
					set.addAll(user);
				}
			}
			List<User> newList = new  ArrayList<User>(); 
			newList.addAll(set);
			info.setTotal(Long.parseLong(String.valueOf(newList.size())));
			info.setData(newList);
		}
		
		info.setCurrPage(gcon.getPageNo());
		
		info.setMessage("查询成功");
		info.setSuccess	(true);
		return info;
		
	}



	@Override
	public boolean checkUserInDept(Integer userId, String depName) {
		boolean rtn = false;
		if(userId==null||depName==null){
			return rtn;
		}
		UserDepartment userDepartment = userDepartmentMapper.selectByUserIdAndDepartmentId(userId,depName);
		if(userDepartment!=null){
			rtn=true;
		}
		return rtn;
	}


	@Override
	public ReturnInfo queryAgencyUserListByPermissionDepId(Permission permission, String depName,
			GeneralCondition gcon) {
		ReturnInfo info = new ReturnInfo();
		
		List<User> users = (List<User>) queryAgencyUserListByPermission(permission,gcon).getData();
		List<User> retlist = new ArrayList<User>();
		for(User user : users) {
			if(checkUserInDept(user.getUserId(),depName)) {
				retlist.add(user);
			}
		}
		

		info.setData(retlist);
		info.setMessage("查询成功");
		info.setSuccess	(true);
		return info;
	}
	
	
	@Override
	public ReturnInfo queryUserByCustId(AgencyCustomer agencyCustomer) {
		ReturnInfo info = new ReturnInfo();	
		List<UserCustomer> userList = userCustomerMapper.getUserByCustId(agencyCustomer);		
		info.setSuccess(true);
		info.setData(userList);
		info.setMessage("查询成功");
		return info;
	}

	@Override
	public ReturnInfo queryContactUserByAgencyId(Integer agencyId) {
		ReturnInfo info = new ReturnInfo();	
		if(agencyId==null){
			info.setSuccess(false);
			info.setMessage("代理机构Id不能为空");
			return info;
		}
		List<User> userList = userMapper.queryContactUserByAgencyId(agencyId);
		info.setSuccess(true);
		info.setData(userList);
		info.setMessage("查询成功");
		return info;
	}

	@Override
	public List<User> selectAgentByAgencyId(Integer agencyId) {
		
		List<User> userList = userMapper.selectAgentByAgencyId(agencyId);
		
		return userList;
	}
	
	
	public List<Map<String, Object>> findUserTaskUrl(String userId)
			throws Exception {
		String url = serviceUrlConfig.getProcessEngineUrl()
				+ "/Task/findusertask?userId=" + userId;
//		logger.info(url);
		String jsonString;

		jsonString = GraspUtil.getText(url);
		ReturnInfo rtnInfo = JsonUtil.toObject(jsonString, ReturnInfo.class);

		List<Map<String, Object>> taskList = (List<Map<String, Object>>) rtnInfo
				.getData();
		return taskList;
	}
	
	
	@Override
	public ReturnInfo queryAdminByAgencyId(Integer agencyId) {
		ReturnInfo info = new ReturnInfo();	
		
		List<User> userList = userMapper.selectAdminByAgencyId(agencyId);
		User user=null;
		if (userList!=null && userList.size()>0){
			user=userList.get(0);
		}
		info.setSuccess(true);
		info.setData(user);
		info.setMessage("查询成功");
		return info;
	}
	
}
