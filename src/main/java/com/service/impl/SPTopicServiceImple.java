package com.service.impl;

import com.dao.SPFileDao;
import com.dao.SPTopicDao;
import com.model.SPFile;
import com.model.SPFileTopic;
import com.model.SPTopic;
import com.service.SPTopicService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static jxl.biff.FormatRecord.logger;

/**
 * Created by nannan.li on 2018/1/10.
 */

@Service("spTopicService")
public class SPTopicServiceImple implements SPTopicService {
    @Autowired
    private SPTopicDao spTopicDao;

    @Autowired
    private SPFileDao spFileDao;


    @Override
    public List<SPTopic> showTopics() {
        List<SPTopic> spTopics = new ArrayList<>();
        try {
            spTopics = spTopicDao.showTopics();
        } catch (Exception e) {
            logger.error("bean:spTopicService, method:showTopics", e);
        }
        return spTopics;
    }

    @Override
    public List<SPFile> showFilesByTopicId(Integer topicId) {
        List<SPFile> spFiles = new ArrayList<>();
        try {
            List<SPFileTopic> spFileTopics = spTopicDao.showTopicFilesByTopicId(topicId);
            for (SPFileTopic spFileTopic : spFileTopics) {
//                SPFile spFile = spFileDao.findById(spFileTopic.getFileId().intValue());
                SPFile spFile = spFileDao.findPartInfoById(spFileTopic.getFileId().intValue());//获取视频文件部分信息
                spFiles.add(spFile);
            }
        } catch (Exception e) {
            logger.error("bean:spTopicService, method:showFilesByTopicId", e);
        }
        return spFiles;
    }
}
