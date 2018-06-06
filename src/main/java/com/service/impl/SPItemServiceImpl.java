package com.service.impl;

import com.dao.SPItemDao;
import com.model.SPFile;
import com.model.SPItem;
import com.service.SPItemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by hongjian.chen on 2018/1/11.
 */
@Service
public class SPItemServiceImpl implements SPItemService {

    @Resource
    SPItemDao spItemDao;

    @Override
    public List<SPItem> getSubTypesById(int id) {
        return spItemDao.getSubTypesById(id);
    }

    @Override
    public List<SPItem> getSubTypes() {
        return spItemDao.getSubTypes();
    }

    @Override
    public List<SPItem> getParentTypes() {
        return spItemDao.getParentTypes();
    }

    @Override
    public List<SPFile> getInfo(Integer id) throws Exception {
        return spItemDao.subTypeInfo(id);
    }

    @Override
    public List<SPItem> getNewId(String id) {
        return spItemDao.getNewId(id);
    }

}
