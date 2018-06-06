package com.service;

import com.model.SPFile;
import java.util.List;

/**
 * Created by yuanjie.fang on 2018/1/11.
 */
public interface SPFileService {
    //通过id查询视频文件详情
    SPFile findById(Integer fileId);

    //展示热门必看
    List<SPFile> getHottestPage(Integer page);

    //展示新作力荐->更多
    List<SPFile> getNewestPage(Integer page);

    //展示新作力荐(后台配置6个)
    List<SPFile> getRecommendFiles();
}
