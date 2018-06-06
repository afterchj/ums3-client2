package com.web.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.service.MemberService;
import com.utils.LoggerUtils;
import com.web.vo.LoginVo;

@Controller("memberController")
@RequestMapping("/app")
public class MemberController extends BaseController{

	private MemberService memberService;
	private Logger system = LoggerUtils.SYSTEM;

	@Autowired
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	@RequestMapping("/member/login")
	public String login(@RequestParam(value="params", required=false)String params, ModelMap model) throws Exception {
		if(StringUtils.isBlank(params)){
			return null;
		}
		LoginVo paramObject = memberService.generate(params);
		if(paramObject == null){
			system.error("MemberController login. params cannot be decoded : " + params);
			return null;
		}
		LoginVo result = memberService.login(paramObject);
		
		if(result != null){
			model.put("member", result.toString());
		}
		return null;
	}
	
	@RequestMapping("/member/update")
	public String update(@RequestParam(value="params", required=false)String params, ModelMap model) throws Exception {
		if(StringUtils.isBlank(params)){
			return null;
		}
		LoginVo paramObject = memberService.generate(params);
		boolean result = memberService.update(paramObject);
		model.put("result", result);
		return null;
	}
	
}
