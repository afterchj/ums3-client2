package com.dao;

import java.util.List;

import com.model.Template;

public interface TemplateDao {

	List<Template> getAll();

	Template get(Long id);

}
