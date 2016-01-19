package com.service;

import java.util.List;
import java.util.Map;

import com.web.vo.CateItemVo;
import com.web.vo.DetailVo;
import com.web.vo.FileStoreInfoVo;
import com.web.vo.FrontCategoryInfoVo;
import com.web.vo.FrontThemeFileVo;
import com.web.vo.TemplateThemeFileVo;

public interface FileStoreInfoService {

	List<FrontThemeFileVo> getHomePage(String gender);
	
	Map<String,List<FrontThemeFileVo>> getNewHomePageFile();
	
	List<FrontCategoryInfoVo> getNewHomePage(Integer num);

	FileStoreInfoVo getStoreInfo(String id);

	List<FileStoreInfoVo> getStoreInfosInShelf(CateItemVo category);

	List<FileStoreInfoVo> getStoreInfosInShelf(String type);

	List<FileStoreInfoVo> shuffInfos(List<FileStoreInfoVo> subInfo);

	List<FrontThemeFileVo> getShelfByPage(String type, Long page);

	List<FrontThemeFileVo> getHottestPage(String gender, Long pageNo);
	
	List<FrontThemeFileVo> getNewestByCategory(String categoryId, Long pageNo);

	List<FrontThemeFileVo> getFileStoreInfoByTopic(Long topicId, Long pageNo);

	List<TemplateThemeFileVo> getFileStoreInfoByTemplate(Long templateId, Long pageNo);
	
	List<FileStoreInfoVo> getFileStoreInfoByTypeWithPage(String type, Long pageNo);

	List<FrontThemeFileVo> getHottestByCategory(String categoryId, Long pageNo);

	List<FileStoreInfoVo> getFileStoreByCategory(String categoryID);

	List<FileStoreInfoVo> getShuffleFileStoreByCategory(String categoryID,
			FileStoreInfoVo current, int num);
	
	List<DetailVo> getShuffleFileStoreByCategory(String categoryID, DetailVo current,
			int num);

	List<FrontThemeFileVo> getAuthorPage(String realName, Long pageNo);

	DetailVo getDetailStoreInfo(String id);

//	String getScrollJson(List<FrontFileStoreInfoVo> infos);

	String getSimplifyScrollJson(List<FrontThemeFileVo> infos);

	String getTemplateScrollJson(List<TemplateThemeFileVo> infos);

}
