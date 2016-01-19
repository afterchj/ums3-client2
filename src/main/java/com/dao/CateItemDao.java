package com.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.model.CateItem;
import com.utils.Constants.Language;

public interface CateItemDao {

	CateItem get(String id);

	List<CateItem> getCategoryByTheme(@Param("themeId")String themeId);
	
	CateItem getCategoryInfoByCategoryAndLang(@Param("categoryId")String categoryId, @Param("lang")Language lang);

	List<CateItem> getAll(String type);

}
