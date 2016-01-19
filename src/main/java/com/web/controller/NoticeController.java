package com.web.controller;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
import com.mapper.BeanMapper;
import com.model.DNotice;
import com.model.dd.ResultDict;
import com.service.NoticeService;
import com.tpadsz.exception.NotExecutedDbException;
import com.tpadsz.exception.NotFoundException;
import com.web.vo.Notice;

@Controller("noticeController")
@RequestMapping("/notice")
public class NoticeController extends BaseDecodedController{
	
	private NoticeService noticeService;
	
	public static final int PAGE_SIZE = 20;
		
	@RequestMapping(value="/list/p_{pageNo}", method=RequestMethod.POST)
	public String list(@ModelAttribute("decodedParams")JSONObject params, @PathVariable("pageNo") Integer pageNo, ModelMap model)  {
		String uid = params.getString("uid");
		try {
			List<DNotice> notices = noticeService.getNoticesByUser(uid, pageNo, PAGE_SIZE);
			if(CollectionUtils.isEmpty(notices) || notices.size() < NoticeController.PAGE_SIZE){
				model.put("pStatus", "0");
			}else {
				model.put("pStatus", "1");
			}
			model.put("result", ResultDict.SUCCESS.getCode());
			model.put("notices", convert(notices));
		} catch (NotExecutedDbException e) {
			system.error(e);
			model.put("result", ResultDict.SYSTEM_ERROR.getCode());
		}
		return null;
	}
	
	@RequestMapping(value="/detail", method=RequestMethod.POST)
	public String noticeDetail(@ModelAttribute("decodedParams")JSONObject params, ModelMap model){
		try {
			String nid = params.getString("id");
			String uid = params.getString("uid");
			DNotice notice = noticeService.getNoticeById(nid, uid);
			model.put("notice", convert(notice));
			model.put("result", ResultDict.SUCCESS.getCode());
		} catch (NotExecutedDbException e) {
			model.put("result", ResultDict.SYSTEM_ERROR.getCode());
			system.error(e);
		} catch (NotFoundException e) {
			model.put("result", ResultDict.ID_NOT_FOUND.getCode());
		}
		return null;
	}

	private Notice convert(DNotice notice) {
		return BeanMapper.map(notice, Notice.class);
	}

	private List<Notice> convert(List<DNotice> notice) {
		return BeanMapper.mapList(notice, Notice.class);
	}

	@Autowired
	public void setNoticeService(NoticeService noticeService) {
		this.noticeService = noticeService;
	}
	

}
