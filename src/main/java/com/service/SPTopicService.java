package com.service;

import com.model.SPFile;
import com.model.SPTopic;

import java.util.List;

public interface SPTopicService {

    List<SPTopic> showTopics();//展示所有专题

    List<SPFile> showFilesByTopicId(Integer topicId);//根据topicId展示专题下的所有视频主题

}
