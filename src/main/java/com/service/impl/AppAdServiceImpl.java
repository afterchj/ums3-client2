package com.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.AppAdDao;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.model.AppAd;
import com.service.AppAdService;
import com.web.vo.FrontThemeFileVo;

@Service("appAdService")
public class AppAdServiceImpl implements AppAdService{

	private AppAdDao appAdDao;

	@Autowired
	public void setAppAdDao(AppAdDao appAdDao) {
		this.appAdDao = appAdDao;
	}

	@Override
	@Deprecated
	public List<FrontThemeFileVo> mixThemeFilesWithAppAds(List<FrontThemeFileVo> themeFiles,
			Long startIndex, Long size, String dtype) {
		if(CollectionUtils.isEmpty(themeFiles) || startIndex < 0 || size <= 0){
			return themeFiles;
		}
		List<AppAd> appAds = appAdDao.getByDtype(dtype, startIndex, startIndex + size);
		if(CollectionUtils.isEmpty(appAds)){
			return themeFiles;
		}
		for(AppAd appAd : appAds){
			FrontThemeFileVo fileVo = FrontThemeFileVo.convert(appAd.getFile());
			long index = appAd.getOffset() - startIndex;
			if(themeFiles.size() >= index){
				themeFiles.add((int)(index - 1), fileVo);
			}else {
				themeFiles.add(fileVo);
			}
		}
		return Lists.newArrayList(themeFiles.subList(0, themeFiles.size() < size.intValue() ? themeFiles.size() : size.intValue()).iterator());
	}

	@Override
	public List<FrontThemeFileVo> mixThemeFilesWithAppAds(
			List<FrontThemeFileVo> themeFiles, Long startIndex, Long size,
			String dtype, Long offset) {
		if(CollectionUtils.isEmpty(themeFiles) || startIndex < 0 || size <= 0 || offset == null || offset < 0){
			return themeFiles;
		}
		List<AppAd> appAds = appAdDao.getByDtype(dtype, startIndex, startIndex + size);
		if(CollectionUtils.isEmpty(appAds)){
			return themeFiles;
		}
		Map<Integer, List<AppAd>> appAdMap = convert(appAds);
		for(Integer os : appAdMap.keySet()){
			List<AppAd> data = appAdMap.get(os);
			long index = os - startIndex;
			int offsetIndex = (int) ((offset % 10) % data.size());
			FrontThemeFileVo fileVo = FrontThemeFileVo.convert(data.get(offsetIndex).getFile());
			if(themeFiles.size() >= index){
				themeFiles.add((int)(index - 1), fileVo);
			}else {
				themeFiles.add(fileVo);
			}
			
		}
		return Lists.newArrayList(themeFiles.subList(0, themeFiles.size() < size.intValue() ? themeFiles.size() : size.intValue()).iterator());
	}

	private Map<Integer, List<AppAd>> convert(List<AppAd> appAds) {
		Map<Integer, List<AppAd>> appAdMap = Maps.newHashMap();
		if(CollectionUtils.isNotEmpty(appAds)){
			for(AppAd appAd : appAds){
				List<AppAd> appAdList = appAdMap.get(appAd.getOffset());
				if(appAdList == null){
					appAdList = Lists.newArrayList();
					appAdMap.put(appAd.getOffset(), appAdList);
				}
				appAdList.add(appAd);
			}
		}
		return appAdMap;
	}
}
