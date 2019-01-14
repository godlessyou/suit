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

import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.customer.model.Customer;
import com.yootii.bdy.customer.model.CustomerContact;
import com.yootii.bdy.customer.service.CustomerContactService;
import com.yootii.bdy.customer.service.CustomerResetPassWordService;
import com.yootii.bdy.customer.service.CustomerService;
import com.yootii.bdy.department.model.Department;
import com.yootii.bdy.department.service.DepartmentService;
import com.yootii.bdy.role.model.Role;
import com.yootii.bdy.user.model.User;
import com.yootii.bdy.user.service.UserService;
import com.yootii.bdy.util.JsonUtil;


@RunWith(SpringJUnit4ClassRunner.class)
//表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = { "classpath:spring-mybatis.xml" })
public class TestCustomerContactService {
	@Resource
	private CustomerContactService customerContactService;
	
	@Resource
	private CustomerResetPassWordService customerResetPassWordService;
	
	private static final Logger logger = Logger.getLogger(TestCustomerContactService.class);

	private MockHttpServletRequest request= new MockHttpServletRequest();
	
	
	@Test
	public void queryCustomerContactTest() {
		
		CustomerContact customerContact=new CustomerContact();
		customerContact.setCustId(5);
		GeneralCondition gcon = new GeneralCondition();
		gcon.setOffset(0);
		gcon.setRows(10);
		Object info = customerContactService.queryCustomerContact(customerContact, gcon);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("queryCustomerContactTest测试通过");
		}
	}
//	@Test
	public void createCustomerContactTest() {
		CustomerContact customerContact=new CustomerContact();
		customerContact.setName("hhhh1");
		customerContact.setAddress("颐园");
		customerContact.setTel("13833333333");
		customerContact.setEmail("xxx@xx.com");
		customerContact.setPostcode("044000");
		customerContact.setCustId(1);
		GeneralCondition gcon = new GeneralCondition();
		Object info = customerContactService.ceateCustomerContact(customerContact);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("createCustomerContactTest测试通过");
		}
	}
//	@Test
	public void modifyCustomerContactTest() {
		CustomerContact customerContact=new CustomerContact();
		customerContact.setId(1);
		customerContact.setName("联系人1");
		customerContact.setAddress("友谊宾馆");
		customerContact.setTel("13833333333");
		customerContact.setEmail("xxx@xx.com");
		customerContact.setPostcode("055000");
		customerContact.setCustId(1);
		GeneralCondition gcon = new GeneralCondition();
		Object info = customerContactService.motifyCustomerContact(customerContact);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("modifyCustomerContactTest测试通过");
		}
	}
	
//	@Test
	public void deleteCustomerContactTest() {
		CustomerContact customerContact=new CustomerContact();
		customerContact.setId(2);
		customerContact.setName("联系人1");
		customerContact.setAddress("友谊宾馆");
		customerContact.setTel("13833333333");
		customerContact.setEmail("xxx@xx.com");
		customerContact.setPostcode("055000");
		customerContact.setCustId(1);
		GeneralCondition gcon = new GeneralCondition();
		Object info = customerContactService.deleteCustomerContact(customerContact);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("modifyCustomerContactTest测试通过");
		}
	}
}
