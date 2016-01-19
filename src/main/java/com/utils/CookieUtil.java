package com.utils;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Cookie工具
 * @author WING
 *
 */
public class CookieUtil{
	/**
	 * 保存Cookie，指定最大生存时间
	 * @param request
	 * @param response
	 * @param name cookie名
	 * @param value cookie值
	 * @param maxAge cookie最大生存时间
	 * @throws UnsupportedEncodingException
	 */
	public static void saveCookie(
			HttpServletRequest request,HttpServletResponse response,
			String name,String value,int maxAge) throws UnsupportedEncodingException{
		Cookie cookie = new Cookie(name, URLEncoder.encode(value, "UTF-8"));
		cookie.setMaxAge(maxAge);
		cookie.setPath(request.getContextPath());
		response.addCookie(cookie);
	}
	/**
	 * 保存Cookie，默认保存一个星期
	 * @param request
	 * @param response
	 * @param name cookie名
	 * @param value cookie值
	 * @throws UnsupportedEncodingException
	 */
	public static void saveCookie(
			HttpServletRequest request,HttpServletResponse response,
			String name,String value) throws UnsupportedEncodingException{
		CookieUtil.saveCookie(request, response, name, value, 7 * 24 * 60 * 60);
	}
	/**
	 * 取指定cookie的值
	 * @param name cookie名
	 * @param request
	 * @return String cookie的值
	 * @throws UnsupportedEncodingException
	 */
	public static String getCookie(String name,HttpServletRequest request) throws UnsupportedEncodingException{
		Cookie[] cookies = request.getCookies();
		if(cookies != null){
			for (Cookie cookie : cookies) {
				if(name.equals(cookie.getName())){
					return URLDecoder.decode(cookie.getValue(), "UTF-8");
				}
			}
		}
		return null;
	}
	/**
	 * 删除指定的cookie
	 * @param request
	 * @param response
	 * @param name cookie名
	 */
	public static void delete(HttpServletRequest request,HttpServletResponse response,String name){
		Cookie cookie = new Cookie(name, "");
		cookie.setMaxAge(0);
		cookie.setPath(request.getContextPath());
		response.addCookie(cookie);
	}
}
