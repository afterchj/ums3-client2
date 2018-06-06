package com.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

public class StringUtil {
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");

	private static SimpleDateFormat sdf3 = new SimpleDateFormat("yyyyMMdd");
	
	private static SimpleDateFormat sdf4 = new SimpleDateFormat("yyyyMMddHHmmss");

	public static String valueOf(Object obj) {
		String str = (obj == null || "null".equalsIgnoreCase(obj.toString().trim())) ? "" : obj.toString();
		return str;
	}

	public static Date stringToDate(String str) {
		if (StringUtils.isBlank(str)) {
			return null;
		}
		Date d = null;
		try {
			d = sdf.parse(str);
		} catch (ParseException e) {
//			e.printStackTrace();
			throw new RuntimeException("日期格式转换错误:" + str);
		}
		return d;
	}

	public static String dateToString(Date date) {
		// System.out.println("here");
		if (date == null) {
			return " ";
		}
		return sdf.format(date);
	}
	
	
	public static Date stringToDateFullNumber(String str) {
		if (StringUtils.isBlank(str)) {
			return null;
		}
		Date d = null;
		try {
			d = sdf4.parse(str);
		} catch (ParseException e) {
//			e.printStackTrace();
			throw new RuntimeException("日期格式转换错误:" + str);
		}
		return d;
	}

	public static String dateToStringWithFullNumber(Date date) {
		// System.out.println("here");
		if (date == null) {
			return " ";
		}
		return sdf4.format(date);
	}

	public static String dateToStringWithDate(Date date) {
		if (date == null) {
			return null;
		}
		return sdf2.format(date);
	}

	public static String dateToStringWithNumber(Date date) {
		if (date == null) {
			return " ";
		}
		return sdf3.format(date);
	}

	public static Date stringToDateWithDate(String str) {
		if (StringUtils.isBlank(str)) {
			return null;
		}
		Date d = null;
		try {
			d = sdf2.parse(str);
		} catch (ParseException e) {
//			e.printStackTrace();
			throw new RuntimeException("日期格式转换错误");
		}
		return d;
	}

	public static Date stringToDateWithNumber(String str) {
		if (StringUtils.isBlank(str)) {
			return null;
		}
		Date d = null;
		try {
			d = sdf3.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
			throw new RuntimeException("日期格式转换错误");
		}
		return d;
	}
	
	public static String getUUID(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	public static void main(String[] args) {
		System.out.println(getUUID());
	}

}
