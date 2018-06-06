package com.service.impl;

import java.util.List;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.dao.TopicDao;
import com.mapper.BeanMapper;
import com.model.Topic;
import com.service.TopicService;
import com.utils.Constants;
import com.utils.DateUtil;
import com.utils.convert.Convert;
import com.web.vo.TopicVo;

@Service("topicService")
public class TopicServiceImpl implements TopicService{
	
	private TopicDao topicDao;

	@Autowired
	public void setTopicDao(TopicDao topicDao) {
		this.topicDao = topicDao;
	}

	@Override
	@Cacheable(value="topicService",key="'getAllTopics'")
	public List<TopicVo> getAllTopics() {
		List<Topic> topics = topicDao.getAll();
		return convert(topics);
	}

	private List<TopicVo> convert(List<Topic> topics) {
		return BeanMapper.mapList(topics, new Convert<TopicVo>() {

			@Override
			public void convert(Object source, TopicVo destinationObject) {
				Topic topic = (Topic)source;
				destinationObject.setIcon(Constants.STATIC_SHOW + "/files/" + topic.getIcon());
				if(topic.getEditDate() != null){
					destinationObject.setEditDate(DateUtil.format(topic.getEditDate(), "yyyy-MM-dd"));
				}
			}
		});
	}

	@Override
	@Cacheable(value="topicService",key="'getTopic' + #id")
	public TopicVo getTopic(Long id) {
		Topic topic = topicDao.get(id);
		Validate.notNull(topic, "cannot find the topic of %d", id);
		TopicVo vo = BeanMapper.map(topic, TopicVo.class);
		vo.setIcon(Constants.STATIC_SHOW + "/files/" + topic.getIcon());
		return vo;
	}
}
