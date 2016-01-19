package com.service.impl;

import java.util.List;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.dao.CategoryDao;
import com.mapper.BeanMapper;
import com.model.Category;
import com.service.CategoryService;
import com.web.vo.CategoryVo;

@Service("categoryService")
public class CategoryServiceImpl implements CategoryService{
	
	private CategoryDao categoryDao;

	@Override
	public List<CategoryVo> getAllCategories() {
		List<Category> categories = categoryDao.getAllByCategory();
		return BeanMapper.mapList(categories, CategoryVo.class);
	}
	
	@Autowired
	public void setCategoryDao(CategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}

	@Override
	@Cacheable(value="categoryService",key="'get' + #categoryId")
	public CategoryVo get(String categoryId) {
		Validate.notNull(categoryId, "Category ID cannot be null.");
		Category category = categoryDao.get(categoryId);
		return BeanMapper.map(category, CategoryVo.class);
	}

}
