package com.yootii.bdy.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yootii.bdy.agency.model.Agency;
import com.yootii.bdy.bill.model.ChargeItem;
import com.yootii.bdy.bill.model.Discount;
import com.yootii.bdy.bill.model.ExchangeRate;
import com.yootii.bdy.customer.model.Customer;
import com.yootii.bdy.department.model.Department;
import com.yootii.bdy.invite.model.AgencyInvite;
import com.yootii.bdy.ipservice.model.AgencyService;
import com.yootii.bdy.ipservice.model.PlatformService;
import com.yootii.bdy.permission.model.Permission;
import com.yootii.bdy.role.model.Role;
import com.yootii.bdy.user.model.User;

public class TranslationNameList {
	 public static final Map<Class, List<String> > nameList = new HashMap<Class, List<String> >();  
	 
	 static {  
		 
		 List<String> AgencyInvitelist = new ArrayList<String>();
		 AgencyInvitelist.add("status");
		 nameList.put(AgencyInvite.class, AgencyInvitelist);
	
		 List<String> Rolelist = new ArrayList<String>();		 
		 Rolelist.add("name");
		 Rolelist.add("description");
		 nameList.put(Role.class,Rolelist);		
		 
		 List<String> AgencyServicelist = new ArrayList<String>();
		 AgencyServicelist.add("platformService");
		 AgencyServicelist.add("currency");
		 nameList.put(AgencyService.class,AgencyServicelist);
		 
		 List<String> PlatformServicelist = new ArrayList<String>();		 
		 PlatformServicelist.add("serviceName");
		 PlatformServicelist.add("serviceDesc");
		 PlatformServicelist.add("caseType");
		 nameList.put(PlatformService.class,PlatformServicelist);
		 
		 List<String> Discountlist = new ArrayList<String>();		 
		 Discountlist.add("caseType");
		 nameList.put(Discount.class,Discountlist);	
		 
		 List<String> ExchangeRatelist = new ArrayList<String>();		 
		 ExchangeRatelist.add("currency1");
		 ExchangeRatelist.add("currency2");
		 nameList.put(ExchangeRate.class,ExchangeRatelist);	
		 
		 List<String> ChargeItemlist = new ArrayList<String>();		 
		 ChargeItemlist.add("caseType");
		 ChargeItemlist.add("chargeType");
		 ChargeItemlist.add("chnName");
		 ChargeItemlist.add("currency");
		 nameList.put(ChargeItem.class,ChargeItemlist);	
		 
		 List<String> Userlist = new ArrayList<String>();		 
		 Userlist.add("userType");
		 Userlist.add("departments");
		 Userlist.add("roles");
		 Userlist.add("customers");
		 Userlist.add("agency");
		 Userlist.add("cooperationAgency");
		 Userlist.add("permissions");
		 nameList.put(User.class,Userlist);	
		 
		 List<String> ReturnInfolist = new ArrayList<String>();		 
		 ReturnInfolist.add("data");
		 ReturnInfolist.add("message");
		 nameList.put(ReturnInfo.class,ReturnInfolist);
		 
//		 List<String> Maplist = new ArrayList<String>();		 
//		 ReturnInfolist.add("data");
//		 ReturnInfolist.add("message");
//		 nameList.put(HashMap.class,ReturnInfolist);
		 
		 
		 
	 }
}
