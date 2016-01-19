package com.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.dao.FileTagDao;
import com.model.FileTag;
import com.service.FileTagService;
import com.utils.ConvertUtils;

@Service("fileTagService")
public class FileTagServiceImpl implements FileTagService{
	
	private FileTagDao fileTagDao;

	@Override
	@Cacheable(value="fileTagService",key="'getByThemeId' + #id")
	public String getByThemeId(String id) {
		List<FileTag> fileTags = fileTagDao.getByTheme(id);
		return ConvertUtils.convertElementPropertyToString(fileTags, "name", ",");
	}

	@Autowired
	public void setFileTagDao(FileTagDao fileTagDao) {
		this.fileTagDao = fileTagDao;
	}

}
