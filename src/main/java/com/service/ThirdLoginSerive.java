package com.service;

import com.model.ThirdLogin;

import java.util.Map;

/**
 * Created by nannan.li on 2018/7/11.
 */
public interface ThirdLoginSerive {

    com.tpadsz.uic.user.api.vo.AppUser getUserInfoById(ThirdLogin thirdLogin);

    void updateTpadUser(Map<String, Object> map);

    void updateAppUser(Map<String, Object> map);

    String getTpadIdByUid(String map);

    Map<String,Object> getAppUserByUid(String uid);

    Map<String,Object> findByMobile(String mobile);

    Map<String,Object> findByTpad(String id);
}
