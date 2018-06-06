package com.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ModelAttribute;

import com.alibaba.fastjson.JSONObject;

public class BaseDecodedController extends BaseController{
	
	public BaseDecodedController(){
		super.encryption = true;
	}
	
	@ModelAttribute("decodedParams")
	public JSONObject beforeInvokingHandlerMethod(HttpServletRequest request){
		return (JSONObject) request.getAttribute("decodedParams");
	}

}
