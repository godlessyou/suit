package com.yootii.bdy.bill.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yootii.bdy.bill.model.PayAcount;
import com.yootii.bdy.common.GeneralCondition;

public interface PayAcountMapper {
    int deleteByPrimaryKey(Integer payAcountId);

    int insert(PayAcount record);

    int insertSelective(PayAcount record);

    PayAcount selectByPrimaryKey(Integer payAcountId);

    int updateByPrimaryKeySelective(PayAcount record);

    int updateByPrimaryKey(PayAcount record);

	List<PayAcount> selectByPayAcount(@Param("payAcount")PayAcount payAcount, @Param("gcon")GeneralCondition gcon);

	Long selectCountByPayAcount(@Param("payAcount")PayAcount payAcount, @Param("gcon")GeneralCondition gcon);

	//int checkPayAcount(@Param("payAcount")PayAcount payAcount);

	int checkPayAcountByBankAcount(@Param("payAcount")PayAcount payAcount);

	int checkPayAcountByPayAcountName(@Param("payAcount")PayAcount payAcount);
}