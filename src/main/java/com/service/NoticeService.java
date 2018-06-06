package com.service;

import java.util.List;

import com.model.DNotice;
import com.tpadsz.ctc.vo.Present;
import com.tpadsz.exception.NotExecutedDbException;
import com.tpadsz.exception.NotFoundException;
import com.tpadsz.exception.SystemAlgorithmException;
import com.tpadsz.uic.user.api.vo.AppUser;

public interface NoticeService {

	List<DNotice> getNoticesByUser(String uid, int pageNo, int pageSize) throws NotExecutedDbException;

	DNotice getNoticeById(String nid, String uid) throws NotFoundException, NotExecutedDbException;

	void commitInvitationNotice(AppUser user, Present present);

	void pushMessage(String name, String content, int type, String uid, int dtype) throws SystemAlgorithmException;

}
