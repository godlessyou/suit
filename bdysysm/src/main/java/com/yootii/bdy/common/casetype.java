package com.yootii.bdy.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class casetype {
	 public static final Map<Integer, Double > MoneyList = new HashMap<Integer, Double >();
	 public static final Map<Integer, String > casetypeList = new HashMap<Integer, String >();
	 public static final Map< String ,Integer> casetypeIdList = new HashMap< String ,Integer>();
	 
	 static {  
		 
		 
		 casetypeList.put(1, "商标注册");
		 casetypeList.put(2, "商标续展");
		 casetypeList.put(3, "商标转让");
		 casetypeList.put(4, "变更代理人/文件接收人");
		 casetypeList.put(5, "变更名义地址集体管理规则成员名单");
		 casetypeList.put(6, "商标更正");
		 casetypeList.put(7, "商标变更");
		 casetypeList.put(8, "商标异议申请");
		 casetypeList.put(9, "商标异议答辩");
		 casetypeList.put(10, "商标无效宣告");
		 casetypeList.put(11, "商标撤三申请");
		 casetypeList.put(12, "商标驳回复审");
		 casetypeList.put(13, "商标不予注册复审");
		 
		 casetypeIdList.put("商标注册",1);
		 casetypeIdList.put("商标续展",2);
		 casetypeIdList.put("商标转让",3);
		 casetypeIdList.put("变更代理人/文件接收人",4);
		 casetypeIdList.put("变更名义地址集体管理规则成员名单",5);
		 casetypeIdList.put("商标更正",6);
		 casetypeIdList.put("商标变更",7);
		 casetypeIdList.put("商标异议申请",8);
		 casetypeIdList.put("商标异议答辩",9);
		 casetypeIdList.put("商标无效宣告",10);
		 casetypeIdList.put("商标撤三申请",11);
		 casetypeIdList.put("商标驳回复审",12);
		 casetypeIdList.put("商标不予注册复审",13);

	 
		 MoneyList.put(1, 300.00); 
		 MoneyList.put(2, 250.00); 
		 MoneyList.put(3, 500.00); 
		 MoneyList.put(4, 250.00); 
		 MoneyList.put(5, 250.00); 
		 MoneyList.put(6, 250.00); 
		 MoneyList.put(7, 250.00); 
		 MoneyList.put(8, 750.00); 
		 MoneyList.put(9, 750.00); 
		 MoneyList.put(10, 500.00); 
		 MoneyList.put(11, 500.00); 
		 MoneyList.put(12, 500.00); 
		 MoneyList.put(13, 500.00);
		 MoneyList.put(14, 500.00);
		 MoneyList.put(15, 500.00);
		 MoneyList.put(16, 500.00);
		 MoneyList.put(17, 500.00);
		 MoneyList.put(18, 500.00);
		 MoneyList.put(19, 500.00);
		 MoneyList.put(20, 500.00);
		 MoneyList.put(22, 500.00);
	
//	受理商标注册费
//	300元
//	受理集体商标注册费
//	1500元
//	受理证明商标注册费
//	1500元
//	补发商标注册证费
//	500元
//	含刊登遗失声明的费用
//	受理转让注册商标费
//	500元
//	受理商标续展注册费
//	1000元
//	受理续展注册迟延费
//	250元
//	受理商标评审费
//	750元
//	商标异议费
//	500元
//	变更费
//	250元
//	出具商标证明费
//	50元
//	撤销商标费
//	500元
//	商标使用许可合同备案费
//	150元
	 }

}
