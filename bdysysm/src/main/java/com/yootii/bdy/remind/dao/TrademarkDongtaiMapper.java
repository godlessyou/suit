package com.yootii.bdy.remind.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.remind.model.TrademarkDongtai;

public interface TrademarkDongtaiMapper {

	TrademarkDongtai selectByPrimaryKey(Integer id);
	
	List<TrademarkDongtai> selectByTrademarkDongtai( @Param("gcon") Integer agencyId, @Param("gcon") Integer custId, @Param("gcon") GeneralCondition gcon);
	
	Long selectcountByTrademarkDongtai( @Param("gcon") Integer agencyId, @Param("gcon") Integer custId);

}