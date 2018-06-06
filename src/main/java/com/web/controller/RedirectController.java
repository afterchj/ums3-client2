package com.web.controller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.utils.DateUtil;
import com.utils.LoggerUtils;
import com.web.vo.RedirectVo;

@Controller("redirectController")
@RequestMapping("/redirect")
public class RedirectController {
	
	private Logger logger = LoggerUtils.REDIRECT;
	
	@RequestMapping("/ad")
	public String ad(@ModelAttribute RedirectVo form, HttpServletResponse response) throws IOException{
		if(StringUtils.isNotBlank(form.getLinkAddr())){
			form.setClazz("ad");
			form.setCreateDate(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			logger.warn(form);
			response.sendRedirect(form.getLinkAddr());
		}
		
		return null;
	}
	
	@RequestMapping("/detail")
	public String detail(@ModelAttribute RedirectVo form, HttpServletResponse response) throws IOException{
		if(StringUtils.isNotBlank(form.getLinkAddr())){
			form.setClazz("detail");
			form.setCreateDate(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			logger.warn(form);
			response.sendRedirect(form.getLinkAddr());
		}
		
		return null;
	}
}
