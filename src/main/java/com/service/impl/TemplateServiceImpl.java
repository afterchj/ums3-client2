package com.service.impl;

import java.util.List;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.dao.TemplateDao;
import com.mapper.BeanMapper;
import com.model.Template;
import com.service.TemplateService;
import com.web.vo.TemplateVo;

@Service("templateService")
public class TemplateServiceImpl implements TemplateService{
	
	private TemplateDao templateDao;

	@Autowired
	public void setTemplateDao(TemplateDao templateDao) {
		this.templateDao = templateDao;
	}

	@Override
	@Cacheable(value="templateService",key="'getAllTemplates'")
	public List<TemplateVo> getAllTemplates() {
		List<Template> templates = templateDao.getAll();
		return convert(templates);
	}

	private List<TemplateVo> convert(List<Template> templates) {
		return BeanMapper.mapList(templates, TemplateVo.class);
	}

	@Override
	@Cacheable(value="templateService",key="'getTemplate' + #id")
	public TemplateVo getTemplate(Long id) {
		Template template = templateDao.get(id);
		Validate.notNull(template, "cannot find the template of %d", id);
		return BeanMapper.map(template, TemplateVo.class);
	}
}
