package com.yootii.bdy.remind.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yootii.bdy.common.CommonController;
import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.common.ReturnInfo;
import com.yootii.bdy.remind.service.RemindService;
import com.yootii.bdy.remind.service.TrademarkRemindService;
import com.yootii.bdy.taskremind.model.TaskRemind;
import com.yootii.bdy.taskremind.service.TaskRemindService;

@Controller
@RequestMapping("interface/trademarkremind")
public class TrademarkRemindController extends CommonController {
	
	@Resource
	private TrademarkRemindService trademarkRemindService;
	
	@RequestMapping(value="/queryxuzhanlist", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo queryXuzhanList(Integer agencyId,Integer custId,GeneralCondition gcon){
		ReturnInfo info = new ReturnInfo();
		makeOffsetAndRows(gcon);
		//验证登录身份
		try {
			if (agencyId == null & custId == null) throw new Exception("参数有误");
			if (agencyId != null & custId != null) throw new Exception("参数有误");
			info = trademarkRemindService.selectTrademarkXuzhan(agencyId, custId, false, gcon);
			info.setSuccess(true);
		} catch (Exception e){
			info.setSuccess(false);
			info.setMessage("查询出错"+e.getMessage());
		}
		
		return info;
	}
	@RequestMapping(value="/querysongdagglist", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo querySongdaGGList(Integer agencyId,Integer custId,GeneralCondition gcon){
		ReturnInfo info = new ReturnInfo();
		makeOffsetAndRows(gcon);
		//验证登录身份
		try {
			if (agencyId == null & custId == null) throw new Exception("参数有误");
			if (agencyId != null & custId != null) throw new Exception("参数有误");
			info = trademarkRemindService.selectTrademarkSongdaGG(agencyId, custId, false, gcon);
			info.setSuccess(true);
		} catch (Exception e){
			info.setSuccess(false);
			info.setMessage("查询出错"+e.getMessage());
		}
		
		return info;
	}
	@RequestMapping(value="/querychushengglist", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo queryChushenGGList(Integer agencyId,Integer custId,GeneralCondition gcon){
		ReturnInfo info = new ReturnInfo();
		makeOffsetAndRows(gcon);
		//验证登录身份
		try {
			if (agencyId == null & custId == null) throw new Exception("参数有误");
			if (agencyId != null & custId != null) throw new Exception("参数有误");
			info = trademarkRemindService.selectTrademarkChushenGG(agencyId, custId, false, gcon);
			info.setSuccess(true);
		} catch (Exception e){
			info.setSuccess(false);
			info.setMessage("查询出错"+e.getMessage());
		}
		
		return info;
	}
	@RequestMapping(value="/querydongtailist", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo queryDongtaiList(Integer agencyId,Integer custId,GeneralCondition gcon){
		ReturnInfo info = new ReturnInfo();
		makeOffsetAndRows(gcon);
		//验证登录身份
		try {
			if (agencyId == null & custId == null) throw new Exception("参数有误");
			if (agencyId != null & custId != null) throw new Exception("参数有误");
			info = trademarkRemindService.selectTrademarkDongtai(agencyId, custId, false, gcon);
			info.setSuccess(true);
		} catch (Exception e){
			info.setSuccess(false);
			info.setMessage("查询出错"+e.getMessage());
		}
		
		return info;
	}
	
	
	@RequestMapping(value="/queryxuzhancount", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo queryXuzhanCount(Integer agencyId,Integer custId,GeneralCondition gcon){
		ReturnInfo info = new ReturnInfo();
		makeOffsetAndRows(gcon);
		//验证登录身份
		try {
			if (agencyId == null & custId == null) throw new Exception("参数有误");
			if (agencyId != null & custId != null) throw new Exception("参数有误");
			info = trademarkRemindService.selectTrademarkXuzhan(agencyId, custId, true, gcon);
			info.setSuccess(true);
		} catch (Exception e){
			info.setSuccess(false);
			info.setMessage("查询出错"+e.getMessage());
		}
		
		return info;
	}
	@RequestMapping(value="/querysongdaggcount", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo querySongdaGGCount(Integer agencyId,Integer custId,GeneralCondition gcon){
		ReturnInfo info = new ReturnInfo();
		makeOffsetAndRows(gcon);
		//验证登录身份
		try {
			if (agencyId == null & custId == null) throw new Exception("参数有误");
			if (agencyId != null & custId != null) throw new Exception("参数有误");
			info = trademarkRemindService.selectTrademarkSongdaGG(agencyId, custId, true, gcon);
			info.setSuccess(true);
		} catch (Exception e){
			info.setSuccess(false);
			info.setMessage("查询出错"+e.getMessage());
		}
		
		return info;
	}
	@RequestMapping(value="/querychushenggcount", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo queryChushenGGCount(Integer agencyId,Integer custId,GeneralCondition gcon){
		ReturnInfo info = new ReturnInfo();
		makeOffsetAndRows(gcon);
		//验证登录身份
		try {
			if (agencyId == null & custId == null) throw new Exception("参数有误");
			if (agencyId != null & custId != null) throw new Exception("参数有误");
			info = trademarkRemindService.selectTrademarkChushenGG(agencyId, custId, true, gcon);
			info.setSuccess(true);
		} catch (Exception e){
			info.setSuccess(false);
			info.setMessage("查询出错"+e.getMessage());
		}
		
		return info;
	}
	@RequestMapping(value="/querydongtaicount", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo queryDongtaiCount(Integer agencyId,Integer custId,GeneralCondition gcon){
		ReturnInfo info = new ReturnInfo();
		makeOffsetAndRows(gcon);
		//验证登录身份
		try {
			if (agencyId == null & custId == null) throw new Exception("参数有误");
			if (agencyId != null & custId != null) throw new Exception("参数有误");
			info = trademarkRemindService.selectTrademarkDongtai(agencyId, custId, true, gcon);
			info.setSuccess(true);
		} catch (Exception e){
			info.setSuccess(false);
			info.setMessage("查询出错"+e.getMessage());
		}
		
		return info;
	}
	
	
	@RequestMapping(value="/sendxuzhan", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo sendXuzhan(Integer agencyId,Integer custId,GeneralCondition gcon){
		ReturnInfo info = new ReturnInfo();
		makeOffsetAndRows(gcon);
		//验证登录身份
		try {
			if (agencyId == null & custId == null) throw new Exception("参数有误");
			if (agencyId != null & custId != null) throw new Exception("参数有误");
			Integer userId = sysService.checkToken(gcon.getTokenID()).getUserID();			
			info = trademarkRemindService.SendTrademarkXuzhan(custId, userId);
			info.setSuccess(true);
		} catch (Exception e){
			info.setSuccess(false);
			info.setMessage("发送出错"+e.getMessage());
		}
		
		return info;
	}
	@RequestMapping(value="/sendsongdagg", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo sendSongdaGG(Integer agencyId,Integer custId,GeneralCondition gcon){
		ReturnInfo info = new ReturnInfo();
		makeOffsetAndRows(gcon);
		//验证登录身份
		try {
			if (agencyId == null & custId == null) throw new Exception("参数有误");
			if (agencyId != null & custId != null) throw new Exception("参数有误");
			info = trademarkRemindService.SendTrademarkSongdaGG(custId);
			info.setSuccess(true);
		} catch (Exception e){
			info.setSuccess(false);
			info.setMessage("发送出错"+e.getMessage());
		}
		
		return info;
	}
	@RequestMapping(value="/sendchushengg", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo sendChushenGG(Integer agencyId,Integer custId,GeneralCondition gcon){
		ReturnInfo info = new ReturnInfo();
		makeOffsetAndRows(gcon);
		//验证登录身份
		try {
			if (agencyId == null & custId == null) throw new Exception("参数有误");
			if (agencyId != null & custId != null) throw new Exception("参数有误");
			info = trademarkRemindService.SendTrademarkChushenGG(custId);
			info.setSuccess(true);
		} catch (Exception e){
			info.setSuccess(false);
			info.setMessage("发送出错"+e.getMessage());
		}
		
		return info;
	}
	@RequestMapping(value="/senddongtai", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo sendDongtai(Integer agencyId,Integer custId,GeneralCondition gcon){
		ReturnInfo info = new ReturnInfo();
		makeOffsetAndRows(gcon);
		//验证登录身份
		try {
			if (agencyId == null & custId == null) throw new Exception("参数有误");
			if (agencyId != null & custId != null) throw new Exception("参数有误");
			info = trademarkRemindService.SendTrademarkDongtai(custId);
			info.setSuccess(true);
		} catch (Exception e){
			info.setSuccess(false);
			info.setMessage("发送出错"+e.getMessage());
		}
		
		return info;
	}
	
}
