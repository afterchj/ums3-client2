package com.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.dao.NoticeDao;
import com.google.common.collect.Lists;
import com.model.DNotice;
import com.service.NoticeService;
import com.tpadsz.ctc.vo.Present;
import com.tpadsz.exception.NotExecutedDbException;
import com.tpadsz.exception.NotFoundException;
import com.tpadsz.exception.SystemAlgorithmException;
import com.tpadsz.uic.user.api.vo.AppUser;
import com.utils.Constants;
import com.utils.StringUtil;

@Service("noticeService")
public class NoticeServiceImpl implements NoticeService{
	
	private NoticeDao noticeDao;

	@Autowired
	public void setNoticeDao(NoticeDao noticeDao) {
		this.noticeDao = noticeDao;
	}

	@Override
	public List<DNotice> getNoticesByUser(String uid, int pageNo, int pageSize) throws NotExecutedDbException {
		List<DNotice> notices = Lists.newArrayList();
		try {
			notices = noticeDao.getSystemAndUserNotice(uid, (pageNo - 1) * pageSize, pageSize);
		} catch (Exception e) {
			throw new NotExecutedDbException("bean:noticeDao, method:getSystemAndUserNotice", e);
		}
		return notices;
	}

	@Override
	public DNotice getNoticeById(String nid, String uid) throws NotFoundException, NotExecutedDbException {
		DNotice notice;
		try {
			notice = noticeDao.getById(nid);
		} catch (Exception e) {
			throw new NotExecutedDbException("bean:noticeDao, method:getById", e);
		}
		if(notice == null){
			throw new NotFoundException();
		}
		int type = notice.getType();
		if((type == Constants.NOTICE_TYPE_RECOMMEND) && !StringUtils.equals(notice.getUid(), uid)){
			throw new NotFoundException();
		}
		return notice;
	}

	@Override
	public void commitInvitationNotice(AppUser user, Present present) {
//		String content = String.format(Constants.NOTICE_INVITATION_PRIZE, present.getGain());
//		DNotice notice = new DNotice();
//		notice.setDetail(content);
//		notice.setDtype(Constants.NOTICE_TYPE_RECOMMEND);
	}

	@Override
	public void pushMessage(String name, String content, int type, String uid, int dtype) throws SystemAlgorithmException {
		try {
			DNotice notice = new DNotice();
			notice.setName(name);
			notice.setDetail(content);
			notice.setDtype(dtype);
			notice.setType(type);
			notice.setUid(uid);
//			save(notice);
		} catch (Exception e) {
			throw new SystemAlgorithmException("bean:noticeService, method:pushMessage", e);
		}
	}

	private void save(DNotice notice) throws NotExecutedDbException {
		try {
			notice.setId(StringUtil.getUUID());
			notice.setPublishDate(new Date());
			noticeDao.save(notice);
		} catch (Exception e) {
			throw new NotExecutedDbException("bean:noticeDao, method:save", e);
		}
	}	

}
