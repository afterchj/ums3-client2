package com.uicdao;

import com.model.ThirdLogin;
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

}