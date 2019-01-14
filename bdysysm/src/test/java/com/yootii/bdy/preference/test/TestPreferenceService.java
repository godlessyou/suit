package com.yootii.bdy.preference.test;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.department.model.Department;
import com.yootii.bdy.preference.model.PreferenceField;
import com.yootii.bdy.preference.model.PreferenceValue;
import com.yootii.bdy.preference.service.PreferenceService;
import com.yootii.bdy.user.model.User;
import com.yootii.bdy.util.JsonUtil;

@RunWith(SpringJUnit4ClassRunner.class)
//表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = { "classpath:spring-mybatis.xml" })
public class TestPreferenceService {
	@Resource
	private PreferenceService preferenceService;
	private static final Logger logger = Logger.getLogger(TestPreferenceService.class);
	
	@Test
	public void queryDeptListTest() {
		PreferenceField preferenceField = new PreferenceField();
		preferenceField.setDataType("int");
		GeneralCondition gcon = new GeneralCondition();
//		gcon.setKeyword("test");
		gcon.setPageNo(1);
		gcon.setOffset(0);
		gcon.setRows(10);
		User caller= new User();
		caller.setUserId(3);
//		caller.setUserType(2);
		Object info = preferenceService.queryPreferenceField(preferenceField, gcon);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("queryUserList测试通过");
		}
	}
	
	//@Test
	public void createDeptListTest() {
		PreferenceField preferenceField = new PreferenceField();
		preferenceField.setDataType("int");
		preferenceField.setDefaultIntValue("1");
		preferenceField.setPreferenceName("测试配置项1");
		GeneralCondition gcon = new GeneralCondition();
//		gcon.setKeyword("test");
		gcon.setPageNo(1);
		gcon.setOffset(0);
		gcon.setRows(10);
		User caller= new User();
		caller.setUserId(3);
//		caller.setUserType(2);
		Object info = preferenceService.createPreferenceField(preferenceField);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("queryUserList测试通过");
		}
	}
	
	//@Test
	public void modifyDeptListTest() {
		PreferenceField preferenceField = new PreferenceField();
		preferenceField.setPreferenceId(23);
		preferenceField.setDataType("int");
		preferenceField.setDefaultIntValue("1");
		preferenceField.setPreferenceName("测试配置项1");
		preferenceField.setPreferenceType("all");
		GeneralCondition gcon = new GeneralCondition();
//		gcon.setKeyword("test");
		gcon.setPageNo(1);
		gcon.setOffset(0);
		gcon.setRows(10);
		User caller= new User();
		caller.setUserId(3);
//		caller.setUserType(2);
		Object info = preferenceService.modifyPreferenceField(preferenceField);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("queryUserList测试通过");
		}
	}
	
	//@Test
	public void deleteDeptListTest() {
		PreferenceField preferenceField = new PreferenceField();
		preferenceField.setPreferenceId(23);
		preferenceField.setDataType("int");
		preferenceField.setDefaultIntValue("1");
		preferenceField.setPreferenceName("测试配置项1");
		preferenceField.setPreferenceType("all");
		GeneralCondition gcon = new GeneralCondition();
//		gcon.setKeyword("test");
		gcon.setPageNo(1);
		gcon.setOffset(0);
		gcon.setRows(10);
		User caller= new User();
		caller.setUserId(3);
//		caller.setUserType(2);
		Object info = preferenceService.deletePreferenceField(preferenceField);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("queryUserList测试通过");
		}
	}
	
	//@Test
	public void createPreferenceValueTest() {

		
		PreferenceValue  preferenceValue = new PreferenceValue();
		preferenceValue.setUserId(1);
		preferenceValue.setPreferenceId(2);
		preferenceValue.setIntValue(123);
		GeneralCondition gcon = new GeneralCondition();
//		gcon.setKeyword("test");
		gcon.setPageNo(1);
		gcon.setOffset(0);
		gcon.setRows(10);
		User caller= new User();
		caller.setUserId(3);
//		caller.setUserType(2);
		Object info = preferenceService.createPreferenceValue(preferenceValue);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("createPreferenceValue测试通过");
		}
	}
	
	
	@Test
	public void queryPreferenceValueTest() {

		
		PreferenceValue  preferenceValue = new PreferenceValue();
		GeneralCondition gcon = new GeneralCondition();
//		gcon.setKeyword("test");
		gcon.setPageNo(1);
		gcon.setOffset(0);
		gcon.setRows(10);

		Object info = preferenceService.queryPreferenceValue(preferenceValue, gcon);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("queryPreferenceValue测试通过");
		}
	}
	
	//@Test
	public void modifyPreferenceValueTest() {

		List<PreferenceValue> p = new ArrayList<>();
		PreferenceValue  preferenceValue1 = new PreferenceValue();
		PreferenceValue  preferenceValue2 = new PreferenceValue();
		preferenceValue1.setId(1);
		preferenceValue1.setStringValue("测试数据1");
		preferenceValue2.setId(2);
		preferenceValue2.setIntValue(321);
		p.add(preferenceValue1);
		p.add(preferenceValue2);
		GeneralCondition gcon = new GeneralCondition();
//		gcon.setKeyword("test");
		gcon.setPageNo(1);
		gcon.setOffset(0);
		gcon.setRows(10);

		Object info = preferenceService.modifyPreferenceValue(p);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("modifyPreferenceValue测试通过");
		}
	}
	
	//@Test
	public void deletePreferenceValueTest() {

		List<PreferenceValue> p = new ArrayList<>();
		PreferenceValue  preferenceValue1 = new PreferenceValue();
		PreferenceValue  preferenceValue2 = new PreferenceValue();
		preferenceValue1.setId(1);
		preferenceValue1.setStringValue("测试数据1");
		preferenceValue2.setId(2);
		preferenceValue2.setIntValue(321);
		p.add(preferenceValue1);
		p.add(preferenceValue2);
		GeneralCondition gcon = new GeneralCondition();
//		gcon.setKeyword("test");
		gcon.setPageNo(1);
		gcon.setOffset(0);
		gcon.setRows(10);

		Object info = preferenceService.deletePreferenceValue(preferenceValue2);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("deletePreferenceValue测试通过");
		}
	}
}
