package com.service;

import com.web.vo.SPAdvertisementVo;

import java.util.List;

public interface SPAdvertisementService {

	List<SPAdvertisementVo> getSpByType(String type);

}
