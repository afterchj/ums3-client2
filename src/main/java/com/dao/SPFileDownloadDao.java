package com.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by yuanjie.fang on 2018/1/11.
 */
@Repository("spFileDownloadDao")
public interface SPFileDownloadDao {

    //每天23点将视频id刷入sp_file_download数据库的表中
    void refreshIntoSpFileDownload();

    //更新下载量
    void updateTotalDownload(@Param("fileId") Integer fileId, @Param("logDate") String logDate);

}
