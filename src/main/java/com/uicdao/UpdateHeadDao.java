package com.uicdao;


import org.apache.ibatis.annotations.Param;

/**
 * Created by hongjian.chen on 2018/7/5.
 */
public interface UpdateHeadDao {
    void updateHead(@Param("id") String id, @Param("icon") String icon);

    String getUrl(String id);
}
