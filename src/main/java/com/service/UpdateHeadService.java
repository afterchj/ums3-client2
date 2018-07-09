package com.service;


/**
 * Created by hongjian.chen on 2018/7/5.
 */
public interface UpdateHeadService {
    void updateHead(String uid, String path);

    String getUrl(String uid);
}
