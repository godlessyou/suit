package com.yootii.bdy.security.service.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;

import com.yootii.bdy.agency.dao.AgencyContactMapper;
import com.yootii.bdy.agency.dao.AgencyMapper;
import com.yootii.bdy.agency.model.Agency;
import com.yootii.bdy.agency.model.AgencyContact;
import com.yootii.bdy.agency.service.AgencyContactService;
import com.yootii.bdy.agency.service.AgencyService;
import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.common.Globals;
import com.yootii.bdy.common.LoginReturnInfo;
import com.yootii.bdy.common.ReturnInfo;
import com.yootii.bdy.customer.dao.CustomerMapper;
import com.yootii.bdy.customer.model.Applicant;
import com.yootii.bdy.customer.model.Customer;
import com.yootii.bdy.customer.service.ApplicantService;
import com.yootii.bdy.customer.service.CustomerService;
import com.yootii.bdy.permission.model.Permission;
import com.yootii.bdy.permission.service.PermissionService;
import com.yootii.bdy.role.model.Role;
import com.yootii.bdy.role.service.RoleService;
import com.yootii.bdy.security.model.Token;
import com.yootii.bdy.security.model.UserName;
import com.yootii.bdy.security.service.AuthenticationService;
import com.yootii.bdy.security.service.SysService;
import com.yootii.bdy.user.dao.UserMapper;
import com.yootii.bdy.user.model.User;
import com.yootii.bdy.user.service.UserService;
import com.yootii.bdy.util.UniqueStringGenerator;




@Service("authenticationService")
public class AuthenticationServiceImpl implements AuthenticationService{
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Resource
	private SysService sysService;
	@Resource
	private StandardPasswordEncoder passwordEncoder;
	@Resource
	protected UserService userService;
	@Resource
	protected AgencyService agencyService;
	@Resource
	protected AgencyMapper agencyMapper;
	@Resource
	protected PermissionService permissionService;
	@Resource
	private CustomerService customerService;
	@Resource
	private RoleService roleService;
	@Resource
	private ApplicantService applicantService;
	@Resource
	private AgencyContactService agencyContactService;
	
	
	@Resource
	protected UserMapper userMapper;
	
	@Resource
	protected CustomerMapper customerMapper;
	
	
	public StandardPasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
	}

	public void setPasswordEncoder(StandardPasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}
	@Override
	public ReturnInfo login(User user1, GeneralCondition gcon) {
		// 返回结果对象
		LoginReturnInfo rtnInfo = new LoginReturnInfo();

		// 身份认证
		User user2 = authentication(user1, gcon, true);
		

		/*UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(user2.getUsername(),user2.getPassword());
				usernamePasswordToken.setRememberMe(true);
				 try {
					Subject subject = SecurityUtils.getSubject();
					 subject.login(usernamePasswordToken);
					 Serializable id = subject.getSession().getId();
					 System.out.println("sessionId : "+id);
				} catch (AuthenticationException e) {
					rtnInfo.setSuccess(false);
					rtnInfo.setMessage("权限不足");
					//rtnInfo.setMessageType(Globals.MESSAGE_TYPE_SESSION_INVALID);
					return rtnInfo;
				}catch(UnauthorizedException e) {
					rtnInfo.setSuccess(false);
					rtnInfo.setMessage("权限不足");
					//rtnInfo.setMessageType(Globals.MESSAGE_TYPE_SESSION_INVALID);
					return rtnInfo;
				}*/


		if (user2 == null) {
			rtnInfo.setSuccess(false);
			rtnInfo.setMessage("用户名或密码不正确,请重新登录");
			//rtnInfo.setMessageType(Globals.MESSAGE_TYPE_SESSION_INVALID);
		} else {
			
			if(user2.getLocked() != null && user2.getLocked() == 1) {
				rtnInfo.setSuccess(false);
				rtnInfo.setMessage("该账号的已被锁定,禁止登陆");
				rtnInfo.setMessageType(Globals.MESSAGE_TYPE_AUTHORTY_INVALID);
				return rtnInfo;
			}
			Integer remainDay=null;

			// 创建token
			Token token = generateToken(user2);
			// 保存token
			sysService.addToken(token);

			String tokenID = token.getTokenID();

			try {

				Integer userTypeObj=user2.getUserType();
				int userType=0;
				if (userTypeObj!=null){
					userType= userTypeObj.intValue();
				}

				Agency agency =null;
				List<Agency> agencys = null;
				List<Agency> cooperationAgencys = null;
				List<Permission> permissions=null;
				List<Role> roles= null;
				
				Integer userId=user2.getUserId();				
				
				User user = userMapper.selectByPrimaryKey(userId);
				
				//代理机构信息
				agency = agencyService.selectAgencyByUserId(userId);
				
				Integer agencyId=null;
				
				if (agency!=null){
					agencyId=agency.getId();
				}
				
				//合作机构信息
				cooperationAgencys=agencyService.selectCooperationAgencyListByUserId(userId);
				//权限信息
//				permissions =permissionService.getPermissionByUserId(user2.getUserId());
//				roles = roleService.getRoleListByUserId(user2.getUserId());
				/*if(userType==20) {
							user2.setAgency(agency);
							user2.setCooperationAgency(cooperationAgencys);
							user2.setPermissions(permissions);
						}*/
				if(userType==1 || userType==10) {
					user.setAgency(agency);
					user.setCooperationAgency(cooperationAgencys);
//					user2.setRoles(roles);
//					user2.setDepartments(null);

				}
				
				//获取用户管理的客户数量
				List<Map<String,Long>> counts =null;
				Integer level=null;
				Customer customer = new Customer();
				if(userService.hasRole("代理机构管理员", user)
						||userService.hasRole("公司领导", user)
						||userType==1){
					//代理机构管理员、公司领导和平台管理员
					counts=customerMapper.selectByCustomerCount(customer, gcon, agencyId);
				}
				else if(userService.hasRole("一级部门负责人", user)){
					level =0;
					counts=customerMapper.selectByOwnCustomerCount(customer, gcon, userId, level);
				}else if(userService.hasRole("二级部门负责人", user)){
					level =1;
					counts=customerMapper.selectByOwnCustomerCount(customer, gcon, userId, level);
				}else if(userService.hasRole("代理人", user)){//通过代理人身份和角色验证
					counts=customerMapper.selectByOwnCustomerCount(customer, gcon, userId, level);
				}
				
				Long customerCount = 0L;
				if (counts!=null){
					for (Map<String,Long> onecount:counts) {
						customerCount+= onecount.get("count");
					} 
				}				
				user.setCustomerCount(customerCount);
				
			//	customerService.
				
				//Modification start, by yang guang, 2018-08-31
				//in order to provide performance	
				/*
				List<Customer> customers = user.getCustomers();
				if(customers.size()>0) {
					for(Customer c : customers) {
						Customer customer = customerService.getCustById(c.getId());
						List<Applicant> applicants = customer.getApplicants();
						if(applicants != null) {
							List<Applicant> apps = new ArrayList<>();
							for(Applicant a :applicants) {
								Applicant applicant	=applicantService.selectAppAndMaterialByPrimaryKey(a.getId());
								apps.add(applicant);
							}
							c.setApplicants(apps);
						}
					}
				}
				
				*/
				
				//Modification end
				
				if(userType==20) {
					/*user2.setAgency(agency);
							user2.setRoles(null);
							user2.setDepartments(null);
							user2.setCustomers(null);*/
					rtnInfo.setSuccess(false);
					rtnInfo.setMessage("该账号的用户类型为客户");
					rtnInfo.setMessageType(Globals.MESSAGE_TYPE_AUTHORTY_INVALID);
					return rtnInfo;
				}
				/*if (userType==2){
							// 判断试用用户的有效期
							Date end=user2.getValidEnd();
							if (end==null) {
								rtnInfo.setSuccess(false);
								rtnInfo.setMessage("该账号还未设试用期限。");
								rtnInfo.setMessageType(Globals.MESSAGE_TYPE_AUTHORTY_INVALID);
								return  rtnInfo;
							}
							Date now=new Date();
							if (end.before(now)){
								rtnInfo.setSuccess(false);
								rtnInfo.setMessage("该账号的试用期已结束。");
								rtnInfo.setMessageType(Globals.MESSAGE_TYPE_AUTHORTY_INVALID);
								return rtnInfo;
							}else{
								int days=DateTool.daysBetween(now, end);
								remainDay=days;
							}
						}


						if (remainDay!=null){
							user2.setRemainDay(remainDay);
						}
				 */
				// 返回用户的tokenID
				rtnInfo.setTokenID(tokenID);
				rtnInfo.setData(user);
				rtnInfo.setSuccess(true);

			} catch (Exception e) {
				rtnInfo.setSuccess(false);
				e.printStackTrace();
				rtnInfo.setMessage("用户登录异常：" + e.getMessage());
				//rtnInfo.setMessageType(Globals.MESSAGE_TYPE_GETDATA_FAILED);
			}

		}
		return rtnInfo;
	}
	@Override
	public ReturnInfo customerin(Customer customer, GeneralCondition gcon) {
		// 返回结果对象
		LoginReturnInfo rtnInfo = new LoginReturnInfo();

		// 身份认证
		Customer customer2 = authenticationCustomer(customer, gcon, true);
		


		if (customer2 == null) {
			rtnInfo.setSuccess(false);
			rtnInfo.setMessage("用户名或密码不正确,请重新登录");
			//rtnInfo.setMessageType(Globals.MESSAGE_TYPE_SESSION_INVALID);
		} else {
			
			if(customer2.getLocked() != null &&customer2.getLocked() == 1) {
				rtnInfo.setSuccess(false);
				rtnInfo.setMessage("该账号的已被锁定,禁止登陆");
				rtnInfo.setMessageType(Globals.MESSAGE_TYPE_AUTHORTY_INVALID);
				return rtnInfo;
			}
			
			Integer remainDay=null;

			// 创建token
			Token token = generateTokenByCustomer(customer2);
			// 保存token
			sysService.addToken(token);

			String tokenID = token.getTokenID();

			try {

				Integer userTypeObj=customer2.getUserType();
				int userType=0;
				if (userTypeObj!=null){
					userType= userTypeObj.intValue();
				}

				List<Agency> agency =null;
				//代理机构信息
				agency = agencyService.selectAgencyByCustId(customer2.getId());
				if(agency!= null) {
					for(Agency a: agency) {
						/*List<User> users= userService.selectAgentByAgencyId(a.getId());
						if(users!=null) {
							User user = users.get(0);
							List<User> u = new ArrayList<>();
							u.add(user);
							a.setUser(u);
						}*/
						//从客户的代理机构联系人表中获取联系人
						AgencyContact agencyContact = new AgencyContact();
						agencyContact.setAgencyId(a.getId());
						agencyContact.setCustId(customer2.getId());
						List<User> users = agencyContactService.queryAgencyContact(agencyContact);
						a.setUser(users);
					}
					customer2.setAgencies(agency);
				}
				
				
				if(userType <20) {
					/*user2.setAgency(agency);
					user2.setRoles(null);
					user2.setDepartments(null);
					user2.setCustomers(null);*/
					rtnInfo.setSuccess(false);
					rtnInfo.setMessage("该账号的用户类型不为客户");
					rtnInfo.setMessageType(Globals.MESSAGE_TYPE_AUTHORTY_INVALID);
					return rtnInfo;
				}
				
				//Modification start, by yang guang, 2018-08-31
				//in order to provide performance				
				/*
				List<Applicant> applicants = customer2.getApplicants();
				if(applicants != null) {
					List<Applicant> apps = new ArrayList<>();
					for(Applicant a :applicants) {
						Applicant applicant	=applicantService.selectAppAndMaterialByPrimaryKey(a.getId());
						apps.add(applicant);
					}
					customer2.setApplicants(apps);
				}				 
				*/				
				//Modification end
				
				
				/*if (userType==2){
					// 判断试用用户的有效期
					Date end=user2.getValidEnd();
					if (end==null) {
						rtnInfo.setSuccess(false);
						rtnInfo.setMessage("该账号还未设试用期限。");
						rtnInfo.setMessageType(Globals.MESSAGE_TYPE_AUTHORTY_INVALID);
						return  rtnInfo;
					}
					Date now=new Date();
					if (end.before(now)){
						rtnInfo.setSuccess(false);
						rtnInfo.setMessage("该账号的试用期已结束。");
						rtnInfo.setMessageType(Globals.MESSAGE_TYPE_AUTHORTY_INVALID);
						return rtnInfo;
					}else{
						int days=DateTool.daysBetween(now, end);
						remainDay=days;
					}
				}


				if (remainDay!=null){
					user2.setRemainDay(remainDay);
				}
				 */
				// 返回用户的tokenID
				rtnInfo.setTokenID(tokenID);
				rtnInfo.setData(customer2);
				rtnInfo.setSuccess(true);

			} catch (Exception e) {
				rtnInfo.setSuccess(false);
				e.printStackTrace();
				rtnInfo.setMessage("用户登录异常：" + e.getMessage());
				//rtnInfo.setMessageType(Globals.MESSAGE_TYPE_GETDATA_FAILED);
			}

		}
		return rtnInfo;
	}
	private Token generateToken(User user) {
		Token token = new Token();
		// 保存token
		Date date = new Date();
		String tokenID = UniqueStringGenerator.getUniqueString();

		token.setTokenID(tokenID);
		token.setCreateTime(date);
		token.setCheckTime(date);
		token.setUserID(user.getUserId());
		token.setUsername(user.getUsername());
		token.setFullname(user.getFullname());
		token.setUser(true);
		return token;
	}
	private Token generateTokenByCustomer(Customer customer) {
		Token token = new Token();
		// 保存token
		Date date = new Date();
		String tokenID = UniqueStringGenerator.getUniqueString();

		token.setTokenID(tokenID);
		token.setCreateTime(date);
		token.setCheckTime(date);
		//token.setUserID(customer.getId());
		token.setCustomerID(customer.getId());
		token.setUsername(customer.getUsername());
		token.setFullname(customer.getFullname());
		
		token.setUser(false);
		return token;
	}

	public User authentication(User user1,
			GeneralCondition gcon, boolean checkPassword) {
		String username = null;
		String rawPassword = null;
		User user2 = null;
		if (user1 != null) {
			username = user1.getUsername();
			rawPassword = user1.getPassword();
		}

		boolean loginFailed = false;
		if (username == null || username.length() == 0) {
			loginFailed = true;
		}
		if (checkPassword) {
			if (rawPassword == null || rawPassword.length() == 0) { 
				loginFailed = true;
			}
		}
		if (!loginFailed) {
			// 获取登陆用户的信息
			user2 = userService.getUserByUsername(username);
			if (user2 == null) {
				loginFailed = true;
			} else {
				String usrName = user2.getUsername();
				String usrPassword = user2.getPassword();
				if (usrName == null || !usrName.equals(username)) {
					loginFailed = true;
				}
				if (checkPassword) {
					boolean isMatche = passwordEncoder.matches(rawPassword,
							usrPassword);
					if (!isMatche) {
						loginFailed = true;
					}
				}
			}
		}
		if (loginFailed) {
			return null;
		}
		return user2;

	}

	@Override
	public ReturnInfo authorize(HttpServletRequest request, GeneralCondition gcon) {
		// 返回结果对象
		ReturnInfo rtnInfo = new ReturnInfo();
		boolean authorized = false;
		Token token = null;
		try {
			// 如果请求参数中没有tokenID，返回提示用户重新登录的信息
			authorized = checkToken(gcon);
			token = sysService.checkToken(gcon.getTokenID());
		} catch (Exception e) {
			rtnInfo.setSuccess(false);
			rtnInfo.setMessage("验证用户身份失败：" + e.getMessage());
			rtnInfo.setMessageType(Globals.MESSAGE_TYPE_GETDATA_FAILED);
			return rtnInfo;
		}
		if (!authorized) {
			rtnInfo.setSuccess(false);
			rtnInfo.setMessage("身份无效。");
			rtnInfo.setMessageType(Globals.MESSAGE_TYPE_SESSION_INVALID);
			return rtnInfo;
		}
		//Modification start, 2018-05-09
		if(token==null){
			logger.info("warning: authenticae success but token is null");
			rtnInfo.setSuccess(false);			
			rtnInfo.setMessage("authenticae success but token is null");
		}else{
			String language=token.getLanguage();
			if (language!=null && !language.equals("")){
				Globals.setLanguage(language);
			}
			Globals.setToken(token);
			rtnInfo.setSuccess(true);
			rtnInfo.setData(token);
			rtnInfo.setMessage("验证成功");
		}
		return rtnInfo;
	}
	// 登出
	public ReturnInfo logout(HttpServletRequest request,GeneralCondition gcon) {
		// 返回结果对象
		ReturnInfo rtnInfo = new ReturnInfo();
		String tokenID = "";
		if(tokenID==null ||"".equals(tokenID)){
			tokenID=gcon.getTokenID();
		}
		Token token = sysService.checkToken(tokenID);
		if(token == null) {
			rtnInfo.setSuccess(true);
			rtnInfo.setMessage("会话已过期");
			rtnInfo.setData("登出成功");
			return rtnInfo;
		}
		User user=new User();

		try {
			if(token.isLoginAs()) {
				if(!("").equals(token.getLoginAsName())&& null != token.getLoginAsName()) {

					token.setLoginAs(false);
					user = userService.getUserByUsername(token.getUsername());
				}

			}

			if(null != user.getUserType() && user.getUserType()==1 ) {

				token.setLoginAsId(null);
				token.setLoginAsName(null);
				token.setLoginAs(false);
				Object loginInfo = generateAdminLoginInfo(user.getUsername(),tokenID);
				return (ReturnInfo) loginInfo;

			}else {

				if (tokenID != null && tokenID.length() > 0) {
					sysService.removeToken(tokenID);				
				}
			}
			rtnInfo.setData("登出成功");
			rtnInfo.setSuccess(true);

		} catch (Exception e) {
			rtnInfo.setSuccess(false);
			rtnInfo.setMessage("用户登出异常：" + e.getMessage());
			rtnInfo.setMessageType(Globals.MESSAGE_TYPE_GETDATA_FAILED);
		}
		return rtnInfo;
	}

	private Object generateAdminLoginInfo(String userName, String tokenID) {
		LoginReturnInfo rtnInfo = new LoginReturnInfo();
		User loginAsName = userService.getUserByUsername(userName);

		try {
			//Government govermentInfo =null;
			//govermentInfo = governmentService.selectGovernmentByID(loginAsName.getGovId());
			//loginAsName.setGovernment(govermentInfo);
			rtnInfo.setSuccess(true);
			rtnInfo.setData(loginAsName);
			rtnInfo.setTokenID(tokenID);
			rtnInfo.setMessage("登录用户身份转变为:"+userName);
		} catch (Exception e) {
			rtnInfo.setSuccess(false);
			rtnInfo.setMessage("转变身份异常：" + e.getMessage());
			e.printStackTrace();
		}
		return rtnInfo;
	}







	// 根据tokenID，获取用户token，从token中获取username
	public ReturnInfo queryUsername(GeneralCondition gcon){
		ReturnInfo rtnInfo = new ReturnInfo();
		try {
			UserName userName =new UserName();
			String tokenID = gcon.getTokenID();
			String username="";
			if (tokenID != null && tokenID.length() != 0) {			

				Token token = sysService.checkToken(tokenID);
				if (token!=null) {
					username = token.getUsername();
				} 
			}

			userName.setUsername(username);
			rtnInfo.setData(userName);
			rtnInfo.setSuccess(true);

		} catch (Exception e) {
			rtnInfo.setSuccess(false);
			rtnInfo.setMessage("获取用户名称失败：" + e.getMessage());
			rtnInfo.setMessageType(Globals.MESSAGE_TYPE_SESSION_INVALID);
			return rtnInfo;
		}


		return rtnInfo;
	}


	// 检查请求参数，如果没有带tokenID，返回认证失败
	private boolean checkToken(GeneralCondition gcon)
			throws Exception {

		boolean authorized = true;

		String tokenID = gcon.getTokenID();
		if (tokenID == null || tokenID.length() == 0) {
			authorized = false;
			return authorized;
		}

		if (sysService.checkToken(tokenID)!=null) {
			authorized = true;    		    		
		}else {
			authorized = false;
		} 

		return authorized;

	}

	// 检查用户是否具备执行当前功能的权限
	/*private boolean checkFunctionAuthorize(HttpServletRequest request,GeneralCondition gcon)
				throws Exception {
			String tokenID = gcon.getTokenID();
			String username="";
			if (tokenID != null && tokenID.length() != 0) {			

				Token token = sysService.checkToken(tokenID);
				if (token!=null) {
					username = token.getUsername();
		    	} 
			}
			String functionUrl = request.getServletPath();		
			boolean authorized=false;
			if(functionUrl!=null){
				authorized= functionResourceService.checkFuncRs(functionUrl, username);
			}
			return authorized;

		}*/

	//password加密，返回一个名码／密码对。如果password参数为空则随机生成明码。
	public Map<String,String> generatePassowrdEncoded(String password) {
		Map<String,String> passwordPair =new HashMap();
		String rawPassword = password;
		if (password==null || password.equals("")) {
			String secretKey = UUID.randomUUID().toString();
			//System.out.println(secretKey);
			int pos = secretKey.indexOf("-");
			if (pos>-1){
				rawPassword=secretKey.substring(0, pos);
				System.out.println(rawPassword);
			}
		}	
		StandardPasswordEncoder passwordEncoder = new StandardPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(rawPassword);
		passwordPair.put("password",rawPassword);
		passwordPair.put("passwordEncoded",encodedPassword);
		return passwordPair;
		/*
			boolean isMatche = passwordEncoder
					.matches(rawPassword, encodedPassword);
			System.out.println(isMatche);
		 */
	}

	public static void generatePassWord() {

		String rawPassword = "123456";
		String secretKey = UUID.randomUUID().toString();
		System.out.println(secretKey);
		int pos = secretKey.indexOf("-");
		if (pos>-1){
			rawPassword=secretKey.substring(0, pos);
			System.out.println(rawPassword);
		}
		StandardPasswordEncoder passwordEncoder = new StandardPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(rawPassword);

		System.out.println(encodedPassword);

		boolean isMatche = passwordEncoder
				.matches(rawPassword, encodedPassword);


		System.out.println(isMatche);

	}


	public static void main(String[] args) {

		generatePassWord();
	}

	@Override
	public Object loginAsSome(HttpServletRequest request, GeneralCondition gcon) {
		LoginReturnInfo rtnInfo = new LoginReturnInfo();

		// 如果请求参数中没有tokenID，返回提示用户重新登录的信息
		String tokenID = request.getParameter("tokenID");
		if("".equals(tokenID) || tokenID == null) {
			rtnInfo.setSuccess(false);
			rtnInfo.setMessage("tokenID 不能为空");
			return rtnInfo;
		}
		String userName = request.getParameter("username");
		if("".equals(userName) || userName == null) {
			rtnInfo.setSuccess(false);
			rtnInfo.setMessage("username 不能为空");
			return rtnInfo;
		}
		try {
			Token token = sysService.checkToken(tokenID);
			User user = userService.getUserByUsername(token.getUsername());
			if(user.getUserType() != 1) {
				rtnInfo.setSuccess(false);
				rtnInfo.setMessage("登录用户不是超级用户");
				return rtnInfo;
			}

			User loginAsName = userService.getUserByUsername(userName);

			token.setLoginAsName(userName);
			token.setLoginAsId(loginAsName.getUserId());
			token.setLoginAs(true);		
			/*Government govermentInfo =null;
				govermentInfo = governmentService.selectGovernmentByID(loginAsName.getGovId());*/

			//loginAsName.setGovernment(govermentInfo);
			rtnInfo.setSuccess(true);
			rtnInfo.setData(loginAsName);
			rtnInfo.setTokenID(tokenID);
			rtnInfo.setMessage("登录用户身份转变为:"+userName);

		} catch (Exception e) {
			rtnInfo.setSuccess(false);

			rtnInfo.setMessage("登录用户身份转变处理失败 :"+e.getMessage());
		}
		return rtnInfo;
	}
	@Override
	public String encodePassword(String rawPassword) {
		String password=passwordEncoder.encode(rawPassword);
		return password;
	}

	@Override
	public Customer authenticationCustomer(Customer customer, GeneralCondition gcon, boolean checkPassword) {
		String username = null;
		String rawPassword = null;
		Customer customer2 = null;
		if (customer != null) {
			username = customer.getUsername();
			rawPassword = customer.getPassword();
		}

		boolean loginFailed = false;
		if (username == null || username.length() == 0) {
			loginFailed = true;
		}
		if (checkPassword) {
			if (rawPassword == null || rawPassword.length() == 0) {
				loginFailed = true;
			}
		}
		if (!loginFailed) {
			// 获取登陆用户的信息
			customer2 = customerService.selectByUsername(username);
			if (customer2 == null) {
				loginFailed = true;
			} else {
				String usrName = customer2.getUsername();
				String usrPassword = customer2.getPassword();
				if (usrName == null || !usrName.equals(username)) {
					loginFailed = true;
				}
				if (checkPassword) {
					boolean isMatche = passwordEncoder.matches(rawPassword,
							usrPassword);
					if (!isMatche) {
						loginFailed = true;
					}
				}
			}
		}
		if (loginFailed) {
			return null;
		}
		return customer2;
	}





}
