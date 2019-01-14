package com.yootii.bdy.invite.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.common.Globals;
import com.yootii.bdy.common.ReturnInfo;
import com.yootii.bdy.invite.dao.AgencyInviteMapper;
import com.yootii.bdy.invite.model.AgencyInvite;
import com.yootii.bdy.invite.service.AgencyInviteService;
import com.yootii.bdy.mail.MailSenderInfo;
import com.yootii.bdy.mail.SendMailUtil;
import com.yootii.bdy.util.MD5Util;

@Service
public class AgencyInviteServiceImpl implements AgencyInviteService {
	
	@Resource
	public AgencyInviteMapper agencyInviteMapper;
	
	@Resource
	protected MailSenderInfo mailSenderInfo;
	
	@Override
	public ReturnInfo queryAgencyInviteList(AgencyInvite agencyInvite,
			GeneralCondition gcon) {
		ReturnInfo info = new ReturnInfo();
		List<AgencyInvite> agencyInvites = agencyInviteMapper.selectAgencyInviteList(agencyInvite, gcon);
		List<Map<String,Long>> counts = agencyInviteMapper.selectAgencyInviteCount(agencyInvite, gcon);
		Long total = 0L;
		for (Map<String,Long> onecount:counts) {
			total+= onecount.get("count");
		} 
		info.setData(agencyInvites);
		info.setTotal(total);
		info.setCurrPage(gcon.getPageNo());
		info.setSuccess(true);
		info.setMessage("查询代理机构邀请信息成功");
		return info;
	}

	@Override
	public ReturnInfo addAgencyInvite(AgencyInvite agencyInvite) {
		//平台管理员添加
		ReturnInfo info = new ReturnInfo();
		String email = agencyInvite.getEmail();
		AgencyInvite agencyInvite2 = agencyInviteMapper.selectByEmail(email);
		if(agencyInvite2!=null){
			info.setSuccess(false);
			info.setMessage("该邮箱已经发送过邀请了");
			return info;
		}
		agencyInvite.setStatus("未开通");
		agencyInviteMapper.insertSelective(agencyInvite);
		
		AgencyInvite agencyInvite3 = agencyInviteMapper.selectByEmail(email);
		Integer id = agencyInvite3.getId();
		Map<String, Integer> data = new HashMap<String, Integer>();
		data.put("id", id);
		info.setSuccess(true);
		info.setData(data);
		info.setMessage("添加代理所邀请成功");
		return info;
	}

	@Override
	public ReturnInfo modifyAgencyInviteStatus(AgencyInvite agencyInvite) {
		//平台管理员修改
		//只修改状态信息。  管理员修改邀请信息时，是先删除，再添加，重启流程
		ReturnInfo info = new ReturnInfo();
		if(agencyInvite.getId()==null){
			info.setSuccess(false);
			info.setMessage("id不能为空");
			return info;
		}
		/*String email = agencyInvite.getEmail();
		AgencyInvite ai = agencyInviteMapper.selectByEmail(email);
		if(ai!=null&&(ai.getId()!=agencyInvite.getId())){
			info.setSuccess(false);
			info.setMessage("该邮箱已经存在");
			return info;
		}
		agencyInviteMapper.updateByPrimaryKeySelective(agencyInvite);*/
		AgencyInvite agencyInvitenew = new AgencyInvite();
		agencyInvitenew.setId(agencyInvite.getId());
		agencyInvitenew.setStatus(agencyInvite.getStatus());
		agencyInvitenew.setProcessInstanceId(agencyInvite.getProcessInstanceId());
		agencyInviteMapper.updateByPrimaryKeySelective(agencyInvitenew);
		info.setSuccess(true);
		info.setMessage("修改代理所邀请成功");
		return info;
	}
	
	@Override
	public ReturnInfo deleteAgencyInvite(AgencyInvite agencyInvite) {
		//平台管理员删除
		ReturnInfo info = new ReturnInfo();
		if(agencyInvite.getId()==null){
			info.setSuccess(false);
			info.setMessage("id不能为空");
			return info;
		}
		agencyInviteMapper.deleteByPrimaryKey(agencyInvite.getId());
		info.setSuccess(true);
		info.setMessage("删除代理所邀请成功");
		return info;
	}
	
	@Override
	public ReturnInfo invateAgency(HttpServletRequest request, AgencyInvite agencyInvite,String agencyId,String otherContent){
		ReturnInfo info = new ReturnInfo();
		if(agencyInvite.getId()==null){
			info.setSuccess(false);
			info.setMessage("代理所邀请信息Id不能为空");
			return info;
		}
		agencyInvite=agencyInviteMapper.selectByPrimaryKey(agencyInvite.getId());
		boolean validate = true;
		String mailSubject = "邀请代理所开通通知";
		String mailContent="";;
		try {
			mailContent = createLink(request, agencyInvite,agencyId);
		} catch (Exception e1) {
			e1.printStackTrace();
			info.setSuccess(false);
			info.setMessage("创建邮件链接失败");
			return info;
		}
		String mailTo = agencyInvite.getEmail();
		mailSenderInfo.setSubject(mailSubject);
		mailSenderInfo.setContent(mailContent+otherContent);
		mailSenderInfo.setToAddress(mailTo);
		mailSenderInfo.setValidate(validate);

		// 发送邮件
		SendMailUtil sendMailUtil = new SendMailUtil();
		try {
			sendMailUtil.sendmail(mailSenderInfo);
		} catch (Exception e) {
			e.printStackTrace();
			info.setSuccess(false);
			info.setMessage("发送邮件失败");
			return info;
		}
		info.setSuccess(true);
		info.setMessage("发送邮件成功");
		return info;
	}
	/**
	 * @Description: 生成邮箱链接地址
	 */
	private String createLink(HttpServletRequest request, AgencyInvite agencyInvite,String agencyId)
			throws Exception {

		// 生成密钥
		String secretKey = UUID.randomUUID().toString();

		// 设置过期时间
		Date outDate = new Date(System.currentTimeMillis() + 48 * 60 * 60 * 1000);// 2天后过期
		
		long date = outDate.getTime() / 1000 * 1000;// 忽略毫秒数 mySql 取出时间是忽略毫秒数的
		System.out.println(outDate.getTime() + " 调整后："+date);
		// 此处应该更新Users表中的过期时间、密钥信息
		outDate = new Date(date);
		agencyInvite.setValidDate(outDate);
		agencyInvite.setValidataCode(secretKey);
		agencyInviteMapper.updateByPrimaryKeySelective(agencyInvite);

		String email = agencyInvite.getEmail();
		// 将用户名、过期时间、密钥生成链接密钥
		String key = email + "$" + date + "$" + secretKey;

		String digitalSignature = MD5Util.getMD5(key);// 数字签名
		
		System.out.println("ValidDate:" + outDate + " ValidataCode:"+ secretKey +" digitalSignature:"+ digitalSignature);

		String basePath = "";
		String resetPassPath = "";
		if (request != null) {

			basePath = request.getScheme() + "://" + request.getServerName()
					+ ":" + request.getServerPort() ;
			String restP = request.getParameter("agencyRegistPath");
			if (restP != null && !restP.equals("")) {
				resetPassPath = restP;
			}else{
				//测试用
				String path = request.getContextPath();
				resetPassPath = path+ "/interface/agency/createagency";
			}
		}

		String resetPassHref = basePath + "/bdy/bdy" + resetPassPath + "?sid="
				+ digitalSignature + "&agnecyId=" + agencyId;

		String emailContent = ""
				+ "<td>"
				+ "请勿回复本邮件。"
				+ "点击下面的链接，开通代理所，本邮件超过48小时，链接将会失效，需要重新申请开通。"
				+ "</td>"
				+ "<p></p>"
				+ "<td><a href=\""+resetPassHref+"\">"
				+ resetPassHref 
				+"</a></td>";

		return emailContent;
	}
	
	
	/**
	 * @Description: 验证sid是否过期，如果请求中的加密字符串正确，并且没有过期，给前台返回成功的信息
	 */
	public ReturnInfo checkLinkOutdate(HttpServletRequest request,String sid,String email){

		// 返回结果对象
		ReturnInfo rtnInfo = new ReturnInfo();

		if(sid==null&&email==null){
			if (request != null) {
				sid = request.getParameter("sid");
				email = request.getParameter("email");
			}
		}

		if (StringUtils.isEmpty(sid) || StringUtils.isEmpty(email)) {
			rtnInfo.setSuccess(false);
			rtnInfo.setMessage("请求的参数不正确，链接无效");
			rtnInfo.setMessageType(Globals.MESSAGE_TYPE_LINK_INVALID);
			return rtnInfo;
		}

		AgencyInvite agencyInvite = agencyInviteMapper.selectByEmail(email);

		if (agencyInvite != null) {
			if("已开通".equals(agencyInvite.getStatus())){
				// 找回密码链接已经过期
				rtnInfo.setSuccess(false);
				rtnInfo.setMessage("用户已注册，该链接无效");
				rtnInfo.setMessageType(Globals.MESSAGE_TYPE_LINK_INVALID);
				return rtnInfo;
			}
			// 获取当前用户申请找回密码的过期时间
			Date date = agencyInvite.getValidDate();
			long outTime = date.getTime();
			if (outTime <= System.currentTimeMillis()) {
				// 找回密码链接已经过期
				rtnInfo.setSuccess(false);
				rtnInfo.setMessage("链接已经过期，链接无效");
				rtnInfo.setMessageType(Globals.MESSAGE_TYPE_LINK_INVALID);
				return rtnInfo;
			}

			// 获取当前登陆人的加密码
			String vailddataCode = agencyInvite.getValidataCode();
			String key = email + "$" + outTime / 1000 * 1000 + "$" + vailddataCode;// 数字签名  email + "$" + date + "$" + secretKey; outDate.getTime() / 1000 * 1000

			String digitalSignature = MD5Util.getMD5(key);// 数字签名
			System.out.println("ValidDate:" + outTime + " ValidataCode:"+ key +" digitalSignature:"+ digitalSignature + " sid:"+ sid);

			if (!digitalSignature.equals(sid)) {
				rtnInfo.setSuccess(false);
				rtnInfo.setMessage("链接中的加密密码不正确，链接无效");
				rtnInfo.setMessageType(Globals.MESSAGE_TYPE_LINK_INVALID);
				return rtnInfo;
			}

		} else {
			rtnInfo.setSuccess(false);
			rtnInfo.setMessage("用户名不正确");
			rtnInfo.setMessageType(Globals.MESSAGE_TYPE_SESSION_INVALID);
			return rtnInfo;
		}
		// 将验证成功返回给前台。
		rtnInfo.setSuccess(true);
		rtnInfo.setMessage("验证成功");
		return rtnInfo;
	}

	@Override
	public AgencyInvite queryAgencyInvite(Integer agencyInviteId) {
		AgencyInvite agencyInvite = agencyInviteMapper.selectByPrimaryKey(agencyInviteId);
		
		return agencyInvite;
	}
}
