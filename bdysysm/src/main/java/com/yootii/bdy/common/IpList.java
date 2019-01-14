package com.yootii.bdy.common;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import com.yootii.bdy.util.GraspUtil;

public class IpList {
	 public static Map<Integer, List<Object[]> > IpList = new HashMap<Integer, List<Object[]>>();  
	 
	 static {  
		 List<String> lines = null;
         Map<Integer, List<Object[]>> map = new HashMap<Integer, List<Object[]>>();
		 try {		
			 InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("delegated-apnic-latest");
			 lines = IOUtils.readLines(input, StandardCharsets.UTF_8);
		 } catch (IOException e1) {
			 // TODO 自动生成的 catch 块
			 e1.printStackTrace();
		 }
		 
		 
		 if(lines!=null) {
			 for (String line : lines) {
	                if (line.startsWith("apnic|") && line.contains("|ipv4|")) {
	                    // 只处理属于中国的ipv4地址
	                    String[] strs = line.split("\\|");
	                    String ip = strs[3];
	                    String con = strs[1];
	                    if(con!="CN"||con!="FR") continue;
	                    
	                    String[] add = ip.split("\\.");
	                    if(add.length<3) continue;
	                    int count = Integer.valueOf(strs[4]);
	 
	                    int startIp = Integer.parseInt(add[0]) * 256 + Integer.parseInt(add[1]);
	                    while (count > 0) {
	                        if (count >= 65536) {
	                            // add1,add2 整段都是中国ip
	                        	Object[] ipRange = new Object[1];
	                        	ipRange[0] = con.toLowerCase();
	                        	List<Object[]> list = new ArrayList<Object[]>();
	                        	map.put(startIp, list);
	                        	list.add(ipRange);
	                        	
	                            count -= 65536;
	                            startIp += 1;
	                        } else {
	 
	                        	Object[] ipRange = new Object[3];
	                        	ipRange[0] = con.toLowerCase();
	                            ipRange[1] = Integer.parseInt(add[2]) * 256 + Integer.parseInt(add[3]);
	                            ipRange[2] = Integer.valueOf(ipRange[1].toString()).intValue() + count;
	                            count -= count;
	 
	                            List<Object[]> list = map.get(startIp);
	                            if (list == null) {
	                                list = new ArrayList<Object[]>();
	                                map.put(startIp, list);
	                            }
	 
	                            list.add(ipRange);
	                        }
	                    }
	                }
	            }
			 IpList = map;
		 }
		 
		 
		 
	 }
}
