package com.web.controller;

import com.alibaba.fastjson.JSONArray;
import com.service.CountService;
import com.service.FileStoreInfoService;
import com.service.FileStoreInfoWithAdService;
import com.web.vo.FrontThemeFileVo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller("hottestController")
@RequestMapping("/app")
public class HottestController {

	private FileStoreInfoService fileStoreInfoService;
	private FileStoreInfoWithAdService fileStoreInfoWithAdService;
	private Logger logger = Logger.getLogger(HottestController.class);

	@Autowired
	private CountService countService;

	@ExceptionHandler(RuntimeException.class)
	public String handleException(RuntimeException re,
			HttpServletRequest request) {
//		if(!(re instanceof TypeMismatchException)){
//			logger.error("hottest.", re);
//		}
		if (request.getContextPath().indexOf("\\/m\\/") > 0) {
			return "forword:/app/m/home";
		} else {
			return "forward:/app/f/home";
		}
	}

	@RequestMapping("/{gender}/hottest")
	public String home(@PathVariable("gender") String gender, ModelMap model){
        List<FrontThemeFileVo> fileStoreInfoVos = fileStoreInfoService.getHottestPage(gender, 1l);
        model.put("data", JSONArray.toJSONString(fileStoreInfoVos));
		return "hottest";
	}

	@RequestMapping("/{gender}/hottest/o{offset}")
	public String homeWithAd(@PathVariable("gender") String gender, @PathVariable("offset")Long offset, ModelMap model){
        List<FrontThemeFileVo> fileStoreInfoVos = fileStoreInfoWithAdService.getHottestPage(gender, 1l, offset);
        for (FrontThemeFileVo vo : fileStoreInfoVos) {
    		vo.setDownloadTimes(countService.countTotalDownload(vo.getTitle()));
    	}
        model.put("data", JSONArray.toJSONString(fileStoreInfoVos));
		return "hottest";
	}

	@RequestMapping("/{gender}/hottest/p{page}")
	@ResponseBody
	public String more(@PathVariable("gender") String gender, ModelMap model, @PathVariable("page") Long page){
        List<FrontThemeFileVo> fileStoreInfoVos = fileStoreInfoService.getHottestPage(gender, page);
        for (FrontThemeFileVo vo : fileStoreInfoVos) {
    		vo.setDownloadTimes(countService.countTotalDownload(vo.getTitle()));
    	}
        return fileStoreInfoService.getSimplifyScrollJson(fileStoreInfoVos);
	}

	//获取最热主题
	@RequestMapping("/{gender}/hottest/p{page}/o{offset}")
	@ResponseBody
	public String moreWithAd(@PathVariable("gender") String gender, @PathVariable("offset")Long offset, @PathVariable("page") Long page, ModelMap model){
		List<FrontThemeFileVo> fileStoreInfoVos = fileStoreInfoWithAdService.getHottestPage(gender, page, offset);
		for (FrontThemeFileVo vo : fileStoreInfoVos) {
    		vo.setDownloadTimes(countService.countTotalDownload(vo.getTitle()));
    	}
        return fileStoreInfoService.getSimplifyScrollJson(fileStoreInfoVos);
	}

	@RequestMapping("/newest/p{page}/o{offset}")
	@ResponseBody
	public String moreWithAd2(@PathVariable("offset")Long offset, @PathVariable("page") Long page, ModelMap model){
        List<FrontThemeFileVo> fileStoreInfoVos = fileStoreInfoWithAdService.getNewestPage(page, offset);
        for (FrontThemeFileVo vo : fileStoreInfoVos) {
    		vo.setDownloadTimes(countService.countTotalDownload(vo.getTitle()));
    	}
        return fileStoreInfoService.getSimplifyScrollJson(fileStoreInfoVos);
	}

	@Autowired
	public void setFileStoreInfoService(FileStoreInfoService fileStoreInfoService) {
		this.fileStoreInfoService = fileStoreInfoService;
	}

	@Autowired
	public void setFileStoreInfoWithAdService(
			FileStoreInfoWithAdService fileStoreInfoWithAdService) {
		this.fileStoreInfoWithAdService = fileStoreInfoWithAdService;
	}

}
