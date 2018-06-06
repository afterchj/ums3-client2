package com.service;

import java.util.List;

import com.web.vo.FrontThemeFileVo;

public interface FileStoreInfoWithAdService {

	List<FrontThemeFileVo> getHottestPage(String gender, Long page, Long offset);
	
	List<FrontThemeFileVo> getNewestPage(Long page, Long offset);

}
