package com.dao;

import com.model.SPFile;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by yuanjie.fang on 2018/1/11.
 */
@Repository("spFileDao")
public interface SPFileDao {
    //展示视频详情
    SPFile findById(@Param("fileId") Integer fileId);

    //展示热门必看
    List<SPFile> getHottestPage(@Param("sdate") String sdate, @Param("edate") String edate, @Param("startIndex") Integer startIndex, @Param("pageSize") Integer pageSize);

    //展示新作力荐-》更多
    List<SPFile> getNewestPage(@Param("sdate") String sdate, @Param("edate") String edate, @Param("startIndex") Integer startIndex, @Param("pageSize") Integer pageSize);

    //展示新作力荐(后台默认配置6个)
    List<SPFile> getRecommendFiles(@Param("sdate") String sdate, @Param("edate") String edate);

    //获取视频部分信息
    SPFile findPartInfoById(@Param("fileId") Integer fileId);
}
