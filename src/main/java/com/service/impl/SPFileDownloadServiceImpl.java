package com.service.impl;

import com.dao.SPFileDownloadDao;
import com.service.SPFileDownloadService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by yuanjie.fang on 2018/1/11.
 */
@Service("spFileDownloadService")
public class SPFileDownloadServiceImpl implements SPFileDownloadService {
    @Resource(name = "spFileDownloadDao")
    private SPFileDownloadDao spFileDownloadDao;

    @Override
    public void updateTotalDown(Integer fileId, String logDate) {
        try {
            spFileDownloadDao.updateTotalDownload(fileId, logDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void refreshIntoSpFileDownload() {
        try {
            spFileDownloadDao.refreshIntoSpFileDownload();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
