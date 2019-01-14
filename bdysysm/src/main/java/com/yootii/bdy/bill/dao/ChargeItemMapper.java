package com.yootii.bdy.bill.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yootii.bdy.bill.model.ChargeItem;
import com.yootii.bdy.common.GeneralCondition;

public interface ChargeItemMapper {
    int deleteByPrimaryKey(Integer chargeItemId);

    int insert(ChargeItem record);

    int insertSelective(ChargeItem record);

    ChargeItem selectByPrimaryKey(Integer chargeItemId);

    int updateByPrimaryKeySelective(ChargeItem record);

    int updateByPrimaryKey(ChargeItem record);

	Integer checkName(@Param("chargeItem")ChargeItem chargeItem);

	List<ChargeItem> selectByChargeItem(@Param("chargeItem")ChargeItem chargeItem, @Param("gcon")GeneralCondition gcon);

	Long selectCountByChargeItem(@Param("chargeItem")ChargeItem chargeItem, @Param("gcon")GeneralCondition gcon);
	
	List<ChargeItem> selectChargeItemListById(Integer agencyServiceId);
	
	

}