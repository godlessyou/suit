package com.yootii.bdy.customer.service.impl;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.common.Globals;
import com.yootii.bdy.common.ReturnInfo;
import com.yootii.bdy.customer.model.Customer;
import com.yootii.bdy.customer.service.CustomerResetPassWordService;
import com.yootii.bdy.customer.service.CustomerService;
import com.yootii.bdy.mail.MailSenderInfo;
import com.yootii.bdy.mail.SendMailUtil;
import com.yootii.bdy.security.service.AuthenticationService;
import com.yootii.bdy.user.model.Mail;
import com.yootii.bdy.user.model.User;
import com.yootii.bdy.util.MD5Util;

@Service("customerResetPassWordService")
public class CustomerResetPassWordServiceImpl implements CustomerResetPassWordService {
	@Resource
	protected AuthenticationService authenticationService;	
	@Resource
	protected CustomerService customerService;
	@Resource
	protected MailSenderInfo mailSenderInfo;
	/**
	 * @Description: 生成邮箱链接地址
	 */
	private String createLink(HttpServletRequest request, Customer customer,GeneralCondition gcon)
			throws Exception {

		// 生成密钥
		String secretKey = UUID.randomUUID().toString();

		// 设置过期时间
		Date outDate = new Date(System.currentTimeMillis() + 30 * 60 * 1000);// 30分钟后过期
		long date = outDate.getTime() / 1000 * 1000;// 忽略毫秒数 mySql 取出时间是忽略毫秒数的

		// 此处应该更新Users表中的过期时间、密钥信息
		outDate = new Date(date);
		customer.setValidDate(outDate);
		customer.setValidataCode(secretKey);
		customerService.modifyCustomerSelf(customer);

		String username = customer.getUsername();
		// 将用户名、过期时间、密钥生成链接密钥
		String key = username + "$" + date + "$" + secretKey;

		String digitalSignature = MD5Util.getMD5(key);// 数字签名
		
		System.out.println("ValidDate:" + outDate + " ValidataCode:"+ secretKey +" digitalSignature:"+ digitalSignature);

		String basePath = "";
		String resetPassPath = "";
		String type = "" ;//0代表客户1代表用户
		if (request != null) {

			basePath = request.getScheme() + "://" + request.getServerName()
					+ ":" + request.getServerPort() ;
			String restP = request.getParameter("resetPassPath");
			type = request.getParameter("type");
			if (restP != null && !restP.equals("")) {
				resetPassPath = restP;
			}else{
				//测试用
				String path = request.getContextPath();
				resetPassPath = path+ "/interface/customer/tofindpassword";
			}
		}

		String resetPassHref = basePath + "/bdy/bdy"+ resetPassPath + "?sid="
				+ digitalSignature + "&username=" + username+"&type=" + type;

		String emailContent = ""
				+ "<td>"
				+ "请勿回复本邮件。"
				+ "点击下面的链接，重设密码，本邮件超过30分钟，链接将会失效，需要重新申请找回密码。"
				+ "</td>"
				+ "<p></p>"
				+ "<td><a href=\""+resetPassHref+"\">"
				+ resetPassHref 
				+"</a></td>";

		return emailContent;
	}

	/**
	 * @Description: 该方法实现用户重置密码第一步： 接收前台发送过来的用户名，校验用户名， 如果用户名正确，生成带有重置密码链接的邮件
	 */
	public ReturnInfo validUser(HttpServletRequest request,Customer customer,
			GeneralCondition gcon) {
		// 返回结果对象
		ReturnInfo rtnInfo = new ReturnInfo();
		try {
			
			String username = customer.getUsername();			
			String email = customer.getEmail();
			
			if(username==null || username.equals("")){
				rtnInfo.setSuccess(false);
				rtnInfo.setMessage("用户名不能为空");
				rtnInfo.setMessageType(Globals.MESSAGE_TYPE_SESSION_INVALID);
				return rtnInfo;
			}
			if(email==null || email.equals("")){
				rtnInfo.setSuccess(false);
				rtnInfo.setMessage("邮箱不能为空");
				rtnInfo.setMessageType(Globals.MESSAGE_TYPE_SESSION_INVALID);
				return rtnInfo;
			}
						
			Customer customer2 = authenticationService.authenticationCustomer(customer,gcon, false);		
			if (customer2 == null) {
				rtnInfo.setSuccess(false);
				rtnInfo.setMessage("用户名不正确");
				rtnInfo.setMessageType(Globals.MESSAGE_TYPE_SESSION_INVALID);
				return rtnInfo;
			} 		
				
			String mailTo = customer2.getEmail();
			if(mailTo==null || mailTo.equals("")){
				rtnInfo.setSuccess(false);
				rtnInfo.setMessage("用户注册的邮箱为空，不能进行密码重置");
				rtnInfo.setMessageType(Globals.MESSAGE_TYPE_SESSION_INVALID);
				return rtnInfo;
			}
			
			if(!mailTo.equals(email)){
				rtnInfo.setSuccess(false);
				rtnInfo.setMessage("邮箱不正确");
				rtnInfo.setMessageType(Globals.MESSAGE_TYPE_SESSION_INVALID);
				return rtnInfo;
			}
				
			// 生成带有重置密码链接的邮件
			String mailContent = createLink(request, customer2,gcon);

			// 设置邮件参数
			String mailSubject = "找回密码";
			
			boolean validate = true;
			mailSenderInfo.setSubject(mailSubject);
			mailSenderInfo.setContent(mailContent);
			mailSenderInfo.setToAddress(mailTo);
			mailSenderInfo.setValidate(validate);

			// 发送邮件
			SendMailUtil sendMailUtil=new SendMailUtil();
			sendMailUtil.sendmail(mailSenderInfo);	
			Mail mail = new Mail();
			mail.setEmail(mailTo);
			
			rtnInfo.setData(mail);
			rtnInfo.setSuccess(true);
			

		} catch (Exception e) {
			rtnInfo.setSuccess(false);
			rtnInfo.setMessage("校验用户并发送邮件过程中出现异常：" + e.getMessage());
			rtnInfo.setMessageType(Globals.MESSAGE_TYPE_GETDATA_FAILED);
		}

		return rtnInfo;
	}

	/**
	 * @Description: 该方法实现用户重置密码第二步： 处理从邮箱链接过来的修改密码请求，
	 *               如果请求中的加密字符串正确，并且没有过期，给前台返回成功的信息
	 */
	public ReturnInfo toFindPassword(HttpServletRequest request) {
		// 返回结果对象
		ReturnInfo rtnInfo = new ReturnInfo();
		try {
			rtnInfo = findPasswordInternal(request);
		} catch (Exception e) {
			e.printStackTrace();
			rtnInfo.setSuccess(false);
			rtnInfo.setMessage("处理重置密码过程中出现异常：" + e.getMessage());
			rtnInfo.setMessageType(Globals.MESSAGE_TYPE_GETDATA_FAILED);
		}
		return rtnInfo;
	}

	private ReturnInfo findPasswordInternal(HttpServletRequest request) throws Exception {
		
		// 返回结果对象
		ReturnInfo rtnInfo = new ReturnInfo();

		// 获取链接中的加密字符串
		String sid = null;
		// 获取链接中的用户名
		String username = null;

		if (request != null) {
			sid = request.getParameter("sid");
			username = request.getParameter("username");
		}

		if (StringUtils.isEmpty(sid) || StringUtils.isEmpty(username)) {
			rtnInfo.setSuccess(false);
			rtnInfo.setMessage("请求的参数不正确，链接无效");
			rtnInfo.setMessageType(Globals.MESSAGE_TYPE_LINK_INVALID);
			return rtnInfo;
		}

		Customer customer = customerService.selectByUsername(username);

		if (customer != null) {
			Date date = customer.getValidDate();
			long outTime = date.getTime();
			if (outTime <= System.currentTimeMillis()) {
				// 找回密码链接已经过期
				rtnInfo.setSuccess(false);
				rtnInfo.setMessage("链接已经过期，链接无效");
				rtnInfo.setMessageType(Globals.MESSAGE_TYPE_LINK_INVALID);
				return rtnInfo;
			}

			// 获取当前登陆人的加密码
			String vailddataConde = customer.getValidataCode();
			String key = username + "$" + outTime / 1000 * 1000 + "$"
					+ vailddataConde;// 数字签名

			String digitalSignature = MD5Util.getMD5(key);// 数字签名
			System.out.println("ValidDate:" + outTime + " ValidataCode:"+ key +" digitalSignature:"+ digitalSignature + " sid:"+ sid);

			if (!digitalSignature.equals(sid)) {
				rtnInfo.setSuccess(false);
				rtnInfo.setMessage("链接中的加密密码不正确，链接无效");
				rtnInfo.setMessageType(Globals.MESSAGE_TYPE_LINK_INVALID);
			}

		} else {
			rtnInfo.setSuccess(false);
			rtnInfo.setMessage("用户名不正确");
			rtnInfo.setMessageType(Globals.MESSAGE_TYPE_SESSION_INVALID);

		}
		// 将验证成功返回给前台。
		rtnInfo.setSuccess(true);
		rtnInfo.setMessage("验证成功");
		return rtnInfo;
	}

	/**
	 * @Description: 该方法实现用户重置密码第三步： 处理前台发送过来的用户名和新设置的密码， 如果用户名正确，重新设置用户密码
	 */
	public ReturnInfo resetPassword(HttpServletRequest request,
			GeneralCondition gcon) {

		// 返回结果对象
		ReturnInfo rtnInfo = new ReturnInfo();

		String username = null;
		String rawPassword = null;

		try {
			// 再次检查链接是否有效
			ReturnInfo obj = findPasswordInternal(request);
			if (!obj.getSuccess()) {
				
				return obj;
			}

			if (request != null) {
				username = request.getParameter("username");
				rawPassword = request.getParameter("password");
			}

			if (StringUtils.isEmpty(username)
					|| StringUtils.isEmpty(rawPassword)) {
				rtnInfo.setSuccess(false);
				rtnInfo.setMessage("请求的参数不正确");
				rtnInfo.setMessageType(Globals.MESSAGE_TYPE_PARAM_INVALID);
				return rtnInfo;
			}

			Customer customer = customerService.selectByUsername(username);
			// 对密码不进行加密，在modify中加密
			customer.setPassword(rawPassword);
			// 更新数据库，重置用户密码
			customerService.modifyCustomerSelf(customer);

			rtnInfo.setSuccess(true);
			rtnInfo.setMessage("重置密码成功");

		} catch (Exception e) {
			rtnInfo.setSuccess(false);
			rtnInfo.setMessage("重置密码过程中出现异常：" + e.getMessage());
			rtnInfo.setMessageType(Globals.MESSAGE_TYPE_GETDATA_FAILED);
		}

		return rtnInfo;

	}


}
