package com.yootii.bdy.bill.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yootii.bdy.bill.model.Discount;
import com.yootii.bdy.bill.model.ExchangeRate;
import com.yootii.bdy.common.GeneralCondition;

public interface DiscountMapper {
	int deleteByPrimaryKey(Integer discountId);

    int insert(Discount record);

    int insertSelective(Discount record);

    Discount selectByPrimaryKey(Integer discountId);

    int updateByPrimaryKeySelective(Discount record);

    int updateByPrimaryKey(Discount record);

	List<Discount> selectByDiscount(@Param("discount")Discount discount, @Param("gcon")GeneralCondition gcon, @Param("userId")Integer userId);

	Long selectCountByDiscount(@Param("discount")Discount discount, @Param("gcon")GeneralCondition gcon, @Param("userId")Integer userId);

	List<Discount> selectByDiscountCoAgency(@Param("discount")Discount discount, @Param("gcon")GeneralCondition gcon);

	Long selectCountByDiscountCoAgency(@Param("discount")Discount discount, @Param("gcon")GeneralCondition gcon);
}