package com.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.service.AdvertisementService;
import com.web.vo.AdVo;
import com.web.vo.AdVoList;

@Controller("adController")
@RequestMapping("/app")
public class AdController {
	
	private AdvertisementService advertisementService;
	
	@RequestMapping("/ad")
	public String home(ModelMap model){
		List<AdVo> adVos = advertisementService.getClientAds();
		AdVoList adVoList = new AdVoList(adVos);
//		System.out.println(XmlUtil.convertToXML(System.out, adVoList));
		model.put("ads", adVoList);
		return "client-ad";
	}

	@Autowired
	public void setAdvertisementService(AdvertisementService advertisementService) {
		this.advertisementService = advertisementService;
	}
}
