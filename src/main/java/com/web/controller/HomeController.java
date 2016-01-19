package com.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.service.AdvertisementService;
import com.service.FileStoreInfoService;
import com.utils.FrontDisplayUtils;
import com.web.vo.FrontCategoryInfoVo;
import com.web.vo.FrontThemeFileVo;

@Controller("homeController")
@RequestMapping("/app")
public class HomeController {
	
	private FileStoreInfoService fileStoreInfoService;
	private AdvertisementService advertisementService;
	private Logger logger = Logger.getLogger(HomeController.class);
	
	
	@ExceptionHandler(RuntimeException.class)
	public String handleException(RuntimeException re,
			HttpServletRequest request) {
		logger.error("home.", re);
		return null;
	}
	
	@RequestMapping("/home")
	public String oldHome(String g, ModelMap model){
		List<FrontThemeFileVo> fileStoreInfoVos = fileStoreInfoService.getHomePage(g);
        String top = FrontDisplayUtils.json(advertisementService.getByType("store"));
        String bottom = FrontDisplayUtils.json(advertisementService.getByType("sbot"));
        model.put("tbars", top);
        model.put("bbars", bottom);
        model.put("data", JSONArray.toJSONString(fileStoreInfoVos));
		return "home";
	}
	
	
	@RequestMapping("/{gender}/home")
	public String home(@PathVariable("gender") String gender, ModelMap model){
		List<FrontThemeFileVo> fileStoreInfoVos = fileStoreInfoService.getHomePage(gender);
		String top = FrontDisplayUtils.json(advertisementService.getByType("store"));
        String bottom = FrontDisplayUtils.json(advertisementService.getByType("sbot"));
        model.put("tbars", top);
        model.put("bbars", bottom);
        model.put("data", JSONArray.toJSONString(fileStoreInfoVos));
		return "home";
	}
	
	@RequestMapping("/home_{num}")
	public String newHome(@PathVariable("num") Integer num, ModelMap model){
		List<FrontCategoryInfoVo> fileStoreInfoVos = fileStoreInfoService.getNewHomePage(num);
		String topicBars = FrontDisplayUtils.json(advertisementService.getByType("category"));
		model.put("tbars", topicBars);
        model.put("data", JSONArray.toJSONString(fileStoreInfoVos));
		return "home";
	}
	
	@Autowired
	public void setFileStoreInfoService(FileStoreInfoService fileStoreInfoService) {
		this.fileStoreInfoService = fileStoreInfoService;
	}

	@Autowired
	public void setAdvertisementService(AdvertisementService advertisementService) {
		this.advertisementService = advertisementService;
	}
	
}
