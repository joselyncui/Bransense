package com.matrix.brainsense.util;

import java.text.DecimalFormat;

public class NumberUtil {
	
	public static double doubleFormat(double number){
		DecimalFormat df=new DecimalFormat("#.00");
		return Double.valueOf(df.format(number));
	}
	
	public static double doubleFormat(String number, String format){
		double doubleNumber = Double.valueOf(number);
		DecimalFormat df=new DecimalFormat(format);
		return Double.valueOf(df.format(doubleNumber));
	}
	
	public static boolean isNumeric(String str) {
		for (int i = str.length(); --i >= 0;) {
			int chr = str.charAt(i);
			if (chr < 48 || chr > 57)
				return false;
		}
		return true;
	}
	
}
