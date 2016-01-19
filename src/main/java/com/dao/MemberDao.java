package com.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.model.member.Member;

public interface MemberDao {

	Member getByLoginName(@Param("loginName") String loginName, @Param("type")String type);

	void save(Member member);

	boolean update(Member member);

	Member getById(String id);

	Long getTotalCountWithoutUid();

	List<Member> getAllWithPageWithoutUid(@Param("startIndex")Long startIndex, @Param("pageSize")Long pageSize);

	boolean updateUid(Member member);

}
