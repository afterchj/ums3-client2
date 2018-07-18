package com.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("searchDao")
public interface SearchDao {

	void save(@Param("searchName") String searchName);

	List<String> findByUser();

	List<String> findByOfficial();

	void delete();

	void refreshHottestSearchNamesByUser();

	void refreshHottestSearchNamesByOfficial();

	void deleteExpiresLog();
}
