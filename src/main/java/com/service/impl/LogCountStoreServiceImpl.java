package com.service.impl;

import com.dao.DLogCountStoreDao;
import com.service.LogCountStoreService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by yuanjie.fang on 2017/11/2.
 */
@Service("logCountStoreService")
public class LogCountStoreServiceImpl implements LogCountStoreService {
    @Resource(name = "dLogCountStoreDao")
    private DLogCountStoreDao dLogCountStoreDao;

    /**
     * 更新主题下载量
     *
     * @param themeName
     * @param logDate
     */
    public void updateTotalDown(String themeName, String logDate) {
        try {
            dLogCountStoreDao.updateTotalDown(themeName, logDate);
        } catch (Exception e) {
            System.err.print(e.getCause());
        }
    }

    /**
     * 更新主题访问量
     *
     * @param themeName
     * @param logDate
     */
    public void updateTotalVisit(String themeName, String logDate) {
        try {
            dLogCountStoreDao.updateTotalVisit(themeName, logDate);
        } catch (Exception e) {
            System.err.print(e.getCause());
        }
    }

//       每天22点将主题数据刷新数据进入log_count_store表
//    public void refreshIntoLogCountStore() {
//        try {
//            dLogCountStoreDao.refreshIntoLogCountStore();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
