package com.yootii.bdy.common;

public class Constant {
	
	public static final class ROLETYPE{
		public static final String AGENCY_MANAGER = "代理机构管理员";
		public static final String PLATFORM_MANAGER = "平台管理员";
	}
	
	public static final class USERTYPE {
		public static final int SYSTEM_ADMIN = 1;
		public static final int GUEST = 2;
		public static final int CUSTOMER = 10;
		public static final int CUSTOMER_TRIER = 19;
		public static final int AGENT = 20;
		public static final int AGENT_LEADER = 21;
		public static final int AGENT_DEPT_MANAGER = 22;
		public static final int AGENT_GENERAL_MANAGER = 23;
	}

	public static final class MESSAGETYPE {
		public final static Integer SESSION_INVALID = -1;// session失效
		public final static Integer GETDATA_FAILED = -2;// 操作数据库失败
		public final static Integer AUTHORTY_INVALID = -3;// 没有访问权限
		public final static Integer PARAM_INVALID = -4;// 参数不正确
		public final static Integer URL_INVALID = -5;// 无效的链接
		public final static Integer OPERATION_INVALID = -6;// 无效操作
		public final static Integer OTHER = -99;// 无效操作
	}
	public static final String base64ImageStr ="data:image/png;base64,";
	
	public static final String imageDir = "downloadimage";
	
	public static final String uploadDir = "upload";
	
	public static final String exportDir = "export";
	
	public static final String casefileDir = "mydoc";
	
	public static final String app_prefix = "biaodeyi";

	public static final String catalina_home = System.getProperty("catalina.home","C:/temp");
	
	public static final String ftpConfigFile = "ftp.properties";

	public static final String app_dir = catalina_home + "/"+ app_prefix;

	public static final String image_dir = app_dir + "/"+ imageDir;
	
	public static final String upload_dir = app_dir + "/" + uploadDir;
	
	public static final String export_dir = app_dir + "/" + exportDir;
	

	
	public static final String agencyLogo = "/agencylogo/";
	
	public static final String platformServiceLogo = "/platformservicelogo/";
	
	public static final String userIcon = "/usericon/";
}
