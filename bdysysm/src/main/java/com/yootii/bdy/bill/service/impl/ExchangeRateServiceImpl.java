package com.yootii.bdy.bill.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yootii.bdy.bill.dao.ExchangeRateMapper;
import com.yootii.bdy.bill.model.ExchangeRate;
import com.yootii.bdy.bill.service.ExchangeRateService;
import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.common.ReturnInfo;
import com.yootii.bdy.util.GraspUtil;
@Service
public class ExchangeRateServiceImpl implements ExchangeRateService{
	
	@Resource
	private ExchangeRateMapper exchangeRateMapper;
	
	@Override
	public ReturnInfo queryExchangeRate(ExchangeRate exchangeRate, GeneralCondition gcon) {
		ReturnInfo rtnInfo = new ReturnInfo();
		if (gcon.getPageSize() <= 0) {
			gcon.setPageSize(10);
		}
		if (gcon.getPageNo() <= 0) {
			gcon.setPageNo(1);
		}
		List<ExchangeRate> exchangeRates = new ArrayList<ExchangeRate>();
		exchangeRates=exchangeRateMapper.selectByExchangeRate(exchangeRate,gcon);
		Long total = exchangeRateMapper.selectCountByExchangeRate(exchangeRate,gcon);
		rtnInfo.setData(exchangeRates);
		rtnInfo.setTotal(total);
		rtnInfo.setCurrPage(gcon.getPageNo());
		rtnInfo.setSuccess(true);
		rtnInfo.setMessageType(4);
		rtnInfo.setMessage("查询汇率成功");
		return rtnInfo;
	}
	@Override
	public ReturnInfo updateExchangeRate(){
		ReturnInfo rtnInfo = new ReturnInfo();
		Long time = System.currentTimeMillis();
		String url="http://www.chinamoney.com.cn/r/cms/www/chinamoney/data/fx/rfx-sp-quot.json?t="+time;
		String jsonString = null;
		try {
			jsonString = GraspUtil.getText(url);
			JSONObject json = JSONObject.parseObject(jsonString);//将json字符串转换为json对象
//			System.out.println(JSONObject.toJSONString(json, true));  
			JSONObject array = json.getJSONObject("data");
			String date = array.getString("showDateCN");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date updateTime = sdf.parse(date);
			JSONArray records = json.getJSONArray("records");
			List<ExchangeRate> rates = new ArrayList<ExchangeRate>();
			if(records!=null&&records.size()>0){
				for(int i=0;i<records.size();i++){
					JSONObject record = records.getJSONObject(i);
					String ccyPair = record.getString("ccyPair");//货币对
					String bidPrc = record.getString("bidPrc");//买报价
					//解析 插入数据库
					String[] ccs = ccyPair.split("/");
					BigDecimal value = new BigDecimal(0);
					String currCode = "";
					if(ccs!=null&&ccs.length>0){
						if(ccs[1].equals("CNY")){
							
							if(ccs[0].equals("100JPY")){
								value = new BigDecimal(bidPrc);
								currCode = "JPY";
							}else{
								BigDecimal bd = new BigDecimal(bidPrc);
								value = bd.multiply(new BigDecimal(100));
								currCode =ccs[0];
							}
							
						}else {
							Double dd = Double.parseDouble(bidPrc);
							value =new BigDecimal(100.0/dd);
							currCode =ccs[1];
						}
					}
					ExchangeRate rate = new ExchangeRate();
					rate.setCurrCode(currCode);
					rate.setValue(value);
					rate.setUpdateTime(updateTime);
					rates.add(rate);
				}
			}
			//批量更新数据库
			exchangeRateMapper.updateByCurrCodeBatch(rates);
		} catch (Exception e) {
			e.printStackTrace();
			rtnInfo.setSuccess(false);
			rtnInfo.setMessage("更新汇率失败");
			return rtnInfo;
		} 
		rtnInfo.setSuccess(true);
		rtnInfo.setMessage("更新汇率成功");
		return rtnInfo;
	}

}
