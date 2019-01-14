package com.yootii.bdy.customer.service;

import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.common.ReturnInfo;
import com.yootii.bdy.customer.model.CustomerContact;

public interface CustomerContactService {

	ReturnInfo queryCustomerContact(CustomerContact customerContact, GeneralCondition gcon);

	ReturnInfo ceateCustomerContact(CustomerContact customerContact);

	ReturnInfo motifyCustomerContact(CustomerContact customerContact);

	ReturnInfo deleteCustomerContact(CustomerContact customerContact);
	
}
