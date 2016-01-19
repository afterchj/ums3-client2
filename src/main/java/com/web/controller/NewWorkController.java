	package com.web.controller;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.model.dd.ResultDict;
import com.service.UserService;
import com.service.WorkService;
import com.service.impl.WorkServiceImpl;
import com.tpadsz.exception.DisabledException;
import com.tpadsz.exception.ParamBlankException;
import com.tpadsz.exception.SystemAlgorithmException;
import com.utils.Constants;
import com.web.vo.WorkNewUploadVo;
import com.web.vo.WorkVo;

@Controller("newWorkController")
@RequestMapping("/zone/work")
public class NewWorkController extends BaseDecodedController{

	private WorkService workService;
	private UserService userService;
	
	@RequestMapping(value="/square", method=RequestMethod.POST)
	public String home(ModelMap model, @ModelAttribute("decodedParams")JSONObject params){
		List<WorkVo> workVos = workService.getAllByUserWithPage(1l);
		workService.completeWorkVo(workVos);
		model.put("works", workVos);
		model.put("result", ResultDict.SUCCESS.getCode());
		return null;
	}
	
	@RequestMapping(value="/square/p_{pageNo}", method=RequestMethod.POST)
	public String homeMore(@PathVariable("pageNo")Long pageNo, ModelMap model, @ModelAttribute("decodedParams")JSONObject params){
		if(pageNo == null){
			pageNo = 1l;
		}
		List<WorkVo> workVos = workService.getAllByUserWithPage(pageNo);
		workService.completeWorkVo(workVos);
		model.put("works", workVos);
		if(CollectionUtils.isEmpty(workVos) || workVos.size() < WorkServiceImpl.pageSize){
			model.put("pStatus", Constants.NO_MORE);
		}else {
			model.put("pStatus", Constants.HAS_MORE);
		}
		model.put("result", ResultDict.SUCCESS.getCode());
		return null;
	}
	
	@RequestMapping(value="/person/p_{pageNo}", method=RequestMethod.POST)
	public String userHomeMore(@ModelAttribute("decodedParams")JSONObject params, @PathVariable("pageNo") Long pageNo, ModelMap model){
		try {
			String person = params.getString("person");
			if(StringUtils.isBlank(person)){
				model.put("result", ResultDict.PARAMS_BLANK);
				return null;
			}
			if(pageNo == null){
				pageNo = 1l;
			}
			List<WorkVo> workVos = workService.getByUserWithPage(person, pageNo);
			workService.completeWorkVo(workVos);
			model.put("works", workVos);
			Long visitors = userService.searchUserVisitors(person);
			if(visitors != null){
				model.put("visitors", visitors);
			}
			if(CollectionUtils.isEmpty(workVos) || workVos.size() < WorkServiceImpl.pageSize){
				model.put("pStatus", Constants.NO_MORE);
			}else {
				model.put("pStatus", Constants.HAS_MORE);
			}
			model.put("result", ResultDict.SUCCESS.getCode());
		} catch (Exception e) {
			model.put("result", ResultDict.SYSTEM_ERROR.getCode());
			system.error("userHomeMore", e);
		}
		return null;
	}
	
	@RequestMapping(value="/release", method=RequestMethod.POST)
	public String release(@ModelAttribute("decodedParams")JSONObject params, @RequestParam(value="file",required=false) MultipartFile file, ModelMap model){
		if(file.isEmpty()){
			model.put("result", ResultDict.FILE_NOT_TRANSMITTED.getCode());
			return null;
		}
		WorkNewUploadVo uploadVo = workService.generateNewUpload(params);
		try {
			String workId = workService.save(uploadVo, file);
			model.put("result", ResultDict.SUCCESS.getCode());
			model.put("work", workId);
		} catch (DisabledException e) {
			model.put("result", ResultDict.AUTHORITY_NOT_ALLOWED.getCode());
		} catch (SystemAlgorithmException e) {
			system.error("bean:workService,method:save", e);
			model.put("result", ResultDict.SYSTEM_ERROR.getCode());
		} catch (ParamBlankException e) {
			model.put("result", ResultDict.PARAMS_BLANK.getCode());
		}
		return null;
	}
	
	@Autowired
	public void setWorkService(WorkService workService) {
		this.workService = workService;
	}

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
}
