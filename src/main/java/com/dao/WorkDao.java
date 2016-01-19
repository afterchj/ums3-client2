package com.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.model.member.Work;

public interface WorkDao {

	List<Work> getAllWithPage(@Param("startIndex")Long startIndex, @Param("pageSize")Long pageSize);

	List<Work> getAllByUserWithPage(@Param("startIndex")Long startIndex, @Param("pageSize")Long pageSize);
	
	List<Work> getByMemberWithPage(@Param("memberId")String memberId, @Param("startIndex")Long startIndex, @Param("pageSize")Long pageSize);

	void save(Work work);

	List<Work> getByLoginNameWithPage(@Param("loginName")String loginName, @Param("type") String type, @Param("startIndex")Long startIndex, @Param("pageSize")Long pageSize);

	List<Work> getByUserWithPage(@Param("uid")String userId, @Param("startIndex")Long startIndex, @Param("pageSize")Long pageSize);

	List<Work> getByMemberWithPageWithoutUid(@Param("memberId") String memberId, @Param("startIndex")Long startIndex, @Param("pageSize")Long pageSize);

	void updateUid(@Param("mid")String mid, @Param("uid")String uid);


}
