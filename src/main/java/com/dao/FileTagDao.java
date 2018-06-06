package com.dao;

import java.util.List;

import com.model.FileTag;

public interface FileTagDao {
	List<FileTag> getByTheme(String themeId);
}
