package com.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.model.FileStoreInfo;


public interface FileStoreInfoDao {
	
	List<FileStoreInfo> getHomeByGender(String gender);

	FileStoreInfo get(String id);

	List<FileStoreInfo> getInShelf(String shelf);

	List<FileStoreInfo> getByCategory(String categoryId);

	List<FileStoreInfo> getByGenderWithPage(@Param("gender")String gender, @Param("startIndex")Long startIndex, @Param("pageSize")Long pageSize);

	List<FileStoreInfo> getByCategoryWithNewestPage(@Param("categoryId")String categoryId, @Param("startIndex")Long startIndex, @Param("pageSize")Long pageSize);

	List<FileStoreInfo> getByTopicWithPage(@Param("topicId")Long topicId, @Param("startIndex")Long startIndex, @Param("pageSize")Long pageSize);

	List<FileStoreInfo> getByHottestWithPage(@Param("sdate")String sdate, @Param("edate")String edate, @Param("startIndex")Long startIndex, @Param("pageSize")Long pageSize);

	List<FileStoreInfo> getByCategoryWithHottestPage(@Param("categoryId")String categoryId, @Param("sdate")String sdate, @Param("edate")String edate, @Param("startIndex")Long startIndex, @Param("pageSize")Long pageSize);

	List<FileStoreInfo> getByTemplateWithPage(@Param("templateId")Long templateId, @Param("startIndex")Long startIndex, @Param("pageSize")Long pageSize);

	List<FileStoreInfo> getByAuthorWithPage(@Param("realName") String realName, @Param("startIndex")Long startIndex, @Param("pageSize")Long pageSize);
	
	List<FileStoreInfo> getNewestInShelf(@Param("startIndex")Long startIndex, @Param("pageSize")Long pageSize);
	
}
