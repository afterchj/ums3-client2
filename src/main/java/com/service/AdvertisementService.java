package com.service;

import java.util.List;

import com.web.vo.AdVo;
import com.web.vo.AdvertisementVo;

public interface AdvertisementService {

	List<AdvertisementVo> getByType(String type);

	List<AdVo> getClientAds();

}
