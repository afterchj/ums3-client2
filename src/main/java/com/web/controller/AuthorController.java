package com.web.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.service.CountService;
import com.service.FileStoreInfoService;
import com.utils.Encodes;
import com.web.vo.FrontThemeFileVo;

@Controller("authorController")
@RequestMapping("/app")
public class AuthorController {
	
	private Logger logger = Logger.getLogger(AuthorController.class);
	
	@Autowired
	private CountService countService;
	
	@ExceptionHandler(RuntimeException.class)
	public String handleException(RuntimeException re,
			HttpServletRequest request) {
		logger.error("author." + re.getMessage());
		if (request.getContextPath().indexOf("\\/m\\/") > 0) {
			return "forword:/app/m/home";
		} else {
			return "forward:/app/f/home";
		}
	}
	
	private FileStoreInfoService fileStoreInfoService;
	
	@RequestMapping("/{gender}/author/{name}")
	public String author(@PathVariable("gender") String gender, @PathVariable("name")String name, ModelMap model) throws UnsupportedEncodingException{
		String realName = new String(Encodes.decodeBase64(name), "utf-8");
		List<FrontThemeFileVo> fileStoreInfoVos = fileStoreInfoService.getAuthorPage(realName, 1l);
		for (FrontThemeFileVo vo : fileStoreInfoVos) {
    		vo.setDownloadTimes(countService.countTotalDownload(vo.getTitle()));	
    	}
		model.put("realName", realName);
		model.put("data", JSONArray.toJSONString(fileStoreInfoVos));
		return "author";
	}
	
	@RequestMapping("/{gender}/author/{name}/p{pageNo}")
	@ResponseBody
	public String authorMore(@PathVariable("gender") String gender, @PathVariable("name")String name, @PathVariable("pageNo")Long pageNo, ModelMap model) throws UnsupportedEncodingException{
		String realName = new String(Encodes.decodeBase64(name), "utf-8");
		List<FrontThemeFileVo> fileStoreInfoVos = fileStoreInfoService.getAuthorPage(realName, pageNo);
		for (FrontThemeFileVo vo : fileStoreInfoVos) {
    		vo.setDownloadTimes(countService.countTotalDownload(vo.getTitle()));	
    	}
		return fileStoreInfoService.getSimplifyScrollJson(fileStoreInfoVos);
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		System.out.println(URLEncoder.encode(Encodes.encodeBase64("Chou".getBytes()), "utf-8"));
	}

	@Autowired
	public void setFileStoreInfoService(FileStoreInfoService fileStoreInfoService) {
		this.fileStoreInfoService = fileStoreInfoService;
	}
}
