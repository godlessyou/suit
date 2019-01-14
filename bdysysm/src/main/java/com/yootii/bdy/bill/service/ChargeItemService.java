package com.yootii.bdy.bill.service;

import com.yootii.bdy.bill.model.ChargeItem;
import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.common.ReturnInfo;

public interface ChargeItemService {
	public ReturnInfo createChargeItem(GeneralCondition gcon,ChargeItem chargeItem);
	public ReturnInfo deleteChargeItem(GeneralCondition gcon,ChargeItem chargeItem);
	public ReturnInfo modifyChargeItem(GeneralCondition gcon,ChargeItem chargeItem);
	public ReturnInfo queryChargeItemList(GeneralCondition gcon,ChargeItem chargeItem);
	public ReturnInfo queryChargeItemDetail(GeneralCondition gcon,ChargeItem chargeItem);
	
	public ReturnInfo queryChargeItemListById(String agencyServiceId);
}
