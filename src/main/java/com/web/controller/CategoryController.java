package com.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.service.AdvertisementService;
import com.service.CategoryService;
import com.service.CountService;
import com.service.FileStoreInfoService;
import com.utils.FrontDisplayUtils;
import com.web.vo.AdvertisementVo;
import com.web.vo.CategoryVo;
import com.web.vo.FrontThemeFileVo;

@Controller("categoryController")
@RequestMapping("/app")
public class CategoryController {

	private CategoryService categoryService;
	private FileStoreInfoService fileStoreInfoService;
	private Logger logger = Logger.getLogger(CategoryController.class);
	private AdvertisementService advertisementService;
	
	@Autowired
	private CountService countService;
	
	@ExceptionHandler(Exception.class)
	public String handleException(RuntimeException re,
			HttpServletRequest request) {
		if(!(re instanceof TypeMismatchException)){
			logger.error("category.",re);
		}
		return null;
	}

	@RequestMapping("/{gender}/category")
	public String home(@PathVariable("gender") String gender, ModelMap model) {
		List<CategoryVo> lists = categoryService.getAllCategories();
		List<CategoryVo> display = Lists.newArrayList();
		for (CategoryVo category : lists) {
			if (StringUtils.contains(category.getDescription(), "hidden")) {
				continue;
			}
			display.add(category);
		}
		model.put("categories", JSONArray.toJSONString(display));
	    List<AdvertisementVo> adVoList = advertisementService.getByType("category");
	    List<AdvertisementVo> adVoNewList = new ArrayList<AdvertisementVo>();
		// 3.6 特殊对应 废弃接口
	    for (AdvertisementVo item : adVoList) {
			if ("ad".equalsIgnoreCase(item.getType())
					|| "lock".equalsIgnoreCase(item.getType())) {
				adVoNewList.add(item);
			}
		}
		if (adVoNewList.size() > 3) {
			adVoNewList = adVoNewList.subList(0, 3);
		}
		String categoryBars = FrontDisplayUtils.json(adVoNewList);
		model.put("tbars", categoryBars);
		return "category-home";
	}
	
	@RequestMapping("/{gender}/category/cid{categoryId}/newest")
	public String newestCategory(@PathVariable("gender") String gender, @PathVariable("categoryId") String categoryId, ModelMap model){
    	CategoryVo categoryVo = categoryService.get(categoryId);
    	if(categoryVo == null){
    		logger.warn(String.format("cannot find the category of %s.", categoryId));
    		return null;
    	}
    	model.put("category", JSONObject.toJSONString(categoryVo));
    	model.put("title", categoryVo.getName());
    	List<FrontThemeFileVo> fileStoreInfoVos = fileStoreInfoService.getNewestByCategory(categoryId, 1l);
    	for (FrontThemeFileVo vo : fileStoreInfoVos) {
    		vo.setDownloadTimes(countService.countTotalDownload(vo.getTitle()));	
    	}
		model.put("data", JSONObject.toJSON(fileStoreInfoVos));
    	return "category-newest-list";
	}
	
	@RequestMapping("/{gender}/category/cid{categoryId}/hottest")
	public String hottestCategory(@PathVariable("gender") String gender, @PathVariable("categoryId") String categoryId, ModelMap model){
    	CategoryVo categoryVo = categoryService.get(categoryId);
    	if(categoryVo == null){
    		logger.warn(String.format("cannot find the category of %s.", categoryId));
    		return null;
    	}
    	model.put("category", JSONObject.toJSONString(categoryVo));
    	model.put("title", categoryVo.getName());
    	List<FrontThemeFileVo> fileStoreInfoVos = fileStoreInfoService.getHottestByCategory(categoryId, 1l);
    	for (FrontThemeFileVo vo : fileStoreInfoVos) {
    		vo.setDownloadTimes(countService.countTotalDownload(vo.getTitle()));	
    	}
    	model.put("data", JSONArray.toJSONString(fileStoreInfoVos));
    	return "category-hottest-list";
	}
	
	@RequestMapping("/{gender}/category/cid{categoryId}/newest/p{pageNo}")
	@ResponseBody
	public String newestMore(@PathVariable("gender") String gender, @PathVariable("categoryId") String categoryId, @PathVariable("pageNo")Long pageNo, ModelMap model) {
    	CategoryVo categoryVo = categoryService.get(categoryId);
    	if(categoryVo == null){
    		logger.warn(String.format("cannot find the category of %s.", categoryId));
    		return null;
    	}
    	model.put("category", JSONObject.toJSONString(categoryVo));
    	List<FrontThemeFileVo> fileStoreInfoVos = fileStoreInfoService.getNewestByCategory(categoryId, pageNo);
    	for (FrontThemeFileVo vo : fileStoreInfoVos) {
    		vo.setDownloadTimes(countService.countTotalDownload(vo.getTitle()));	
    	}
    	return fileStoreInfoService.getSimplifyScrollJson(fileStoreInfoVos);
	}
	
	@RequestMapping("/{gender}/category/cid{categoryId}/hottest/p{pageNo}")
	@ResponseBody
	public String hottestMore(@PathVariable("gender") String gender, @PathVariable("categoryId") String categoryId, @PathVariable("pageNo")Long pageNo, ModelMap model) {
    	CategoryVo categoryVo = categoryService.get(categoryId);
    	if(categoryVo == null){
    		logger.warn(String.format("cannot find the category of %s.", categoryId));
    		return null;
    	}
    	model.put("category", categoryVo);
    	List<FrontThemeFileVo> fileStoreInfoVos = fileStoreInfoService.getHottestByCategory(categoryId, pageNo);
    	for (FrontThemeFileVo vo : fileStoreInfoVos) {
    		vo.setDownloadTimes(countService.countTotalDownload(vo.getTitle()));	
    	}
    	return fileStoreInfoService.getSimplifyScrollJson(fileStoreInfoVos);
	}

	@Autowired
	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
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
