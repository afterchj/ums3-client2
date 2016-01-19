	package com.web.controller;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.service.MemberService;
import com.service.WorkService;
import com.utils.LoggerUtils;
import com.utils.StringUtil;
import com.web.vo.MemberVo;
import com.web.vo.WorkUploadVo;
import com.web.vo.WorkVo;

@Controller("workController")
@RequestMapping("/app")
public class WorkController {

	private WorkService workService;
	private MemberService memberService;
	private Logger system = LoggerUtils.SYSTEM;

	@Autowired
	public void setWorkService(WorkService workService) {
		this.workService = workService;
	}
	
	@ExceptionHandler(Exception.class)
	public String handleException(RuntimeException re, ModelMap model) {
		system.error("WorkController handleException.",re);
		model.put("result", "500");
		return null;
	}
	
	@RequestMapping("/work")
	public String home(ModelMap model){
		List<WorkVo> workVos = workService.getAllByMemberWithPage(1l);
		workService.completeWorkVo(workVos);
		model.put("works", workVos);
		return "work-home";
	}
	
	@RequestMapping("/work/p{pageNo}")
	public String homeMore(@PathVariable("pageNo")Long pageNo, ModelMap model){
		List<WorkVo> workVos = workService.getAllByMemberWithPage(pageNo);
		workService.completeWorkVo(workVos);
		model.put("works", workVos);
		if(CollectionUtils.isEmpty(workVos)){
			model.put("code", "900");
		}else {
			model.put("code", "200");
		}
		return "work-home-more";
	}
	
	@RequestMapping("/work/mid{id}")
	public String memberHome(ModelMap model, @PathVariable("id") String memberId){
		try {
			if(StringUtils.isBlank(memberId) || StringUtils.equalsIgnoreCase(memberId, "null")){
				return "work-home";
			}
			MemberVo member = memberService.getById(memberId);
			if(member != null){
				List<WorkVo> workVos = workService.getByMemberWithPage(memberId, 1l);
				workService.completeWorkVo(workVos);
				Long visitors = memberService.searchMemberVisitors(memberId);
				model.put("memberVisitors", visitors);
				model.put("works", workVos);
				model.put("tip", member.getDescr());
			}
		} catch (Exception e) {
			system.error("memberHome", e);
		}
		return "work-home";
	}
	
	@RequestMapping("/work/mid{id}/p{pageNo}")
	public String memberHomeMore(@PathVariable("pageNo")Long pageNo, @PathVariable("id") String memberId, ModelMap model){
		try {
			if(StringUtils.isBlank(memberId) || StringUtils.equalsIgnoreCase(memberId, "null")){
				return "work-home-more";
			}
			MemberVo member = memberService.getById(memberId);
			List<WorkVo> workVos = workService.getByMemberWithPage(memberId, pageNo);
			workService.completeWorkVo(workVos);
			Long visitors = memberService.searchMemberVisitors(memberId);
			model.put("memberVisitors", visitors);
			model.put("works", workVos);
			if(CollectionUtils.isEmpty(workVos)){
				model.put("code", "900");
			}else {
				model.put("code", "200");
			}
			model.put("tip", member.getDescr());
		} catch (Exception e) {
			system.error("memberHomeMore", e);
		}
		return "work-home-more";
	}
	
	@RequestMapping("/work/t{type}/mid{id}")
	public String memberHome(ModelMap model, @PathVariable("id") String loginName, @PathVariable("type") String type){
		try {
			List<WorkVo> workVos = workService.getByMemberWithPage(loginName, type, 1l);
			Long visitors = workService.searchWorkVisitors(loginName);
			model.put("memberVisitors", visitors);
			model.put("works", workVos);
		} catch (Exception e) {
			system.error("memberHome", e);
		}
		return "work-home";
	}
	
	@RequestMapping("/work/t{type}/mid{id}/p{pageNo}")
	public String memberHomeMore(@PathVariable("pageNo")Long pageNo, @PathVariable("id") String loginName, @PathVariable("type") String type, ModelMap model){
		try {
			List<WorkVo> workVos = workService.getByMemberWithPage(loginName, type, pageNo);
			Long visitors = workService.searchWorkVisitors(loginName);
			model.put("memberVisitors", visitors);
			model.put("works", workVos);
			if(CollectionUtils.isEmpty(workVos)){
				model.put("code", "900");
			}else {
				model.put("code", "200");
			}
		} catch (Exception e) {
			system.error("memberHomeMore", e);
		}
		return "work-home-more";
	}
	
	@RequestMapping("/work/release")
	public String release(@RequestParam(value="params", required=false) String params, @RequestParam("file") MultipartFile file, ModelMap model){
		if(StringUtils.isBlank(params)){
			model.put("result", "001");
			return null;
		}
		if(file.isEmpty()){
			model.put("result", "002");
			return null;
		}
		WorkUploadVo uploadVo = workService.generateUpload(params);
		if(uploadVo == null){
			model.put("result", "003");
			return null;
		}
		uploadVo.setId(StringUtil.getUUID());
		String result = workService.save(uploadVo, file);
		model.put("result", result);
		model.put("work", uploadVo.getId());
		return null;
	}
	
	@Autowired
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}
	
}
