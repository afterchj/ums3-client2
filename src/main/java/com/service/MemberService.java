package com.service;

import com.model.member.Member;
import com.tpadsz.exception.NotExecutedDbException;
import com.web.vo.LoginVo;
import com.web.vo.MemberVo;

public interface MemberService {

	LoginVo generate(String params);

	LoginVo login(LoginVo member);

	Member generateByLoginName(String loginName, String type);

	boolean update(LoginVo paramObject);

	MemberVo getById(String memberId);

	Long searchMemberVisitors(String memberId);

	Member generateAppUser(Member member);

	void update(Member member) throws NotExecutedDbException;

	void updateMemberByFillingUid();

}
