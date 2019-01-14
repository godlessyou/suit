package com.yootii.bdy.agency.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.yootii.bdy.agency.model.Agency;
import com.yootii.bdy.agency.model.CooperationAgency;
import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.common.ReturnInfo;
import com.yootii.bdy.customer.model.Customer;
import com.yootii.bdy.user.model.User;


public interface AgencyService {

	ReturnInfo queryAgencySimpleList(Agency agency,GeneralCondition gcon);
	
	ReturnInfo queryAgencyDetailByUser(Agency agency,GeneralCondition gcon,User user);
	
	ReturnInfo queryAgencyList(Agency agency,GeneralCondition gcon);
	
	ReturnInfo createAgency(Agency agency);

	ReturnInfo checkName(Agency agency);
	
	ReturnInfo modifyAgency(Agency agency,GeneralCondition gcon,User user);
	
	ReturnInfo deleteAgency(Agency agency);
	
	ReturnInfo uploadLogo(HttpServletRequest request, Agency agency);
	
	ReturnInfo bindcooperationagency ( CooperationAgency cooperationAgency,User user);
	
	ReturnInfo unbindcooperationagency ( CooperationAgency cooperationAgency,User user);
	
	ReturnInfo querycooperationagency  ( Agency agency,GeneralCondition gcon,Integer userId);

	Agency selectAgencyByUserId(Integer userId);

	List<Agency> selectCooperationAgencyListByUserId(Integer userId);

	List<Agency> selectAgencyByCustId(Integer id);

	ReturnInfo queryAgencyDetailByCustomer(Agency agency, GeneralCondition gcon, Customer customer);

	ReturnInfo statsCustAndCpAgency(HttpServletRequest request, Integer agencyId, Integer userId, GeneralCondition gcon);

	ReturnInfo queryCustIdandUserIdByagency(HttpServletRequest request, Integer agencyId, GeneralCondition gcon);

	ReturnInfo queryuncooperationagency(Agency agency, GeneralCondition gcon);
	
	ReturnInfo queryAgencyChannel(GeneralCondition gcon);
}
