package com.yootii.bdy.remind.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.remind.model.TrademarkXuzhan;

public interface TrademarkXuzhanMapper {

    TrademarkXuzhan selectByPrimaryKey(Integer id);
	
	List<TrademarkXuzhan> selectByTrademarkXuzhan( @Param("gcon") Integer agencyId, @Param("gcon") Integer custId, @Param("gcon") GeneralCondition gcon);
	
	Long selectcountByTrademarkXuzhan( @Param("gcon") Integer agencyId, @Param("gcon") Integer custId);
}