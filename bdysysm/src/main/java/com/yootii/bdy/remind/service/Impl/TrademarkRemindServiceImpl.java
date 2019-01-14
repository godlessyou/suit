package com.yootii.bdy.remind.service.Impl;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.yootii.bdy.common.GeneralCondition;
import com.yootii.bdy.common.ReturnInfo;
import com.yootii.bdy.customer.service.CustomerService;
import com.yootii.bdy.invite.model.AgencyInvite;
import com.yootii.bdy.mail.MailSenderInfo;
import com.yootii.bdy.mail.SendMailUtil;
import com.yootii.bdy.remind.dao.TrademarkDongtaiMapper;
import com.yootii.bdy.remind.dao.TrademarkGonggaoMapper;
import com.yootii.bdy.remind.dao.TrademarkXuzhanMapper;
import com.yootii.bdy.remind.model.TrademarkDongtai;
import com.yootii.bdy.remind.model.TrademarkGonggao;
import com.yootii.bdy.remind.model.TrademarkXuzhan;
import com.yootii.bdy.remind.service.RemindService;
import com.yootii.bdy.remind.service.TrademarkRemindService;
import com.yootii.bdy.taskremind.dao.TaskRemindMapper;
import com.yootii.bdy.taskremind.model.TaskRemind;
import com.yootii.bdy.taskremind.service.TaskRemindService;
import com.yootii.bdy.user.model.User;
import com.yootii.bdy.user.service.UserService;


@Service
public class TrademarkRemindServiceImpl implements TrademarkRemindService {

	@Resource
	private TrademarkDongtaiMapper trademarkDongtaiMapper;
	@Resource
	private TrademarkGonggaoMapper trademarkGonggaoMapper;
	@Resource
	private TrademarkXuzhanMapper trademarkXuzhanMapper;
	

	@Resource
	private UserService userService;
	@Resource
	private CustomerService customerService;
	

	@Resource
	protected MailSenderInfo mailSenderInfo;
	
	@Override
	public ReturnInfo selectTrademarkXuzhan(Integer agencyId, Integer custId,Boolean justcount,GeneralCondition gcon) {
		ReturnInfo ret = new ReturnInfo();

		ret.setTotal(trademarkXuzhanMapper.selectcountByTrademarkXuzhan(agencyId, custId));
		if(!justcount) {
			ret.setData(trademarkXuzhanMapper.selectByTrademarkXuzhan(agencyId, custId, gcon));
		}
		ret.setSuccess(true);
		ret.setMessage("查询成功");
		ret.setCurrPage(gcon.getPageNo());
		return ret;
	}

	@Override
	public ReturnInfo SendTrademarkXuzhan(Integer custId,Integer userId) throws Exception {		
		ReturnInfo ret = new ReturnInfo();
		GeneralCondition gcon = new GeneralCondition();
		List<TrademarkXuzhan> list = trademarkXuzhanMapper.selectByTrademarkXuzhan(null, custId, gcon);
		String message = "尊敬的用户"+customerService.getCustById(custId).getName()+"：您好！"+
				"<p></p>"+
				"下表中贵司名下的商标已进入或即将进入续展期，请及时提交续展申请，以免到期未续展导致商标失效。"+
				"<p></p>";
		message += "<table border='1'>" +
				"<thead><tr>" +
				"<td>序号</td><td>商标名</td><td>商标号</td><td>类别</td><td>申请人</td><td>注册日期</td><td>续展期</td></thead>";
		int i=1;	
		for(TrademarkXuzhan a:list) {
			message += "<tr><td>"+i+"</td>"+
					"<td>"+a.getTmName()+"</td>" +
					"<td>"+a.getRegNumber()+"</td>" +
					"<td>"+a.getTmType()+"</td>" +
					"<td>"+a.getAppName()+"</td>" +
					"<td>"+a.getValidStartDate()+"</td>" +
					"<td>"+a.getValidStartDate()+"至"+a.getValidEndDate()+"</td></tr>";
					i++;
		}
		message+="</table>";
		
		if (userId!=null) {
			User user = userService.getUserById(userId);
			message = message +"<p></p>如需续展，请联系商标顾问"+user.getFullname()+"，邮箱："+
					user.getEmail()+"或电话:"+user.getPhone();
			}
		else 
			message = message +"<p></p>如需续展，请联系商标顾问。";
		
		SendMessage(message,custId,"商标续展提醒");
		return ret;
	}

	@Override
	public ReturnInfo selectTrademarkChushenGG(Integer agencyId, Integer custId,Boolean justcount,GeneralCondition gcon) {
		ReturnInfo ret = new ReturnInfo();

		ret.setTotal(trademarkGonggaoMapper.selectcountByTrademarkChushengg(agencyId, custId));
		if(!justcount) {
			ret.setData(trademarkGonggaoMapper.selectByTrademarkChushengg(agencyId, custId, gcon));
		}
		ret.setSuccess(true);
		ret.setMessage("查询成功");
		ret.setCurrPage(gcon.getPageNo());
		return ret;
	}

	@Override
	public ReturnInfo SendTrademarkChushenGG(Integer custId) throws Exception {
		ReturnInfo ret = new ReturnInfo();
		GeneralCondition gcon = new GeneralCondition();
		List<TrademarkGonggao> list = trademarkGonggaoMapper.selectByTrademarkChushengg(null, custId, gcon);
		String message =  "<td>"
				+ "请勿回复本邮件。"
				+ "</td>"
				+ "<p></p>";		
		for(TrademarkGonggao a:list) {
			message = message 
					+ "<td>"
					+ "你商标号为"+a.getRegNumber()+"的商标(商标名："+a.getTmName()+"）有公告信息（公告期号"+a.getGgQihao()+"），"
					+"</td>";
		}
		SendMessage(message,custId,"商标公告提醒");
		return ret;
	}

	@Override
	public ReturnInfo selectTrademarkSongdaGG(Integer agencyId, Integer custId,Boolean justcount,GeneralCondition gcon) {
		ReturnInfo ret = new ReturnInfo();

		ret.setTotal(trademarkGonggaoMapper.selectcountByTrademarkSongdagg(agencyId, custId));
		if(!justcount) {
			ret.setData(trademarkGonggaoMapper.selectByTrademarkSongdagg(agencyId, custId, gcon));
		}
		ret.setSuccess(true);
		ret.setMessage("查询成功");
		ret.setCurrPage(gcon.getPageNo());
		return ret;
	}

	@Override
	public ReturnInfo SendTrademarkSongdaGG(Integer custId) throws Exception {
		ReturnInfo ret = new ReturnInfo();
		GeneralCondition gcon = new GeneralCondition();
		List<TrademarkGonggao> list = trademarkGonggaoMapper.selectByTrademarkSongdagg(null, custId, gcon);
		String message =  "<td>"
				+ "请勿回复本邮件。"
				+ "</td>"
				+ "<p></p>";		
		for(TrademarkGonggao a:list) {
			message = message 
					+ "<td>"
					+ "你商标号为"+a.getRegNumber()+"的商标(商标名："+a.getTmName()+"）有公告信息（公告期号"+a.getGgQihao()+"），"
					+"</td>";
		}
		SendMessage(message,custId,"商标公告提醒");
		return ret;
	}

	@Override
	public ReturnInfo selectTrademarkDongtai(Integer agencyId, Integer custId,Boolean justcount,GeneralCondition gcon) {
		ReturnInfo ret = new ReturnInfo();

		ret.setTotal(trademarkDongtaiMapper.selectcountByTrademarkDongtai(agencyId, custId));
		if(!justcount) {
			ret.setData(trademarkDongtaiMapper.selectByTrademarkDongtai(agencyId, custId,gcon));
		}
		ret.setSuccess(true);
		ret.setMessage("查询成功");
		ret.setCurrPage(gcon.getPageNo());
		return ret;
	}

	@Override
	public ReturnInfo SendTrademarkDongtai(Integer custId) throws Exception {
		ReturnInfo ret = new ReturnInfo();
		GeneralCondition gcon = new GeneralCondition();
		List<TrademarkDongtai> list = trademarkDongtaiMapper.selectByTrademarkDongtai(null, custId,gcon);
		String message =  "<td>"
				+ "请勿回复本邮件。"
				+ "</td>"
				+ "<p></p>";		
		for(TrademarkDongtai a:list) {
			message = message 
					+ "<td>"
					+ "你商标号为"+a.getRegNumber()+"的商标（商标名："+a.getTmName()+"）变化状态："+a.getrTitle()+"，"
					+"</td>";
		}
		SendMessage(message,custId,"商标动态提醒");
		return ret;
	}

	private void SendMessage(String message, Integer custId,String mailSubject) throws Exception {
		mailSenderInfo.setSubject(mailSubject);
		mailSenderInfo.setContent(message);
		mailSenderInfo.setToAddress(customerService.getCustById(custId).getEmail());
		mailSenderInfo.setValidate(true);

		// 发送邮件
		SendMailUtil sendMailUtil=new SendMailUtil();
		Boolean ret = sendMailUtil.sendmail(mailSenderInfo);	
		
	}

	

	
}