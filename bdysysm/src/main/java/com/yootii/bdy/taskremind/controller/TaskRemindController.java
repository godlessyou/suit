package com.yootii.bdy.taskremind.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yootii.bdy.common.CommonController;
import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.common.ReturnInfo;
import com.yootii.bdy.taskremind.model.TaskRemind;
import com.yootii.bdy.taskremind.service.TaskRemindService;
import com.yootii.bdy.user.model.User;

@Controller
@RequestMapping("interface/taskremind")
public class TaskRemindController extends CommonController {
	
	@Resource
	private TaskRemindService taskRemindService;

	private static final Logger logger = Logger.getLogger(TaskRemindController.class);
	
	@RequestMapping(value="/querytaskremindlist", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo queryTaskRemindList(TaskRemind taskRemind,GeneralCondition gcon){
		ReturnInfo info = new ReturnInfo();
		makeOffsetAndRows(gcon);
		//验证登录身份
		try {
			Integer userId=sysService.checkToken(gcon.getTokenID()).getUserID();
			info = taskRemindService.queryTaskRemindList(taskRemind,userId, gcon);
			info.setSuccess(true);
		} catch (Exception e){
			info.setSuccess(false);
			info.setMessage("查询出错");
			logger.error(e.toString());
		}
		
		return info;
	}
	@RequestMapping(value="/addtaskremind", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo addTaskremind(HttpServletRequest request,TaskRemind taskRemind,GeneralCondition gcon){
		ReturnInfo info = new ReturnInfo();
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
		//验证登录身份
		try {
			info.setData(taskRemindService.insertTaskRemind(taskRemind));
			info.setSuccess(true);
		} catch (Exception e){
			info.setSuccess(false);
			info.setMessage("插入出错");
			logger.error(e.toString());
		}
		return info;
	}
	
	@RequestMapping(value="/deletetaskremind", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ReturnInfo deleteTaskremind(HttpServletRequest request,Integer taskRemindId,GeneralCondition gcon){
		ReturnInfo info = new ReturnInfo();
		this.addTokenId(gcon.getTokenID());  // AOP需要用到tokenID
		this.addURL(request.getRequestURI());
		//验证登录身份
		try {
			info.setData(taskRemindService.deleteTaskRemind(taskRemindId));
			info.setSuccess(true);
		} catch (Exception e){
			info.setSuccess(false);
			info.setMessage("删除出错");
			logger.error(e.toString());
		}
		return info;
	}
}
