package com.yootii.bdy.task.service.Impl;

import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yootii.bdy.agency.model.Agency;
import com.yootii.bdy.agency.service.AgencyService;
import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.common.ReturnInfo;
import com.yootii.bdy.invite.model.AgencyInvite;
import com.yootii.bdy.invite.model.CustomerInvite;
import com.yootii.bdy.invite.service.AgencyInviteService;
import com.yootii.bdy.process.service.ProcessService;
import com.yootii.bdy.task.service.AgentOpenTaskService;
import com.yootii.bdy.user.model.User;
import com.yootii.bdy.user.service.UserService;
import com.yootii.bdy.util.GraspUtil;
import com.yootii.bdy.util.JsonUtil;

@Service
public class AgentOpenTaskServiceImpl implements AgentOpenTaskService {

	@Autowired
	private ProcessService processService;
	
	@Autowired
	private AgencyInviteService agencyInviteService;
	
	@Autowired
	private AgencyService agencyService;
	
	@Autowired
	private UserService userService;
	
	@Override
	public ReturnInfo startProAgentOpen(HttpServletRequest request,User user,Agency agency, AgencyInvite agencyInvite,String Email2,String userId) {
		ReturnInfo res = new ReturnInfo();
		String proKey = "agent_open";
		String permission = "代理机构开通:启动";
		try {
			Map<String, Object> runMap = new HashMap<String, Object>();
			ReturnInfo checkret = processService.checkuserstart(permission, userId);
			if(!checkret.getSuccess()) throw new Exception(checkret.getMessage());
			//添加流程参数
			runMap.put("AgentName", agencyInvite.getName());
			runMap.put("Email", agencyInvite.getEmail());
			String processInstanceId;

			User useradmin = new User();
			
			
			String email =user.getEmail();
			
			String Emailname = email.split("@")[0];
			
			String Emailagencyname = email.split("@")[1].replace('.', '_');
			
			if(Email2 == null) {
				Email2 = email;
			}
			
			//Modification start,  by yang guang
			//2018-10-24
			String userName=useradmin.getUsername();
			if (userName==null || userName.equals("")){
				useradmin.setUsername(Emailname+"_admin_"+Emailagencyname);
			}
			useradmin.setEmail(Email2);
			
			userName=user.getUsername();
			if (userName==null || userName.equals("")){
				user.setUsername(Emailname+"_"+Emailagencyname);
			}
			//Modification end
		

			ReturnInfo r1 = userService.checkAgencyAdmin(user);
			if(r1.getSuccess()!=true) {
				String s=user.getUsername();
		   		throw new Exception("创建用户失败|"+r1.getMessage()+", username:"+ s);
		   	}
			
			ReturnInfo r2 = userService.checkAgencyAdmin(useradmin);
			if(r2.getSuccess()!=true) {
				String s=useradmin.getUsername();
		   		throw new Exception("创建管理员失败|"+r2.getMessage()+", username:"+ s);
		   	}
			agency.setStatus("正常");
			//创建代理所
			ReturnInfo r3 = agencyService.createAgency(agency);
			if(r3.getSuccess()!=true) {
		   		throw new Exception("创建代理所失败|"+r3.getMessage());
		   	}
			Integer agencyId = ((Agency)r3.getData()).getId();
			
			if(agency.getLevel()==null) {
				agency.setLevel(2);
			}
			
			if(agency.getLevel().intValue()==1) {
			//创建用户
				ReturnInfo r4 = userService.createAgencyAdmin(useradmin, agencyId);
				if(r4.getSuccess()!=true) {
			   		throw new Exception("创建用户失败|"+r4.getMessage());
			   	}
			}
			
			
			
			ReturnInfo r5 = userService.createUser(user, useradmin,agencyId,null);
			if(r5.getSuccess()!=true) {
		   		throw new Exception("创建用户失败|"+r5.getMessage());
		   	}
			
			//添加邀请列表
//			ReturnInfo r6 = agencyInviteService.addAgencyInvite(agencyInvite);
//			if(r6.getSuccess()!=true) {
//				throw new Exception("数据表添加失败|"+r6.getMessage());
//			}
//		
//			agencyInvite.setId(Integer.valueOf(((Map<String, Object>)r2.getData()).get("id").toString()));
//			runMap.put("AgentInviteId", agencyInvite.getId());
//			//启动流程			
//			res = processService.startProcessByKey(proKey, runMap);
//			if(!res.getSuccess()) {
//				throw new Exception("流程启动失败|"+res.getMessage());
//			}
//			//启动流程成功
//			Map<String, Object> resData = (Map<String, Object>) res.getData();
//
//			processInstanceId =resData.get("ProcessInstanceId").toString();
//			agencyInvite.setProcessInstanceId(Integer.valueOf(processInstanceId));
			
//			String otherContent = "<td>UserAdmin 用户名："+useradmin.getUsername()+"</td>"+
//					"<td>UserAdmin 密码："+r4.getData().toString()+"</td>"+
//					"<td>User 用户名："+user.getUsername()+"</td>"+
//					"<td>User 密码："+r5.getData().toString()+"</td>";
			
//			发送邮件
//			ReturnInfo r7 = agencyInviteService.invateAgency(request, agencyInvite,agencyId.toString(),otherContent);
//			if(r7.getSuccess()!=true) {
//				throw new Exception("邮件发送失败|"+r7.getMessage());
//			}
//			//修改邀请列表
//			agencyInvite.setStatus("已发送");
//			ReturnInfo r3 = agencyInviteService.modifyAgencyInviteStatus(agencyInvite);
//			if(r3.getSuccess()!=true) {
//				throw new Exception("数据表修改失败|"+r3.getMessage());
//			}
		   	res.setSuccess(true);
		   	res.setMessage("创建成功");
			return res;
			
		} catch (Exception e) {
			e.printStackTrace();
			res.setSuccess(false);
			res.setMessage(e.getMessage());
			return res;
		}

	}


	@Override
	public ReturnInfo proAgentOpencheck(HttpServletRequest request,String proId,String sid) {
		ReturnInfo res =new ReturnInfo();
		Map<String, Object> runMap = new HashMap<String, Object>();
		try {
			//获取参数   
			ReturnInfo r1 = processService.showtaskvariablesbypro(proId);
		   	if(r1.getSuccess()!=true) {
		   		throw new Exception("参数获取失败|"+r1.getMessage());
		   	}
		   	
		   	Map<String, Object> proMap = (Map<String, Object>) r1.getData();
		   	//测试权限
		   	ReturnInfo r2 = agencyInviteService.checkLinkOutdate(request, sid, proMap.get("Email").toString());
		   	if(r2.getSuccess()!=true) {
		   		throw new Exception("无效链接|"+r2.getMessage());
		   	}

		   	//
		   	Map<String, Object> resMap = new HashMap<String, Object>();
		   	res.setSuccess(true);
		   	resMap.put("AgentName",proMap.get("AgentName"));
		   	res.setData(resMap);
		}
		catch(Exception e) {
			e.printStackTrace();
			res.setSuccess(false);
			res.setMessage(e.getMessage());
			return res;
		}
		return res;
	}



	@Override
	public ReturnInfo doTaskAOEmail(HttpServletRequest request,String proId,String sid,User user,Agency agency) {
		ReturnInfo res =new ReturnInfo();
		try {
			//获取参数
			Map<String, Object> runMap = new HashMap<String, Object>();
			ReturnInfo r1 = processService.showtaskvariablesbypro(proId);
		   	if(r1.getSuccess()!=true) {
		   		throw new Exception("参数获取失败|"+r1.getMessage());
		   	}
			Map<String, Object> proMap = (Map<String, Object>) r1.getData();
		   	//测试权限
		   	ReturnInfo r2 = agencyInviteService.checkLinkOutdate(request, sid, proMap.get("Email").toString());
		   	if(r2.getSuccess()!=true) {
		   		throw new Exception("无效链接|"+r2.getMessage());
		   	}
		   	ReturnInfo r2_5 = userService.checkAgencyAdmin(user);
			if(r2_5.getSuccess()!=true) {
		   		throw new Exception("创建用户失败|"+r2_5.getMessage());
		   	}
		   	
			//创建代理所
			ReturnInfo r3 = agencyService.createAgency(agency);
			if(r3.getSuccess()!=true) {
		   		throw new Exception("创建代理所失败|"+r3.getMessage());
		   	}
			Integer agencyId = ((Agency)r3.getData()).getId();
			//创建用户
			user.setEmail(proMap.get("Email").toString());
			ReturnInfo r4 = userService.createAgencyAdmin(user, agencyId);
			if(r4.getSuccess()!=true) {
		   		throw new Exception("创建用户失败|"+r4.getMessage());
		   	}
			//修改邀请列表
			AgencyInvite agencyInvite = new AgencyInvite();
			agencyInvite.setStatus("已开通");
			agencyInvite.setId(Integer.valueOf(proMap.get("AgentInviteId").toString()));
			ReturnInfo r5 = agencyInviteService.modifyAgencyInviteStatus(agencyInvite);
			if(r4.getSuccess()!=true) {
				throw new Exception("数据表修改失败|"+r5.getMessage());
			}
			//做任务
			res = processService.doTaskByPro(proId, runMap);
			if(!res.getSuccess()) {
				throw new Exception("流程做任务失败|"+res.getMessage());
			}
		}
		catch(Exception e) {
			//关闭流程
			e.printStackTrace();
			res.setSuccess(false);
			res.setMessage(e.getMessage());
			return res;
		}
		return res;
	}




	
}