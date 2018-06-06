package com.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by yuanjie.fang on 2017/11/2.
 */
@Repository("dLogCountStoreDao")
public interface DLogCountStoreDao {

    //每天22点将主题名刷入log_count_store数据库的表中
    void refreshIntoLogCountStore();

    //更新下载量
    void updateTotalDown(@Param("themeName") String themeName, @Param("logDate") String logDate);

    //更新访问量
    void updateTotalVisit(@Param("themeName") String themeName, @Param("logDate") String logDate);
}
