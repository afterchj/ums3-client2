package com.service.impl;

import com.dao.AdvertisementDao;
import com.dao.SPAdvertisementDao;
import com.google.common.collect.Lists;
import com.mapper.BeanMapper;
import com.model.Advertisement;
import com.model.SPAdvertisement;
import com.service.AdvertisementService;
import com.service.SPAdvertisementService;
import com.utils.CollectionUtil;
import com.web.vo.AdVo;
import com.web.vo.AdvertisementVo;
import com.web.vo.SPAdvertisementVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("spadvertisementService")
public class SPAdvertisementServiceImpl implements SPAdvertisementService {
	private SPAdvertisementDao spadvertisementDao;

	@Autowired
	public void setSPAdvertisementDao(SPAdvertisementDao spadvertisementDao) {
		this.spadvertisementDao = spadvertisementDao;
	}

	@Override
	@Cacheable(value="spadvertisementService",key="'getSpByType' + #type")
	public List<SPAdvertisementVo> getSpByType(String type) {
		List<SPAdvertisement> spadvertisements = spadvertisementDao.getSpByType(type);
		return BeanMapper.mapList(spadvertisements, SPAdvertisementVo.class);
	}

}
