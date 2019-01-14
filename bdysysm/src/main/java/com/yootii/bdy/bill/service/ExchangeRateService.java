package com.yootii.bdy.bill.service;

import com.yootii.bdy.bill.model.ExchangeRate;
import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.common.ReturnInfo;

public interface ExchangeRateService {

	ReturnInfo queryExchangeRate(ExchangeRate exchangeRate, GeneralCondition gcon);
	
	public ReturnInfo updateExchangeRate();

}
