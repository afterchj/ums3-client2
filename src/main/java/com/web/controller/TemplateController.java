package com.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.model.dd.DataDict;
import com.service.FileStoreInfoService;
import com.service.TemplateService;
import com.web.vo.TemplateThemeFileVo;
import com.web.vo.TemplateVo;

@Controller("templateController")
@RequestMapping("/app")
public class TemplateController {
	
	private TemplateService templateService;
	private FileStoreInfoService fileStoreInfoService;
	private Logger logger = Logger.getLogger(TemplateController.class);
	
	@ExceptionHandler(RuntimeException.class)
	public String handleException(RuntimeException re,
			HttpServletRequest request) {
		re.printStackTrace();
		logger.error("template." + re.getMessage());
		if (request.getContextPath().indexOf("\\/m\\/") > 0) {
			return "forword:/app/m/home";
		} else {
			return "forward:/app/f/home";
		}
	}

	@RequestMapping("/{gender}/template")
	public String home(@PathVariable("gender") String gender, ModelMap model) {
		List<TemplateVo> templateVos = templateService.getAllTemplates();
		model.put("templates", templateVos);
//		String templateBars = FrontDisplayUtils.json(advertisementService.getByType("template"));
//		model.put("tbars", templateBars);
		return "template-home";
	}
	
	@RequestMapping("/{gender}/template/tid{tid}")
	public String home(@PathVariable("gender") String gender, ModelMap model, @PathVariable("tid")String tid) {
        if(StringUtils.isBlank(tid)){
            tid="";
        }
        tid= DataDict.getIconLevel(tid);//兼容莫名的参数格式:1#
        Long id=Long.valueOf(tid);
        TemplateVo template=templateService.getTemplate(id);
        List<TemplateThemeFileVo> fileStoreInfoVos = fileStoreInfoService.getFileStoreInfoByTemplate(id, 1l);
        model.put("data", fileStoreInfoVos);
        model.put("template", template);
        return "template-list";
	}
	
	
	@RequestMapping("/{gender}/template/tid{tid}/p{page}")
	@ResponseBody
	public String newMore(@PathVariable("gender") String gender, ModelMap model, @PathVariable("tid")String tid, @PathVariable("page") Long page) {
        if(StringUtils.isBlank(tid)){
            tid="";
        }
        tid= DataDict.getIconLevel(tid);//兼容莫名的参数格式:1#
        Long id=Long.valueOf(tid);
        List<TemplateThemeFileVo> fileStoreInfoVos = fileStoreInfoService.getFileStoreInfoByTemplate(id, page);
        return fileStoreInfoService.getTemplateScrollJson(fileStoreInfoVos);
	}
	
	@Autowired
	public void setTemplateService(TemplateService templateService) {
		this.templateService = templateService;
	}

	@Autowired
	public void setFileStoreInfoService(FileStoreInfoService fileStoreInfoService) {
		this.fileStoreInfoService = fileStoreInfoService;
	}

}
