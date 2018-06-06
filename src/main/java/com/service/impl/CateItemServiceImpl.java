package com.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.dao.CateItemDao;
import com.mapper.BeanMapper;
import com.model.CateItem;
import com.service.CateItemService;
import com.utils.CollectionUtil;
import com.utils.Constants;
import com.web.vo.CateItemVo;

@Service("cateItemService")
public class CateItemServiceImpl implements CateItemService{
	
	private CateItemDao cateItemDao;
	
	@Override
	@Cacheable(value="cateItemService",key="'getAll' + #type")
	public List<CateItem> getAll(String type) {
		List<CateItem> categories = cateItemDao.getAll(type);
		return categories;
	}

	@Override
	public CateItemVo get(String id) {
		CateItem cateItem = cateItemDao.get(id);
		CateItemVo cateItemVo = BeanMapper.map(cateItem, CateItemVo.class);
		return cateItemVo;
	}

	@Autowired
	public void setCateItemDao(CateItemDao cateItemDao) {
		this.cateItemDao = cateItemDao;
	}

	@Override
	@Cacheable(value="cateItemService",key="'getByTheme' + #id")
	public CateItemVo getByTheme(String id) {
		List<CateItem> cateItems = cateItemDao.getCategoryByTheme(id);
		if(CollectionUtil.isBlank(cateItems)){
			return null;
		}
		CateItemVo cateItemVo = BeanMapper.map(cateItems.get(0), CateItemVo.class);
		return cateItemVo;
	}

	@Override
	@Cacheable(value="cateItemService",key="'getCategoryInfo' + #id")
	public CateItemVo getCategoryInfo(String id) {
		CateItem cateItem = cateItemDao.getCategoryInfoByCategoryAndLang(id, Constants.Language.ZH);
		return BeanMapper.map(cateItem, CateItemVo.class);
	}

}
