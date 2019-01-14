package com.yootii.bdy.util;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;

public class StringUtils {
	
	// 整型数组排序
	public static void bubbleSort(int[] numbers) {
		int temp; // 记录临时中间值
		int size = numbers.length; // 数组大小
		for (int i = 0; i < size - 1; i++) {
			for (int j = i + 1; j < size; j++) {
				if (numbers[i] > numbers[j]) { // 交换两数的位置
					temp = numbers[i];
					numbers[i] = numbers[j];
					numbers[j] = temp;
				}
			}
		}
	}
	
	// 字符串排序
	public static void listSort(List<?> list) {		
		Collections.sort(list, new Comparator<Object>() {
	      @Override
	      public int compare(Object o1, Object o2) {
	        return new Integer((String) o1).compareTo(new Integer((String) o2));
	      }
	    });		
	}
	

	//判断字符串是否是数字， 只要字符串中有一个字符不是数字，就返回false
	public static boolean isNumeric(String str) {
		for (int i = 0; i < str.length(); i++) {
//			System.out.println(str.charAt(i));
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}
	
	
	//判断字符串是否是数字或者英文
	public static boolean isNotNumeric(String str) {
		for (int i = 0; i < str.length(); i++) {			
			if (Character.isDigit(str.charAt(i))) {			
				return false;
			}
		}
		return true;
	}
	
	
	public static boolean isNumericOrEn(String word) { 
		for (int i = 0; i < word.length(); i++) {
			char c=word.charAt(i);
			if (!Character.isDigit(c)) {
				//如果既不是数字，也不是英文，那么返回false
				 if (!(c >= 'A' && c <= 'Z')  && !(c >= 'a' && c <= 'z')) {  
		               return false;  
		         } 
			}
		}  
		return true;
    }  
	


	public static boolean isInteger(String str) {
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
		return pattern.matcher(str).matches();
	}
	
	
	
	public static boolean isNum(String str) {
		return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
	}
	
	
	public static String getPercent(long y, long z, boolean baifenhao){
		String baifenbi = "";// 接受百分比的值
		double baiy = y * 1.0;
		double baiz = z * 1.0;
		double fen = baiy / baiz;
		// NumberFormat nf = NumberFormat.getPercentInstance();注释掉的也是一种方法
		// nf.setMinimumFractionDigits( 2 ); 保留到小数点后几位
//		DecimalFormat df1 = new DecimalFormat("##.00%");
		DecimalFormat df2 = new DecimalFormat("0.00%");
		
		// ##.00%
		// 百分比格式，后面不足2位的用0补齐
		// baifenbi=nf.format(fen);
//		baifenbi = df1.format(fen);		
		
		baifenbi = df2.format(fen);	
		if(!baifenhao){	
			if (baifenbi!=null){
				int len=baifenbi.length();
				baifenbi = baifenbi.substring(0,len-1);
			}			
		}	
		
		return baifenbi;
	} 
	
	public static void main(String[] args) {
//		 ArrayList list = new ArrayList();
//	    list.add("1");
//	    list.add("10");
//	    list.add("22");
//	    list.add("33");
//	    list.add("41");
//	    list.add("45");	    
//	    listSort(list);
	    
//	    System.out.println(list.toString());
		
		String baifenbi=getPercent(29,59, false);
		System.out.println(baifenbi);
		
		baifenbi=getPercent(29,59, true);
		System.out.println(baifenbi);
		 
		 
	}


}
