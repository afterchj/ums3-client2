package com.dao;

import java.util.List;

import com.model.Topic;

public interface TopicDao {

	List<Topic> getAll();

	Topic get(Long id);

}
