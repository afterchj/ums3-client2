package com.service;

/**
 * Created by yuanjie.fang on 2018/1/11.
 * 视频文件下载量
 */
public interface SPFileDownloadService {
    //更新主题下载量
    void updateTotalDown(Integer fileId, String logDate);
}
