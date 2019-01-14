package com.yootii.bdy.bill.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yootii.bdy.bill.model.ExchangeRate;
import com.yootii.bdy.common.GeneralCondition;

public interface ExchangeRateMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ExchangeRate record);

    int insertSelective(ExchangeRate record);

    ExchangeRate selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ExchangeRate record);

    int updateByPrimaryKey(ExchangeRate record);

	List<ExchangeRate> selectByExchangeRate(@Param("exchangeRate")ExchangeRate exchangeRate, @Param("gcon")GeneralCondition gcon);

	Long selectCountByExchangeRate(@Param("exchangeRate")ExchangeRate exchangeRate, @Param("gcon")GeneralCondition gcon);
	
	int updateByCurrCodeBatch(List<ExchangeRate> rates);
}