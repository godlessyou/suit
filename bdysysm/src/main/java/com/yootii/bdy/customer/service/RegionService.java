package com.yootii.bdy.customer.service;

import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.common.ReturnInfo;
import com.yootii.bdy.customer.model.Region;

public interface RegionService {

	ReturnInfo queryRegionList(Region region, GeneralCondition gcon);

	
}
