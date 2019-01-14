package com.yootii.bdy.remind;

import java.util.List;

import org.apache.xmlbeans.impl.xb.xsdschema.impl.PublicImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.remind.dao.RemindMapper;
import com.yootii.bdy.remind.model.Remind;
import com.yootii.bdy.remind.service.RemindService;
import com.yootii.bdy.util.JsonUtil;

@RunWith(SpringJUnit4ClassRunner.class)
//表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = { "classpath:spring-mybatis.xml" })
public class TestRemind {

	
	@Autowired
	private RemindService remindService;
	@Autowired 
	private RemindMapper remindMapper;
	
	@Test
	public void Test1(){
		GeneralCondition gcon = new GeneralCondition();
		gcon.setOffset(10);
		gcon.setPageNo(1);
		Remind remind = new Remind();
		remind.setCustid(5);
		/*remind.setUserid(3);*/
		remindMapper.selectByRemind(remind, gcon);
	}
	
	@Test
	public void test2(){
		Remind remind = new Remind();
		remind.setCustid(5);
		remind.setUserid(4);
		List<Remind> reminds = remindMapper.selectRemindList(remind);
		System.out.println(reminds.size());
	}
	
	@Test
	public void Test3(){
		GeneralCondition gcon = new GeneralCondition();
		gcon.setOffset(10);
		gcon.setPageNo(1);
		Remind remind = new Remind();
		remind.setCustid(5);
		/*remind.setUserid(3);*/
		Long count = remindMapper.selectByRemindCount(remind, gcon);
		System.out.println(count);
	}
	
	@Test
	public void Test4(){
		GeneralCondition gCondition= new GeneralCondition();
		gCondition.setOffset(0);
		gCondition.setRows(10);
		Remind remind = new Remind();
		remind.setCustid(3);
		remind.setCaseId(15780);
		/*List<Remind>list = remindService.selectRemindLists(remind, gCondition);*/
		Object object = remindService.queryRemindList(remind, gCondition);
		if(object!=null){
			System.out.println(JsonUtil.toJson(object));
		}
	}
	
}
