package com.yootii.bdy.bill.service;

import com.yootii.bdy.bill.model.PayAcount;
import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.common.ReturnInfo;

public interface PayAcountService {

	ReturnInfo queryPayAcountList(PayAcount payAcount, GeneralCondition gcon);

	ReturnInfo queryPayAcountDetail(PayAcount payAcount, GeneralCondition gcon);

	ReturnInfo createPayAcount(PayAcount payAcount, GeneralCondition gcon);

	ReturnInfo modifyPayAcount(PayAcount payAcount, GeneralCondition gcon);

	ReturnInfo deletePayAcount(PayAcount payAcount, GeneralCondition gcon);

	

}
