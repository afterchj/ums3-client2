package com.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.model.dd.ResultDict;
import com.service.SearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller("searchController")
@RequestMapping("/search")
public class SearchController extends BaseDecodedController{
	
	private Logger logger = LoggerFactory.getLogger(SearchController.class);
	
	private SearchService searchService;

	@RequestMapping(value="/save", method=RequestMethod.POST)
	public String search(@ModelAttribute("decodedParams")JSONObject params, ModelMap model){
		try {
			String searchName = params.getString("searchName");
			searchService.save(searchName);
			model.put("result", ResultDict.SUCCESS.getCode());
		} catch (Exception e) {
			model.put("result", ResultDict.SYSTEM_ERROR.getCode());
		}
		return null;
	}

	@RequestMapping(value="/hottest", method = RequestMethod.GET)
	public String hottest(ModelMap model){
		try {
			List<String> hottestNames = searchService.findByUser();
			List<String> officialNames = searchService.findByOfficial();
			officialNames.addAll(hottestNames);
			model.put("data", officialNames);
			model.put("result", ResultDict.SUCCESS.getCode());
		} catch (Exception e) {
			model.put("result", ResultDict.SYSTEM_ERROR.getCode());
		}
		return null;
	}


	@Autowired
	public void setSearchService(SearchService searchService) {
		this.searchService = searchService;
	}

}
