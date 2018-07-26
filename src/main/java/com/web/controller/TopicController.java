package com.web.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.model.dd.DataDict;
import com.service.AdvertisementService;
import com.service.CountService;
import com.service.FileStoreInfoService;
import com.service.TopicService;
import com.utils.FrontDisplayUtils;
import com.web.vo.FrontThemeFileVo;
import com.web.vo.TopicVo;
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

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller("topicController")
@RequestMapping("/app")
public class TopicController {
	
	private TopicService topicService;
	private FileStoreInfoService fileStoreInfoService;
	private AdvertisementService advertisementService;
	
	@Autowired
	private CountService countService;
	
	private Logger logger = Logger.getLogger(TopicController.class);
	
	@ExceptionHandler(RuntimeException.class)
	public String handleException(RuntimeException re,
			HttpServletRequest request) {
		if(!(re instanceof TypeMismatchException)){
			logger.error("topic." + re.getMessage());
		}
		if (request.getContextPath().indexOf("\\/m\\/") > 0) {
			return "forword:/app/m/home";
		} else {
			return "forward:/app/f/home";
		}
	}

	@RequestMapping("/{gender}/topic")
	public String home(@PathVariable("gender") String gender, ModelMap model) {
		List<TopicVo> topicVos = topicService.getAllTopics();
		model.put("topics", JSONArray.toJSONString(topicVos));
		String topicBars = FrontDisplayUtils.json(advertisementService.getByType("topic"));
		model.put("tbars", topicBars);
		return "topic-home";
	}
	
	@RequestMapping("/{gender}/topic/tid{tid}")
	public String home(@PathVariable("gender") String gender, ModelMap model, @PathVariable("tid")String tid) {
        if(StringUtils.isBlank(tid)){
        	return null;
        }
        tid= DataDict.getIconLevel(tid);//兼容莫名的参数格式:1#
        Long id=Long.valueOf(tid);
        TopicVo topic=topicService.getTopic(id);
        List<FrontThemeFileVo> fileStoreInfoVos = fileStoreInfoService.getFileStoreInfoByTopic(id, 1l);
        for (FrontThemeFileVo vo : fileStoreInfoVos) {
    		vo.setDownloadTimes(countService.countTotalDownload(vo.getTitle()));	
    	}
        model.put("data", JSONArray.toJSONString(fileStoreInfoVos));
        model.put("topic", JSONObject.toJSONString(topic));
        model.put("title", topic.getName());
        return "topic-list";
	}
	
	
	@RequestMapping("/{gender}/topic/tid{tid}/p{page}")
	@ResponseBody
	public String newMore(@PathVariable("gender") String gender, ModelMap model, @PathVariable("tid")String tid, @PathVariable("page") Long page) {
        if(StringUtils.isBlank(tid)){
        	return null;
        }
        tid= DataDict.getIconLevel(tid);//兼容莫名的参数格式:1#
        Long id=Long.valueOf(tid);
        List<FrontThemeFileVo> fileStoreInfoVos = fileStoreInfoService.getFileStoreInfoByTopic(id, page);
        for (FrontThemeFileVo vo : fileStoreInfoVos) {
    		vo.setDownloadTimes(countService.countTotalDownload(vo.getTitle()));	
    	}
        return fileStoreInfoService.getSimplifyScrollJson(fileStoreInfoVos);
	}
	
	@Autowired
	public void setTopicService(TopicService topicService) {
		this.topicService = topicService;
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
