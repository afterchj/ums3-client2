package com.model.dd;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.utils.PropertiesLoader;

public class DataDict {
	
	public static final PropertiesLoader PROPERTIES_LOADER = PropertiesLoader.getInstance();
	
	public static String getGender(String g){
		if("f".equalsIgnoreCase(g) || "female".equalsIgnoreCase(g)){
			return "female";
		}else {
			return "male";
		}
	}
	
	public static String getIconLevel(String fileName) {
		Pattern p = Pattern.compile("[\\d]+");
		Matcher m = p.matcher(fileName);
		while (m.find()) {
			return m.group();
		}
		return "";
	}
	
}
