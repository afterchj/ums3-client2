package com.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.dao.AdvertisementDao;
import com.google.common.collect.Lists;
import com.mapper.BeanMapper;
import com.model.Advertisement;
import com.service.AdvertisementService;
import com.utils.CollectionUtil;
import com.web.vo.AdVo;
import com.web.vo.AdvertisementVo;

@Service("advertisementService")
public class AdvertisementServiceImpl implements AdvertisementService{
	private AdvertisementDao advertisementDao;

	@Autowired
	public void setAdvertisementDao(AdvertisementDao advertisementDao) {
		this.advertisementDao = advertisementDao;
	}

	@Override
	@Cacheable(value="advertisementService",key="'getByType' + #type")
	public List<AdvertisementVo> getByType(String type) {
		List<Advertisement> advertisements = advertisementDao.getByType(type);
		return BeanMapper.mapList(advertisements, AdvertisementVo.class);
	}

	@Override
	@Cacheable(value="advertisementService",key="'getClientAds'")
	public List<AdVo> getClientAds() {
		List<Advertisement> advertisements = advertisementDao.getClientAds();
		if(CollectionUtil.isBlank(advertisements)){
			return Lists.newArrayList();
		}
		List<AdVo> adVos = BeanMapper.mapList(advertisements, AdVo.class);
		for (AdVo dto : adVos) {
            dto.setVersion("1");
            dto.setDownloadUrl(com.utils.Constants.STATIC_SHOW+"/files/"+dto.getDownloadUrl());
        }
		return adVos;
	}
}
