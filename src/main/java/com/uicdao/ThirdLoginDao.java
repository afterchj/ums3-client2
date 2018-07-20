package com.uicdao;

import com.model.DAppUser;
import com.model.DTpadUser;
import com.model.ThirdLogin;
import com.tpadsz.exception.NotExecutedDbException;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * Created by nannan.li on 2018/7/11.
 */
public interface ThirdLoginDao {

    Map<String, Object> getUserInfoById(@Param("uid") String uid);

    ThirdLogin getUserThidInfoById(String uid);

    void updateUserQQ(ThirdLogin third);

    void updateUserWx(ThirdLogin third);

    void saveUserThirdInfo(ThirdLogin third);

    void updateTpadUser(Map<String, Object> map);

    void updateAppUser(Map<String, Object> map);


    String getTpadIdByUid(String map) throws Exception;

    Map<String,Object> getAppUserByUid(String uid);

    Map<String,Object> findByMobile(String mobile);

    Map<String,Object> findByTpad(String tpadId);

    DAppUser getById(String uid);

    void update(DTpadUser tpadUser) throws NotExecutedDbException;
}
