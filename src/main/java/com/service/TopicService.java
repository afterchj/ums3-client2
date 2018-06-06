package com.service;

import java.util.List;

import com.web.vo.TopicVo;

public interface TopicService {

	List<TopicVo> getAllTopics();

	TopicVo getTopic(Long id);

}
