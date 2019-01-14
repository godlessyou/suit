package com.yootii.bdy.customer.service;

import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.common.ReturnInfo;
import com.yootii.bdy.customer.model.Applicant;

public interface ApplicantService {

	ReturnInfo queryApplicant(Applicant applicant, GeneralCondition gcon, Integer customerId);

	ReturnInfo queryApplicantDetail(Applicant applicant, GeneralCondition gcon);

	ReturnInfo ceateApplicant(Applicant applicant, GeneralCondition gcon, Integer customerId);

	ReturnInfo motifyApplicant(Applicant applicant, GeneralCondition gcon, Integer customerId);

	ReturnInfo deleteapplicant(Applicant applicant, GeneralCondition gcon,Integer customerId);

	ReturnInfo queryApplicantByCustId(GeneralCondition gcon, Integer customerId);
	
	ReturnInfo queryApplicantNameByCustId(GeneralCondition gcon, Integer customerId);

	Applicant selectAppAndMaterialByPrimaryKey(Integer id);

	ReturnInfo queryAndCreateAapplicant(GeneralCondition gcon, Applicant applicant, Integer custId);

	ReturnInfo queryApplicantbyappcnname(String applicantCnName, GeneralCondition gcon);

	ReturnInfo queryApplicantbyappname(String appCnName, String appEnName, GeneralCondition gcon);

	ReturnInfo checkAndSaveApplicant(Applicant applicant, Integer customerId);

	ReturnInfo queryAllApplicantByCustId(GeneralCondition gcon, Integer customerId);

	ReturnInfo queryAllApplicantByAppId(GeneralCondition gcon, Integer AppId);

	ReturnInfo bindApplicant(String idlist, Integer mainId);

	ReturnInfo unBindApplicant(Integer id);
	
	Applicant getApplicantByName(Applicant applicant);
	
	ReturnInfo queryTmCountByAppIdList(GeneralCondition gcon, String appIdList);

}
