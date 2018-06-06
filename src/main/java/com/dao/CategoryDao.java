package com.dao;

import java.util.List;

import com.model.Category;

public interface CategoryDao {

	List<Category> getAllByCategory();
	
	Category get(String categoryId);

}
