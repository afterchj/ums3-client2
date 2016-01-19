package com.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.model.dd.DataDict;
import com.service.FileStoreInfoService;
import com.web.vo.FrontThemeFileVo;

public class ShelfController {

	private Logger logger = Logger.getLogger(ShelfController.class);
	private FileStoreInfoService fileStoreInfoService;

	@ExceptionHandler(RuntimeException.class)
	public String handleException(RuntimeException re,
			HttpServletRequest request) {
		if(!(re instanceof TypeMismatchException)){
			logger.error("detail." + re.getMessage());
		}
		if (request.getContextPath().indexOf("\\/m\\/") > 0) {
			return "forword:/app/m/home";
		} else {
			return "forward:/app/f/home";
		}
	}

	@RequestMapping("/{gender}/shelf")
	public String home(@PathVariable("gender") String gender, ModelMap model) {
		List<FrontThemeFileVo> fileStoreInfoVos = fileStoreInfoService
				.getShelfByPage(gender, 1l);
		model.put("data", fileStoreInfoVos);
		String title = "男生专区";
		if ("female".equals(DataDict.getGender(gender))) {
			title = "女生专区";
		}
		model.put("title", title);
		return "shelf";
	}

	@RequestMapping("/{gender}/shelf/p{page}")
	@ResponseBody
	public String more(@PathVariable("page") Long page,
			@PathVariable("gender") String gender, ModelMap model)
			throws Exception {
		List<FrontThemeFileVo> fileStoreInfoVos = fileStoreInfoService
				.getShelfByPage(gender, page);
		// model.put("jsonObject", fileStoreInfoVos);
		return fileStoreInfoService.getSimplifyScrollJson(fileStoreInfoVos);
	}

	@Autowired
	public void setFileStoreInfoService(
			FileStoreInfoService fileStoreInfoService) {
		this.fileStoreInfoService = fileStoreInfoService;
	}

}
