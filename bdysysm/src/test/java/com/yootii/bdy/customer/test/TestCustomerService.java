package com.yootii.bdy.customer.test;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONObject;
import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.common.LoginReturnInfo;
import com.yootii.bdy.customer.dao.RegionMapper;
import com.yootii.bdy.customer.model.Customer;
import com.yootii.bdy.customer.service.CustomerResetPassWordService;
import com.yootii.bdy.customer.service.CustomerService;
import com.yootii.bdy.department.model.Department;
import com.yootii.bdy.department.service.DepartmentService;
import com.yootii.bdy.role.model.Role;
import com.yootii.bdy.security.service.AuthenticationService;
import com.yootii.bdy.user.model.User;
import com.yootii.bdy.user.service.UserService;
import com.yootii.bdy.util.JsonUtil;


@RunWith(SpringJUnit4ClassRunner.class)
//表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = { "classpath:spring-mybatis.xml" })
public class TestCustomerService {
	@Resource
	private CustomerService customerService;
	
	@Resource
	private CustomerResetPassWordService customerResetPassWordService;
	
	private static final Logger logger = Logger.getLogger(TestCustomerService.class);

	private MockHttpServletRequest request= new MockHttpServletRequest();
	
	@Resource
	private RegionMapper regionMapper;
	
	@Resource
	private AuthenticationService authenticationService;
	
//	@Test
	public void queryRegionListTest() {
	
		Object info = regionMapper.selectRegions();
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("queryRegionListTest测试通过");
		}
	}
	
	
//	@Test
	public void queryCustomerRegionTest() {
		User caller= new User();
		caller.setUserId(81);
		caller.setUserType(10);
		Object info = customerService.queryCustomerRegion(caller);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("queryCustomerRegionTest测试通过");
		}
	}
	
	
	@Test
	public void queryCustomerOwnRegionTest() {
		User caller= new User();
		caller.setUserId(3);
		caller.setUserType(10);
		int level=1;
		Object info = customerService.queryCustomerOwnRegion(caller, level);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("queryCustomerOwnRegionTest测试通过");
		}
	}
	
	
	
//	@Test
	public void queryCustomerListTest() {
		Customer customer = new Customer();
		customer.setRegionId(987654321);
//		customer.setId(1);
		GeneralCondition gcon = new GeneralCondition();
//		gcon.setKeyword("test");
		gcon.setPageNo(1);
		gcon.setOffset(0);
		gcon.setRows(10);
		User caller= new User();
		caller.setUserId(2);
		caller.setUserType(10);
		
		Object info = customerService.queryCustomer(customer, caller, gcon);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("queryUserList测试通过");
		}
	}
	
	
	
	
//	@Test
	public void getCustDetailByIdTest() {

		Integer id=1;
		Object info = customerService.getCustDetailById(id);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("queryUserList测试通过");
		}
	}
	
	
	
	
//	@Test
	public void queryOwnCustomerListTest() {
		Customer customer = new Customer();
		customer.setRegionId(197);
//		customer.setId(1);
		GeneralCondition gcon = new GeneralCondition();
//		gcon.setKeyword("test");
		gcon.setPageNo(1);
		gcon.setOffset(0);
		gcon.setRows(10);
		User caller= new User();
		caller.setUserId(3);
		caller.setUserType(2);
		int level=1;
		Object info = customerService.queryOwnCustomer(customer, caller, gcon, level);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("queryUserList测试通过");
		}
	}
	
//	@Test
	public void querCustomerUserListTest() {
		Customer customer = new Customer();
		customer.setId(1);
		GeneralCondition gcon = new GeneralCondition();
//		gcon.setKeyword("test");
		gcon.setPageNo(1);
		gcon.setOffset(0);
		gcon.setRows(10);
		Object info = customerService.queryCustomerUser(1, gcon);
		String json = JsonUtil.toJson(info);
		
		
		/*Customer cus = JsonUtil.toObject(js,Customer.class);
		List<User> user = cus.getUsers();*/
		/*for(User u:user){
			System.out.println(u.getFullname());
		}*/
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("queryUserList测试通过");
		}
	}
//	@Test
	public void createCustomerTest() {
		Customer customer = new Customer();
		customer.setEmail("zhangziwei@ipshine.com");
		customer.setUsername("zz");
		customer.setPassword("123456");
		customer.setName("北京某某公司");
		customer.setAddress("北京市海淀区中关村南大街");
		customer.setCountry("中国");
		GeneralCondition gcon = new GeneralCondition();
//		gcon.setKeyword("test");
		gcon.setPageNo(1);
		gcon.setOffset(0);
		gcon.setRows(10);
		User caller= new User();
		caller.setUserId(3);
		caller.setUsername("whd_wangfang");
		caller.setPassword("123456");
		
		String tokenID =login(caller);
		gcon.setTokenID(tokenID);
		
//		caller.setUserType(2);
		Object info = customerService.createCustomer(customer, gcon, 1, 3);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("createCustomerTest测试通过");
		}
	}
//	@Test
	public void checkUsernameTest() {
		Object info = customerService.checkUsername("dd");
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("queryUserList测试通过");
		}
	}
//	@Test
	public void checkCompanyNameTest() {
		Object info = customerService.checkCompanyName("北京公司");
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("queryUserList测试通过");
		}
	}
//	@Test
	public void bindAgencyTest() {
		Object info = customerService.bindAgency(5, 6, 3);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("queryUserList测试通过");
		}
	}
//	@Test
	public void unbindAgencyTest() {
		Object info = customerService.unbindAgency(6, 5);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("queryUserList测试通过");
		}
	}
//	@Test
	public void modifyCustomerTest() {
		Customer customer = new Customer();
		customer.setId(5);
		customer.setEmail("zhangziwei@ipshine.com");
		customer.setUsername("zz");
//		customer.setPassword("123456");
		customer.setName("北京zz公司");
		customer.setAddress("海淀区中关村南大街");
		customer.setCountry("中国");
		GeneralCondition gcon = new GeneralCondition();
//		gcon.setKeyword("test");
		gcon.setPageNo(1);
		gcon.setOffset(0);
		gcon.setRows(10);
		User caller= new User();
		caller.setUserId(3);
//		caller.setUserType(2);
		Object info = customerService.modifyCustomerSelf(customer);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("queryUserList测试通过");
		}
	}
//	@Test
	public void validCustTest() {
		Customer customer = new Customer();
		customer.setId(5);
		customer.setEmail("810734009@qq.com");
		customer.setUsername("zz");
//		customer.setPassword("123456");
		GeneralCondition gcon = new GeneralCondition();
		User caller= new User();
		Object info = customerResetPassWordService.validUser(request, customer, gcon);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("queryUserList测试通过");
		}
	}
	
//	@Test
	public void toFindPasswordTest() {
		request.setParameter("sid", "47262D25B9C7A621A11937A5D4F97B4D");
		request.setParameter("username", "zz");
		Object info = customerResetPassWordService.toFindPassword(request);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("queryUserList测试通过");
		}
	}
//	@Test
	public void resetPasswordTest() {
		request.setParameter("sid", "836FC1C62B7B94E7E02A459FFD3F08B1");
		request.setParameter("username", "zz");
		request.setParameter("password", "123123");
		GeneralCondition gcon = new GeneralCondition();
		Object info = customerResetPassWordService.resetPassword(request, gcon);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("queryUserList测试通过");
		}
	}
//	@Test
	public void bindRoleTest() {
		Object info = customerService.bindRole(5, 6);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("bindRoleTest测试通过");
		}
	}
//	@Test
	public void unbindRoleTest() {
		Object info = customerService.unbindRole(5, 6);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("unbindRoleTest测试通过");
		}
	}
//	@Test
	public void queryAllCustomerTest() {
		Object info = customerService.queryAllCustomer();
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("unbindRoleTest测试通过");
		}
	}
//	@Test
	public void sendEmailTest() {
		Object info = customerService.sendAccountEmail(29);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("unbindRoleTest测试通过");
		}
	}
	
	private String login(User user) {
		GeneralCondition gcon = new GeneralCondition();
		Object obj = authenticationService.login(user, gcon);		
		LoginReturnInfo rtnInfo = (LoginReturnInfo)obj;		
		String tokenID = rtnInfo.getTokenID();		
		return tokenID;
		
	}
}
