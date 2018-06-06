package com.dao;

import com.model.SPFileTopic;
import com.model.SPTopic;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SPTopicDao {

    List<SPTopic> showTopics();//展示所有专题

    List<SPFileTopic> showTopicFilesByTopicId(@Param("topicId") Integer topicId);//根据专题id查询专题文件集合

}
