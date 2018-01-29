package com.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.dao.FileStoreInfoDao;
import com.model.FileStoreInfo;
import com.service.AppAdService;
import com.service.FileStoreInfoWithAdService;
import com.utils.DateUtil;
import com.web.vo.FrontThemeFileVo;

@Service("fileStoreInfoWithAdService")
public class FileStoreInfoServiceWithAdImpl implements FileStoreInfoWithAdService {

    private FileStoreInfoDao fileStoreInfoDao;
    private AppAdService appAdService;
    public static final long SIZE = 51;

    @Override
    @Cacheable(value = "fileStoreInfoWithAdService", key = "'getHottestPage' + #gender + '@' + #page + '@' + #offset")
    public List<FrontThemeFileVo> getHottestPage(String gender, Long page, Long offset) {
        String edate = com.utils.DateUtil.format(new Date(), "yyyy-MM-dd");
        String sdate = DateUtil.format(
                com.utils.DateUtil.getPerWeekDate(new Date()), "yyyy-MM-dd");
//        System.out.println("sdate:" + sdate + ",edate:" + edate + ",page:" + page);
        //查询数据库中最热的主题
        List<FileStoreInfo> fileStoreInfos = fileStoreInfoDao
                .getByHottestWithPage(sdate, edate, (page - 1) * SIZE, SIZE);
        //转换为前端显示的vo对象
        List<FrontThemeFileVo> themeFiles = convertSimplifyFront(fileStoreInfos);
        return mixThemeFilesWithAppAds(themeFiles, (page - 1) * SIZE, SIZE, offset, "hottest");
    }

    @Override
    public List<FrontThemeFileVo> getNewestPage(Long page, Long offset) {
        List<FileStoreInfo> fileStoreInfos = fileStoreInfoDao
                .getNewestInShelf((page - 1) * SIZE, SIZE);
        List<FrontThemeFileVo> themeFiles = convertSimplifyFront(fileStoreInfos);
        return mixThemeFilesWithAppAds(themeFiles, (page - 1) * SIZE, SIZE, offset, "newest");
    }


    private List<FrontThemeFileVo> mixThemeFilesWithAppAds(List<FrontThemeFileVo> themeFiles, Long startIndex, Long pageSize, Long offset, String type) {
        return appAdService.mixThemeFilesWithAppAds(themeFiles, startIndex, SIZE, type, offset);
    }


    @Autowired
    public void setFileStoreInfoDao(FileStoreInfoDao fileStoreInfoDao) {
        this.fileStoreInfoDao = fileStoreInfoDao;
    }

    private List<FrontThemeFileVo> convertSimplifyFront(
            List<FileStoreInfo> fileStoreInfos) {
        return FrontThemeFileVo.convertSimplifyFront(fileStoreInfos);
    }

    @Autowired
    public void setAppAdService(AppAdService appAdService) {
        this.appAdService = appAdService;
    }


}
