package com.service.impl;

import com.dao.DLogCountStoreDao;
import com.service.LogCountStoreService;
import com.tpadsz.exception.NotExecutedDbException;
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
    @Override
    public void updateTotalDown(String themeName, String logDate) {
        try {
            dLogCountStoreDao.updateTotalDown(themeName, logDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新主题访问量
     *
     * @param themeName
     * @param logDate
     */
    @Override
    public void updateTotalVisit(String themeName, String logDate) {
        try {
            dLogCountStoreDao.updateTotalVisit(themeName, logDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //每天22点将主题数据刷新数据进入log_count_store表
    public void refreshIntoLogCountStore() {
        try {
            dLogCountStoreDao.refreshIntoLogCountStore();
//            System.out.println("新增数据成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
