package com.yootii.bdy.bill.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yootii.bdy.agency.model.Agency;
import com.yootii.bdy.agency.service.AgencyService;
import com.yootii.bdy.bill.model.ChargeItem;
import com.yootii.bdy.bill.model.Discount;
import com.yootii.bdy.bill.model.ExchangeRate;
import com.yootii.bdy.bill.model.PayAcount;
import com.yootii.bdy.bill.service.ChargeItemService;
import com.yootii.bdy.bill.service.DiscountService;
import com.yootii.bdy.bill.service.ExchangeRateService;
import com.yootii.bdy.bill.service.PayAcountService;
import com.yootii.bdy.common.CommonController;
import com.yootii.bdy.common.Constant;
import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.common.ReturnInfo;
import com.yootii.bdy.user.model.User;

@Controller
@RequestMapping("interface/bill")
public class BillController extends CommonController{
	@Resource
	private ChargeItemService chargeItemService;
	
	@Resource
	private ExchangeRateService exchangeRateService;
	@Resource
	private DiscountService discountService;
	
	@Resource
	private PayAcountService payAcountService;
	
	@Resource
	private AgencyService agencyService;
	
	@RequestMapping(value="/createchargeitem", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object createChargeItem(HttpServletRequest request,ChargeItem chargeItem,GeneralCondition gcon) {
		ReturnInfo rtnInfo = this.checkUserAndRole(request, gcon, Constant.ROLETYPE.AGENCY_MANAGER);	
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
		makeOffsetAndRows(gcon);		 
		if (rtnInfo != null && rtnInfo.getSuccess()) { // 通过身份验证
			//检查权限
			try {				
				rtnInfo =chargeItemService.createChargeItem(gcon, chargeItem);
				return rtnInfo;
			} catch (Exception e) {
				rtnInfo.setSuccess(false);
				rtnInfo.setMessage(e.getMessage());
				e.printStackTrace();
				return rtnInfo;
			}
		} else {
			return rtnInfo;
		}						
	}
	
	@RequestMapping(value="/deletechargeitem", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object deleteChargeItem(HttpServletRequest request,ChargeItem chargeItem,GeneralCondition gcon) {
		ReturnInfo rtnInfo = this.checkUserAndRole(request, gcon, Constant.ROLETYPE.AGENCY_MANAGER);	
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
		makeOffsetAndRows(gcon);		 
		if (rtnInfo != null && rtnInfo.getSuccess()) { // 通过身份验证
			//检查权限
			try {				
				rtnInfo =chargeItemService.deleteChargeItem(gcon, chargeItem);
				return rtnInfo;
			} catch (Exception e) {
				rtnInfo.setSuccess(false);
				rtnInfo.setMessage(e.getMessage());
				e.printStackTrace();
				return rtnInfo;
			}
		} else {
			return rtnInfo;
		}						
	}
	@RequestMapping(value="/modifychargeitem", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object modifyhargeiIem(HttpServletRequest request,ChargeItem chargeItem,GeneralCondition gcon) {
		ReturnInfo rtnInfo = this.checkUserAndRole(request, gcon, Constant.ROLETYPE.AGENCY_MANAGER);	
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
		makeOffsetAndRows(gcon);		 
		if (rtnInfo != null && rtnInfo.getSuccess()) { // 通过身份验证
			//检查权限
			try {				
				rtnInfo =chargeItemService.modifyChargeItem(gcon, chargeItem);
				return rtnInfo;
			} catch (Exception e) {
				rtnInfo.setSuccess(false);
				rtnInfo.setMessage(e.getMessage());
				e.printStackTrace();
				return rtnInfo;
			}
		} else {
			return rtnInfo;
		}						
	}
	
	@RequestMapping(value="/querychargeitemlist", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object queryChargeItemList(HttpServletRequest request,ChargeItem chargeItem,GeneralCondition gcon) {
		ReturnInfo rtnInfo = this.checkUser(request, gcon);		
		makeOffsetAndRows(gcon);		 
		if (rtnInfo != null && rtnInfo.getSuccess()) { // 通过身份验证
			//检查权限
			try {				
				rtnInfo =chargeItemService.queryChargeItemList(gcon, chargeItem);
				return rtnInfo;
			} catch (Exception e) {
				rtnInfo.setSuccess(false);
				rtnInfo.setMessage(e.getMessage());
				e.printStackTrace();
				return rtnInfo;
			}
		} else {
			return rtnInfo;
		}						
	}
	
	@RequestMapping(value="/querychargeitemdetail", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object queryChargeItemDetail(HttpServletRequest request,ChargeItem chargeItem,GeneralCondition gcon) {
		ReturnInfo rtnInfo = this.checkUser(request, gcon);		
		makeOffsetAndRows(gcon);		 
		if (rtnInfo != null && rtnInfo.getSuccess()) { // 通过身份验证
			//检查权限
			try {				
				rtnInfo =chargeItemService.queryChargeItemDetail(gcon, chargeItem);
				return rtnInfo;
			} catch (Exception e) {
				rtnInfo.setSuccess(false);
				rtnInfo.setMessage(e.getMessage());
				e.printStackTrace();
				return rtnInfo;
			}
		} else {
			return rtnInfo;
		}						
	}
	/**
	 * 查询汇率 支持分页
	 * @param request
	 * @param chargeItem
	 * @param gcon
	 * @return
	 */
	@RequestMapping(value="/queryexchangerate", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object queryExchangeRate(HttpServletRequest request,ExchangeRate exchangeRate,GeneralCondition gcon) {
		ReturnInfo rtnInfo = this.checkUser(request, gcon);		
		makeOffsetAndRows(gcon);		 
		if (rtnInfo != null && rtnInfo.getSuccess()) { // 通过身份验证
			//检查权限
			try {				
				rtnInfo =exchangeRateService.queryExchangeRate(exchangeRate,gcon);
				return rtnInfo;
			} catch (Exception e) {
				rtnInfo.setSuccess(false);
				rtnInfo.setMessage(e.getMessage());
				e.printStackTrace();
				return rtnInfo;
			}
		} else {
			return rtnInfo;
		}						
	}
	/**
	 * 增加折扣记录
	 * @param request
	 * @param exchangeRate
	 * @param gcon
	 * @return
	 */
	@RequestMapping(value="/creatediscount", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object createDiscount(HttpServletRequest request,Discount discount,GeneralCondition gcon) {
		ReturnInfo rtnInfo = this.checkUserAndRole(request, gcon, Constant.ROLETYPE.AGENCY_MANAGER);	
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
		makeOffsetAndRows(gcon);		 
		if (rtnInfo != null && rtnInfo.getSuccess()) { // 通过身份验证
			//检查权限
			try {				
				rtnInfo =discountService.createDiscount(discount,gcon);
				return rtnInfo;
			} catch (Exception e) {
				rtnInfo.setSuccess(false);
				rtnInfo.setMessage(e.getMessage());
				e.printStackTrace();
				return rtnInfo;
			}
		} else {
			return rtnInfo;
		}						
	}
	/**
	 * 删除折扣记录
	 * @param request
	 * @param exchangeRate
	 * @param gcon
	 * @return
	 */
	@RequestMapping(value="/deletediscount", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object deleteDiscount(HttpServletRequest request,Discount discount,GeneralCondition gcon) {
		ReturnInfo rtnInfo = this.checkUserAndRole(request, gcon, Constant.ROLETYPE.AGENCY_MANAGER);
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
		makeOffsetAndRows(gcon);		 
		if (rtnInfo != null && rtnInfo.getSuccess()) { // 通过身份验证
			//检查权限
			try {				
				rtnInfo =discountService.deleteDiscount(discount,gcon);
				return rtnInfo;
			} catch (Exception e) {
				rtnInfo.setSuccess(false);
				rtnInfo.setMessage(e.getMessage());
				e.printStackTrace();
				return rtnInfo;
			}
		} else {
			return rtnInfo;
		}						
	}
	/**
	 * 修改折扣记录
	 * @param request
	 * @param exchangeRate
	 * @param gcon
	 * @return
	 */
	@RequestMapping(value="/modifydiscount", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object modifyDiscount(HttpServletRequest request,Discount discount,GeneralCondition gcon) {
		ReturnInfo rtnInfo = this.checkUserAndRole(request, gcon, Constant.ROLETYPE.AGENCY_MANAGER);
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
		makeOffsetAndRows(gcon);		 
		/*ThreadLocalDemo local = new  ThreadLocalDemo();
		local.addTokenId(gcon.getTokenID());*/
		
		if (rtnInfo != null && rtnInfo.getSuccess()) { // 通过身份验证
			//检查权限
			try {				
				rtnInfo =discountService.modifyDiscount(discount,gcon);
				return rtnInfo;
			} catch (Exception e) {
				rtnInfo.setSuccess(false);
				rtnInfo.setMessage(e.getMessage());
				e.printStackTrace();
				return rtnInfo;
			}
		} else {
			return rtnInfo;
		}						
	}
	/**
	 * 查询折扣记录 分页
	 * @param request
	 * @param exchangeRate
	 * @param gcon
	 * @return
	 */
	@RequestMapping(value="/querydiscountlist", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object queryDiscount(HttpServletRequest request,Discount discount,GeneralCondition gcon) {
		ReturnInfo rtnInfo = this.checkUser(request, gcon);	
		ReturnInfo rtnInfo1 = this.checkUserAndRole(request, gcon, "代理机构管理员");//代理机构管理员
		makeOffsetAndRows(gcon);	
		boolean isAgency =false;
		if (rtnInfo1 != null && rtnInfo1.getSuccess()) {
			 isAgency =true;
			 try {
				Integer userId=sysService.checkToken(gcon.getTokenID()).getUserID();
					if(discount.getAgencyId()==null ) {
						
						//User user = userService.getUserById(userId);
						Agency agency = agencyService.selectAgencyByUserId(userId);
						discount.setAgencyId(agency.getId().toString());
					}
					rtnInfo =discountService.queryDiscountList(discount,gcon,userId,isAgency);
					return rtnInfo;
			} catch (Exception e) {
				rtnInfo.setSuccess(false);
				rtnInfo.setMessage(e.getMessage());
				e.printStackTrace();
				return rtnInfo;
			}
		}
		if (rtnInfo != null && rtnInfo.getSuccess()) { // 通过身份验证
			//检查权限
			try {		
				
				Integer userId=sysService.checkToken(gcon.getTokenID()).getUserID();
				if(discount.getAgencyId()==null ) {
					
					//User user = userService.getUserById(userId);
					Agency agency = agencyService.selectAgencyByUserId(userId);
					discount.setAgencyId(agency.getId().toString());
				}
				rtnInfo =discountService.queryDiscountList(discount,gcon,userId,isAgency);
				return rtnInfo;
			} catch (Exception e) {
				rtnInfo.setSuccess(false);
				rtnInfo.setMessage(e.getMessage());
				e.printStackTrace();
				return rtnInfo;
			}
		} else {
			return rtnInfo;
		}						
	}
	
	/**
	 * 查询折扣记录
	 * @param request
	 * @param exchangeRate
	 * @param gcon
	 * @return
	 */
	@RequestMapping(value="/querydiscountdetail", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object queryDiscountDetail(HttpServletRequest request,Discount discount,GeneralCondition gcon) {
		ReturnInfo rtnInfo = this.checkUser(request, gcon);		
		makeOffsetAndRows(gcon);		 
		if (rtnInfo != null && rtnInfo.getSuccess()) { // 通过身份验证
			//检查权限
			try {				
				rtnInfo =discountService.queryDiscountDetail(discount,gcon);
				return rtnInfo;
			} catch (Exception e) {
				rtnInfo.setSuccess(false);
				rtnInfo.setMessage(e.getMessage());
				e.printStackTrace();
				return rtnInfo;
			}
		} else {
			return rtnInfo;
		}						
	}
	
	/**
	 * 查询收款账户信息列表
	 * @param request
	 * @param payAcount
	 * @param gcon
	 * @return
	 */
	@RequestMapping(value="/querypayacountlist", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object queryPayAcountList(HttpServletRequest request,PayAcount payAcount,GeneralCondition gcon) {
		ReturnInfo rtnInfo = this.checkUser(request, gcon);		
		makeOffsetAndRows(gcon);		 
		if (rtnInfo != null && rtnInfo.getSuccess()) { // 通过身份验证
			//检查权限
			try {				
				Integer userId=sysService.checkToken(gcon.getTokenID()).getUserID();
				if(payAcount.getAgencyId()==null ) {
					
					//User user = userService.getUserById(userId);
					Agency agency = agencyService.selectAgencyByUserId(userId);
					payAcount.setAgencyId(agency.getId());
				}
				rtnInfo =payAcountService.queryPayAcountList(payAcount,gcon);
				return rtnInfo;
			} catch (Exception e) {
				rtnInfo.setSuccess(false);
				rtnInfo.setMessage(e.getMessage());
				e.printStackTrace();
				return rtnInfo;
			}
		} else {
			return rtnInfo;
		}						
	}
	/**
	 * 查询收款账户信息
	 * @param request
	 * @param payAcount
	 * @param gcon
	 * @return
	 */
	@RequestMapping(value="/querypayacountdetail", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object queryPayAcountDetail(HttpServletRequest request,PayAcount payAcount,GeneralCondition gcon) {
		ReturnInfo rtnInfo = this.checkUser(request, gcon);		
		makeOffsetAndRows(gcon);		 
		if (rtnInfo != null && rtnInfo.getSuccess()) { // 通过身份验证
			//检查权限
			try {				
				rtnInfo =payAcountService.queryPayAcountDetail(payAcount,gcon);
				return rtnInfo;
			} catch (Exception e) {
				rtnInfo.setSuccess(false);
				rtnInfo.setMessage(e.getMessage());
				e.printStackTrace();
				return rtnInfo;
			}
		} else {
			return rtnInfo;
		}						
	}
	
	/**
	 * 创建收款账户信息
	 * @param request
	 * @param payAcount
	 * @param gcon
	 * @return
	 */
	@RequestMapping(value="/createpayacount", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object createPayAcount(HttpServletRequest request,PayAcount payAcount,GeneralCondition gcon) {
		ReturnInfo rtnInfo = this.checkUserAndRole(request, gcon, Constant.ROLETYPE.AGENCY_MANAGER);
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
		makeOffsetAndRows(gcon);		 
		if (rtnInfo != null && rtnInfo.getSuccess()) { // 通过身份验证
			//检查权限
			try {
				if(payAcount.getAgencyId()==null ) {
					Integer userId=sysService.checkToken(gcon.getTokenID()).getUserID();
					//User user = userService.getUserById(userId);
					Agency agency = agencyService.selectAgencyByUserId(userId);
					payAcount.setAgencyId(agency.getId());
				}
				
				rtnInfo =payAcountService.createPayAcount(payAcount,gcon);
				return rtnInfo;
			} catch (Exception e) {
				rtnInfo.setSuccess(false);
				rtnInfo.setMessage(e.getMessage());
				e.printStackTrace();
				return rtnInfo;
			}
		} else {
			return rtnInfo;
		}						
	}
	
	/**
	 * 修改收款账户信息
	 * @param request
	 * @param payAcount
	 * @param gcon
	 * @return
	 */
	@RequestMapping(value="/modifypayacount", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object modifyPayAcount(HttpServletRequest request,PayAcount payAcount,GeneralCondition gcon) {
		ReturnInfo rtnInfo = this.checkUserAndRole(request, gcon, Constant.ROLETYPE.AGENCY_MANAGER);	
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
		makeOffsetAndRows(gcon);		 
		if (rtnInfo != null && rtnInfo.getSuccess()) { // 通过身份验证
			//检查权限
			try {				
				rtnInfo =payAcountService.modifyPayAcount(payAcount,gcon);
				return rtnInfo;
			} catch (Exception e) {
				rtnInfo.setSuccess(false);
				rtnInfo.setMessage(e.getMessage());
				e.printStackTrace();
				return rtnInfo;
			}
		} else {
			return rtnInfo;
		}						
	}
	/**
	 * 删除收款账户信息
	 * @param request
	 * @param payAcount
	 * @param gcon
	 * @return
	 */
	@RequestMapping(value="/deletepayacount", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object deletePayAcount(HttpServletRequest request,PayAcount payAcount,GeneralCondition gcon) {
		ReturnInfo rtnInfo = this.checkUserAndRole(request, gcon, Constant.ROLETYPE.AGENCY_MANAGER);	
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
		makeOffsetAndRows(gcon);		 
		if (rtnInfo != null && rtnInfo.getSuccess()) { // 通过身份验证
			//检查权限
			try {				
				rtnInfo =payAcountService.deletePayAcount(payAcount,gcon);
				return rtnInfo;
			} catch (Exception e) {
				rtnInfo.setSuccess(false);
				rtnInfo.setMessage(e.getMessage());
				e.printStackTrace();
				return rtnInfo;
			}
		} else {
			return rtnInfo;
		}						
	}
	
	
	//通过chargeItemId获取ChargeItem集合。
	@RequestMapping(value="/querychargeitemlistbyid", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object queryChargeItem(HttpServletRequest request,String agencyServiceId,GeneralCondition gcon) {
		ReturnInfo rtnInfo = this.checkUser(request, gcon);		
		makeOffsetAndRows(gcon);		 
		if (rtnInfo != null && rtnInfo.getSuccess()) { // 通过身份验证
			//检查权限
			try {				
				rtnInfo =chargeItemService.queryChargeItemListById(agencyServiceId);
				return rtnInfo;
			} catch (Exception e) {
				rtnInfo.setSuccess(false);
				rtnInfo.setMessage(e.getMessage());
				e.printStackTrace();
				return rtnInfo;
			}
		} else {
			return rtnInfo;
		}						
	}
}
