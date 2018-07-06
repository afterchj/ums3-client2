package com.service.impl;

import com.dao.UpdateHeadDao;
import com.service.UpdateHeadService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by hongjian.chen on 2018/7/5.
 */

@Service
public class UpdateHeadServiceImpl implements UpdateHeadService {
    @Resource
    private UpdateHeadDao headDao;
    @Override
    public void updateHead(String id, String icon) {
        headDao.updateHead(id,icon);
    }
}
