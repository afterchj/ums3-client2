package com.service.impl;

import com.dao.SPFileDao;
import com.model.SPFile;
import com.service.SPFileService;
import com.utils.DateUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import static jxl.biff.FormatRecord.logger;

/**
 * Created by yuanjie.fang on 2018/1/11.
 */
@Service("spFileService")
public class SPFileServiceImpl implements SPFileService {
    public static final int SIZE = 12;
    @Resource(name = "spFileDao")
    private SPFileDao spFileDao;

    @Override
    public SPFile findById(Integer fileId) {
        try {
            return spFileDao.findById(fileId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<SPFile> getHottestPage(Integer page) {
        try {
            String edate = DateUtil.format(new Date(), "yyyy-MM-dd");
            String lastOneWeek = DateUtil.format(
                    DateUtil.getPerWeekDate(new Date()), "yyyy-MM-dd");
            String sdate = DateUtil.format(
                    DateUtil.getPerWeekDate(DateUtil.parse(lastOneWeek, "yyyy-MM-dd")), "yyyy-MM-dd");
//            System.out.println("sdate:" + sdate + ",edate:" + edate);
            return spFileDao.getHottestPage(sdate, edate, (page - 1) * SIZE, SIZE);
        } catch (ParseException e) {
//            e.printStackTrace();
            logger.error("bean:spFileService, method:getHottestPage", e);
        }
        return null;
    }

    @Override
    public List<SPFile> getNewestPage(Integer page) {
        try {
            String edate = DateUtil.format(new Date(), "yyyy-MM-dd");
            String lastOneWeek = DateUtil.format(DateUtil.getPerWeekDate(new Date()), "yyyy-MM-dd");
            String sdate = DateUtil.format(DateUtil.getPerWeekDate(DateUtil.parse(lastOneWeek, "yyyy-MM-dd")), "yyyy-MM-dd");
            return spFileDao.getNewestPage(sdate, edate, (page - 1) * SIZE, SIZE);
        } catch (ParseException e) {
            logger.error("bean:spFileService, method:getNewestPage", e);
        }
        return null;
    }

    @Override
    public List<SPFile> getRecommendFiles() {
        try {
            String edate = DateUtil.format(new Date(), "yyyy-MM-dd");
            String lastOneWeek = DateUtil.format(DateUtil.getPerWeekDate(new Date()), "yyyy-MM-dd");
            String sdate = DateUtil.format(DateUtil.getPerWeekDate(DateUtil.parse(lastOneWeek, "yyyy-MM-dd")), "yyyy-MM-dd");
            return spFileDao.getRecommendFiles(sdate, edate);
        } catch (ParseException e) {
            logger.error("bean:spFileService, method:getRecommendFiles", e);
        }
        return null;
    }
}
