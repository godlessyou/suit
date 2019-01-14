package com.yootii.bdy.util;

import java.util.List;

import org.apache.poi.ss.formula.functions.T;

public class ListUtils {

	public static  List top(int offset,int rows,List list) {
		
		try {
			List ret = list.getClass().newInstance();
			int begin = offset*rows;
			if(list.size()<begin) return list;
			for(int i = 0;i<rows;i++) {				
				ret.add(list.get(begin+i));
				if(begin+i==list.size()) return ret;
			}			
			return ret;
		} catch (Exception e) {
			return list;
			// TODO 自动生成的 catch 块
		}
		


	}
}
