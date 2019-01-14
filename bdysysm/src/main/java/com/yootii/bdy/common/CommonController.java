package com.yootii.bdy.common;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.apache.shiro.SecurityUtils;
//import org.apache.shiro.authz.UnauthorizedException;
//import org.apache.shiro.session.Session;
//import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;

import com.yootii.bdy.agency.service.AgencyService;
import com.yootii.bdy.customer.service.CustomerService;
import com.yootii.bdy.security.model.Token;
import com.yootii.bdy.security.service.AuthenticationService;
import com.yootii.bdy.security.service.SysService;
import com.yootii.bdy.user.model.User;
import com.yootii.bdy.user.service.UserService;
import com.yootii.bdy.util.JsonUtil;




public class CommonController {
	
	@Resource
	protected SysService sysService;
	@Resource
	protected AuthenticationService authenticationService;
	@Resource
	protected UserService userService;
	@Resource
	protected CustomerService customerService;
	@Resource
	protected AgencyService agencyService;
	
	private final static ThreadLocal local = new ThreadLocal();
	  
	private final static ThreadLocal local1 = new ThreadLocal();    

	public void addTokenId(String str) {
		local.set(str);
		
	}
	public Object getTokenId() {
		
		return local.get();
		
	}  
	
	public void addURL(String str) {
		local1.set(str);
		
	}
	public Object getURL() {
		
		return local1.get();
		
	}  
		
	protected ReturnInfo checkUser(HttpServletRequest request,GeneralCondition gcon) {
		ReturnInfo rtnInfo = new ReturnInfo();
//		if (request != null) {
			rtnInfo =  authenticationService
					.authorize(request, gcon);
//		}
		if (!rtnInfo.getSuccess())
			return rtnInfo;
//		String tokenId = gcon.getTokenID();
//		Token token = sysService.checkToken(tokenId);
//		Integer userID = token.getUserID();
//		User user = userService.getUserById(userID);
//		Integer userType = user.getUserType();
//		boolean isUser=token.isUser();
		//把user放在了session里
		/*Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		session.setAttribute("user", user);*/
		return rtnInfo;
	}
	protected ReturnInfo checkUserAndRole(HttpServletRequest request,GeneralCondition gcon,String role) {
		ReturnInfo rtnInfo = new ReturnInfo();
//		if (request != null) {
			rtnInfo =  authenticationService
					.authorize(request, gcon);
//		}
		if (!rtnInfo.getSuccess())
			return rtnInfo;
		String tokenId = gcon.getTokenID();
		Token token = sysService.checkToken(tokenId);
		Integer userID = token.getUserID();
		User user = userService.getUserById(userID);
		if(user==null){
			rtnInfo.setSuccess(false);
			rtnInfo.setMessage("角色验证失败");
			return rtnInfo;
		}
		boolean flag = userService.hasRole(role, user);
		if(!flag){
			rtnInfo.setSuccess(false);
			rtnInfo.setMessage("角色验证失败");
			return rtnInfo;
		}
		Integer userType = user.getUserType();
		//把user放在了session里
		/*Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		session.setAttribute("user", user);*/
		return rtnInfo;
	}
	protected void makeOffsetAndRows(GeneralCondition gcon) {
		// 当前页号（默认0）
		Integer defaultPageNo = 1;
		// 每页大小（默认10）
		Integer defaultPageSize = 10;
		// 截止页（默认1）
		Integer defaultPageEnd = 1;

		Integer pageNo = (Integer) gcon.getPageNo();
		Integer pageSize = (Integer) gcon.getPageSize();
		Integer pageEnd = defaultPageEnd;

		int startPage = defaultPageNo;
		if (pageNo != null) {
			startPage = pageNo;
		}
		
		gcon.setPageNo(startPage);
		
		
		if (startPage > 0) {
			startPage--;
		}

		Integer offset = startPage * defaultPageSize;

		Integer rows = defaultPageSize * defaultPageEnd;

		if (pageSize != null && pageSize > 0) {
			offset = startPage * pageSize;
			rows = pageSize * defaultPageEnd;
		}

		gcon.setOffset(offset);
		gcon.setRows(rows);
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new DateEditor());
	}
	
//	 @ExceptionHandler({ UnauthorizedException.class, UnauthorizedException.class })
//	    public String authorizationException(HttpServletRequest request, HttpServletResponse response) {
//	        if (WebUtilsPro.isAjaxRequest(request)) {
//	            // 输出JSON
//	            Map<String,Object> map = new HashMap<String,Object>();
//	            map.put("code", "-4");
//	            map.put("message", "没有权限");
//	            writeJson(map, response);
//	            return null;
//	        } else {
//	        	 Map<String,Object> map = new HashMap<String,Object>();
//		            map.put("code", "-4");
//		            map.put("message", "没有权限");
//		            writeJson(map, response);
//		            return null;
//	           // return "redirect:/index.jsp";
//		            
//	        }
//	    }
	 public static class WebUtilsPro {

		    /**
		     * 是否是Ajax请求
		     *
		     * @param request
		     * @return
		     * @author SHANHY
		     * @create 2017年4月4日
		     */
		    public static boolean isAjaxRequest(HttpServletRequest request) {
		        String requestedWith = request.getHeader("x-requested-with");
		        if (requestedWith != null && requestedWith.equalsIgnoreCase("XMLHttpRequest")) {
		            return true;
		        } else {
		            return false;
		        }
		    }
		}
	 private void writeJson(Map<String,Object> map, HttpServletResponse response) {
	        PrintWriter out = null;
	        try {
	            response.setCharacterEncoding("UTF-8");
	            response.setContentType("application/json; charset=utf-8");
	            out = response.getWriter();
	            out.write(JsonUtil.toJson(map));
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if (out != null) {
	                out.close();
	            }
	        }
	    }
	/* private final static ThreadLocal local = new ThreadLocal();
	  

		public void addTokenId(String tokenID) {
			local.set(tokenID);
			
		}
		public Object getTokenId() {
			
			return local.get();
			
		}  
	 */
}
