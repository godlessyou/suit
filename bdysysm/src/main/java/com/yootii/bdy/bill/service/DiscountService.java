package com.yootii.bdy.bill.service;

import com.yootii.bdy.bill.model.Discount;
import com.yootii.bdy.bill.model.ExchangeRate;
import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.common.ReturnInfo;

public interface DiscountService {

	ReturnInfo createDiscount(Discount discount, GeneralCondition gcon);

	ReturnInfo deleteDiscount(Discount discount, GeneralCondition gcon);

	ReturnInfo modifyDiscount(Discount discount, GeneralCondition gcon);

	ReturnInfo queryDiscountList(Discount discount, GeneralCondition gcon, Integer userId, boolean isAgency);

	ReturnInfo queryDiscountDetail(Discount discount, GeneralCondition gcon);

	ReturnInfo createDiscountByValue(Discount discount, GeneralCondition gcon);

}
