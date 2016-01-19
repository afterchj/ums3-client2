package com.service;

import java.util.List;

import com.web.vo.CategoryVo;

public interface CategoryService {

	List<CategoryVo> getAllCategories();

	CategoryVo get(String categoryId);

}
