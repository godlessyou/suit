package com.yootii.bdy.remind.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.remind.model.TrademarkGonggao;

public interface TrademarkGonggaoMapper {

    TrademarkGonggao selectByPrimaryKey(Integer id);
	
	List<TrademarkGonggao> selectByTrademarkSongdagg( @Param("gcon") Integer agencyId, @Param("gcon") Integer custId, @Param("gcon") GeneralCondition gcon);
	
	Long selectcountByTrademarkSongdagg( @Param("gcon") Integer agencyId, @Param("gcon") Integer custId);
	
	List<TrademarkGonggao> selectByTrademarkChushengg( @Param("gcon") Integer agencyId, @Param("gcon") Integer custId, @Param("gcon") GeneralCondition gcon);
	
	Long selectcountByTrademarkChushengg( @Param("gcon") Integer agencyId, @Param("gcon") Integer custId);
}