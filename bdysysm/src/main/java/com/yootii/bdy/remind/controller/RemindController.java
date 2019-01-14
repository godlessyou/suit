package com.yootii.bdy.remind.controller;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yootii.bdy.common.CommonController;
import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.common.ReturnInfo;
import com.yootii.bdy.remind.model.Remind;
import com.yootii.bdy.remind.service.RemindService;
import com.yootii.bdy.taskremind.model.TaskRemind;
import com.yootii.bdy.taskremind.service.TaskRemindService;

@Controller
@RequestMapping("interface/remind")
public class RemindController extends CommonController {
	
	@Resource
	private RemindService remindService;
	
	@RequestMapping(value="/queryremindlist", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo queryTaskRemindList(HttpServletRequest request,Remind remind,GeneralCondition gcon){
		ReturnInfo info = this.checkUser(request, gcon);	
		makeOffsetAndRows(gcon);
		if(info.getSuccess()){//验证登录身份通过
			try {
				info = remindService.queryRemindList(remind, gcon);
				info.setSuccess(true);
			} catch (Exception e){
				e.printStackTrace();
				info.setSuccess(false);
				info.setMessage("查询出错");
			}
		}
		return info;
	}
	@RequestMapping(value="/addremind", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo addTaskremind(HttpServletRequest request,Integer type,Date date,Integer agencyId,Integer custId,Integer caseId,GeneralCondition gcon){
		ReturnInfo info  = this.checkUser(request, gcon);	
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
		if(info.getSuccess()){//验证登录身份通过
			try {
				info.setData(remindService.insertRemindByType(type, date, agencyId,custId,caseId));
				info.setSuccess(true);
			} catch (Exception e){
				e.printStackTrace();
				info.setSuccess(false);
				info.setMessage("插入出错");
			}
		}
		return info;
	}
	
	@RequestMapping(value="/deletetaskremind", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo deleteTaskremind(HttpServletRequest request,Integer remindId,GeneralCondition gcon){
		ReturnInfo info  = this.checkUser(request, gcon);	
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
		if(info.getSuccess()){//验证登录身份通过
			try {
				info.setData(remindService.deleteRemind(remindId));
				info.setSuccess(true);
			} catch (Exception e){
				e.printStackTrace();
				info.setSuccess(false);
				info.setMessage("删除出错");
			}
		}
		return info;
	}
}
