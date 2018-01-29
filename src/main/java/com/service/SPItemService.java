package com.service;

import com.model.SPFile;
import com.model.SPItem;

import java.util.List;

/**
 * Created by hongjian.chen on 2018/1/11.
 */
public interface SPItemService {
    List<SPItem> getSubTypesById(int id);

    List<SPItem> getSubTypes();

    List<SPItem> getParentTypes();

    List<SPFile> getInfo(Integer id) throws Exception;
}
