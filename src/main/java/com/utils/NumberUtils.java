package com.utils;

import java.text.DecimalFormat;
import java.text.ParseException;

import org.apache.commons.lang3.StringUtils;

public class NumberUtils {
	public static Number stringToDouble(String num, String format){
		if(StringUtils.isBlank(num)){
			return null;
		}
		if(StringUtils.isBlank(format)){
			return Double.parseDouble(num);
		}
		try {
			return new DecimalFormat(format).parse(new DecimalFormat(format).format(Double.parseDouble(num)));
		} catch (NumberFormatException e) {
//			e.printStackTrace();
		} catch (ParseException e) {
//			e.printStackTrace();
		}
		return 0.0;
	}
	
	public static Number doubleToDouble(Double num, String format){
		if(num == null){
			return null;
		}
		if(StringUtils.isBlank(format)){
			return num;
		}
		try {
			return new DecimalFormat(format).parse(new DecimalFormat(format).format(num));
		} catch (NumberFormatException e) {
//			e.printStackTrace();
		} catch (ParseException e) {
//			e.printStackTrace();
		}
		return 0.0;
	}
}
