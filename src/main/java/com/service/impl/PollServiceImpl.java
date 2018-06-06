package com.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.dao.PollDao;
import com.mapper.BeanMapper;
import com.model.Poll;
import com.service.PollService;
import com.utils.convert.Convert;
import com.web.vo.PollForm;

@Service("pollService")
public class PollServiceImpl implements PollService{

	private PollDao pollDao;

	@Autowired
	public void setPollDao(PollDao pollDao) {
		this.pollDao = pollDao;
	}

	@Override
	@Cacheable(value = "pollService", key = "'getAll'")
	public List<PollForm> getAll() {
		List<Poll> polls = pollDao.getAll();
		return convertStandard(polls);
	}

	private List<PollForm> convertStandard(List<Poll> polls) {
		return BeanMapper.mapList(polls, new Convert<PollForm>() {

			@Override
			public void convert(Object source, PollForm destinationObject) {
				destinationObject.setImageDownUrl(com.utils.Constants.STATIC_SHOW + "/" + destinationObject.getImageDownUrl());
			}
		});
	}
}
