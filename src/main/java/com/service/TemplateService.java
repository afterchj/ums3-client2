package com.service;

import java.util.List;

import com.web.vo.TemplateVo;

public interface TemplateService {

	List<TemplateVo> getAllTemplates();

	TemplateVo getTemplate(Long id);

}
