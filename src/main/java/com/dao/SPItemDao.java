package com.dao;

import com.model.SPFile;
import com.model.SPItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by hongjian.chen on 2018/1/11.
 */
public interface SPItemDao {

    List<SPItem> getSubTypesById(int id);

    List<SPItem> getSubTypes();

    List<SPItem> getParentTypes();

    List<SPFile> subTypeInfo(@Param(value = "id") Integer id) throws Exception;

    List<SPItem> getNewId(@Param(value = "id") String id);
}
