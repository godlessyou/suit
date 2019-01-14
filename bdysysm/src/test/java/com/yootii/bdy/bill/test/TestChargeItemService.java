package com.yootii.bdy.bill.test;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yootii.bdy.bill.model.ChargeItem;
import com.yootii.bdy.bill.model.Discount;
import com.yootii.bdy.bill.model.ExchangeRate;
import com.yootii.bdy.bill.model.PayAcount;
import com.yootii.bdy.bill.service.ChargeItemService;
import com.yootii.bdy.bill.service.DiscountService;
import com.yootii.bdy.bill.service.ExchangeRateService;
import com.yootii.bdy.bill.service.PayAcountService;
import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.util.JsonUtil;

@RunWith(SpringJUnit4ClassRunner.class)
//表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = { "classpath:spring-mybatis.xml" })
public class TestChargeItemService {
	
	@Resource
	private ChargeItemService chargeItemService;
	
	@Resource
	private ExchangeRateService exchangeRateService;
	
	@Resource
	private DiscountService discountService;
	
	@Resource
	private PayAcountService payAcountService;
	
	private static final Logger logger = Logger.getLogger(TestChargeItemService.class);
	
	@Test
	public void queryChargeItemListTest() {
		ChargeItem chargeItem =new ChargeItem();
		String caseType="商标注册";		
		String chargeType="官费";
		chargeItem.setCaseType(caseType);
		chargeItem.setChargeType(chargeType);	
		chargeItem.setAgencyId(1);
		//chargeItem.setChargeItemId(1);
		GeneralCondition gcon = new GeneralCondition();
//		gcon.setKeyword("官费");
//		gcon.setKeyword("test");
		gcon.setPageNo(1);
		gcon.setOffset(0);
		gcon.setRows(10);
		//User caller= new User();
		//caller.setUserId(3);
//		caller.setUserType(2);
		Object info = null;
		info = chargeItemService.queryChargeItemList(gcon, chargeItem);
		//info=chargeItemService.queryChargeItemDetail(gcon, chargeItem);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("queryChargeItemListTest测试通过");
		}
	}
	//@Test
	public void createChargeItemTest() {
		ChargeItem chargeItem =new ChargeItem();
		//chargeItem.setChargeItemId(1);
		//chargeItem.setAgencyId(1);
		chargeItem.setCaseType("行政诉讼");
		chargeItem.setChargeType("服务费");
		chargeItem.setChnName("呵呵呵");
		chargeItem.setPrice(new BigDecimal(200));
		GeneralCondition gcon = new GeneralCondition();
		gcon.setKeyword("sss");
//		gcon.setKeyword("test");
		gcon.setPageNo(1);
		gcon.setOffset(0);
		gcon.setRows(10);
		//User caller= new User();
		//caller.setUserId(3);
//		caller.setUserType(2);
		Object info = null;
		//info = chargeItemService.queryChargeItemList(gcon, chargeItem);
		info=chargeItemService.createChargeItem(gcon, chargeItem);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("createChargeItemTest测试通过");
		}
	}
	//@Test
	public void deleteChargeItemTest() {
		ChargeItem chargeItem =new ChargeItem();
		chargeItem.setChargeItemId(3);
		//chargeItem.setAgencyId(1);
		chargeItem.setCaseType("行政诉讼");
		chargeItem.setChargeType("服务费");
		chargeItem.setChnName("呵呵呵");
		chargeItem.setPrice(new BigDecimal(200));
		GeneralCondition gcon = new GeneralCondition();
		gcon.setKeyword("sss");
//		gcon.setKeyword("test");
		gcon.setPageNo(1);
		gcon.setOffset(0);
		gcon.setRows(10);
		//User caller= new User();
		//caller.setUserId(3);
//		caller.setUserType(2);
		Object info = null;
		//info = chargeItemService.queryChargeItemList(gcon, chargeItem);
		info=chargeItemService.deleteChargeItem(gcon, chargeItem);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("deleteChargeItemTest测试通过");
		}
	}
	
	//@Test
	public void modifyChargeItemTest() {
		ChargeItem chargeItem =new ChargeItem();
		chargeItem.setChargeItemId(1);
		//chargeItem.setAgencyId(1);
		//chargeItem.setCaseType("行政诉讼");
		//chargeItem.setChargeType("服务费");
		chargeItem.setChnName("收费项1");
		chargeItem.setPrice(new BigDecimal(200));
		GeneralCondition gcon = new GeneralCondition();
		gcon.setKeyword("sss");
//		gcon.setKeyword("test");
		gcon.setPageNo(1);
		gcon.setOffset(0);
		gcon.setRows(10);
		//User caller= new User();
		//caller.setUserId(3);
//		caller.setUserType(2);
		Object info = null;
		//info = chargeItemService.queryChargeItemList(gcon, chargeItem);
		info=chargeItemService.modifyChargeItem(gcon, chargeItem);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("modifyChargeItem测试通过");
		}
	}
	
	//@Test
	public void queryExchangeRateTest() {
		ExchangeRate e =new ExchangeRate();
		e.setCurrency1("人民币");
		GeneralCondition gcon = new GeneralCondition();
		gcon.setKeyword("sss");
//		gcon.setKeyword("test");
		gcon.setPageNo(1);
		gcon.setOffset(0);
		gcon.setRows(10);
		//User caller= new User();
		//caller.setUserId(3);
//		caller.setUserType(2);
		Object info = null;
		//info = chargeItemService.queryChargeItemList(gcon, chargeItem);
		info=exchangeRateService.queryExchangeRate(e, gcon);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("modifyChargeItem测试通过");
		}
	}
//	@Test
	public void updateExchangeRateTest() {
		exchangeRateService.updateExchangeRate();
	}
	
	//@Test
	public void createDiscountTest() {
		Discount d =new Discount();
		d.setAgencyId("1");
		d.setCustId(1);
		d.setStatus(1);
		d.setValue(new BigDecimal(0.8));
		GeneralCondition gcon = new GeneralCondition();
		gcon.setKeyword("sss");
//		gcon.setKeyword("test");
		gcon.setPageNo(1);
		gcon.setOffset(0);
		gcon.setRows(10);
		//User caller= new User();
		//caller.setUserId(3);
//		caller.setUserType(2);
		Object info = null;
		//info = chargeItemService.queryChargeItemList(gcon, chargeItem);
		info=discountService.createDiscount(d, gcon);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("createDiscount测试通过");
		}
	}
	
	//@Test
	public void deleteDiscountTest() {
		Discount d =new Discount();
		d.setDiscountId(2);
		d.setAgencyId("1");
		d.setCustId(1);
		d.setStatus(1);
		d.setValue(new BigDecimal(0.8));
		GeneralCondition gcon = new GeneralCondition();
		gcon.setKeyword("sss");
//		gcon.setKeyword("test");
		gcon.setPageNo(1);
		gcon.setOffset(0);
		gcon.setRows(10);
		//User caller= new User();
		//caller.setUserId(3);
//		caller.setUserType(2);
		Object info = null;
		//info = chargeItemService.queryChargeItemList(gcon, chargeItem);
		info=discountService.deleteDiscount(d, gcon);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("deleteDiscount测试通过");
		}
	}
	//@Test
	public void modifyDiscountTest() {
		Discount d =new Discount();
		d.setDiscountId(1);
		d.setAgencyId("1");
		d.setCustId(1);
		d.setStatus(1);
		d.setValue(new BigDecimal(0.81));
		GeneralCondition gcon = new GeneralCondition();
		gcon.setKeyword("sss");
//		gcon.setKeyword("test");
		gcon.setPageNo(1);
		gcon.setOffset(0);
		gcon.setRows(10);
		//User caller= new User();
		//caller.setUserId(3);
//		caller.setUserType(2);
		Object info = null;
		//info = chargeItemService.queryChargeItemList(gcon, chargeItem);
		info=discountService.modifyDiscount(d, gcon);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("modifyDiscount测试通过");
		}
	}
	@Test
	public void queryDiscountListTest() {
		Discount d =new Discount();
		//d.setDiscountId(1);
		d.setAgencyId("1");
		d.setCustId(2);
		//d.setStatus(1);
		//d.setValue(new BigDecimal(0.81));
		GeneralCondition gcon = new GeneralCondition();
	//	gcon.setKeyword("商标");
//		gcon.setKeyword("test");
		gcon.setPageNo(1);
		gcon.setOffset(0);
		gcon.setRows(10);
		//User caller= new User();
		//caller.setUserId(3);
//		caller.setUserType(2);
		Object info = null;
		//info = chargeItemService.queryChargeItemList(gcon, chargeItem);
		info=discountService.queryDiscountList(d, gcon,3,true);
		//info=discountService.queryDiscountDetail(d, gcon);
		if (info != null) {
			logger.info(JsonUtil.toJson(info));
			logger.info("queryDiscountList测试通过");
		}
	}
	
	//@Test
		public void createPayAcountTest() {
			PayAcount payAcount =new PayAcount();
			payAcount.setAgencyId(1);
			payAcount.setBankName("中信银行");
			payAcount.setBankAcountAddress("北京");
			payAcount.setPayAcountName("支付账户2");
			payAcount.setBankAcountName("银行账户1");
			payAcount.setBankAcount("1111210182200032551");
			GeneralCondition gcon = new GeneralCondition();
			gcon.setKeyword("sss");
//			gcon.setKeyword("test");
			gcon.setPageNo(1);
			gcon.setOffset(0);
			gcon.setRows(10);
			//User caller= new User();
			//caller.setUserId(3);
//			caller.setUserType(2);
			Object info = null;
			//info = chargeItemService.queryChargeItemList(gcon, chargeItem);
			info=payAcountService.createPayAcount(payAcount, gcon);
				logger.info(JsonUtil.toJson(info));
				logger.info("createDiscount测试通过");
			}
	//@Test
	public void modifyPayAcountTest() {
		PayAcount payAcount =new PayAcount();
		payAcount.setPayAcountId(3);
		payAcount.setAgencyId(1);
		payAcount.setBankName("中信银行");
		payAcount.setBankAcountAddress("北京");
		payAcount.setPayAcountName("支付账户2");
		payAcount.setBankAcountName("银行账户2");
		payAcount.setBankAcount("1111210182200032551");
		GeneralCondition gcon = new GeneralCondition();
		gcon.setKeyword("sss");
//		gcon.setKeyword("test");
		gcon.setPageNo(1);
		gcon.setOffset(0);
		gcon.setRows(10);
		//User caller= new User();
		//caller.setUserId(3);
//		caller.setUserType(2);
		Object info = null;
		//info = chargeItemService.queryChargeItemList(gcon, chargeItem);
		info=payAcountService.modifyPayAcount(payAcount, gcon);
			logger.info(JsonUtil.toJson(info));
			logger.info("createDiscount测试通过");
		}
	//@Test
	public void deletePayAcountTest() {
		PayAcount payAcount =new PayAcount();
		payAcount.setPayAcountId(3);
		payAcount.setAgencyId(1);
		payAcount.setBankName("中信银行");
		payAcount.setBankAcountAddress("北京");
		payAcount.setPayAcountName("支付账户2");
		payAcount.setBankAcountName("银行账户2");
		payAcount.setBankAcount("1111210182200032551");
		GeneralCondition gcon = new GeneralCondition();
		gcon.setKeyword("sss");
//		gcon.setKeyword("test");
		gcon.setPageNo(1);
		gcon.setOffset(0);
		gcon.setRows(10);
		//User caller= new User();
		//caller.setUserId(3);
//		caller.setUserType(2);
		Object info = null;
		//info = chargeItemService.queryChargeItemList(gcon, chargeItem);
		info=payAcountService.deletePayAcount(payAcount, gcon);
			logger.info(JsonUtil.toJson(info));
			logger.info("createDiscount测试通过");
		}
	@Test
	public void queryPayAcountListTest() {
		PayAcount payAcount =new PayAcount();
		payAcount.setPayAcountId(1);
		payAcount.setAgencyId(1);
		payAcount.setBankName("中信银行");
		payAcount.setBankAcountAddress("北京");
		payAcount.setPayAcountName("支付账户");
		payAcount.setBankAcountName("银行账户");
		payAcount.setBankAcount("1111210182200032551");
		GeneralCondition gcon = new GeneralCondition();
		//gcon.setKeyword("sss");
//		gcon.setKeyword("test");
		gcon.setPageNo(1);
		gcon.setOffset(0);
		gcon.setRows(10);
		//User caller= new User();
		//caller.setUserId(3);
//		caller.setUserType(2);
		Object info = null;
		//info = chargeItemService.queryChargeItemList(gcon, chargeItem);
		info=payAcountService.queryPayAcountList(payAcount, gcon);
		//info=payAcountService.queryPayAcountDetail(payAcount, gcon);
			logger.info(JsonUtil.toJson(info));
			logger.info("queryPayAcountListTest测试通过");
		}
}
