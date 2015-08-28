package com.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegCheck {
		public static boolean Check(String reg,String str){
			boolean tem=false;
			Pattern p=Pattern.compile(reg);
			Matcher m=p.matcher(str);
			tem=m.matches();
			return tem;
		}
		public static boolean CheckChinese(String str){
			String reg="^[\u4E00-\u9FA5]{1,}[0-9]*$";
			return Check(reg,str);
		}
		public static boolean CheckNum(String str){
			String reg="^[0-9]+$";
			return Check(reg,str);
		}
		public static boolean CheckLetterAndNum(String str){
			//String reg="^[A-Za-z0-9]*_{0,1}[A-Za-z0-9]*$";
			return true;//Check(reg,str);
		}
		public static boolean CheckKeyword(String str){
			String reg="^[\u4E00-\u9FA5A-Za-z0-9]*·{0,1}[\u4E00-\u9FA5A-Za-z0-9]*$";
			return Check(reg,str);
		}
		public static boolean CheckEnglish(String str){
			String reg="^[A-Za-z]*$";
			return Check(reg,str);
		}
		
}
