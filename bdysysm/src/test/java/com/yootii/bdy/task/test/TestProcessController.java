package com.yootii.bdy.task.test;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yootii.bdy.agency.model.Agency;
import com.yootii.bdy.agency.service.AgencyService;
import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.common.LoginReturnInfo;
import com.yootii.bdy.common.ReturnInfo;
import com.yootii.bdy.customer.model.Customer;
import com.yootii.bdy.invite.model.AgencyInvite;
import com.yootii.bdy.invite.model.CustomerInvite;
import com.yootii.bdy.invite.service.AgencyInviteService;
import com.yootii.bdy.process.service.ProcessService;
import com.yootii.bdy.util.ProcessEnginUrlConfig;
import com.yootii.bdy.security.service.AuthenticationService;
import com.yootii.bdy.task.service.AgentOpenTaskService;
import com.yootii.bdy.task.service.CustOpenTaskService;
import com.yootii.bdy.user.model.User;
import com.yootii.bdy.util.JsonUtil;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})
public class TestProcessController{
	@Resource
	private AgentOpenTaskService taskService;
	
	@Resource
	private CustOpenTaskService custOpenTaskService;
	@Resource	
	private ProcessService processService;
	
	@Resource	
	private ProcessEnginUrlConfig processEnginUrlConfig;
	
	@Resource	
	private AgentOpenTaskService agentOpenTaskService;
	
	@Resource
	private AuthenticationService authenticationService;
	
	
//	@Test
	public void testProcessStart() {
		
		String prokey="qingjia";
		
		Map<String,Object> runMap=new HashMap<String,Object>();
		
		String url=processEnginUrlConfig.getProcessEngineUrl();
		
		System.out.println(url);
		
//		processService.startProcessByKey(prokey, runMap);
	}
	
//	@Test
	public void testDoCo() {
		User user = new User();
		CustomerInvite customerInvite = new CustomerInvite();
		user.setUserId(3);
		customerInvite.setName("嘻嘻嘻公司");
		customerInvite.setEmail("zhangziwei@ipshine.com");
		customerInvite.setAgencyId(1);
		Customer customer = new Customer();
		customer.setName("嘻嘻嘻公司");
		customer.setEmail("zhangziwei@ipshine.com");
		customer.setAddress("北京中关村");
		customer.setCountry("中国");
		customer.setSex("女");
		customer.setPhone("123456");
		customer.setFullname("测试");
		
		String agencyContactId="13";
		
		custOpenTaskService.startProCustOpen(null, user, customerInvite, customer, agencyContactId);
	}
	
	
//	@Test
	public void testCheck() {
	
		Integer trId=216;
		
		User user2 = new User();
		user2.setUserId(3);
		
		custOpenTaskService.doTaskCOReview(null, trId, user2, true);
	}
	
	
//	@Test
	public void testDoAO() {
		User user = new User();		
		user.setEmail("chenshengdong@ipshine.com");
		
		Agency agency=new Agency();
		agency.setAddress("测试地址");
		agency.setName("测试名称");
		agency.setTel("测试tel");
		
		AgencyInvite agencyInvite= new AgencyInvite();
		agencyInvite.setEmail("chenshengdong@ipshine.com");
		
		
		agentOpenTaskService.startProAgentOpen(null, user, agency, agencyInvite, null, "1");
	}
	
	//@Test
	public void validCallerTest(){
		String username = "admin";
		String password = "123456";
		Object info ;
		try{
			info= custOpenTaskService.validCaller(username, password);
			if(info != null){
				System.err.println(JsonUtil.toJson(info));
			}
		}catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
	//@Test
	public void addCustomerTest(){
		
		String username = "mmq";
		String password = "123456";
		String email = "3269585@qq.com";
		String phone =null;
		Object info;
		try{
			info = custOpenTaskService.addCustomer(username, email, password, phone);
			if(info !=null){
				System.err.println(JsonUtil.toJson(info));
				System.err.println("测试完毕");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	//@Test
	public void updateCustomerTest(){
		
		Customer customer = new Customer();
		customer.setId(3692);
		customer.setName("宝能有限公司");
		customer.setAddress("北京市海淀区莲花路1弄");
		customer.setCountry("中国");
		Object info;
		try{
			info = custOpenTaskService.updateCustomer(customer);
			if(info !=null){
				System.err.println(JsonUtil.toJson(info));
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testDiscount() {
		
		User user2 = new User();
		user2.setUserId(3);
		Double discount=0.8;
		Integer trId=240;
	
		
//		custOpenTaskService.doTaskCOFinancial(null, trId, user2, discount);
	}
	
	
	
//	@Test
	public void testdoAO() {
		
//		Map<String,Object> runMap = new HashMap<String,Object>();
//		
//		User user = new User();
//		user.setUserId(1);
//		AgencyInvite agencyInvite = new AgencyInvite();
//		agencyInvite.setEmail("826249375@qq.com");
//		agencyInvite.setName("chen");
//		ReturnInfo ao1 = taskService.startProAgentOpen(null, user, agencyInvite);
//		
//		System.out.println(JsonUtil.toJson(ao1));
//		
//		Map<String,Object> retMap = (Map<String, Object>) ao1.getData();
//		
//		ReturnInfo ao2 = taskService.proAgentOpencheck(null,retMap.get("ProcessInstanceId").toString() , null);
//		
//		System.out.println(JsonUtil.toJson(ao2));
//		
//		User user1 = new User();
//		user1.setFullname("test000111");
//		user1.setUsername("test01");		
//		user1.setPassword("123456");
//		user1.setEmail("0000@00.com");	
//		
//		Agency agency = new Agency();
//		agency.setName("testAgency000111");
//		
//		ReturnInfo ao3 = taskService.doTaskAOEmail(user1, agency, retMap.get("ProcessInstanceId").toString());
//		
//		System.out.println(JsonUtil.toJson(ao3));
	
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