package com.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.service.PollService;
import com.web.vo.PollForm;

@Controller("pollController")
@RequestMapping("/app/poll")
public class PollController {
	
	private PollService pollService;

	@RequestMapping("/lock")
	public String lock(ModelMap model){
		List<PollForm> polls = pollService.getAll();
		model.put("polls", polls);
		return null;
	}

	@Autowired
	public void setPollService(PollService pollService) {
		this.pollService = pollService;
	}
}
