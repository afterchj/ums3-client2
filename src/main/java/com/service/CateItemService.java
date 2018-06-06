package com.service;

import java.util.List;

import com.model.CateItem;
import com.web.vo.CateItemVo;

public interface CateItemService {

	CateItemVo get(String id);

	CateItemVo getByTheme(String id);

	CateItemVo getCategoryInfo(String id);
	
	List<CateItem> getAll(String type);

}
