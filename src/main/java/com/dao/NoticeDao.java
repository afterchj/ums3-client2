package com.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.model.DNotice;

public interface NoticeDao {

	List<DNotice> getSystemAndUserNotice(@Param("uid")String uid, @Param("startIndex")int startIndex, @Param("pageSize")int pageSize);

	DNotice getById(String nid);

	void save(DNotice notice);

}
