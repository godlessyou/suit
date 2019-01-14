package com.yootii.bdy.agency.service;

import java.util.List;

import com.yootii.bdy.agency.model.AgencyContact;
import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.common.ReturnInfo;
import com.yootii.bdy.user.model.User;

public interface AgencyContactService {
	ReturnInfo createAgencyContact(AgencyContact agencyContact);
	ReturnInfo deleteAgencyContact(Integer agencyContactId);
	List<User> queryAgencyContact(AgencyContact agencyContact);
	ReturnInfo queryAgencyContactById(Integer agencyId,Integer custId);
	ReturnInfo modifyAgencyContact(Integer agencyContactId,Integer userId);
	ReturnInfo queryAgencyContactByAgencyId(AgencyContact agencyContact,GeneralCondition gcon,String name,String fullname);
}
