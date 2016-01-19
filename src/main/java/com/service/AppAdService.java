package com.service;

import java.util.List;

import com.web.vo.FrontThemeFileVo;

public interface AppAdService {

	@Deprecated
	List<FrontThemeFileVo> mixThemeFilesWithAppAds(List<FrontThemeFileVo> themeFiles,
			Long startIndex, Long size, String dtype);

	List<FrontThemeFileVo> mixThemeFilesWithAppAds(
			List<FrontThemeFileVo> themeFiles, Long startIndex, Long size,
			String dtype, Long offset);

}
