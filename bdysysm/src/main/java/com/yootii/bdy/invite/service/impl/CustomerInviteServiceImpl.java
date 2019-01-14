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

import com.yootii.bdy.agency.dao.AgencyMapper;
import com.yootii.bdy.agency.dao.AgencyUserMapper;
import com.yootii.bdy.agency.model.Agency;
import com.yootii.bdy.agency.service.AgencyService;
import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.common.Globals;
import com.yootii.bdy.common.ReturnInfo;
import com.yootii.bdy.invite.dao.CustomerInviteMapper;
import com.yootii.bdy.invite.model.AgencyInvite;
import com.yootii.bdy.invite.model.CustomerInvite;
import com.yootii.bdy.invite.service.CustomerInviteService;
import com.yootii.bdy.mail.MailSenderInfo;
import com.yootii.bdy.mail.SendMailUtil;
import com.yootii.bdy.util.MD5Util;

@Service
public class CustomerInviteServiceImpl implements CustomerInviteService{
	
	@Resource
	public CustomerInviteMapper customerInviteMapper;
	
	@Resource
	public MailSenderInfo mailSenderInfo;
	
	@Resource
	private AgencyUserMapper agencyUserMapper;
	
	@Resource
	private AgencyMapper agencyMapper;
	
	@Override
	public ReturnInfo queryCustomerInviteList(CustomerInvite record,
			GeneralCondition gcon,Integer userId) {
		ReturnInfo info = new ReturnInfo();
		Integer agencyId = agencyUserMapper.selectAgencyIdByUserId(userId);
		record.setAgencyId(agencyId);
		List<CustomerInvite> customerInvites = customerInviteMapper.selectCustomerInviteList(record, gcon);
		List<Map<String, Long>> counts= customerInviteMapper.selectCustomerInviteCount(record, gcon);
		Long total = 0L;
		for (Map<String,Long> onecount:counts) {
			total+= onecount.get("count");
		} 
		info.setData(customerInvites);
		info.setTotal(total);
		info.setCurrPage(gcon.getPageNo());
		info.setSuccess(true);
		info.setMessage("查询客户邀请信息成功");
		return info;
	}
	
	@Override
	public CustomerInvite queryCustomerInviteByAgencyIdAndName(Integer agencyId,String name) {
		if(agencyId==null){
			return null;
		}
		if(name==null){
			return null;
		}
		CustomerInvite customerInvite = customerInviteMapper.selectByAgencyIdAndName(agencyId, name);
		return customerInvite;
	}
	
	@Override
	public CustomerInvite queryCustomerInviteDetail(Integer id) {
		if(id==null){
			return null;
		}
		CustomerInvite customerInvite = customerInviteMapper.selectByPrimaryKey(id);
		return customerInvite;
	}
	@Override
	public ReturnInfo addCustomerInvite(CustomerInvite record) {
		ReturnInfo info = new ReturnInfo();
		Integer agencyId = record.getAgencyId() ;
		if(agencyId==null){
			info.setSuccess(false);
			info.setMessage("代理机构ID不能为空");
			return info;
		}
		String name = record.getName();
		CustomerInvite customerInvite = customerInviteMapper.selectByAgencyIdAndName(agencyId, name);
		if(customerInvite!=null){
			info.setSuccess(false);
			info.setMessage("您已经邀请过该客户了");
			return info;
		}
		record.setStatus("未开通");
		customerInviteMapper.insertSelective(record);
		
		CustomerInvite customerInvite2 = customerInviteMapper.selectByAgencyIdAndName(agencyId, name);
		
		Integer id = customerInvite2.getId();
		Map<String, Integer> data = new HashMap<String, Integer>();
		data.put("id", id);
		info.setSuccess(true);
		info.setData(data);
		info.setMessage("添加客户邀请成功");
		return info;
	}

	@Override
	public ReturnInfo modifyCustomerInviteStatus( CustomerInvite record) {
		//只修改状态信息。  代理机构管理员修改邀请信息时，是先删除，再添加，重启流程
		//根据用户的tokenID确定用户的agencyId,查询该用户是否有权限修改该邀请信息
		ReturnInfo info = new ReturnInfo();
		if(record.getId()==null){
			info.setSuccess(false);
			info.setMessage("id不能为空");
			return info;
		}
		/*Integer agencyId = record.getAgencyId() ;
		String email = record.getEmail();
		CustomerInvite customerInvite1 = customerInviteMapper.selectByPrimaryKey(record.getId());
		if(agencyId==null){
			agencyId=customerInvite1.getAgencyId();
		}
		if(email==null){
			email=customerInvite1.getEmail();
		}
		CustomerInvite customerInvite2 = customerInviteMapper.selectByAgencyIdAndEmail(agencyId, email);
		if(customerInvite2!=null&&(record.getId()!=customerInvite2.getId())){
			info.setSuccess(false);
			info.setMessage("您已经邀请过该客户了");
			return info;
		}*/
		CustomerInvite customerInvitenew = new CustomerInvite();
		customerInvitenew.setId(record.getId());
		customerInvitenew.setStatus(record.getStatus());
		customerInvitenew.setProcessInstanceId(record.getProcessInstanceId());
		
		customerInviteMapper.updateByPrimaryKeySelective(customerInvitenew);
		info.setSuccess(true);
		info.setMessage("修改客户邀请成功");
		return info;
	}
	
	@Override
	public ReturnInfo modifyCustomerInviteEMail(CustomerInvite record) {
		//只修改状态信息。  代理机构管理员修改邀请信息时，是先删除，再添加，重启流程
		//根据用户的tokenID确定用户的agencyId,查询该用户是否有权限修改该邀请信息
		ReturnInfo info = new ReturnInfo();
		if(record.getId()==null){
			info.setSuccess(false);
			info.setMessage("id不能为空");
			return info;
		}
		/*Integer agencyId = record.getAgencyId() ;
		String email = record.getEmail();
		CustomerInvite customerInvite1 = customerInviteMapper.selectByPrimaryKey(record.getId());
		if(agencyId==null){
			agencyId=customerInvite1.getAgencyId();
		}
		if(email==null){
			email=customerInvite1.getEmail();
		}
		CustomerInvite customerInvite2 = customerInviteMapper.selectByAgencyIdAndEmail(agencyId, email);
		if(customerInvite2!=null&&(record.getId()!=customerInvite2.getId())){
			info.setSuccess(false);
			info.setMessage("您已经邀请过该客户了");
			return info;
		}*/
		CustomerInvite customerInvitenew = new CustomerInvite();
		customerInvitenew.setId(record.getId());
		customerInvitenew.setEmail(record.getEmail());
		
		customerInviteMapper.updateByPrimaryKeySelective(customerInvitenew);
		info.setSuccess(true);
		info.setMessage("修改客户邀请成功");
		return info;
	}
	public ReturnInfo invateCustomer(HttpServletRequest request, CustomerInvite customerInvite,String processId,String username,String password){
		ReturnInfo info = new ReturnInfo();
		if(customerInvite.getId()==null){
			info.setSuccess(false);
			info.setMessage("客户邀请信息Id不能为空");
			return info;
		}
		customerInvite = customerInviteMapper.selectByPrimaryKey(customerInvite.getId());
		boolean validate = true;
		String mailSubject = "邀请客户通知";
		String mailContent="";
		try {
			mailContent = createLink(request, customerInvite,processId, username, password);
		} catch (Exception e1) {
			e1.printStackTrace();
			info.setSuccess(false);
			info.setMessage("创建邮件链接失败");
			return info;
		}
		String mailTo = customerInvite.getEmail();
		mailSenderInfo.setSubject(mailSubject);
		mailSenderInfo.setContent(mailContent);
		mailSenderInfo.setToAddress(mailTo);
		mailSenderInfo.setValidate(validate);

		// 发送邮件
		SendMailUtil sendMailUtil = new SendMailUtil();
		try {
			sendMailUtil.sendmail(mailSenderInfo);
		} catch (Exception e) {
			e.printStackTrace();
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
	private String createLink(HttpServletRequest request, CustomerInvite customerInvite,String processId,String username,String password)
			throws Exception {

		// 生成密钥
		String secretKey = UUID.randomUUID().toString();

		// 设置过期时间
		Date outDate = new Date(System.currentTimeMillis() + 48 * 60 * 60 * 1000);// 30分钟后过期
		System.out.println(System.currentTimeMillis());
		long date = outDate.getTime() / 1000 * 1000;// 忽略毫秒数 mySql 取出时间是忽略毫秒数的

		// 此处应该更新customerInvite表中的过期时间、密钥信息
		outDate = new Date(date);
		customerInvite.setValidDate(outDate);
		customerInvite.setValidataCode(secretKey);
		customerInviteMapper.updateByPrimaryKeySelective(customerInvite);

		String name = customerInvite.getName();
		Integer agencyId = customerInvite.getAgencyId();
		// 将邮箱、代理所ID、过期时间、密钥生成链接密钥
		String key = name+"$" + agencyId + "$" + date + "$" + secretKey;

		String digitalSignature = MD5Util.getMD5(key);// 数字签名
		
		System.out.println("ValidDate:" + outDate + " ValidataCode:"+ secretKey +" digitalSignature:"+ digitalSignature);

		String basePath = "";
		String customerRegistPath = "";
		if (request != null) {

			basePath = request.getScheme() + "://" + request.getServerName()
					+ ":" + request.getServerPort() ;
			String restP = request.getParameter("customerRegistPath");
			if (restP != null && !restP.equals("")) {
				customerRegistPath = restP;
			}else{
				//测试用
				String path = request.getContextPath();
				customerRegistPath = path+ "/interface/customer/createcustomer";
			}
		}

		String resetPassHref = basePath + "/bdy/bdy" + customerRegistPath + "?sid="
				+ digitalSignature + "&processId=" + processId;
		
		Agency agency = agencyMapper.selectByPrimaryKey(agencyId);
		String emailContent = null;
		if(password!=null){
			emailContent = ""
					+ "<td>"
					+ "请勿回复本邮件。"
					+ "您已注册为"+agency.getName()+"代理机构的客户,点击下面的链接确认信息，本邮件超过48小时，链接将会失效，如有疑问请登录http://124.205.246.82:8000。"
					+ "</td>"
					+ "<p></p>"
					+ "<td><a href=\""+resetPassHref+"\">"
					+ resetPassHref 
					+"</a></td>"
					+ "<p>用户名："+username+"<br>"
					+ "初始密码："+password+"</p>";
		}else{
			emailContent = ""
					+ "<td>"
					+ "请勿回复本邮件。"
					+ agency.getName()+"代理机构已与您的标得宜账户关联，可以选择在该代理机构办理商标业务，点击下面的链接登录系统。"
					+ "</td>"
					+ "<p></p>"
					+ "<td><a href=\"http://47.105.166.130\">http://www.yootii.com.cn</a></td>";
		}
		

		return emailContent;
	}
	
	
	
	/**
	 * @Description: 验证sid是否过期，如果请求中的加密字符串正确，并且没有过期，给前台返回成功的信息
	 */
	public ReturnInfo checkLinkOutdate(HttpServletRequest request,String sid,String name,Integer agencyId){

		// 返回结果对象
		ReturnInfo rtnInfo = new ReturnInfo();

		if(sid==null&&name==null&&agencyId==null){
			if (request != null) {
				sid = request.getParameter("sid");
				name = request.getParameter("name");
				agencyId=Integer.parseInt(request.getParameter("agencyId"));
			}
		}

		if (StringUtils.isEmpty(sid) || StringUtils.isEmpty(name)||(agencyId==null)) {
			rtnInfo.setSuccess(false);
			rtnInfo.setMessage("请求的参数不正确，链接无效");
			rtnInfo.setMessageType(Globals.MESSAGE_TYPE_LINK_INVALID);
			return rtnInfo;
		}

		CustomerInvite customerInvite = customerInviteMapper.selectByAgencyIdAndName(agencyId, name);

		if (customerInvite != null) {
			if("已开通".equals(customerInvite.getStatus())){
				// 找回密码链接已经过期
				rtnInfo.setSuccess(false);
				rtnInfo.setMessage("用户已注册，该链接无效");
				rtnInfo.setMessageType(Globals.MESSAGE_TYPE_LINK_INVALID);
				return rtnInfo;
			}
			// 获取当前用户申请找回密码的过期时间
			Date date = customerInvite.getValidDate();
			long outTime = date.getTime();
			if (outTime <= System.currentTimeMillis()) {
				// 找回密码链接已经过期
				rtnInfo.setSuccess(false);
				rtnInfo.setMessage("链接已经过期，链接无效");
				rtnInfo.setMessageType(Globals.MESSAGE_TYPE_LINK_INVALID);
				return rtnInfo;
			}

			// 获取当前登陆人的加密码
			String vailddataCode = customerInvite.getValidataCode();
			String key = name +"$" + agencyId  +"$" + outTime / 1000 * 1000 + "$" + vailddataCode;// 数字签名

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
			rtnInfo.setMessage("邮箱不正确");
			rtnInfo.setMessageType(Globals.MESSAGE_TYPE_SESSION_INVALID);
			return rtnInfo;
		}
		// 将验证成功返回给前台。
		rtnInfo.setSuccess(true);
		rtnInfo.setMessage("验证成功");
		return rtnInfo;
	}

	@Override
	public ReturnInfo deleteCustomerInvite(CustomerInvite record) {
		//平台管理员删除
		ReturnInfo info = new ReturnInfo();
		if(record.getId()==null){
			info.setSuccess(false);
			info.setMessage("id不能为空");
			return info;
		}
		customerInviteMapper.deleteByPrimaryKey(record.getId());
		info.setSuccess(true);
		info.setMessage("删除代理所邀请成功");
		return info;
	}

}
